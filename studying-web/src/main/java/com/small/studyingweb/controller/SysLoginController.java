package com.small.studyingweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录
 * @author Liang
 */
@Controller
@Slf4j
public class SysLoginController {
    @GetMapping("/index")
    public String index(){
        log.info("进入Index页面");
        return "index";
    }

    @GetMapping("/clock")
    public String clock(){
        log.info("进入clock界面");
        return "clock";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
