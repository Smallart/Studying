package com.small.common.base.service.impl;

import com.small.common.base.BaseDao;
import com.small.common.base.BaseObject;
import com.small.common.base.BaseQuery;
import com.small.common.base.service.BaseService;

import java.util.List;

/**
 * 封装基础的Service层
 * @param <T>
 * @author Liang
 */
public class BaseServiceImpl<T extends BaseObject,U extends BaseQuery,V extends BaseDao> implements BaseService<T,U> {

    private BaseDao<T,U> dao;

    public void setDao(BaseDao<T,U> dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(T t) {
        return dao.save(t);
    }

    @Override
    public List<T> find(U u) {
        return dao.find(u);
    }

    @Override
    public Integer count(U u) {
        return dao.count(u);
    }

    @Override
    public Integer update(T t) {
        return dao.update(t);
    }

    @Override
    public Integer batchDelete(List<Long> id) {
        return dao.batchDelete(id);
    }
}

