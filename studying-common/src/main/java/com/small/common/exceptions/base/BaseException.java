package com.small.common.exceptions.base;

/**
 * 自定义异常基类
 * @author Liang
 */
public class BaseException extends RuntimeException{
    /**
     * 模块
     */
    private String module;
    /**
     * 异常代码，用于国际化消息
     */
    private String code;

    public BaseException(String module,String code){
        this.module = module;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.code;
    }
}
