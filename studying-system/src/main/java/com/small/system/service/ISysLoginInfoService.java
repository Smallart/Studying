package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysLoginInfo;
import com.small.system.query.SysUserLoginInfoQuery;

/**
 * 日志service 层
 * @author Liang
 */
public interface ISysLoginInfoService extends BaseService<SysLoginInfo, SysUserLoginInfoQuery> {
    /**
     * 删除所有信息重新构建表
     * @return
     */
    int cleanLogininfo();
}
