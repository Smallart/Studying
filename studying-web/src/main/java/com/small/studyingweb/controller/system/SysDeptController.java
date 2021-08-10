package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.small.common.base.enitity.SysDepartment;
import com.small.common.constant.UserConstants;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.util.AuthorizationUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysDeptWebService;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.query.SysDepartmentQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * SysDept controller层
 * @author Liang
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptWebService deptWebService;

    @Autowired
    private SysUserWebService userWebService;

    @GetMapping
    @RequiresPermissions("system:dept:view")
    public String index(){
        return "back/system/back_dept";
    }

    @GetMapping("/find")
    @RequiresPermissions("system:dept:list")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "deptName",required = false)String deptName,
                               @RequestParam(value = "deptStatus",required = false) Integer deptStatus){
        SysDepartmentQuery query = new SysDepartmentQuery();
        query.setDeptName(deptName);
        query.setDeptStatus(deptStatus);
        return success("success",deptWebService.addDeptSelectFind(query));
    }


    @GetMapping("/add")
    @RequiresPermissions("system:dept:add")
    public String add(@RequestParam(value = "deptId",required = false) Long deptId,ModelMap mmp){
        mmp.put("deptId",deptId);
        return "back/system/back_dept/add";
    }


    @GetMapping("/edit")
    @RequiresPermissions("system:dept:edit")
    public String edit(@RequestParam("deptId")Long deptId, ModelMap mmp){
        mmp.put("deptId",deptId);
        return "back/system/back_dept/edit";
    }

    @GetMapping("/init/{deptId}")
    @ResponseBody
    public SysDepartment editInit(@PathVariable("deptId")Long deptId){
        return deptWebService.findDeptById(deptId);
    }

    @PostMapping("/edit")
    @RequiresPermissions("system:dept:edit")
    @ResponseBody
    public ResponseResult edit(@RequestBody String json){
        SysDepartment sysDepartment = JSONObject.parseObject(json,SysDepartment.class);
        if(sysDepartment.getParentId().longValue()==sysDepartment.getDepartmentId().longValue()){
            return error("父部门不能是自己");
        }
        if (deptWebService.checkDeptInSameParent(sysDepartment.getDepartmentName(),sysDepartment.getParentId(),sysDepartment.getDepartmentId())){
            return error("新增"+sysDepartment.getDepartmentName()+"失败，命名重复");
        }
        if(UserConstants.DEPT_DISABLE.equalsIgnoreCase(sysDepartment.getStatus()+"")&&
                deptWebService.selectNormalChildrenDeptById(sysDepartment.getDepartmentId()).size()>0){
            return error("该部门包含未停用子部门");
        }
        sysDepartment.setUpdateBy(ShiroUtils.getLoginName());
        return deptWebService.update(sysDepartment)?success("修改部门成功"):error("修改部门失败");
    }

    /**
     * 保存
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("system:dept:save")
    @ResponseBody
    public ResponseResult save(@RequestBody String json){
        SysDepartment sysDepartment = JSONObject.parseObject(json, SysDepartment.class);
        if (deptWebService.checkDeptInSameParent(sysDepartment.getDepartmentName(),sysDepartment.getParentId(),null)){
            return error("新增"+sysDepartment.getDepartmentName()+"失败，该部门已存在");
        }
        return deptWebService.save(sysDepartment)?success("保存部门成功"):error("保存部门失败");
    }

    @GetMapping("/del")
    @RequiresPermissions("system:dept:del")
    @ResponseBody
    public ResponseResult del(@RequestParam("deptId") Long deptId){
        if (deptWebService.selectDeptCount(deptId)>0){
            return error("当前部门包含子部门无法删除");
        }
        if (userWebService.findExitUserById(deptId).size()>0){
            return error("当前部门分配了用户无法删除");
        }
        return deptWebService.delete(deptId)?success("删除成功"):error("删除失败");
    }

    @GetMapping("/checkDeptInSameParent")
    @ResponseBody
    public boolean checkDeptInSameParent(@RequestParam("deptName")String deptName,
                                         @RequestParam("parentId")Long parentId,
                                         @RequestParam(value = "deptId",required = false)Long deptId){
        return deptWebService.checkDeptInSameParent(deptName,parentId,deptId);
    }
}
