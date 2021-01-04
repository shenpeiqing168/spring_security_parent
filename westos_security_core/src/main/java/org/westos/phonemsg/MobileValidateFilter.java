package org.westos.phonemsg;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.westos.exception.ImageCheckCodeException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
//校验图片验证码的Filter
// OncePerRequestFilter  （在// 所有请求前都被调用一次）
@Component("mobileValidateFilter")
public class MobileValidateFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //判断用户的登录请求
        if (httpServletRequest.getMethod().equalsIgnoreCase("post") && httpServletRequest.getRequestURI().equals("/mobile/form")) {
            try {
                validateCode(httpServletRequest);
            } catch (AuthenticationException e) {
                //如果校验验证码出现异常，把他交给异常处理器，来进行处理
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        //放行请求
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validateCode(HttpServletRequest httpServletRequest) {
        //获取用户输入的验证码
        String userCode = httpServletRequest.getParameter("code");
        //获取后台生成的验证码
        String checkCode = (String) httpServletRequest.getSession().getAttribute("phoneCode");
        if (StringUtils.isBlank(userCode)) {
            //验证码为空
            throw new ImageCheckCodeException("验证码不能为空");
        }
        if (!userCode.equalsIgnoreCase(checkCode)) {
            //验证码输入错误
            //验证码为空
            throw new ImageCheckCodeException("验证码输入错误");
        }
    }
}
