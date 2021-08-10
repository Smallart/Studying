package com.small.common.exceptions.user;

public class CaptchaException extends UserException{

    public CaptchaException() {
        super("user.jcaptcha.error");
    }
}
