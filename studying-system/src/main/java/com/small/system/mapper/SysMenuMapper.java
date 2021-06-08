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
}
