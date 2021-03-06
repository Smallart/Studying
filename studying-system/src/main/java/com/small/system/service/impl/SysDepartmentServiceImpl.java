package com.small.system.service.impl;

import com.small.common.base.service.impl.BaseServiceImpl;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.mapper.SysDepartmentMapper;
import com.small.system.mapper.SysMenuMapper;
import com.small.system.query.SysDepartmentQuery;
import com.small.system.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDepartment service层 impl
 * @author Liang
 */
@Service
public class SysDepartmentServiceImpl extends BaseServiceImpl<SysDepartment,SysDepartmentQuery, SysMenuMapper> implements ISysDepartmentService{
    @Autowired
    private SysDepartmentMapper departmentMapper;

    @PostConstruct
    private  void init(){
        setDao(departmentMapper);
    }

    @Override
    public List<SysDepartment> dynamicGetDeptByPId(SysDepartmentQuery query) {
        return departmentMapper.dynamicGetDeptByPId(query);
    }

    @Override
    public List<SysDepartment> findDeptByRoleId(Long roleId) {
        return departmentMapper.findDeptByRoleId(roleId);
    }

    @Override
    public SysDepartment findDeptById(Long deptId) {
        return departmentMapper.findDeptById(deptId);
    }

    @Override
    public Integer selectDeptCount(SysDepartmentQuery query) {
        return departmentMapper.selectDeptCount(query);
    }

    @Override
    public SysDepartment checkDeptInSameParent(SysDepartmentQuery query) {
        return departmentMapper.checkDeptInSameParent(query);
    }

    @Override
    public List<SysDepartment> selectNormalChildrenDeptById(Long departmentId) {
        return departmentMapper.selectNormalChildrenDeptById(departmentId);
    }

    @Override
    public List<SysDepartment> selectSubDepts(Long departmentId) {
        return departmentMapper.selectSubDepts(departmentId);
    }

    @Override
    public void batchUpdateDepts(List<SysDepartment> sysDepartments) {
        departmentMapper.batchUpdateDepts(sysDepartments);
    }
}
