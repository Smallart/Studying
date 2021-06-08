package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.common.base.enitity.SysUser;
import com.small.system.query.SysUserQuery;

/**
 * 系统用户Dao层
 */
public interface SysUserMapper extends BaseDao<SysUser, SysUserQuery> {
    /**
     * 通过登录名称查找用户
     * @param loginName
     * @return
     */
    SysUser findUserByLoginName(String loginName);
}
