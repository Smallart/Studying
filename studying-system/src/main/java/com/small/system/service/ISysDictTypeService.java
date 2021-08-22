package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysDictType;
import com.small.system.query.SysDictTypeQuery;

import java.util.List;

/**
 * SysDeptType 实体 service层
 * @author Liang
 */
public interface ISysDictTypeService extends BaseService<SysDictType, SysDictTypeQuery> {
    /**
     * 查询SysDeptType
     * @param query
     * @return
     */
    List<SysDictType> checkSysDictTypeExistByQuery(SysDictTypeQuery query);

    /**
     * 添加字典类型
     * @param sysDictType
     * @return
     */
    Integer addDictType(SysDictType sysDictType);

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
