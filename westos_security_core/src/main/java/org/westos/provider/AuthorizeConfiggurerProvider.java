package org.westos.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置统一接口, 所有模块授权配置类都实现此接口
 */
public interface AuthorizeConfiggurerProvider {

    /**
     *     * 参数为 authorizeRequests() 的返回值
     *     * @param conﬁg
     *    
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                           config);

}
