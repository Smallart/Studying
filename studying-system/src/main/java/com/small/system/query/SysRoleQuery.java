package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 角色查询 query 实体
 * @author Liang
 */
@Data
public class SysRoleQuery extends BaseQuery {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限字符
     */
    private String roleKey;

    /**
     * 角色状态
     */
    private Integer status;

    /**
     * 创建起始时间
     */
    private Date createStartTime;

    /**
     * 创建结束时间
     */
    private Date createEndTime;
}
