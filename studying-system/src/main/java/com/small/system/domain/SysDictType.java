package com.small.system.domain;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 * 字典类别实体
 * @author Liang
 */
@Data
public class SysDictType extends BaseObject {
    /**
     * 字典类别Id
     */
    private Long dictId;
    /**
     * 字典类别名称
     */
    private String dictName;
    /**
     * 字典类型code
     */
    private String dictType;
    /**1
     * 状态
     * 0 正常
     * 1 停用
     */
    private Integer status;
}
