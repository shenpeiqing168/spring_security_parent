package org.westos.web.provider;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import org.westos.provider.AuthorizeConfiggurerProvider;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 9:17
 */
@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfiggurerProvider {

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user").hasAuthority("sys:user")
                // 有 sys:role 权限的可以访问 get方式的/role
                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
                .antMatchers(HttpMethod.GET, "/permission")
                // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
                .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')");
    }
}
