package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * SysPost查询参数实体
 * @author Liang
 */
@Data
public class SysPostQuery extends BaseQuery {
    /**
     * 岗位编码
     */
    private String postCode;
    /**
     * 岗位i名称
     */
    private String postName;
    /**
     * 岗位状态
     */
    private Integer postStatus;

    /**
     * 创建日期开始范围
     */
    private Date createStartDate;

    /**
     * 创建日期结束范围
     */
    private Date createEndDate;
}
