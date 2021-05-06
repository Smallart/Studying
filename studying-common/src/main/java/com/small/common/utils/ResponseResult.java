package com.small.common.utils;

import java.util.HashMap;

/**
 * 请求返回应答结果
 * @author Liang
 */
public class ResponseResult extends HashMap<String,Object> {
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    /**
     * 状态类型
     */
    public enum Type{
        /**
         * 成功
         */
        SUCCESS(0,"操作成功"),
        /**
         * 警告
         */
        WARN(301,"警告"),
        /**
         * 错误
         */
        ERROR(500,"操作失败");
        private final int value;
        private String tips;
        Type(int value,String tips) {
            this.value = value;
            this.tips = tips;
        }
        public int getValue(){
            return this.value;
        }
        public String getTips(){
            return this.tips;
        }
    }

    /**
     * 空消息
     */
    public ResponseResult(){

    }

    /**
     * 初始化创建一个新ResponseResult
     * @param type 状态码
     * @param msg 返回消息
     */
    public ResponseResult(Type type,String msg){
        this.put(CODE_TAG,type.getValue());
        this.put(MSG_TAG,msg);
    }

    /**
     * 初始化创建一个新ResponseResult
     * @param type 状态码
     * @param msg 返回消息
     * @param data 返回数据
     */
    public ResponseResult(Type type,String msg,Object data){
        this.put(CODE_TAG,type.getValue());
        this.put(MSG_TAG,msg);
        if (data!=null){
            this.put(DATA_TAG,data);
        }
    }

    /**
     * 返回成功结果
     * @param msg 消息
     * @param data 数据
     * @return
     */
    public static ResponseResult success(String msg,Object data){
        return new ResponseResult(Type.SUCCESS,msg,data);
    }

    /**
     * 返回成功结果
     * @param msg 返回消息
     * @return
     */
    public static ResponseResult success(String msg){
        return ResponseResult.success(msg,null);
    }

    /**
     * 返回成功结果
     * @param data 返回数据
     * @return
     */
    private static ResponseResult success(Object data) {
        return ResponseResult.success(Type.SUCCESS.getTips(),data);
    }

    /**
     * 返回成功结果
     * @return
     */
    public static ResponseResult success(){
        return ResponseResult.success(Type.SUCCESS.getTips());
    }

    /**
     * 返回警告结果
     * @param msg 返回消息
     * @param data 返回数据
     * @return
     */
    public static ResponseResult warn(String msg,Object data){
        return new ResponseResult(Type.WARN,msg,data);
    }

    /**
     * 返回警告结果
     * @param msg 返回消息
     * @return
     */
    public static ResponseResult warn(String msg){
        return ResponseResult.warn(msg,null);
    }

    /**
     * 返回错误结果
     * @param msg 返回消息
     * @param object 返回数据
     * @return
     */
    public static ResponseResult error(String msg,Object object){
        return new ResponseResult(Type.ERROR,msg,object);
    }

    /**
     * 返回错误结果
     * @param msg 返回消息
     * @return 返回数据
     */
    public static ResponseResult error(String msg){
        return ResponseResult.error(msg,null);
    }

    /**
     * 返货错误结果
     * @return
     */
    public static ResponseResult error(){
        return ResponseResult.error(Type.ERROR.getTips());
    }
}
