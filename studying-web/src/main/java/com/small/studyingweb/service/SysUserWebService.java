package com.small.studyingweb.service;

import com.small.common.base.enitity.SysRole;
import com.small.common.base.enitity.SysUser;
import com.small.system.query.SysUserQuery;

import java.util.List;
import java.util.Map;

/**
 * SysUser web层service
 * @author Liang
 */
public interface SysUserWebService {
    /**
     * 获得SysUser table的数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysUserQuery query);

    /**
     * 检查注册登录名是否已经存在
     * @param loginName
     * @return
     */
    boolean checkRegisterName(String loginName);


    /**
     * 通过UserId查询相关用户
     * @param userId
     * @return
     */
    SysUser findUserByUserId(Long userId);

    /**
     * 判断注册名是否唯一
     * @param loginName
     * @return
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 判断注册的电话是否唯一
     * @param user
     * @return
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 判断邮件是否唯一
     * @param user
     * @return
     */
    String checkEmailUnique(SysUser user);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    boolean insertUser(SysUser user);

    /**
     * 批量删除
     * @param user
     * @return
     */
    boolean batchDelete(String user);

    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    boolean update(SysUser sysUser);

    /**
     * 重置密码
     * @param sysUser
     * @return
     */
    boolean resetPwd(SysUser sysUser);

    /**
     * 通过RoleId查询绑定在该角色上的用户
     * @param query
     * @return
     */
    Map<String,Object> findBindUserByRoleId(SysUserQuery query);

    /**
     * 通过RoleId查询没有绑定在该角色上的用户
     * @return
     */
    Map<String,Object> findNotBindUser(SysUserQuery query);

    /**
     * 通过deptId查询相关用户
     * @param deptId
     * @return
     */
    List<SysUser> findExitUserById(Long deptId);
}
