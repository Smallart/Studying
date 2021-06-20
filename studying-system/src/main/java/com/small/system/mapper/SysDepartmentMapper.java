package com.small.system.mapper;

import com.small.common.base.BaseDao;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.query.SysDepartmentQuery;

import java.util.List;

/**
 * SysDepartment dao
 * @author Liang
 */
public interface SysDepartmentMapper extends BaseDao<SysDepartment, SysDepartmentQuery> {
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
