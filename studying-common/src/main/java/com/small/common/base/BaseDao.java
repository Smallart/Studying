package com.small.common.base;

/**
 * 基础Dao层
 * @author Liang
 */
public interface BaseDao<T,U>{
    /**
     * 保存用户
     * @param t
     */
    void save(T t);

    /**
     * 查询相关信息
     * @param t
     */
    T find(U u);
}
