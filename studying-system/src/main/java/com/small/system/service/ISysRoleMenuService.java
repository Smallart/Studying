package com.small.system.service;

import com.small.system.domain.SysRoleMenu;

import java.util.List;

/**
 * SysRoleMenu service层
 * @author Liang
 */
public interface ISysRoleMenuService {
    /**
     * 批量存储
     * @param sysRoleMenus
     * @return
     */
    Integer batchSave(List<SysRoleMenu> sysRoleMenus);

    /**
     * 通过Roleids批量删除角色与菜单之间的关系
     * @param roleIds
     * @return
     */
    Integer batchDeleteByRoleId(List<Long> roleIds);


    /**
     * 查询通过RoleId查询所有相关菜单信息
     * @param roleId
     * @return
     */
    List<SysRoleMenu> findRoleMenuByRoleId(Long roleId);
}
