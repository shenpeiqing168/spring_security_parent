package org.westos.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.westos.utils.ResponseResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/4 16:25
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {


    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //清除缓存中的session
        //sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
        SessionRegistry sessionRegistry = (SessionRegistry) ApplicationContextUtils.getBean("sessionRegistry");
        sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
        // 将浏览器的sessionid清除，不关闭浏览器cookie不会被删除，一直请求都提示：Session失效
        cancelCookie(request, response);
        ResponseResult responseResult = ResponseResult.build(HttpStatus.UNAUTHORIZED.value(), "长时间为操作，请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(responseResult.toJsonString());

    }


    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }


    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
