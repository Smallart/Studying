package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysLoginInfo;
import com.small.system.query.SysUserLoginInfoQuery;

public interface SysUserLoginInfoMapper extends BaseDao<SysLoginInfo, SysUserLoginInfoQuery> {
    /**
     * 删除所有信息重新构建表
     * @return
     */
    int cleanLogininfo();
}
