package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysDeptWebService;
import com.small.system.domain.SysDepartment;
import com.small.system.query.SysDepartmentQuery;
import com.small.system.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysDept web service实现
 */
@Service
public class SysDeptWebServiceImpl implements SysDeptWebService {
    @Autowired
    private ISysDepartmentService departmentService;
    @Override
    public List<SysDepartment> dynamicGetDeptByPId(SysDepartmentQuery query) {
        return departmentService.dynamicGetDeptByPId(query);
    }

    @Override
    public List<SysDepartment> addDeptSelectFind(SysDepartmentQuery query) {
        return departmentService.find(query);
    }
}
