package com.small.system.domain;

import lombok.Data;

/**
 * 用户和角色关系表实体
 * @author Liang
 */
@Data
public class SysUserRole {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 角色Id
     */
    private Long roleId;
}
