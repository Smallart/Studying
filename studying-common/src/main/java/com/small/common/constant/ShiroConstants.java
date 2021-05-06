package com.small.common.constant;

/**
 * shiro常用常量
 * @author Liang
 */
public enum ShiroConstants {
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("captchaError"),
    CURRENT_CAPTCHA("captcha");

    private String msg;
    ShiroConstants(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
