package com.small.studyingweb.service.impl;

import com.small.studyingweb.service.SysDeptWebService;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.query.SysDepartmentQuery;
import com.small.system.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<SysDepartment> tagDataScope(Long roleId) {
        List<SysDepartment> sysDepartments = departmentService.find(new SysDepartmentQuery());
        List<SysDepartment> deptByRoleId = departmentService.findDeptByRoleId(roleId);
        List<Long> ids = deptByRoleId.stream().map(item -> item.getDepartmentId()).collect(Collectors.toList());
        sysDepartments.forEach(item->{
            if (ids.contains(item.getDepartmentId())){
                item.setChecked(true);
            }
        });
        return sysDepartments;
    }
}
