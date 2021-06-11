package com.small.common.exceptions;

public class BusinessException extends RuntimeException{
    private static final long serialVerisionUID = 1L;
    protected final String message;
    public BusinessException(String message){
        this.message = message;
    }
    public BusinessException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
