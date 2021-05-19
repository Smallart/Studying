package com.small.common.exceptions.user;

/**
 * 用户错误最大次数异常类
 * @author ruoyi
 */
public class UserPasswordRetryLimitExceedException extends UserException{

    public UserPasswordRetryLimitExceedException() {
        super("user.password.retry.limit.exceed");
    }
}
