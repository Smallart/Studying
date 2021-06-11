package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysDictWebService;
import com.small.system.domain.SysDictDetail;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;
import com.small.system.service.ISysDictDetailService;
import com.small.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysDictType web层service具体实现
 * @author Liang
 */
@Service
public class SysDictWebServiceImpl implements SysDictWebService {

    @Autowired
    private ISysDictTypeService sysDeptTypeService;

    @Autowired
    private ISysDictDetailService sysDictDetailService;

    @Override
    public Map<String, Object> findType(SysDictTypeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysDeptTypeService.find(query));
        map.put("total",sysDeptTypeService.count(query));
        return map;
    }

    @Override
    public Map<String, Object> findDetail(SysDictDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysDictDetailService.find(query));
        map.put("total",sysDictDetailService.count(query));
        return map;
    }

    @Override
    public List<SysDictDetail> findDetailByType(String type) {
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictType(type);
        return sysDictDetailService.find(query);
    }
}
