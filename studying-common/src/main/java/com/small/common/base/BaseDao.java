package com.small.common.base;

import java.util.List;

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
    List<T> find(U u);

    /**
     * 计算查询的个数
     * @param u
     * @return
     */
    Integer count(U u);

//    /**
//     * 根据id批量删除
//     * @param ids
//     * @return
//     */
//    Integer batchDelete(List<Integer> ids);
}
