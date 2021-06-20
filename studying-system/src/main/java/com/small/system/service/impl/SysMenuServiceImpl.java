package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysMenu;
import com.small.system.mapper.SysMenuMapper;
import com.small.system.query.SysMenuQuery;
import com.small.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysMenuService 实现
 * @author Liang
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu, SysMenuQuery, SysMenuMapper> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @PostConstruct
    public void init(){
        setDao(menuMapper);
    }

    @Override
    public List<SysMenu> menusZtree() {
        return menuMapper.menusZtree();
    }
}
