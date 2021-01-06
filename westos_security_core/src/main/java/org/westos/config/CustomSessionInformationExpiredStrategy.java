package org.westos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/4 17:01
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    MyAuthFailHandler myAuthFailHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        UserDetails principal = (UserDetails) event.getSessionInformation().getPrincipal();
        String username = principal.getUsername();
        AuthenticationException exception = new AuthenticationServiceException(String.format("[%s]用户在另外一台电脑登录，您已被下线", username));
        try {
            event.getRequest().setAttribute("toAuthentication", true);
            myAuthFailHandler.onAuthenticationFailure(event.getRequest(), event.getResponse(), exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
