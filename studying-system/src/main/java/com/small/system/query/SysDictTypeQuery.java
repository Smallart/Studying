package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 字典总类型query实体
 * @author Liang
 */
@Data
public class SysDictTypeQuery extends BaseQuery {
    /**
     * 字典名称
     */
    private String dictTypeName;
    /**
     * 字典类型
     */
    private String dictTypeCode;
    /**
     * 字典状态
     */
    private Integer dictTypeStatus;
    /**
     * 创建时间开始范围
     */
    private Date createStartDate;
    /**
     * 创建时间结束范围
     */
    private Date createEndDate;
}
