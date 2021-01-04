package org.westos.phonemsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
public class SmsCodeSender implements SmsSend {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSms(String mobile, String content) {
        String sendContent = String.format("验证码%s，请勿泄露他人。", content);
        logger.info("向手机号" + mobile + "发送的短信为:" + sendContent);
        return true;
    }
}
