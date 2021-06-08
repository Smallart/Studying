package com.small.common.base;

import lombok.Data;

import java.util.Date;

/**
 * 基础类
 * @author Liang
 */
@Data
public class BaseObject {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 跟新者
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;
}
