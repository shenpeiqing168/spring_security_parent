package org.westos.config;


import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
//登录失败的异步处理
@Component
//public class MyAuthFailHandler implements AuthenticationFailureHandler {
public class MyAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
       /* ResponseResult result = ResponseResult.build(HttpStatus.UNAUTHORIZED.value(), "认证未通过");
        String s = result.toJsonString();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(s);*/
        //=====================================================================
        // setDefaultFailureUrl("/login/page?error");
        //获取上一次请求路径
        String referer = httpServletRequest.getHeader("Referer");
        logger.info("referer:" + referer);
        //截取？之前的路径
        String lastUrl = StringUtils.substringBefore(referer, "?");
        logger.info("上一次请求的路径 ：" + lastUrl);
        if (httpServletRequest.getParameter("toAuthentication") != null) {
            setDefaultFailureUrl("/login/page");
        } else {
            setDefaultFailureUrl(lastUrl + "?error");
        }

        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
