package com.small.frame.web;

import com.small.common.exceptions.base.BaseException;
import com.small.common.exceptions.user.UserException;
import com.small.common.utils.PermissionUtils;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ServletUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e){
        if (ServletUtils.isAjaxRequest(request)){
            return ResponseResult.error(PermissionUtils.getMsg(e.getMessage()));
        }else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error/unauth");
            return modelAndView;
        }
    }
}
