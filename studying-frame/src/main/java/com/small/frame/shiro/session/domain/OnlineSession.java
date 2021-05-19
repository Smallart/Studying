package com.small.frame.shiro.session.domain;

import lombok.Data;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * 自定义的session
 * @author Liang
 */
@Data
public class OnlineSession extends SimpleSession {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 头像
     */
    private String avater;
    /**
     * ip
     */
    private String host;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 当前用户是否是在线状态
     */
    private String status;
    /**
     * 记录session变化情况
     */
    private Boolean sessionChange;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 当session改变时做个记录，之后好同步到数据库中
     */
    public void sessionChanged(){
        this.sessionChange = true;
    }

    /**
     * 恢复session状态
     */
    public void resetSessionStatus(){
        this.sessionChange = false;
    }

}
