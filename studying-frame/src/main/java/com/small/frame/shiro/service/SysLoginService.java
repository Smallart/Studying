package com.small.frame.shiro.service;

import com.small.common.constant.ShiroConstants;
import com.small.common.exceptions.user.CaptchaException;
import com.small.common.exceptions.user.UserException;
import com.small.common.utils.ServletUtils;
import org.springframework.stereotype.Component;

/**
 * 登录操作
 * @author Liang
 */
@Component
public class SysLoginService {
    public void login(String userName,String password)throws UserException {
        if (ShiroConstants.CAPTCHA_ERROR.getMsg().equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA.getMsg()))){
            throw new CaptchaException();
        }
    }
}
