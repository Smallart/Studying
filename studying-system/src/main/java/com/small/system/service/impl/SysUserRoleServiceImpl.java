package com.small.system.service.impl;

import com.small.system.domain.SysUserRole;
import com.small.system.mapper.SysUserRoleMapper;
import com.small.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * SysUserRole service实现层
 * @author Liang
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Integer batchInsert(List<SysUserRole> sysUserRoles) {
        return sysUserRoleMapper.batchInsert(sysUserRoles);
    }

    @Override
    public Integer batchDelete(List<Long> ids) {
        return sysUserRoleMapper.batchDelete(ids);
    }

    @Override
    public Integer batchDeleteBySysUserRoles(Map<String, Object> info) {
        return sysUserRoleMapper.batchDeleteBySysUserRoles(info);
    }
}
