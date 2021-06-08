package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 在线用户实体
 * @author Liang
 */
@Data
public class SysUserOnline extends BaseObject {
    /**
     * SessionId
     */
    private String sessionId;

    /**
     * 用户名称
     */
    private String loginName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器种类
     */
    private String browser;

    /**
     * 系统种类
     */
    private String os;

    /**
     * 用户状态
     */
    private String status;

    /**
     * session创建时间
     */
    private Date startTimeStamp;

    /**
     * 最后一次访问时间
     */
    private Date lastAccessTime;

    /**
     * 过期时间
     */
    private long expireTime;
}
