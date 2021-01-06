package org.westos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.westos.filter.CheckCodeImageFilter;
import org.westos.phonemsg.MobileAuthenticationConfig;
import org.westos.phonemsg.MobileValidateFilter;
import org.westos.provider.AuthorizeConfigurerManager;

import javax.sql.DataSource;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//开启认证的过滤器链
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService customUserDetailsService;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    MobileAuthenticationConfig mobileAuthenticationConfig;
    @Autowired
    MobileValidateFilter mobileValidateFilter;
    @Autowired
    InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;
    @Autowired
    private AuthorizeConfigurerManager authorizeConfigurerManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //认证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //对密码进行加密
        //  String pwd = passwordEncoder().encode("123");
        //  auth.inMemoryAuthentication().withUser("lisi").password(pwd).authorities("admin");
        auth.userDetailsService(customUserDetailsService);
    }

    @Autowired
    private CheckCodeImageFilter checkCodeImageFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单登录的认证方式
        //设置校验图片验证码的过滤器
        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(checkCodeImageFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login/page") //设置自定义的登录页面
                .loginProcessingUrl("/login/form")//登录也表单提交的地址，默认是 /login
                .usernameParameter("username") // 登录表单，表单项的name属性值 默认是 username
                .passwordParameter("password") // 登录表单，表单项的name属性值 默认是 password
                //设置响应成功处理器，给前台响应JSON字符串
                .successHandler(authenticationSuccessHandler)
                //设置认证失败的处理器
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                //设置跳转登录页面的请求，不要拦截，图像验证码，不要拦截
                //.antMatchers("/login/page", "/code/image", "/mobile/page", "/code/mobile").permitAll()
                // 有 sys:user 权限的可以访问任意请求方式的/role
                // .antMatchers("/user").hasAuthority("sys:user")
                // // 有 sys:role 权限的可以访问 get方式的/role
                // .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
                // .antMatchers(HttpMethod.GET, "/permission")
                // // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
                // .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')")
                //.anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepositoryImpl())
                .tokenValiditySeconds(60 * 60 * 24 * 1)
                .and()
                //设置session超时，以json形式返回给前台
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                // 每个用户在系统中的最大session数
                .maximumSessions(1)
                // 当用户达到最大session数后，则调用此处的实现
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                // 当一个用户达到最大session数，则不允许后面进行登录
                //.maxSessionsPreventsLogin(true)
                //.sessionRegistry(sessionRegistry())
                //.and().and().logout()
                //.addLogoutHandler(customLogoutHandler)
                .and().and().logout()
                .logoutUrl("/logout") //退出请求路径，默认是logout 你可以自己定义
                .logoutSuccessUrl("/mobile/page")//退出成功后跳转到哪个页面
                .deleteCookies("JSESSIONID") //退出后，删除指定的cookie
        ;
        //退出时，采用get方式时，需要关闭csrf
        http.csrf().disable();
        //把手机验证码的配置添加到过滤链中
        http.apply(mobileAuthenticationConfig);
        // 权限相关配置管理者, 将所有授权配置管理起来了
        authorizeConfigurerManager.configure(http.authorizeRequests());
    }

    //放行静态资源
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }

    //记住我功能更
    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean("sessionRegistry")
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
