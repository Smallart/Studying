package com.small.studyingweb.service;

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
    List<SysMenu> find();
}
