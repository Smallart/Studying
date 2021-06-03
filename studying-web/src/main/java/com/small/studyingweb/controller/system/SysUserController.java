package com.small.studyingweb.controller.system;

import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.query.SysUserQuery;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 用户 Controller层
 * @author Liang
 */
@Controller
@RequestMapping("/system")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserWebService userWebService;

    /**
     * 访问用户管理页面
     * @return
     */
    @GetMapping("/user")
    public String index(){
        return "/back/system/back_user";
    }

    /**
     * 访问用户添加页面
     * @return
     */
    @GetMapping("/userAdd")
    public String addIndex(){
        return "/back/system/back_user/back_user_add";
    }

    /**
     * 用户添加页面上
     * @return
     */
    @GetMapping("/userDept")
    public String addUserDeptIndex(){
        return "/back/system/back_user/back_user_add_dept";
    }

    @GetMapping("/user/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "loginName",required = false) String loginName,
                                   @RequestParam(value = "iphone",required = false) String iphone,
                                   @RequestParam(value = "userStatus",required = false) Integer userStatus,
                                   @RequestParam(value = "createStartTime",required = false) String createStartTime,
                                   @RequestParam(value = "createEndTime",required = false) String createEndTime,
                                   @RequestParam(value = "offset")Integer offset,
                                   @RequestParam(value = "limit")Integer limit,
                                   @RequestParam(value = "order",required = false)String  order,
                                   @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysUserQuery sysUserQuery = new SysUserQuery();
        sysUserQuery.setLoginName(loginName);
        sysUserQuery.setIphone(iphone);
        sysUserQuery.setUserStatus(userStatus);
        sysUserQuery.setCreateStartTime(DateUtils.parseDate(createStartTime));
        sysUserQuery.setCreateEndTime(DateUtils.parseDate(createEndTime));
        sysUserQuery.setOffset(offset);
        sysUserQuery.setLimit(limit);
        sysUserQuery.setOrder(order);
        sysUserQuery.setOrderStrategy(orderStrategy);
        return success("success",userWebService.find(sysUserQuery));
    }

    @GetMapping("/user/checkRegisterName")
    @ResponseBody
    public ResponseResult checkRegisterName(@RequestParam("loginName")String loginName){
        return success("success",userWebService.checkRegisterName(loginName));
    }
}
