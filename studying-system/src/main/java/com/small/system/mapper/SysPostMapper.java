package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;

import java.util.List;

/**
 * SysPost的dao层
 */
public interface SysPostMapper extends BaseDao<SysPost, SysPostQuery> {
    /**
     * 通过UserId查询岗位
     * @param userId
     * @return
     */
    List<SysPost> findPostByUserId(Integer userId);
}
