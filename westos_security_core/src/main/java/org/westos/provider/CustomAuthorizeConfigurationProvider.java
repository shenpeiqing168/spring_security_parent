package org.westos.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 9:11
 */
@Component
public class CustomAuthorizeConfigurationProvider implements AuthorizeConfiggurerProvider {
    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //设置跳转登录页面的请求，不要拦截，图像验证码，不要拦截
        config.antMatchers("/login/page", "/code/image", "/mobile/page", "/code/mobile", "/druid/*").permitAll();
    }
}
