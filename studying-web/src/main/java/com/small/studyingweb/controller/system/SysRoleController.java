package com.small.studyingweb.controller.system;

import com.small.common.base.BaseObject;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysRoleWebService;
import com.small.system.query.SysRoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SysRole controller 层
 * @author Liang
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleWebService roleWebService;

    @GetMapping
    public String index(){
        return "back/system/back_role";
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "roleName",required = false) String roleName,
                               @RequestParam(value = "roleKey",required = false) String roleKey,
                               @RequestParam(value = "roleStatus",required = false) Integer status,
                               @RequestParam(value = "createStartTime",required = false) String createStartTime,
                               @RequestParam(value = "createEndTime",required = false) String createEndTime,
                               @RequestParam(value = "offset")Integer offset,
                               @RequestParam(value = "limit")Integer limit,
                               @RequestParam(value = "order",required = false)String  order,
                               @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        sysRoleQuery.setRoleName(roleName);
        sysRoleQuery.setRoleKey(roleKey);
        sysRoleQuery.setStatus(status);
        sysRoleQuery.setCreateStartTime(DateUtils.parseDate(createStartTime));
        sysRoleQuery.setCreateEndTime(DateUtils.parseDate(createEndTime));
        sysRoleQuery.setOffset(offset);
        sysRoleQuery.setLimit(limit);
        sysRoleQuery.setOrder(order);
        sysRoleQuery.setOrderStrategy(orderStrategy);
        return success("success",roleWebService.find(sysRoleQuery));
    }

    @GetMapping("/add")
    public String add(){
        return "back/system/back_role/back_role_add";
    }
}
