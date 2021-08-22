package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysDictType;
import com.small.system.mapper.SysDictTypeMapper;
import com.small.system.query.SysDictTypeQuery;
import com.small.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDeptType service的实现
 * @author Liang
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictType, SysDictTypeQuery, SysDictTypeMapper> implements ISysDictTypeService {
    @Autowired
    private SysDictTypeMapper sysDeptTypeMapper;
    @PostConstruct
    public void init(){
        setDao(sysDeptTypeMapper);
    }

    @Override
    public List<SysDictType> checkSysDictTypeExistByQuery(SysDictTypeQuery query) {
        return sysDeptTypeMapper.findSysDictTypeByQuery(query);
    }

    @Override
    public Integer addDictType(SysDictType sysDictType) {
        return sysDeptTypeMapper.save(sysDictType);
    }

    @Override
    public SysDictType findDictTypeById(Long dictId) {
        return sysDeptTypeMapper.findDictTypeById(dictId);
    }

    @Override
    public List<SysDictType> getAllDictTypeNames() {
        return sysDeptTypeMapper.getAllDictTypeNames();
    }
}
