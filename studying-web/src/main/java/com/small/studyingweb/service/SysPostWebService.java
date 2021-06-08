package com.small.studyingweb.service;

import com.small.system.query.SysPostQuery;

import java.util.Map;

public interface SysPostWebService {

    /**
     * 岗位 table数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysPostQuery query);
}
