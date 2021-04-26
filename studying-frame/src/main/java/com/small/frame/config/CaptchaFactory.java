package com.small.frame.config;

import com.small.common.constant.CaptchaEnum;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;

/**
 * 验证码工厂
 * @author Liang
 */
public class CaptchaFactory {
    public static Captcha captchaFactory(int index,int width,int high){
        Captcha captcha = null;
        switch (CaptchaEnum.getValue(index)){
            case CHINESECAPTCHA:
                captcha = new ChineseCaptcha(width,high);break;
            case CHINESEGIFCAPTCHA:
                captcha = new ChineseGifCaptcha(width,high);break;
            case ARITHMETICCAPTCHA:
                captcha = new ArithmeticCaptcha(width,high);break;
            case GIFCAPTCHA:
            default:
                captcha = new GifCaptcha(width,high);break;
        }
        return captcha;
    }
}
