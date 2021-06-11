package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * SysUser 查询实体
 */
@Data
public class SysUserQuery extends BaseQuery {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer userStatus;
    /**
     * 创建时间
     */
    private Date createStartTime;
    /**
     * 创建时间
     */
    private Date createEndTime;
}
