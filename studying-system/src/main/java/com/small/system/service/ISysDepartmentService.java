package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.query.SysDepartmentQuery;

import java.util.List;

/**
 * 部门Service
 * @author Liang
 */
public interface ISysDepartmentService extends BaseService<SysDepartment, SysDepartmentQuery> {
    /**
     * 通过Pid动态获得组织
     * @param query
     * @return
     */
    List<SysDepartment> dynamicGetDeptByPId(SysDepartmentQuery query);

    /**
     * 通过roleId查询绑定的dept
     * @param roleId
     * @return
     */
    List<SysDepartment> findDeptByRoleId(Long roleId);
}
