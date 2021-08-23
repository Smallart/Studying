package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysLoginInfo;
import com.small.system.mapper.SysUserLoginInfoMapper;
import com.small.system.query.SysUserLoginInfoQuery;
import com.small.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SysLoginInfoServiceImpl extends BaseServiceImpl<SysLoginInfo, SysUserLoginInfoQuery, SysUserLoginInfoMapper> implements ISysLoginInfoService{
    @Autowired
    private SysUserLoginInfoMapper userLoginInfoMapper;

    @PostConstruct
    private  void init(){
        setDao(userLoginInfoMapper);
    }

    @Override
    public int cleanLogininfo() {
        return userLoginInfoMapper.cleanLogininfo();
    }
}
