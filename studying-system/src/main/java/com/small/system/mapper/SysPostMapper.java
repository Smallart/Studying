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
    /**
     * 根据某些值检测是否唯一
     * @param query
     * @return
     */
    SysPost checkUnique(SysPostQuery query);

    /**
     * 通过id查询Syspost
     * @param postId
     * @return
     */
    SysPost findPostById(Long postId);
}
