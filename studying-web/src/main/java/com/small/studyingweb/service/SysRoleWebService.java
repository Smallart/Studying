package com.small.studyingweb.service;

import com.small.system.query.SysRoleQuery;

import java.util.Map;

/**
 * SysRole web层service
 * @author Liang
 */
public interface SysRoleWebService {
    /**
     * SysRole table数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysRoleQuery query);
}
