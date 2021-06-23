package com.small.studyingweb.service;

import com.small.common.utils.ResponseResult;
import com.small.studyingweb.domain.MenuItem;
import com.small.system.domain.SysMenu;
import com.small.system.query.SysMenuQuery;

import java.util.List;

/**
 * SysMenu web层Service
 * @author Liang
 */
public interface SysMenuWebService {
    /**
     * 查询Menus
     * @return
     */
    List<MenuItem> findMenus();

    /**
     * Menu页面的查询功能
     * @return
     */
    List<SysMenu> find(SysMenuQuery query);

    /**
     * 查询menu树所需数据
     * @return
     */
    List<SysMenu> menusZtree();

    /**
     * input menu树所需数据
    * @return
     */
    List<SysMenu> selectMenusZtree();

    /**
     * 通过menuId查询SysMenu
     * @param menuId
     * @return
     */
    SysMenu findMenuById(Long menuId);

    /**
     * 查询在同一父下是否又相同菜单
     * @param query
     * @return
     */
    boolean checkMenuInSameParent(SysMenuQuery query);

    /**
     * 保存新增菜单
     * @param sysMenu
     * @return
     */
    boolean save(SysMenu sysMenu);

    /**
     * 更新相关信息
     * @param sysMenu
     * @return
     */
    boolean update(SysMenu sysMenu);

    /**
     * 查询该menuId的子菜单
     * @param menuId
     * @return
     */
    List<SysMenu> findChildMenuByMenuId(Long menuId);

    /**
     * 判断该菜单是否被分配
     * @param menuId
     * @return
     */
    boolean alreadyShare(Long menuId);

    /**
     * 删除
     * @param menuId
     */
    boolean delete(Long menuId);
}
