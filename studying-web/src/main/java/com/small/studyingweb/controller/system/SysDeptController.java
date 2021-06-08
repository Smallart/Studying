package com.small.studyingweb.controller.system;

import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysDeptWebService;
import com.small.system.query.SysDepartmentQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SysDept controller层
 * @author Liang
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptWebService deptWebService;

    @GetMapping
    public String index(){
        return "back/system/back_dept";
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "deptName",required = false)String deptName,
                               @RequestParam(value = "deptStatus",required = false) Integer deptStatus){
        SysDepartmentQuery query = new SysDepartmentQuery();
        query.setDeptName(deptName);
        query.setDeptStatus(deptStatus);
        return success("success",deptWebService.addDeptSelectFind(query));
    }


    @GetMapping("/add")
    public String add(){
        return "back/system/back_dept/back_dept_add";
    }
}
