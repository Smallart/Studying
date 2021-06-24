package com.small.studyingweb.service.impl;

import com.small.common.anno.DataScope;
import com.small.common.base.enitity.SysRole;
import com.small.common.base.enitity.SysUser;
import com.small.common.constant.UserConstants;
import com.small.common.exceptions.BusinessException;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.service.SysPasswordService;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.domain.SysUserPost;
import com.small.system.domain.SysUserRole;
import com.small.system.query.SysUserQuery;
import com.small.system.service.ISysUserPostService;
import com.small.system.service.ISysUserRoleService;
import com.small.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SysUser web层serviceImpl
 * @author Liang
 */
@Service
public class SysUserWebServiceImpl implements SysUserWebService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserPostService sysUserPostService;

    @Autowired
    private SysPasswordService passwordService;

    @Override
    public Map<String, Object> find(SysUserQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysUserService.find(query));
        map.put("total",sysUserService.count(query));
        return map;
    }

    @Override
    public boolean checkRegisterName(String loginName) {
        SysUserQuery query = new SysUserQuery();
        query.setLoginName(loginName);
        return sysUserService.checkInputUnique(query)==null?false:true;
    }

    @Override
    public SysUser findUserByUserId(Long userId) {
        SysUserQuery query = new SysUserQuery();
        query.setId(userId);
        List<SysUser> sysUsers = sysUserService.find(query);
        return sysUsers!=null&&sysUsers.size()>0?sysUsers.get(0):null;
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        if (checkRegisterName(loginName)){
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        SysUserQuery query = new SysUserQuery();
        query.setPhone(user.getIphone());
        SysUser sysUser = sysUserService.checkInputUnique(query);
        if (sysUser!=null&&sysUser.getUserId().longValue()!=user.getUserId().longValue()){
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        SysUserQuery query = new SysUserQuery();
        query.setEmail(user.getEmail());
        SysUser sysUser = sysUserService.checkInputUnique(query);
        if (sysUser!=null&&sysUser.getUserId().longValue()!=user.getUserId().longValue()){
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    @Override
    @Transactional
    public boolean insertUser(SysUser user) {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user,user.getPassword()));
        user.setCreateBy(ShiroUtils.getLoginName());
        Integer row = sysUserService.save(user);
        Long[] roles = user.getRoles();
        Long[] postIds = user.getPostIds();
        insertUserPost(postIds,user.getUserId());
        insertUserRole(roles,user.getUserId());
        return row>0?true:false;
    }

    @Override
    @Transactional
    public boolean batchDelete(String ids) {
        List<Long> userIds = Arrays.asList(ids.split(",")).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
        for (Long userId : userIds) {
            checkUserAllowed(userId);
        }
        sysUserRoleService.batchDelete(userIds);
        sysUserPostService.batchDelete(userIds);
        return sysUserService.batchDelete(userIds)>0?true:false;
    }

    @Override
    @Transactional
    public boolean update(SysUser sysUser) {
        checkUserAllowed(sysUser.getUserId());
        updateUserRole(sysUser.getUserId(),sysUser.getRoles());
        updateUserPost(sysUser.getUserId(),sysUser.getPostIds());
        sysUser.setUpdateBy(ShiroUtils.getLoginName());
        return sysUserService.update(sysUser)>0?true:false;
    }

    @Override
    public boolean resetPwd(SysUser sysUser) {
        checkUserAllowed(sysUser.getUserId());
        sysUser.setPwdUpdateTime(DateUtils.getNowDate());
        sysUser.setUpdateBy(ShiroUtils.getLoginName());
        sysUser.setSalt(ShiroUtils.randomSalt());
        sysUser.setPassword(passwordService.encryptPassword(sysUser,sysUser.getPassword()));
        Integer row = sysUserService.update(sysUser);
        if (row>0){
            if (sysUser.getUserId().longValue()==ShiroUtils.getUserId().longValue()){
                ShiroUtils.setPrincipal(sysUser);
            }
        }
        return row>0?true:false;
    }

    @Override
    @DataScope(deptAlias = "d",userAlias = "u")
    public Map<String,Object> findBindUserByRoleId(SysUserQuery query) {
        Map<String,Object> map = new HashMap<>();
        List<SysUser> sysUsers = sysUserService.findBindUserByRoleId(query);
        map.put("data",sysUsers);
        map.put("total",sysUsers.size());
        return map;
    }

    @Override
    @DataScope(deptAlias = "d",userAlias = "u")
    public Map<String, Object> findNotBindUser(SysUserQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysUserService.findNotBindUserByRoleId(query));
        map.put("total",sysUserService.findNotBindUserCount(query));
        return map;
    }

    @Override
    public List<SysUser> findExitUserById(Long deptId) {
        SysUserQuery query = new SysUserQuery();
        query.setDeptId(deptId);
        return sysUserService.find(query);
    }

    /**
     * 更新user和role之间的关系
     * @param userId
     * @param roles
     */
    public void updateUserRole(Long userId,Long[] roles){
        if (roles==null||roles.length<1){
            return;
        }
        List<Long> userIds = Arrays.asList(userId);
        sysUserRoleService.batchDelete(userIds);
        List<SysUserRole> sysRoles = new ArrayList<>();
        for (Long role : roles) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role);
            sysUserRole.setUserId(userId);
            sysRoles.add(sysUserRole);
        }
        if (sysRoles.size()>0){
            sysUserRoleService.batchInsert(sysRoles);
        }
    }

    /**
     * 更新user和post之间的关系
     * @param userId
     * @param postIds
     */
    public void updateUserPost(Long userId,Long[] postIds){
        if (postIds==null||postIds.length<1){
            return;
        }
        List<Long> userIds = Arrays.asList(userId);
        sysUserPostService.batchDelete(userIds);
        List<SysUserPost> sysUserPosts = new ArrayList<>();
        for (Long postId : postIds) {
            SysUserPost sysUserPost = new SysUserPost();
            sysUserPost.setPostId(postId);
            sysUserPost.setUserId(userId);
            sysUserPosts.add(sysUserPost);
        }
        if (sysUserPosts.size()>0){
            sysUserPostService.batchInsert(sysUserPosts);
        }
    }

    /**
     * 检查该操作是否允许
     * @param userId
     */
    public void checkUserAllowed(Long userId){
        if (SysUser.isAdmin(userId)){
            throw new BusinessException("不允许对管理员账号进行操作");
        }
    }

    /**
     * 建立用户与岗位的关系
     * @param postIds
     * @param userId
     */
    public void insertUserPost(Long[] postIds,Long userId){
        List<SysUserPost> userPosts = new ArrayList<>();
        for (Long postId : postIds) {
            SysUserPost sysUserPost = new SysUserPost();
            sysUserPost.setPostId(postId);
            sysUserPost.setUserId(userId);
            userPosts.add(sysUserPost);
        }
        if (userPosts.size()>0){
            sysUserPostService.batchInsert(userPosts);
        }
    }

    /**
     * 建立用户和角色的关系
     * @param roles
     * @param userId
     */
    public void insertUserRole(Long[] roles,Long userId){
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long role : roles) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role);
            sysUserRole.setUserId(userId);
            userRoles.add(sysUserRole);
        }
        if (userRoles.size()>0){
            sysUserRoleService.batchInsert(userRoles);
        }
    }
}
