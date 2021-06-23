package com.small.system.service.impl;

import com.small.system.domain.SysRoleMenu;
import com.small.system.mapper.SysRoleMenuMapper;
import com.small.system.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysRoleMenu service实现
 * @author Liang
 */
@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Integer batchSave(List<SysRoleMenu> sysRoleMenus) {
        return sysRoleMenuMapper.batchSave(sysRoleMenus);
    }

    @Override
    public Integer batchDeleteByRoleId(List<Long> roleIds) {
        return sysRoleMenuMapper.batchDeleteByRoleId(roleIds);
    }

    @Override
    public List<SysRoleMenu> findRoleMenuByRoleId(Long roleId) {
        return sysRoleMenuMapper.findRoleMenuByRoleId(roleId);
    }

    @Override
    public List<SysRoleMenu> alreadyShare(Long menuId) {
        return sysRoleMenuMapper.alreadyShare(menuId);
    }
}
