package com.small.studyingweb.service;

import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;

import java.util.Map;

/**
 * SysDictType web层service实现
 * @author Liang
 */
public interface SysDictWebService {
    /**
     * 查询table字典类型所需数据
     * @param query
     * @return
     */
    Map<String,Object> findType(SysDictTypeQuery query);

    /**
     * 查询table字典细节所需数据
     * @param query
     * @return
     */
    Map<String,Object> findDetail(SysDictDetailQuery query);
}
