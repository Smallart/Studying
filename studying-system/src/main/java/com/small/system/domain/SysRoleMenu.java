package com.small.system.domain;

import lombok.Data;

/**
 * 角色菜单关系实体
 * @author liang
 */
@Data
public class SysRoleMenu {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单Id
     */
    private Long menuId;
}
