package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysDictDetail;
import com.small.system.mapper.SysDictDetailMapper;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.service.ISysDictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * SysDetailService service层的实现
 * @author Liang
 */
@Service
public class SysDictDetailServiceImpl extends BaseServiceImpl<SysDictDetail, SysDictDetailQuery, SysDictDetailMapper> implements ISysDictDetailService{
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;
    @PostConstruct
    public void init(){
        setDao(sysDictDetailMapper);
    }
}
