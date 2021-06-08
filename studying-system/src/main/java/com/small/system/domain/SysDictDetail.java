package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 * 字典细节 实体
 * @author Liang
 */
@Data
public class SysDictDetail extends BaseObject {
    /**
     * 字典编码
     */
    private Long dictCode;
    /**
     * 字典排序
     */
    private Integer dictSort;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典键值
     */
    private String dictValue;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 是否默认
     * Y 是 N否
     */
    private String isDefault;
    /**
     * 状态
     * 0 正常 1 停用
     */
    private String dictDetailStatus;
}
