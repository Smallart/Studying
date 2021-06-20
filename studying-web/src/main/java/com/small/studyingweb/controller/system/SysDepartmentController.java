package com.small.studyingweb.controller.system;

import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysDeptWebService;
import com.small.common.base.enitity.SysDepartment;
import com.small.system.query.SysDepartmentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Department 的controller层
 * @author Liang
 */
@Controller
@RequestMapping("/system")
public class SysDepartmentController extends BaseController {

    @Autowired
    private SysDeptWebService deptWebService;

    @GetMapping("/dynamicGetDept")
    @ResponseBody
    public List<SysDepartment> dynamicGetDeptByPId(@RequestParam(value = "departmentId",required = false)Integer pId){
        SysDepartmentQuery query = new SysDepartmentQuery();
        if(pId==null){
            query.setParentId(0);
        }else{
            query.setParentId(pId);
        }
        query.setUserId(ShiroUtils.getUserId());
        return deptWebService.dynamicGetDeptByPId(query);
    }

    /**
     * 弹出框内的树结构
     * @param deptName
     * @return
     */
    @GetMapping("/deptSelect")
    @ResponseBody
    public List<SysDepartment> deptSelect(@RequestParam(value = "deptName",required = false) String deptName){
        SysDepartmentQuery query = new SysDepartmentQuery();
        query.setDeptName(deptName);
        return deptWebService.addDeptSelectFind(query);
    }

    @GetMapping("/dept/dataScope/{roleId}")
    @ResponseBody
    public List<SysDepartment> tagDataScope(@PathVariable("roleId") Long roleId){
        return deptWebService.tagDataScope(roleId);
    }
}
