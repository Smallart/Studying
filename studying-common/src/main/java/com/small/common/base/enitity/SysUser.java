package com.small.common.base.enitity;
import com.small.common.base.BaseObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户类
 * @author Liang
 */
@Data
public class SysUser extends BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户类型 00 系统用户
     * 01 注册用户
     */
    private String userType;
    /**
     * 性别
     * 0女 1男
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 删除标志
     */
    private String delFlag;
    /**
     * 最后登录IP地址
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 密码最新登录时间
     */
    private Date pwdUpdateTime;
}
