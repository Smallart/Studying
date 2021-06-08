package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysPostWebService;
import com.small.system.query.SysPostQuery;
import com.small.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * SysPost web 实现层
 * @author Liang
 */
@Service
public class SysPostWebServiceImpl implements SysPostWebService {

    @Autowired
    private ISysPostService postService;

    @Override
    public Map<String, Object> find(SysPostQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",postService.find(query));
        map.put("total",postService.count(query));
        return map;
    }
}
