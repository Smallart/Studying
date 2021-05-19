package com.small.studyingweb.controller.common;

import com.small.common.utils.ResponseResult;

/**
 * web层通用数据处理
 * @author ruoyi
 */
public class BaseController {
    /**
     * 返回成功
     * @return
     */
    public ResponseResult success(){
        return ResponseResult.success();
    }

    /**
     * 返回失败消息
     * @return
     */
    public ResponseResult error(){
        return ResponseResult.error();
    }

    /**
     * 返回带成功消息的应答
     * @param message
     * @return
     */
    public ResponseResult success(String message){
        return ResponseResult.success(message);
    }

    /**
     * 返回带成功数据和消息的应答
     * @param message
     * @param data
     * @return
     */
    public ResponseResult success(String message,Object data){
        return ResponseResult.success(message,data);
    }

    public ResponseResult error(String msg){
        return ResponseResult.error(msg);
    }
}
