package com.small.common.exceptions.user;

/**
 * 账号或密码格式不对
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends UserException{
    public UserPasswordNotMatchException() {
        super("user.PassswordOrAccount.not.match");
    }
}
