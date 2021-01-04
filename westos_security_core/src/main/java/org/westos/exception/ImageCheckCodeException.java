package org.westos.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
//图片验证码校验异常类
public class ImageCheckCodeException extends AuthenticationException {
    public ImageCheckCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageCheckCodeException(String msg) {
        super(msg);
    }
}
