package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

/**
 * SysDict查询实体
 * @author Liang
 */
@Data
public class SysDictDetailQuery extends BaseQuery {
    /**
     * 字典编码
     */
    private Long dictCode;
    /**
     * 字典状态
     */
    private String dictType;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 数据状态
     */
    private Integer dictDetailStatus;

    /**
     * 字典键值
     */
    private String dictValue;
}
