package com.small.frame.shiro.service;

import com.small.common.base.enitity.SysUser;
import com.small.common.constant.Constants;
import com.small.common.constant.ShiroConstants;
import com.small.common.constant.UserConstants;
import com.small.common.enums.UserStatus;
import com.small.common.exceptions.user.*;
import com.small.common.utils.ServletUtils;
import com.small.frame.manager.AsyncManager;
import com.small.frame.manager.facotry.AsyncFactory;
import com.small.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录操作
 * @author Liang
 */
@Component
public class SysLoginService {

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysUserService userService;

    public SysUser login(String userName,String password)throws UserException {
        // 验证码校验
        if (ShiroConstants.CAPTCHA_ERROR.getMsg().equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA.getMsg()))){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new CaptchaException();
        }
        // 账号或密码为空
        if (!StringUtils.hasLength(userName)||!StringUtils.hasLength(password)){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserNotExistsException();
        }
        // 用户名格式不匹配
        if (userName.length()> UserConstants.USERNAME_MAX_LENGTH||userName.length()<UserConstants.USERNAME_MIN_LENGTH){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserPasswordNotMatchException();
        }
        // 密码格式不匹配
        if (password.length()> UserConstants.PASSWORD_MAX_LENGTH||password.length()<UserConstants.PASSWORD_MIN_LENGTH){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserPasswordNotMatchException();
        }
        // 查询用户
        SysUser sysUser = userService.findUserByLoginName(userName);
        if (sysUser==null){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserNotExistsException();
        }
        // 判断用户是否是删除标志
        if (UserStatus.DELETED.getCode().equals(sysUser.getStatus())){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserDeleteException();
        }
        // 判断用户是否被禁用
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(userName, Constants.LOGIN_FAIL,""));
            throw new UserBlockedException();
        }
        passwordService.validate(sysUser,password);
        return sysUser;
    }
}
