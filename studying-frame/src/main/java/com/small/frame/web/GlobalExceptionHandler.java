package com.small.frame.web;

import com.small.common.exceptions.base.BaseException;
import com.small.common.exceptions.user.UserException;
import com.small.common.utils.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *  全局异常处理
 * @author Liang
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseResult handleEntityNotFound(BaseException ex){
        return ResponseResult.error(ex.getMessage());
    }
}
