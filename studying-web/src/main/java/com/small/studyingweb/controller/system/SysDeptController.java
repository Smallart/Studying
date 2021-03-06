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
 * SysDept controllerå±‚
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
            return error("çˆ¶éƒ¨é—¨ä¸?èƒ½æ˜¯è‡ªå·±");
        }
        if (deptWebService.checkDeptInSameParent(sysDepartment.getDepartmentName(),sysDepartment.getParentId(),sysDepartment.getDepartmentId())){
            return error("æ–°å¢ž"+sysDepartment.getDepartmentName()+"å¤±è´¥ï¼Œå‘½å??é‡?å¤?");
        }
        if(UserConstants.DEPT_DISABLE.equalsIgnoreCase(sysDepartment.getStatus()+"")&&
                deptWebService.selectNormalChildrenDeptById(sysDepartment.getDepartmentId()).size()>0){
            return error("è¯¥éƒ¨é—¨åŒ…å?«æœªå?œç”¨å­?éƒ¨é—¨");
        }
        sysDepartment.setUpdateBy(ShiroUtils.getLoginName());
        return deptWebService.update(sysDepartment)?success("ä¿®æ”¹éƒ¨é—¨æˆ?åŠŸ"):error("ä¿®æ”¹éƒ¨é—¨å¤±è´¥");
    }

    /**
     * ä¿?å­˜
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("system:dept:save")
    @ResponseBody
    public ResponseResult save(@RequestBody String json){
        SysDepartment sysDepartment = JSONObject.parseObject(json, SysDepartment.class);
        if (deptWebService.checkDeptInSameParent(sysDepartment.getDepartmentName(),sysDepartment.getParentId(),null)){
            return error("æ–°å¢ž"+sysDepartment.getDepartmentName()+"å¤±è´¥ï¼Œè¯¥éƒ¨é—¨å·²å­˜åœ¨");
        }
        return deptWebService.save(sysDepartment)?success("ä¿?å­˜éƒ¨é—¨æˆ?åŠŸ"):error("ä¿?å­˜éƒ¨é—¨å¤±è´¥");
    }

    @GetMapping("/del")
    @RequiresPermissions("system:dept:del")
    @ResponseBody
    public ResponseResult del(@RequestParam("deptId") Long deptId){
        if (deptWebService.selectDeptCount(deptId)>0){
            return error("å½“å‰?éƒ¨é—¨åŒ…å?«å­?éƒ¨é—¨æ— æ³•åˆ é™¤");
        }
        if (userWebService.findExitUserById(deptId).size()>0){
            return error("å½“å‰?éƒ¨é—¨åˆ†é…?äº†ç”¨æˆ·æ— æ³•åˆ é™¤");
        }
        return deptWebService.delete(deptId)?success("åˆ é™¤æˆ?åŠŸ"):error("åˆ é™¤å¤±è´¥");
    }

    @GetMapping("/checkDeptInSameParent")
    @ResponseBody
    public boolean checkDeptInSameParent(@RequestParam("deptName")String deptName,
                                         @RequestParam("parentId")Long parentId,
                                         @RequestParam(value = "deptId",required = false)Long deptId){
        return deptWebService.checkDeptInSameParent(deptName,parentId,deptId);
    }
}
