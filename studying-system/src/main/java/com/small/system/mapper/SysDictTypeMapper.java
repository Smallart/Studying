package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysDictType;
import com.small.system.query.SysDictTypeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysDeptType 的dao层
 * @author Liang
 */
public interface SysDictTypeMapper extends BaseDao<SysDictType, SysDictTypeQuery> {
    /**
     * 通过查询添加查询SysDictType
     * @param query
     * @return
     */
    List<SysDictType> findSysDictTypeByQuery(SysDictTypeQuery query);


    /**
     * 通过dictId查询DictType
     * @param dictId
     * @return
     */
    SysDictType findDictTypeById(Long dictId);

    /**
     * 获得所有字典名称
     * @return
     */
    List<SysDictType> getAllDictTypeNames();
}
