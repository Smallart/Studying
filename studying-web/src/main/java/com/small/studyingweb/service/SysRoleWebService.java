package com.small.studyingweb.service;

import com.small.common.base.enitity.SysUser;
import com.small.system.domain.SysMenu;
import com.small.common.base.enitity.SysRole;
import com.small.system.domain.SysUserRole;
import com.small.system.query.SysRoleQuery;
import com.small.system.query.SysUserQuery;

import java.util.List;
import java.util.Map;

/**
 * SysRole web层service
 * @author Liang
 */
public interface SysRoleWebService {
    /**
     * SysRole table数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysRoleQuery query);

    /**
     * 通过userId查询相关角色
     * @param userId
     * @return
     */
    List<SysRole> tagRoles(Long userId);

    /**
     * 分配角色的面板
     * @param userId
     * @return
     */
    Map<String,Object> assignRoleTable(Long userId);

    /**
     * 通过roleId查询SysRole
     * @param roleId
     * @return
     */
    SysRole findRoleById(Long roleId);

    /**
     * 通过RoleId查询绑定在该角色上的所有用户
     * @param query
     * @return
     */
    List<SysUser> findBindUserByRoleId(SysUserQuery query);

    /**
     * 判断注册的角色名称是否唯一
     * @param sysRole
     * @return
     */
    boolean checkRoleNameUnique(SysRole sysRole);

    /**
     * 判断注册的角色权限字符是否唯一
     * @param sysRole
     * @return
     */
    boolean checkRoleKeyUnique(SysRole sysRole);

    /**
     * 保存
     * @param sysRole
     * @return
     */
    boolean save(SysRole sysRole);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    boolean delete(String ids);

    /**
     * 标记MenuTree
     * @param roleId
     * @return
     */
    List<SysMenu> tagMenusTree(Long roleId);

    /**
     * 修改角色
     * @return
     */
    boolean update(SysRole role);

    /**
     *
     * @return
     */
    boolean updateDataSCope(SysRole role);

    /**
     * 为角色添加用户
     * @param roleId
     * @param userIds
     * @return
     */
    boolean assignUser(Long roleId,String userIds);

    /**
     * 取消用户角色
     * @param sysUserRole
     * @return
     */
    boolean cancelAuthUser(SysUserRole sysUserRole);

    /**
     * 批量取消用户授权
     * @param roleId
     * @param userIds
     * @return
     */
    boolean cancelAuthAllUser(Long roleId, String userIds);

    /**
     * 改变角色状态
     * @param sysRole
     * @return
     */
    boolean changeStatus(SysRole sysRole);
}
