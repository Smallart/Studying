package com.small.system.mapper;

import com.small.system.domain.SysUserRole;

import java.util.List;

/**
 * SysUserRole dao层
 * @author Liang
 */
public interface SysUserRoleMapper {
    /**
     * 批量插入用户和角色的关系
     * @param sysUserRoles
     * @return
     */
    Integer batchInsert(List<SysUserRole> sysUserRoles);

    /**
     * 根据Ids批量删除
     * @param ids
     * @return
     */
    Integer batchDelete(List<Long> ids);
}
