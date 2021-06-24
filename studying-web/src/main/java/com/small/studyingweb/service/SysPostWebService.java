package com.small.studyingweb.service;

import com.small.common.utils.ResponseResult;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;

import java.util.List;
import java.util.Map;

public interface SysPostWebService {

    /**
     * 岗位 table数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysPostQuery query);

    /**
     * 通过UserId查询岗位
     * @param userId
     * @return
     */
    List<SysPost> findPostByUserId(Integer userId);

    /**
     * 检测PostName是否已经存在
     * @param postName
     * @return
     */
    Boolean checkPostNameUnique(String postName,Long postId);

    /**
     * 检测PostCode是否已经存在
     * @param postCode
     * @return
     */
    Boolean checkPostCodeUnique(String postCode,Long postId);

    /**
     * 保存
     * @return
     */
    boolean save(SysPost sysPost);

    /**
     * 根据postId查询相关信息
     * @param postId
     * @return
     */
    SysPost initValue(Long postId);

    /**
     * 更新
     * @param sysPost
     * @return
     */
    boolean update(SysPost sysPost);

    /**
     * 批量删除
     * @param postIds
     * @return
     */
    boolean batchDelete(String postIds);
}
