package com.small.common.exceptions.user;

/**
 * 用户锁定异常类
 * @author ruoyi
 */
public class UserBlockedException extends UserException{

    public UserBlockedException() {
        super("user.blocked");
    }
}
