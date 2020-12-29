package org.westos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: ShenMouMou
 * @Company:西部开源教育科技有限公司
 * @Description:简简单单，只为教育。
 */
@Controller
public class MainController {
    @RequestMapping({"/index", "/", ""})
    public String main() {
        System.out.println("跳转到主页");
        return "index";
    }
}
