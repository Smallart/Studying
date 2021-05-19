package com.small.common.base.service;

/**
 * 基础Service层
 * @author Liang
 */
public interface BaseService<T,U>{
    /**
     * 保存相关信息
     * @param t
     */
    void save(T t);

    /**
     * 查询相关信息
     * @param u
     * @return
     */
    T find(U u);
}
