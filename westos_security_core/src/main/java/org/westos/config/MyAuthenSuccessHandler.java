package org.westos.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
//登录成功，返回JSON字符串
@Component
//public class MyAuthenSuccessHandler implements AuthenticationSuccessHandler {
public class MyAuthenSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // ResponseResult result = ResponseResult.ok("响应成功");
        // String s = result.toJsonString();
        // httpServletResponse.setContentType("text/json;charset=utf-8");
        // httpServletResponse.getWriter().write(s);
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
