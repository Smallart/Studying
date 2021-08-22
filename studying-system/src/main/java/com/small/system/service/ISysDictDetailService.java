package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysDictDetail;
import com.small.system.query.SysDictDetailQuery;

import java.util.List;

/**
 * SysDictDetail细节
 * @author Liang
 */
public interface ISysDictDetailService extends BaseService<SysDictDetail, SysDictDetailQuery> {
    /**
     * 更新DictType中的DictType类型
     * @param oldDictType
     * @param newDictType
     * @return
     */
    int updateDictTypeAndDictDetail(String oldDictType, String newDictType);

    /**
     * 通过dictType查询DictDetail
     * @param dictType
     * @return
     */
    List<SysDictDetail> findDictDetailByType(String dictType);

    /**
     * 根据传入查询SysDictDetail是否存在
     * @param query
     * @return
     */
    List<SysDictDetail> checkUnique(SysDictDetailQuery query);

    /**
     * 批量删除DictDetail
     * @return
     */
    boolean batchDeleteDictDetail(List<Long> ids);

    /**
     * 通过query查询DictDetail
     * @param query
     * @return
     */
    SysDictDetail findDictDetailByQuery(SysDictDetailQuery query);
}
