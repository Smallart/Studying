package com.small.studyingweb.service.impl;

import com.small.common.base.enitity.SysUser;
import com.small.studyingweb.service.SysRoleWebService;
import com.small.system.domain.SysRole;
import com.small.system.query.SysRoleQuery;
import com.small.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

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

    @Override
    public List<SysRole> findRoleById(Long userId) {
        List<SysRole> userRoles = roleService.findRoleById(userId);
        userRoles.forEach(item->item.setFlag(true));
        List<SysRole> roles = roleService.find(new SysRoleQuery());
        List<Long> ids = userRoles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        roles.forEach(item->{
            if (ids.contains(item.getRoleId())){
                item.setFlag(true);
            }
        });
        return SysUser.isAdmin(userId)?roles:userRoles;
    }

    @Override
    public Map<String, Object> assignRoleTable(Long userId) {
        Map<String,Object> map = new HashMap<>();
        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        List<SysRole> sysRoles = roleService.find(sysRoleQuery);
        List<SysRole> userRole = roleService.findRoleById(userId);
        List<Long> ids = userRole.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        sysRoles.forEach(item->{
            if(ids.contains(item.getRoleId())){
                item.setFlag(true);
           }
        });
        map.put("total",roleService.count(sysRoleQuery));
        map.put("data",sysRoles);
        return map;
    }
}
