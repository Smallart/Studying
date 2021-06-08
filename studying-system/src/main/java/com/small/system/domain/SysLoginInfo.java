package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 登录消息表
 * @author Liang
 */
@Data
public class SysLoginInfo extends BaseObject {
    /**
     * 访问ID
     */
    private Long infoId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统类型
     */
    private String os;

    /**
     * 状态
     */
    private String status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private Date loginTime;
}
