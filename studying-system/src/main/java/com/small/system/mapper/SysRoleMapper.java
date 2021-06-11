package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysRole;
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
    List<SysRole> findRoleById(Long userId);
}
