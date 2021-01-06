package org.westos.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 9:21
 */
@Component
public class CustomAuthorizeConfigurationManager implements AuthorizeConfigurerManager {

    @Autowired
    List<AuthorizeConfiggurerProvider> authorizeConfigurerProviders;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfiggurerProvider provider : authorizeConfigurerProviders) {
            provider.configure(config);
        }
        //除了 AuthorizeConﬁgurerProvider 实现类中配置的,其他请求都需要身份认证
        config.anyRequest().authenticated();
    }
}
