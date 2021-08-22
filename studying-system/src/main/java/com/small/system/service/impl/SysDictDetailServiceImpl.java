package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysDictDetail;
import com.small.system.mapper.SysDictDetailMapper;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.service.ISysDictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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



    @Override
    public int updateDictTypeAndDictDetail(String oldDictType, String newDictType) {
        return sysDictDetailMapper.updateDictTypeAndDictDetail(oldDictType, newDictType);
    }

    @Override
    public List<SysDictDetail> findDictDetailByType(String dictType) {
        return sysDictDetailMapper.findDictDetailByType(dictType);
    }

    @Override
    public List<SysDictDetail> checkUnique(SysDictDetailQuery query) {
        return sysDictDetailMapper.checkUnique(query);
    }

    @Override
    public boolean batchDeleteDictDetail(List<Long> ids) {
        return sysDictDetailMapper.batchDelete(ids)>0?true:false;
    }

    @Override
    public SysDictDetail findDictDetailByQuery(SysDictDetailQuery query) {
        return sysDictDetailMapper.findDictDetailByQuery(query);
    }
}
