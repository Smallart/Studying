package com.small.studyingweb.domain;

import lombok.Data;

import java.util.List;

/**
 * 菜单项
 * @author Liang
 */
@Data
public class MenuItem {
    /**
     * 图标
     */
    private String icon;
    /**
     * 菜单名词
     */
    private String menuName;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单父ID
     */
    private Long parentId;
    /**
     * 链接URL
     */
    private String url;

    /**
     * 子菜单
     */
    private List<MenuItem> childrenItems;
}
