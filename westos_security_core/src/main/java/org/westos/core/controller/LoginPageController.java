package org.westos.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
