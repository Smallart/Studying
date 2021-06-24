package com.small.system.service.impl;

import com.small.system.domain.SysUserPost;
import com.small.system.domain.SysUserRole;
import com.small.system.mapper.SysUserPostMapper;
import com.small.system.service.ISysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SysUserPostService implements ISysUserPostService {

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public Integer batchInsert(List<SysUserPost> userPost) {
        return sysUserPostMapper.batchInsert(userPost);
    }

    @Override
    public Integer batchDelete(List<Long> userId) {
        return sysUserPostMapper.batchDelete(userId);
    }

    @Override
    public Integer checkUserNumByPostId(Long postId) {
        return sysUserPostMapper.checkUserNumByPostId(postId);
    }
}
