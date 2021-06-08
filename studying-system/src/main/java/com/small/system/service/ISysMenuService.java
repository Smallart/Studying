package com.small.system.service;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysMenu;
import com.small.system.query.SysMenuQuery;

import java.util.List;

/**
 * menu service层
 * @author Liang
 */
public interface ISysMenuService extends BaseDao<SysMenu, SysMenuQuery> {
    /**
     * 查询menuTree所需数据
     * @return
     */
    List<SysMenu> menusZtree();
}
