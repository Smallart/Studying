package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysRoleWebService;
import com.small.system.query.SysRoleQuery;
import com.small.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * SysRole web层service impl
 * @author Liang
 */
@Service
public class SysRoleWebServiceImpl implements SysRoleWebService {

    @Autowired
    private ISysRoleService roleService;

    @Override
    public Map<String, Object> find(SysRoleQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",roleService.find(query));
        map.put("total",roleService.count(query));
        return map;
    }
}
