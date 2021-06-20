package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.common.base.enitity.SysUser;
import com.small.system.query.SysUserQuery;

import java.util.List;

/**
 * 系统用户Dao层
 */
public interface SysUserMapper extends BaseDao<SysUser, SysUserQuery> {
    /**
     * 通过登录名称查找用户
     *
     * @param query
     * @return
     */
    SysUser CheckInputUnique(SysUserQuery query);

    /**
     * 通过RoleId查询绑定在该角色上的所有用户
     *
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
