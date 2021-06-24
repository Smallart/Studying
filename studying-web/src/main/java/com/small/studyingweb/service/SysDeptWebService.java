package com.small.studyingweb.service;

import com.small.common.base.enitity.SysDepartment;
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

    /**
     * 标记数据范围
     * @param roleId
     * @return
     */
    List<SysDepartment> tagDataScope(Long roleId);

    /**
     * 通过Id查询dept
     * @param dpetId
     * @return
     */
    SysDepartment findDeptById(Long dpetId);

    /**
     * 更新dept
     * @param sysDept
     * @return
     */
    boolean update(SysDepartment sysDept);

    /**
     * 保存
     * @param sysDepartment
     * @return
     */
    boolean save(SysDepartment sysDepartment);

    /**
     *
     * @param deptId
     * @return
     */
    boolean delete(Long deptId);

    /**
     * 查询相关部门数量
     * @param deptId
     * @return
     */
    Integer selectDeptCount(Long deptId);

    /**
     * 查询dept名称是否存在
     * @param deptName
     * @param parentId
     * @return
     */
    boolean checkDeptInSameParent(String deptName, Long parentId,Long deptId);

    /**
     * 查询状态正常的子部门
     * @param departmentId
     * @return
     */
    List<SysDepartment> selectNormalChildrenDeptById(Long departmentId);
}
