package com.small.studyingweb.controller;

import com.small.frame.config.CaptchaFactory;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 生成验证码
 * @author Liang
 */
@Controller
public class CaptchaController {
    /**
     * 验证码宽度
      */
    @Value("${captcha.width}")
    private int captchaWidth;
    /**
     * 验证码高度
     */
    @Value("${captcha.high}")
    private int captchaHigh;
    /**
     * 验证码中内容个数
     */
    @Value("${captcha.length}")
    private int length;
    /**
     * 验证码类型
     */
    @Value("${captcha.type}")
    private int type;

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, HttpServletRequest request){
        try {
            Captcha captcha = CaptchaFactory.captchaFactory(type,captchaWidth,captchaHigh);
            CaptchaUtil.out(captcha,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
