package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysUserOnline;
import com.small.system.query.SysUserOnlineQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 在线用户 dao 层
 * @author Liang
 */
public interface SysUserOnlineMapper extends BaseDao<SysUserOnline, SysUserOnlineQuery> {
    /**
     * 查询数据库中过期UserOnline
     * @param query
     * @return
     */
    List<SysUserOnline> findExpireUserOnline(SysUserOnlineQuery query);

    /**
     * 批量通过sessionId删除会话
     * @param sessions
     */
    void batchDeleteOnline(List<String> sessions);

    /**
     * 通过sessionId删除会话
     * @param sessionId
     */
    void deleteOnlineById(String sessionId);
}
