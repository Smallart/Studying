package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysMenu;
import com.small.system.query.SysMenuQuery;

import java.util.List;

/**
 * SysMenu dao层
 * @author Liang
 */
public interface SysMenuMapper extends BaseDao<SysMenu,SysMenuQuery> {
    /**
     * 查询menu树所需数据
     * @return
     */
    List<SysMenu> menusZtree();

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
    SysMenu checkMenuInSameParent(SysMenuQuery query);

    /**
     * 查询该menuId的子菜单
     * @param menuId
     * @return
     */
    List<SysMenu> findChildMenuByMenuId(Long menuId);
}
