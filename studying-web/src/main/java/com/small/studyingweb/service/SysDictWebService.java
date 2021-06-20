package com.small.studyingweb.service;

import com.small.system.domain.SysDictDetail;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;

import java.util.List;
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

    /**
     * 根据Type查询字典相关细节
     * @param type
     * @return
     */
    List<SysDictDetail> findDetailByType(String type);
}
