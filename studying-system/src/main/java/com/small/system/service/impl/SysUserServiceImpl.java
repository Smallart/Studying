package com.small.system.service.impl;

import com.small.common.base.enitity.SysUser;
import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.mapper.SysUserMapper;
import com.small.system.query.SysUserQuery;
import com.small.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
    public SysUser findUserByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }
}
