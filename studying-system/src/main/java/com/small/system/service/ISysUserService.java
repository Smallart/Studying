package com.small.system.service;

import com.small.common.base.enitity.SysUser;
import com.small.common.base.service.BaseService;
import com.small.system.query.SysUserQuery;

/**
 * SysUser service层
 * @author Liang
 */
public interface ISysUserService extends BaseService<SysUser, SysUserQuery> {
    /**
     * 通过登录名称查找用户
     * @param loginName
     * @return
     */
    SysUser findUserByLoginName(String loginName);
}
