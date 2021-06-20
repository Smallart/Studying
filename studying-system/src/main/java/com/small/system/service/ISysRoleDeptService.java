package com.small.system.service;

import com.small.system.domain.SysRoleDept;

import java.util.List;

/**
 * SysRoleDept service层
 * @author Liang
 */
public interface ISysRoleDeptService {
    /**
     * 通过roleIds集合批量删除之间的关系
     * @param roleId
     * @return
     */
    Integer batchDeleteByRoleId(List<Long> roleId);

    /**
     * 批量创建Role和部门之间的关系
     * @param sysRoleDepts
     * @return
     */
    Integer batchInsert(List<SysRoleDept> sysRoleDepts);
}
