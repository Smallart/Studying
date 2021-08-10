package com.small.studyingweb.service.impl;

import com.small.common.anno.DataScope;
import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.service.SysDeptWebService;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.query.SysDepartmentQuery;
import com.small.system.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SysDept web service实现
 */
@Service
public class SysDeptWebServiceImpl implements SysDeptWebService{
    @Autowired
    private ISysDepartmentService departmentService;
    @Override
    @DataScope
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

    @Override
    public SysDepartment findDeptById(Long deptId) {
        return departmentService.findDeptById(deptId);
    }

    @Override
    @Transactional
    public boolean update(SysDepartment sysDept) {
        SysDepartment parentDept = findDeptById(sysDept.getParentId());
        SysDepartment dept = findDeptById(sysDept.getDepartmentId());
        //互换了父结点
        if (dept.getParentId().longValue() != parentDept.getDepartmentId().longValue()){
            String newAncestors = parentDept.getAncestors()+","+parentDept.getDepartmentId();
            sysDept.setAncestors(newAncestors);
            String oldAncestors = dept.getAncestors()+","+dept.getDepartmentId();
            updateSubDepts(newAncestors,oldAncestors,sysDept);
        }
        return departmentService.update(sysDept)>0?true:false;
    }

    public void updateSubDepts(String newAncestors,String oldAncestors,SysDepartment sysDept){
        List<SysDepartment> childrenDept = departmentService.selectSubDepts(sysDept.getDepartmentId());
        for (SysDepartment sysDepartment : childrenDept) {
            sysDepartment.setAncestors(sysDepartment.getAncestors().replace(oldAncestors,newAncestors));
        }
        if (childrenDept.size()>0){
            departmentService.batchUpdateDepts(childrenDept);
        }
    }

    @Override
    @Transactional
    public boolean save(SysDepartment sysDepartment) {
        sysDepartment.setCreateBy(ShiroUtils.getLoginName());
        departmentService.save(sysDepartment);
        SysDepartment parentDept = new SysDepartment();
        parentDept.setDepartmentId(sysDepartment.getParentId());
        parentDept.setUpdateBy(ShiroUtils.getLoginName());
        parentDept.setIsParent(true);
        return departmentService.update(parentDept)>0?true:false;
    }

    @Override
    public boolean delete(Long deptId) {
        return departmentService.batchDelete(Arrays.asList(deptId))>0?true:false;
    }

    @Override
    public Integer selectDeptCount(Long deptId) {
        SysDepartmentQuery query = new SysDepartmentQuery();
        query.setParentId(deptId);
        return departmentService.selectDeptCount(query);
    }

    @Override
    public boolean checkDeptInSameParent(String deptName, Long parentId,Long deptId) {
        SysDepartmentQuery query = new SysDepartmentQuery();
        query.setParentId(parentId);
        query.setDeptName(deptName);
        SysDepartment sysDepartment = departmentService.checkDeptInSameParent(query);
        if (sysDepartment==null){
            return false;
        }
        if (deptId==null){
            return true;
        }
        return sysDepartment.getDepartmentId().longValue()==deptId.longValue()?false:true;
    }

    @Override
    public List<SysDepartment> selectNormalChildrenDeptById(Long departmentId) {
        return departmentService.selectNormalChildrenDeptById(departmentId);
    }
}
