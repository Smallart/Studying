package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysUserOnline;
import com.small.system.mapper.SysUserOnlineMapper;
import com.small.system.query.SysUserOnlineQuery;
import com.small.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 在线用户service层实现
 * @author Liang
 */
@Service
public class SysUserOnlineServiceImpl extends BaseServiceImpl<SysUserOnline, SysUserOnlineQuery, SysUserOnlineMapper> implements ISysUserOnlineService {

    @Autowired
    private SysUserOnlineMapper userOnlineMapper;

    @PostConstruct
    private void init(){
        setDao(userOnlineMapper);
    }

    @Override
    public List<SysUserOnline> findExpireUserOnline(SysUserOnlineQuery query) {
        return userOnlineMapper.findExpireUserOnline(query);
    }

    @Override
    public void batchDeleteOnline(List<String> sessions) {
        userOnlineMapper.batchDeleteOnline(sessions);
    }

    @Override
    public void deleteOnlineById(String sessionId) {
        userOnlineMapper.deleteOnlineById(sessionId);
    }

    @Override
    public SysUserOnline findUserOnlineBySessionId(String sessionId) {
        return userOnlineMapper.findUserOnlineBySessionId(sessionId);
    }
}
