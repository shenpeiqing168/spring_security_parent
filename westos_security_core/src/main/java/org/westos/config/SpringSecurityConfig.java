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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单登录的认证方式
        http.formLogin()
                .loginPage("/login/page") //设置自定义的登录页面
                .loginProcessingUrl("/login/form")//登录也表单提交的地址，默认是 /login
                .usernameParameter("username") // 登录表单，表单项的name属性值 默认是 username
                .passwordParameter("password") // 登录表单，表单项的name属性值 默认是 password
                .and()
                .authorizeRequests()
                //设置跳转登录页面的请求，不要拦截，
                .antMatchers("/login/page").permitAll()
                .anyRequest().authenticated();
    }
    //放行静态资源

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }
}
