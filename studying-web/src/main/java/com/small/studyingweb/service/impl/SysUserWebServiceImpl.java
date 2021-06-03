package com.small.studyingweb.service.impl;

import com.small.common.base.enitity.SysUser;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.query.SysUserQuery;
import com.small.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * SysUser web层serviceImpl
 * @author Liang
 */
@Service
public class SysUserWebServiceImpl implements SysUserWebService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public Map<String, Object> find(SysUserQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysUserService.find(query));
        map.put("total",sysUserService.count(query));
        return map;
    }

    @Override
    public boolean checkRegisterName(String loginName) {
        return sysUserService.findUserByLoginName(loginName)==null?false:true;
    }
}
