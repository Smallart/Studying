package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysDictType;
import com.small.system.mapper.SysDictTypeMapper;
import com.small.system.query.SysDictTypeQuery;
import com.small.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
}
