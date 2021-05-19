package com.small.common.exceptions.user;

/**
 * 用户账号已经删除
 * @author ruoyi
 */
public class UserDeleteException extends UserException{
    public UserDeleteException() {
        super("user.password.delete");
    }
}
