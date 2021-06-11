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
     * 根据Ids删除用户
     * @param ids
     * @return
     */
    Integer batchDelete(List<Long> ids);
}
