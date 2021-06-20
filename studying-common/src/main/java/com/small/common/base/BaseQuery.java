package com.small.common.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询的基础类型
 * @author Liang
 */
@Data
public class BaseQuery {
    /**
     * Id
     */
    private long id;
    /**
     * 查询内容
     */
    private String search;
    /**
     * 位置
     */
    private int limit;
    /**
     * 个数
     */
    private int offset;

    /**
     * 排序字段
     */
    private String order;

    /**
     * 排序策略
     */
    private String orderStrategy;

    public int getLimit() {
        return (limit-1);
    }


    /**
     * 其余参数
     */
    protected Map<String,Object> params;

    public Map<String, Object> getParams() {
        if (this.params==null){
            this.params = new HashMap<>();
        }
        return params;
    }
}
