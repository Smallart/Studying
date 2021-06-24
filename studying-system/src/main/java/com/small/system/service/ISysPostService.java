package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;

import java.util.List;

/**
 * SysPost service层
 * @author Liang
 */
public interface ISysPostService extends BaseService<SysPost, SysPostQuery> {
    /**
     * 通过UserId查询岗位
     * @param userId
     * @return
     */
    List<SysPost> findPostByUserId(Integer userId);

    /**
     * 通过id查询Syspost
     * @param postId
     * @return
     */
    SysPost findPostById(Long postId);

    /**
     * 根据某些值检测是否唯一
     * @param query
     * @return
     */
    SysPost checkUnique(SysPostQuery query);
}
