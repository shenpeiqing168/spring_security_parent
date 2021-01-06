package org.westos.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.westos.phonemsg.SmsCodeSender;
import org.westos.phonemsg.SmsSend;

@Configuration
public class SecurityConfigBean {
    
    /**
     *    * @ConditionalOnMissingBean(SmsSend.class)  
     *    * 默认采用SmsCodeSender实例 ，但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
     *    
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

    /**
     *     * 注入Session失效策略实例
     *     * @return
     *    
     */


    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }
}
