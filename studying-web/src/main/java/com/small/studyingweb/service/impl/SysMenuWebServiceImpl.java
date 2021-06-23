package com.small.studyingweb.service.impl;

import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.domain.MenuItem;
import com.small.studyingweb.service.SysMenuWebService;
import com.small.system.domain.SysMenu;
import com.small.system.domain.SysRoleMenu;
import com.small.system.query.SysMenuQuery;
import com.small.system.service.ISysMenuService;
import com.small.system.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ISysRoleMenuService roleMenuService;

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
    public List<SysMenu> find(SysMenuQuery query) {
        return menuService.find(query);
    }

    @Override
    public List<SysMenu> menusZtree() {
        List<SysMenu> sysMenus = menuService.menusZtree();
        sysMenus.forEach(item->{
            if (item.getPerms()!=null){
                item.setMenuName(item.getMenuName()+" "+item.getPerms());
            }
        });
        return sysMenus;
    }

    @Override
    public List<SysMenu> selectMenusZtree() {
        return menuService.menusZtree();
    }

    @Override
    public SysMenu findMenuById(Long menuId) {
        return menuService.findMenuById(menuId);
    }

    @Override
    public boolean checkMenuInSameParent(SysMenuQuery query) {
        SysMenu sysMenus = menuService.checkMenuInSameParent(query);
        if (sysMenus==null){
            return false;
        }
        // 添加操作时验证
        if (query.getMenuId()==null&&sysMenus!=null){
            return false;
        }
        return sysMenus.getMenuId().longValue()==query.getMenuId()?false:true;
    }

    @Override
    @Transactional
    public boolean save(SysMenu sysMenu) {
        return menuService.save(sysMenu)>0?true:false;
    }

    @Override
    @Transactional
    public boolean update(SysMenu sysMenu) {
        return menuService.update(sysMenu)>0?true:false;
    }

    @Override
    public List<SysMenu> findChildMenuByMenuId(Long menuId) {
        return menuService.findChildMenuByMenuId(menuId);
    }

    @Override
    public boolean alreadyShare(Long menuId) {
        List<SysRoleMenu> sysRoleMenus = roleMenuService.alreadyShare(menuId);
        return sysRoleMenus.size()>0?true:false;
    }

    @Override
    public boolean delete(Long menuId) {
        return menuService.batchDelete(Arrays.asList(menuId))>0?true:false;
    }

    private List<MenuItem> changeToMenuItems(List<SysMenu> menus){
        List<MenuItem> menuItems = new ArrayList<>();
        for (SysMenu menu : menus) {
            MenuItem menuItem = new MenuItem();
            menuItem.setIcon(menu.getMenuIcon());
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
