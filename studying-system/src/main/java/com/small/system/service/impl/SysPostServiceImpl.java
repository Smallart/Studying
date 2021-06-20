package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.system.domain.SysPost;
import com.small.system.mapper.SysPostMapper;
import com.small.system.query.SysPostQuery;
import com.small.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysPost service实现层
 * @author Liang
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPost, SysPostQuery, SysPostMapper> implements ISysPostService {
    @Autowired
    private SysPostMapper sysPostMapper;

    @PostConstruct
    public void init(){
        setDao(sysPostMapper);
    }

    @Override
    public List<SysPost> findPostByUserId(Integer userId) {
        return sysPostMapper.findPostByUserId(userId);
    }
}
