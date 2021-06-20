package com.small.common.base.enitity;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 * 角色实体
 * @author Liang
 */
@Data
public class SysRole extends BaseObject {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 显示顺序
     */
    private Integer roleSort;
    /**
     * 角色权限字符串
     */
    private String roleKey;
    /**
     * 数据范围
     * 1. 全部数据权限
     * 2. 自定义权限
     * 3. 部门权限
     */
    private String dataScope;
    /**
     * 角色状态
     * 0 正常
     * 1 停用
     */
    private String status;
    /**
     * 删除标志
     * 0 存在
     * 2 删除
     */
    private String delFlag;

    /**
     * 是否选中的标志
     */
    private boolean flag;

    /**
     * 菜单ids
     */
    private Long[] menuIds;

    /**
     * 部门id
     */
    private Long[] deptIds;

    /**
     * 判断该Role是否是超级角色
     * @param roleId
     * @return
     */
    public static boolean isAdmin(Long roleId){
        return roleId!=null&&roleId==1;
    }
}
