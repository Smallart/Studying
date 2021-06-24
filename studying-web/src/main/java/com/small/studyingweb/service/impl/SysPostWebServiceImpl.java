package com.small.studyingweb.service.impl;

import com.small.common.exceptions.BusinessException;
import com.small.studyingweb.service.SysPostWebService;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;
import com.small.system.service.ISysPostService;
import com.small.system.service.ISysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SysPost web 实现层
 * @author Liang
 */
@Service
public class SysPostWebServiceImpl implements SysPostWebService {

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysUserPostService userPostService;

    @Override
    public Map<String, Object> find(SysPostQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",postService.find(query));
        map.put("total",postService.count(query));
        return map;
    }

    @Override
    public List<SysPost> findPostByUserId(Integer userId) {
        List<SysPost> userPosts = postService.findPostByUserId(userId);
        List<SysPost> sysPosts = postService.find(new SysPostQuery());
        List<Long> ids = userPosts.stream().map(item -> item.getPostId()).collect(Collectors.toList());
        sysPosts.forEach(item->{
            if (ids.contains(item.getPostId())){
                item.setFlag(true);
            }
        });
        return sysPosts;
    }

    @Override
    public Boolean checkPostNameUnique(String postName, Long postId) {
        SysPostQuery sysPostQuery = new SysPostQuery();
        sysPostQuery.setPostName(postName);
        sysPostQuery.setPostId(postId);
        SysPost sysPost = postService.checkUnique(sysPostQuery);
        if (sysPost==null){
            return false;
        }
        if(postId ==null){
            return true;
        }
        return sysPost.getPostId().longValue()==postId.longValue()?false:true;
    }

    @Override
    public Boolean checkPostCodeUnique(String postCode, Long postId) {
        SysPostQuery sysPostQuery = new SysPostQuery();
        sysPostQuery.setPostCode(postCode);
        sysPostQuery.setPostId(postId);
        SysPost sysPost = postService.checkUnique(sysPostQuery);
        if (sysPost==null){
            return false;
        }
        if(postId==null){
            return true;
        }
        return sysPost.getPostId().longValue()==postId.longValue()?false:true;
    }

    @Override
    @Transactional
    public boolean save(SysPost sysPost) {
        return postService.save(sysPost)>0?true:false;
    }

    @Override
    public SysPost initValue(Long postId) {
        return postService.findPostById(postId);
    }

    @Override
    public boolean update(SysPost sysPost) {
        return postService.update(sysPost)>0?true:false;
    }

    @Override
    @Transactional
    public boolean batchDelete(String postIds) {
        List<Long> postIdList =Arrays.stream(postIds.split(",")).map(item -> Long.parseLong(item)).collect(Collectors.toList());
        for (Long postId : postIdList) {
            SysPost post = postService.findPostById(postId);
            if(userPostService.checkUserNumByPostId(postId)>0){
                throw new BusinessException(post.getPostName()+"已被分配");
            }
        }
        return postService.batchDelete(postIdList)>0?true:false;
    }
}
