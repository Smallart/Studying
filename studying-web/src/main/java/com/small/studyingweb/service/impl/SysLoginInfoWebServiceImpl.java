package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysLoginInfoWebService;
import com.small.system.query.SysUserLoginInfoQuery;
import com.small.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysLoginInfoWebServiceImpl implements SysLoginInfoWebService {

    @Autowired
    private ISysLoginInfoService loginInfoService;

    @Override
    public Map<String, Object> find(SysUserLoginInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",loginInfoService.find(query));
        map.put("total",loginInfoService.count(query));
        return map;
    }

    @Override
    @Transactional
    public boolean batchDelete(String ids) {
        List<Long> idsList = Arrays.stream(ids.split(",")).map(item -> Long.parseLong(item)).collect(Collectors.toList());
        return loginInfoService.batchDelete(idsList)>0?true:false;
        }

    @Override
    @Transactional
    public void cleanLogininfo() {
        loginInfoService.cleanLogininfo();
    }

    ;
}
