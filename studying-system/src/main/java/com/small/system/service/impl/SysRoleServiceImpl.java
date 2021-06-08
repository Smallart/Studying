package com.small.system.service.impl;

import com.small.common.base.BaseDao;
import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysRole;
import com.small.system.mapper.SysRoleMapper;
import com.small.system.query.SysRoleQuery;
import com.small.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * SysRole service层实现
 * @author Liang
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleQuery, SysRoleMapper> implements ISysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @PostConstruct
    public void init(){
        setDao(roleMapper);
    }
}
