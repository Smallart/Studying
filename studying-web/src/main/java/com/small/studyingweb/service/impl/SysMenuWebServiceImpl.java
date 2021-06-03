package com.small.studyingweb.service.impl;

import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.domain.MenuItem;
import com.small.studyingweb.service.SysMenuWebService;
import com.small.system.domain.SysMenu;
import com.small.system.query.SysMenuQuery;
import com.small.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SysMenu web层 Serviceimpl
 * @author Liang
 */
@Service
public class SysMenuWebServiceImpl implements SysMenuWebService {

    @Autowired
    private ISysMenuService menuService;

    @Override
    public List<MenuItem> findMenus() {
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        sysMenuQuery.setVisible(0);
        sysMenuQuery.setOperation(SysMenuQuery.OperationEnum.NOT_INCLUDE_F.getVal());
        if (!SysMenu.isAdmin(ShiroUtils.getUserId())){
            sysMenuQuery.setUserId(ShiroUtils.getUserId());
        }
        List<SysMenu> menus = menuService.find(sysMenuQuery);
        return changeToMenuItems(menus);
    }

    @Override
    public List<SysMenu> find() {
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        return menuService.find(sysMenuQuery);
    }

    private List<MenuItem> changeToMenuItems(List<SysMenu> menus){
        List<MenuItem> menuItems = new ArrayList<>();
        for (SysMenu menu : menus) {
            MenuItem menuItem = new MenuItem();
            menuItem.setIcon(menu.getIcon());
            menuItem.setMenuId(menu.getMenuId());
            menuItem.setMenuName(menu.getMenuName());
            menuItem.setParentId(menu.getParentId());
            menuItem.setUrl(menu.getUrl());
            menuItems.add(menuItem);
        }
        Map<Long, List<MenuItem>> parentMap = menuItems.stream().collect(Collectors.groupingBy(MenuItem::getParentId));
        List<MenuItem> topMenus = parentMap.get(Long.valueOf(0));
        Queue<MenuItem> queue = new LinkedList<>();
        for (MenuItem topMenu : topMenus) {
            queue.add(topMenu);
            while (!queue.isEmpty()){
                MenuItem currentItem = queue.poll();
                List<MenuItem> childrenItems = parentMap.get(currentItem.getMenuId());
                if(childrenItems==null){
                    continue;
                }
                List<MenuItem> children = currentItem.getChildrenItems();
                if (children==null){
                    children = new ArrayList<>();
                    currentItem.setChildrenItems(children);
                }
                children.addAll(childrenItems);
                queue.addAll(childrenItems);
            }
        }
        return topMenus;
    }
}
