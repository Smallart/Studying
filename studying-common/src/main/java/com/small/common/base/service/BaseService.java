package com.small.common.base.service;

import java.util.List;

/**
 * 基础Service层
 * @author Liang
 */
public interface BaseService<T,U>{
    /**
     * 保存相关信息
     * @param t
     */
    Integer save(T t);

    /**
     * 查询相关信息
     * @param u
     * @return
     */
    List<T> find(U u);

    /**
     * 计算查询数量
     * @param u
     * @return
     */
    Integer count(U u);

    /**
     * 更新
     * @param t
     * @return
     */
    Integer update(T t);
}
