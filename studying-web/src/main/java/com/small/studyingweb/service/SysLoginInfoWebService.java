package com.small.studyingweb.service;

import com.small.system.query.SysUserLoginInfoQuery;

import java.util.Map;

/**
 * LoginInfo的web层Service
 */
public interface SysLoginInfoWebService {
    /**
     * 前端表显示数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysUserLoginInfoQuery query);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    boolean batchDelete(String ids);

    /**
     * 删除所有信息重新构建表
     * @return
     */
    void cleanLogininfo();
}
