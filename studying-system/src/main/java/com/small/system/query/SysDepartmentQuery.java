package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

/**
 * 部门Query实体
 * @author Liang
 */
@Data
public class SysDepartmentQuery extends BaseQuery {
    /**
     * 当前用户Id
     */
    private Long userId;
    /**
     * 当前父id
     */
    private Long parentId;

    /**
     * 组织名称
     */
    private String deptName;

    /**
     * 部门状态
     */
    private Integer deptStatus;

    /**
     * 部门Id
     */
    private Long deptId;
}
