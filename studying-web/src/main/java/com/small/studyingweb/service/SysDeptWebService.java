package com.small.studyingweb.service;

import com.small.system.domain.SysDepartment;
import com.small.system.query.SysDepartmentQuery;

import java.util.List;

/**
 * Dept web层 service
 * @author Liang
 */
public interface SysDeptWebService {
    /**
     * 通过Pid动态获得组织
     * @param query
     * @return
     */
    List<SysDepartment> dynamicGetDeptByPId(SysDepartmentQuery query);

    /**
     * 弹出框的组织树
     * @param query
     * @return
     */
    List<SysDepartment> addDeptSelectFind(SysDepartmentQuery query);
}
