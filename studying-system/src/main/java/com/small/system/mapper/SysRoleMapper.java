package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.common.base.enitity.SysRole;
import com.small.system.query.SysRoleQuery;

import java.util.List;

/**
 * SysRoleMapper层
 * @author Liang
 */
public interface SysRoleMapper extends BaseDao<SysRole, SysRoleQuery> {
    /**
     * 通过userId查询相关角色
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 通过RoleId查询角色信息
     * @param roleId
     * @return
     */
    SysRole findRoleById(Long roleId);
    /**
     * 判断角色名是否唯一
     * @param sysRoleQuery
     * @return
     */
    SysRole checkRoleFieldUnique(SysRoleQuery sysRoleQuery);
}
