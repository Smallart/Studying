package com.small.studyingweb.controller;

import com.small.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录
 * @author Liang
 */
@Controller
@Slf4j
@RequestMapping("/sysLogin")
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
    public String doLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(String userName,String password,Boolean rememberMe){
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password.toCharArray());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return ResponseResult.success("登录成功");
    }

    @GetMapping("/backIndex")
    public String backIndex(){
        return "back/back_index";
    }

    @GetMapping("/backMain")
    public String backMain(){
        return "back/back_main";
    }
}
