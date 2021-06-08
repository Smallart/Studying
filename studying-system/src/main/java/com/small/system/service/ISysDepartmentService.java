package com.small.system.service;

import com.small.common.base.service.BaseService;
import com.small.system.domain.SysDepartment;
import com.small.system.query.SysDepartmentQuery;
import org.springframework.stereotype.Service;

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
}
