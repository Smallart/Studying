package com.small.system.mapper;

import com.small.system.domain.SysUserPost;

import java.util.List;

/**
 * SysUserPost实体dao层
 * @author Liang
 */
public interface SysUserPostMapper {
    /**
     * 批量插入用户与岗位之间的关系
     * @param userPost
     * @return
     */
    Integer batchInsert(List<SysUserPost> userPost);
    /**
     * 批量删除用户与角色之间的关系
     * @param userId
     * @return
     */
    Integer batchDelete(List<Long> userId);

    /**
     * 通过postId查询相关联的用户
     * @param postId
     * @return
     */
    Integer checkUserNumByPostId(Long postId);
}
