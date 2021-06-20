package com.small.system.domain;

import lombok.Data;

/**
 * 角色与部门之间的关系
 */
@Data
public class SysRoleDept {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 公司id
     */
    private Long deptId;
}
