package org.westos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.westos.filter.CheckCodeImageFilter;
import org.westos.phonemsg.MobileAuthenticationConfig;
import org.westos.phonemsg.MobileValidateFilter;

import javax.sql.DataSource;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Configuration
@EnableWebSecurity
        //开启认证的过滤器链
class SpringSecuityConfig extends WebSecurityConfigurerAdapter {
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
                .antMatchers("/login/page", "/code/image", "/mobile/page", "/code/mobile").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepositoryImpl())
                .tokenValiditySeconds(60 * 60 * 24 * 1);


        http.apply(mobileAuthenticationConfig);

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
}
