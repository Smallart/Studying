package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 * SysPost 岗位实体类
 * @author Liang
 */
@Data
public class SysPost extends BaseObject {
    /**
     * 岗位ID
     */
    private Long postId;
    /**
     * 岗位代码
     */
    private String postCode;
    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 显示顺序
     */
    private Integer postSort;
    /**
     * 岗位状态
     */
    private Integer status;
}
