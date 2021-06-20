package com.small.system.service.impl;

import com.small.common.base.enitity.SysUser;
import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.mapper.SysUserMapper;
import com.small.system.query.SysUserQuery;
import com.small.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysUser service层实现
 * @author Liang
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserQuery, SysUserMapper> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @PostConstruct
    private void init(){
        setDao(userMapper);
    }

    @Override
    public SysUser checkInputUnique(SysUserQuery query) {
        return userMapper.CheckInputUnique(query);
    }

    @Override
    public List<SysUser> findBindUserByRoleId(SysUserQuery query) {
        return userMapper.findBindUserByRoleId(query);
    }

    @Override
    public List<SysUser> findNotBindUserByRoleId(SysUserQuery query) {
        return userMapper.findNotBindUserByRoleId(query);
    }

    @Override
    public Integer findNotBindUserCount(SysUserQuery query) {
        return userMapper.findNotBindUserCount(query);
    }
}
