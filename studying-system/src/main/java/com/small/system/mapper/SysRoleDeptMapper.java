package com.small.system.mapper;

import com.small.system.domain.SysRoleDept;

import java.util.List;

/**
 * 角色与部门关系 Dao层
 * @author Liang
 */
public interface SysRoleDeptMapper {
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
