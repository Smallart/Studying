package com.small.studyingweb.service;

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
}
