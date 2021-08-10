package com.small.studyingweb.service.impl;

import com.small.common.base.enitity.SysRole;
import com.small.common.base.enitity.SysUser;
import com.small.common.exceptions.BusinessException;
import com.small.studyingweb.service.SysRoleWebService;
import com.small.system.domain.SysMenu;
import com.small.system.domain.SysRoleDept;
import com.small.system.domain.SysRoleMenu;
import com.small.system.domain.SysUserRole;
import com.small.system.query.SysRoleQuery;
import com.small.system.query.SysUserQuery;
import com.small.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SysRole web层service impl
 * @author Liang
 */
@Service
public class SysRoleWebServiceImpl implements SysRoleWebService
{

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Autowired
    private ISysRoleDeptService roleDeptService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Override
    public Map<String, Object> find(SysRoleQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",roleService.find(query));
        map.put("total",roleService.count(query));
        return map;
    }

    @Override
    public List<SysRole> tagRoles(Long userId) {
        List<SysRole> userRoles = roleService.findRolesByUserId(userId);
        userRoles.forEach(item->item.setFlag(true));
        List<SysRole> roles = roleService.find(new SysRoleQuery());
        List<Long> ids = userRoles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        roles.forEach(item->{
            if (ids.contains(item.getRoleId())){
                item.setFlag(true);
            }
        });
        return SysUser.isAdmin(userId)?roles:roles.stream().filter(item->!item.isAdmin(item.getRoleId())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> assignRoleTable(Long userId) {
        Map<String,Object> map = new HashMap<>();
        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        List<SysRole> sysRoles = roleService.find(sysRoleQuery);
        List<SysRole> userRole = roleService.findRolesByUserId(userId);
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

    @Override
    public SysRole findRoleById(Long roleId) {
        return roleService.findRoleById(roleId);
    }

    @Override
    public List<SysUser> findBindUserByRoleId(SysUserQuery query) {
        return userService.findBindUserByRoleId(query);
    }

    @Override
    public boolean checkRoleNameUnique(SysRole sysRole) {
        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        sysRoleQuery.setRoleName(sysRole.getRoleName());
        Long userId = sysRole.getRoleId()==null? -1L:sysRole.getRoleId();
        SysRole role = roleService.checkRoleFieldUnique(sysRoleQuery);
        return role!=null&&role.getRoleId().longValue()!=userId.longValue()?true:false;
    }

    @Override
    public boolean checkRoleKeyUnique(SysRole sysRole) {
        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        sysRoleQuery.setRoleKey(sysRole.getRoleKey());
        SysRole role = roleService.checkRoleFieldUnique(sysRoleQuery);
        Long roleId = sysRole.getRoleId()==null?-1L:sysRole.getRoleId();
        return role!=null&&role.getRoleId().longValue()!=roleId.longValue()?true:false;
    }

    @Override
    @Transactional
    public boolean save(SysRole sysRole) {
        sysRole.setDelFlag("0");
        Integer row = roleService.save(sysRole);
        saveSysMenuRole(sysRole.getRoleId(),sysRole.getMenuIds());
        return row>0?true:false;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        String[] split = ids.split(",");
        List<Long> roleIds = Arrays.asList(split).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
        for (Long roleId : roleIds) {
            if (SysRole.isAdmin(roleId)){
                throw new BusinessException("超级管理员角色无法删除");
            }
            SysUserQuery query = new SysUserQuery();
            query.setId(roleId);
            List<SysUser> users = userService.findBindUserByRoleId(query);
            if (users!=null&&users.size()>0){
                throw new BusinessException("存在已经分配了用户角色，无法被删除");
            }
        }
        roleDeptService.batchDeleteByRoleId(roleIds);
        roleMenuService.batchDeleteByRoleId(roleIds);
        return roleService.batchDelete(roleIds)>0?true:false;
    }

    @Override
    public List<SysMenu> tagMenusTree(Long roleId) {
        List<SysMenu> sysMenus = menuService.menusZtree();
        if (SysRole.isAdmin(roleId)){
            sysMenus.forEach(item->item.setChecked(true));
        }else{
            List<SysRoleMenu> roleMenuByRoleId = roleMenuService.findRoleMenuByRoleId(roleId);
            List<Long> ids = roleMenuByRoleId.stream().map(item->item.getMenuId()).collect(Collectors.toList());
            sysMenus.forEach(item->{
                if (ids.contains(item.getMenuId())){
                    item.setChecked(true);
                }
            });
        }
        return sysMenus;
    }

    @Override
    @Transactional
    public boolean update(SysRole sysRole) {
        roleMenuService.batchDeleteByRoleId(Arrays.asList(sysRole.getRoleId()));
        saveSysMenuRole(sysRole.getRoleId(),sysRole.getMenuIds());
        return roleService.update(sysRole)>0?true:false;
    }

    @Override
    @Transactional
    public boolean updateDataSCope(SysRole role) {
        roleDeptService.batchDeleteByRoleId(Arrays.asList(role.getRoleId()));
        saveSysRoleDept(role.getRoleId(),role.getDeptIds());
        return roleService.update(role)>0?true:false;
    }

    @Override
    @Transactional
    public boolean assignUser(Long roleId, String userIds) {
        List<Long> userIdList = Arrays.asList(userIds.split(",")).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
        List<SysUserRole> sysUserRoles = createSysUserRoles(roleId, userIdList);
        return userRoleService.batchInsert(sysUserRoles)>0?true:false;
    }

    @Override
    public boolean cancelAuthUser(SysUserRole sysUserRole) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleId",sysUserRole.getRoleId());
        map.put("userIds",Arrays.asList(sysUserRole.getRoleId()));
        return userRoleService.batchDeleteBySysUserRoles(map)>0?true:false;
    }

    @Override
    public boolean cancelAuthAllUser(Long roleId, String userIds) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleId",roleId);
        map.put("userIds",Arrays.asList(userIds.split(",")).stream().map(item->Long.parseLong(item)).collect(Collectors.toList()));
        return userRoleService.batchDeleteBySysUserRoles(map)>0?true:false;
    }

    @Override
    @Transactional
    public boolean changeStatus(SysRole sysRole) {
        Integer row = roleService.update(sysRole);
        return row>0?true:false;
    }

    public List<SysUserRole> createSysUserRoles(Long roleId,List<Long> userIds){
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        return sysUserRoles;
    }

    public void saveSysRoleDept(Long roleId,Long[] deptIds){
        List<SysRoleDept> sysRoleDepts = new ArrayList<>();
        for (Long deptId : deptIds) {
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setDeptId(deptId);
            sysRoleDept.setRoleId(roleId);
            sysRoleDepts.add(sysRoleDept);
        }
        if (sysRoleDepts.size()>0){
            roleDeptService.batchInsert(sysRoleDepts);
        }
    }

    public void saveSysMenuRole(Long roleId,Long[] menuIds){
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (int i = 0; i < menuIds.length; i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuIds[i]);
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenus.add(sysRoleMenu);
        }
        if (sysRoleMenus.size()>0){
            roleMenuService.batchSave(sysRoleMenus);
        }
    }
}
