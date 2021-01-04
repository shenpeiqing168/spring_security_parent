package org.westos.core.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Controller
public class LoginPageController {
    @RequestMapping("/login/page")
    public String loginPage() {

        return "login";
    }

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 获取图形验证码
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 获取验证码字符串
        String code = defaultKaptcha.createText();
        Log logger = LogFactory.getLog(LoginPageController.class);
        logger.info("生成的图形验证码是：" + code);
        // 2. 字符串把它放到session中
        request.getSession().setAttribute("checkCode", code);
        // 3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        // 4. 将验证码图片把它写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

}
