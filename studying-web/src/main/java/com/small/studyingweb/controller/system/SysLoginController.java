package com.small.studyingweb.controller.system;

import com.small.common.utils.ResponseResult;
import com.small.common.utils.ServletUtils;
import com.small.studyingweb.controller.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 * @author Liang
 */
@Controller
@Slf4j
@RequestMapping("/sysLogin")
public class SysLoginController extends BaseController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/clock")
    public String clock(){
        return "clock";
    }

    @GetMapping("/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response){
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(String userName,String password,Boolean rememberMe){
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password.toCharArray(),rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            String msg = "用户或密码错误";
            if (StringUtils.hasText(e.getMessage())){
                msg = e.getMessage();
            }
            return error(msg);
        }
        return success("登录成功");
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
