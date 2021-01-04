package org.westos.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
