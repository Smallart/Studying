package com.small.system.service;

import com.small.common.base.enitity.SysUser;
import com.small.common.base.service.BaseService;
import com.small.system.query.SysUserQuery;

import java.util.List;

/**
 * SysUser service层
 * @author Liang
 */
public interface ISysUserService extends BaseService<SysUser, SysUserQuery> {
    /**
     * 通过登录名称查找用户
     * @param query
     * @return
     */
    SysUser checkInputUnique(SysUserQuery query);

    /**
     * 通过RoleId查询绑定在该角色上的所有用户
     * @param query
     * @return
     */
    List<SysUser> findBindUserByRoleId(SysUserQuery query);

    /**
     * 查询没有绑定在该角色上的用户
     * @return
     */
    List<SysUser> findNotBindUserByRoleId(SysUserQuery query);

    /**
     * 没有绑定在该角色上的总个数
     * @param query
     * @return
     */
    Integer findNotBindUserCount(SysUserQuery query);
}
