package com.small.studyingweb.service;

import com.small.system.query.SysUserQuery;

import java.util.Map;

/**
 * SysUser web层service
 * @author Liang
 */
public interface SysUserWebService {
    /**
     * 获得SysUser table的数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysUserQuery query);

    /**
     *
     * @param loginName
     * @return
     */
    boolean checkRegisterName(String loginName);
}
