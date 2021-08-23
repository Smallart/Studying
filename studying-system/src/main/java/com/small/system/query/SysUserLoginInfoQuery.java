package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 用户登录消息查询条件
 * @author Liang
 */
@Data
public class SysUserLoginInfoQuery extends BaseQuery {
    /**
     * ip地址
     */
    private String ipAddr;
    /**
     * 状态
     */
    private String status;
    /**
     * 登录名称
     */
    private String loginName;
    /**
     * 查询开始时间
     */
    private Date beginTime;
    /**
     * 查询结束时间
     */
    private Date endTime;
}
