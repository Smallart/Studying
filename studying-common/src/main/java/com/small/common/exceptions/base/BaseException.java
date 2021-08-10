package com.small.common.exceptions.base;

import com.small.common.utils.MessageUtils;

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


    private Object[] args;

    public BaseException(String module,String code){
        this.module = module;
        this.code = code;
    }

    public BaseException(String module,String code,Object[] args){
        this(module,code);
        this.args = args;
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(this.code,this.args);
    }
}
