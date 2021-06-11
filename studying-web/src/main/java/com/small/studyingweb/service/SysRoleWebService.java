package com.small.studyingweb.service;

import com.small.system.domain.SysRole;
import com.small.system.query.SysRoleQuery;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Map;

/**
 * SysRole web层service
 * @author Liang
 */
public interface SysRoleWebService {
    /**
     * SysRole table数据
     * @param query
     * @return
     */
    Map<String,Object> find(SysRoleQuery query);

    /**
     * 通过userId查询相关角色
     * @param userId
     * @return
     */
    List<SysRole> findRoleById(Long userId);

    /**
     * 分配角色的面板
     * @param userId
     * @return
     */
    Map<String,Object> assignRoleTable(Long userId);
}
