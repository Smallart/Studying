package com.small.common.exceptions.user;

import com.small.common.exceptions.base.BaseException;

/**
 * 用户模块的异常基类
 * @author Liang
 */
public class UserException extends BaseException {
    public UserException(String code) {
        super("user", code);
    }
}
