package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 *  菜单实体类
 * @author Liang
 */
@Data
public class SysMenu extends BaseObject {
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 显示顺序
     */
    private int orderNum;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单状态
     */
    private String visible;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 是否是Admin
     * @return
     */
    public static boolean isAdmin(Long userId){
        return userId!=null && userId == 1L;
    }

    /**
     * 标记是否被选中
     */
    private boolean checked;
}
