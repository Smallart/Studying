package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysUserOnline;
import com.small.system.query.SysUserOnlineQuery;

import java.util.List;

/**
 * 在线用户 服务层
 * @author Liang
 */
public interface ISysUserOnlineService extends BaseService<SysUserOnline, SysUserOnlineQuery> {
    /**
     * 查询数据库中过期UserOnline
     * @param query
     * @return
     */
    List<SysUserOnline> findExpireUserOnline(SysUserOnlineQuery query);

    /**
     * 通过sessionIds删除userOnline
     * @param sessions
     */
    void batchDeleteOnline(List<String> sessions);

    /**
     * 通过sessionId删除会话
     * @param sessionId
     */
    void deleteOnlineById(String sessionId);
}
