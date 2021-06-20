package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.common.base.enitity.SysRole;
import com.small.system.query.SysRoleQuery;

import java.util.List;

/**
 * ISysRoleService service层
 * @author Liang
 */
public interface ISysRoleService extends BaseService<SysRole, SysRoleQuery> {
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
