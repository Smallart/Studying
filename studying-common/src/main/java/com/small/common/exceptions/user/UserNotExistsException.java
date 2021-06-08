package com.small.common.exceptions.user;

/**
 * 用户存在异常
 * @author ruoyi
 */
public class UserNotExistsException extends UserException{
    public UserNotExistsException() {
        super("user.not.exists");
    }
}
