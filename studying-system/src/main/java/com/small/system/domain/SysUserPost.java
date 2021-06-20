package com.small.system.domain;

import lombok.Data;

/**
 * User与post关系表的实体
 * @author Liang
 */
@Data
public class SysUserPost {
    /**
     * userId
     */
    private Long userId;
    /**
     * postId
     */
    private Long postId;
}
