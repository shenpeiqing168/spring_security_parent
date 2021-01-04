package org.westos.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.westos.phonemsg.SmsSend;
import org.westos.utils.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Controller
public class MobileLoginController {
    @Autowired
    private SmsSend smsSend;

    //跳转到手机登录页面
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile"; // templates/login-mobile.html
    }

    //发送验证码
    @RequestMapping("/code/mobile")
    @ResponseBody
    public ResponseResult sendPhoneCheckCode(HttpServletRequest request) {
        //生成随机验证码
        String code = RandomStringUtils.randomNumeric(4);
        System.out.println(code);
        //把验证码放到session中
        request.getSession().setAttribute("phoneCode", code);
        //获取手机号
        String phoneNumber = request.getParameter("mobile");

        smsSend.sendSms(phoneNumber, code);

        return ResponseResult.ok();
    }
}
