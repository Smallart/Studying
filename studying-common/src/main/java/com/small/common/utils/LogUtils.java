package com.small.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 处理并记录日志文件
 * @author ruoyi
 */
public class LogUtils {
    public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
    public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");

    /**
     * 记录访问日志[username][jsessionId][ip][accept][userAgent][url][params][Referer]
     * @param request
     */
    public static void logAccess(HttpServletRequest request) throws Exception {
        String loginName = ShiroUtils.getLoginName();
        String jsessionId = request.getRequestedSessionId();
        String ip = IpUtils.getIpAddr(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader("User-Agent");
        String url  = request.getRequestURI();
        String params = getParams(request);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(loginName));
        s.append(getBlock(jsessionId));
        s.append(getBlock(ip));
        s.append(getBlock(accept));
        s.append(getBlock(userAgent));
        s.append(getBlock(url));
        s.append(getBlock(params));
        s.append(getBlock(request.getHeader("Referer")));
        getAccessLog().info(s.toString());
    }

    /**
     * 记录异常错误 格式[exception]
     * @param message
     * @param e
     */
    public static void logError(String message,Throwable e){
        String userName = ShiroUtils.getLoginName();
        StringBuilder s = new StringBuilder();
        s.append(getBlock("exception"));
        s.append(getBlock(userName));
        s.append(getBlock(message));
        getErrorLog().error(s.toString(),e);
    }

    protected static String getParams(HttpServletRequest request) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return JSONUtils.marshal(parameterMap);
    }

    public static String getBlock(Object msg){
        if (msg==null){
            msg = "";
        }
        return "["+msg.toString()+"]";
    }

    public static Logger getAccessLog(){
        return ACCESS_LOG;
    }

    public static Logger getErrorLog(){
        return ERROR_LOG;
    }
}
