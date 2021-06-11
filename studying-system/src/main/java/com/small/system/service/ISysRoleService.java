package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysRole;
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
    List<SysRole> findRoleById(Long userId);
}
