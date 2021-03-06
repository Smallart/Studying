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

    /**
     * 通过deptId查询Dept
     * @param deptId
     * @return
     */
    SysDepartment findDeptById(Long deptId);

    /**
     * 查询相关部门数量
     * @param query
     * @return
     */
    Integer selectDeptCount(SysDepartmentQuery query);

    /**
     * 检测是否部门名称是否重复
     * @param query
     * @return
     */
    SysDepartment checkDeptInSameParent(SysDepartmentQuery query);

    /**
     * 查询状态正常的子部门
     * @param departmentId
     * @return
     */
    List<SysDepartment> selectNormalChildrenDeptById(Long departmentId);

    /**
     * 查询相关祖先子部门
     * @param departmentId
     * @return
     */
    List<SysDepartment> selectSubDepts(Long departmentId);

    /**
     * 批量更新部门
     */
    void batchUpdateDepts(List<SysDepartment> sysDepartments);
}
