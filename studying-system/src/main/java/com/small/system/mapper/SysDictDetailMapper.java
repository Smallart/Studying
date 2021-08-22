package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysDictDetail;
import com.small.system.query.SysDictDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictDetailMapper extends BaseDao<SysDictDetail, SysDictDetailQuery> {
    /**
     * 更新DictType中的DictType类型
     * @param oldDictType
     * @param newDictType
     * @return
     */
    int updateDictTypeAndDictDetail(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);

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
     * 通过query查询DictDetail
     * @param query
     * @return
     */
    SysDictDetail findDictDetailByQuery(SysDictDetailQuery query);
}
