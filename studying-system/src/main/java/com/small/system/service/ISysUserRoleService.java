package com.small.system.service;

import com.small.system.domain.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * SysUserRole service层
 * @author Liang
 */
public interface ISysUserRoleService {
    /**
     * 批量插入用户和角色的关系
     * @param sysUserRoles
     * @return
     */
    Integer batchInsert(List<SysUserRole> sysUserRoles);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer batchDelete(List<Long> ids);


    /**
     * 批量取消授权
     * @param info
     * @return
     */
    Integer batchDeleteBySysUserRoles(Map<String,Object> info);
}
