package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.small.common.base.enitity.SysUser;
import com.small.common.constant.UserConstants;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.service.SysPasswordService;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.query.SysUserQuery;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        return "back/system/back_user";
    }

    /**
     * 访问用户添加页面
     * @return
     */
    @GetMapping("user/add")
    public String addIndex(){
        return "back/system/back_user/add";
    }

    /**
     * 用户添加页面上
     * @return
     */
    @GetMapping("/userDept")
    public String addUserDeptIndex(){
        return "/back/system/back_user/back_user_add_dept";
    }

    /**
     * 查询
     * @param loginName
     * @param iphone
     * @param userStatus
     * @param createStartTime
     * @param createEndTime
     * @param offset
     * @param limit
     * @param order
     * @param orderStrategy
     * @return
     */
    @GetMapping("/user/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "loginName",required = false) String loginName,
                                   @RequestParam(value = "iphone",required = false) String iphone,
                                   @RequestParam(value = "userStatus",required = false) Integer userStatus,
                                   @RequestParam(value = "createStartTime",required = false) String createStartTime,
                                   @RequestParam(value = "createEndTime",required = false) String createEndTime,
                                   @RequestParam(value = "deptId",required = false) String deptId,
                                   @RequestParam(value = "offset")Integer offset,
                                   @RequestParam(value = "limit")Integer limit,
                                   @RequestParam(value = "order",required = false)String  order,
                                   @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysUserQuery sysUserQuery = new SysUserQuery();
        sysUserQuery.setLoginName(loginName);
        sysUserQuery.setPhone(iphone);
        sysUserQuery.setUserStatus(userStatus);
        sysUserQuery.setCreateStartTime(DateUtils.parseDate(createStartTime));
        sysUserQuery.setCreateEndTime(DateUtils.parseDate(createEndTime));
        sysUserQuery.setOffset(offset);
        sysUserQuery.setLimit(limit);
        sysUserQuery.setOrder(order);
        sysUserQuery.setOrderStrategy(orderStrategy);
        sysUserQuery.setSearch(deptId);
        return success("success",userWebService.find(sysUserQuery));
    }

    /**
     * 检查注册名称
     * @param loginName
     * @return
     */
    @GetMapping("/user/checkRegisterName")
    @ResponseBody
    public ResponseResult checkRegisterName(@RequestParam("loginName")String loginName){
        return success("success",userWebService.checkRegisterName(loginName));
    }

    /**
     * 保存用户信息
     * @param userJson
     * @return
     */
    @PostMapping("/user/save")
    @ResponseBody
    public ResponseResult save(@RequestBody String userJson)
    {
        SysUser sysUser = JSONObject.parseObject(userJson, SysUser.class);
        if (UserConstants.USER_NAME_NOT_UNIQUE.equalsIgnoreCase(userWebService.checkLoginNameUnique(sysUser.getLoginName()))){
            return error("新增账号："+sysUser.getLoginName()+"失败，账号已经被注册");
        }
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equalsIgnoreCase(userWebService.checkPhoneUnique(sysUser))){
            return error("新增账号："+sysUser.getLoginName()+"失败，电话号码已经被注册");
        }
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equalsIgnoreCase(userWebService.checkEmailUnique(sysUser))){
            return error("新增账号："+sysUser.getLoginName()+"失败，邮箱已经被注册");
        }
        if (userWebService.insertUser(sysUser)){
            return success("success",sysUser);
        }
        return error("error");
    }

    @GetMapping("/user/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId,ModelMap mmp){
        mmp.put("user",userWebService.findUserByUserId(userId));
        return "back/system/back_user/edit";
    }

    /**
     * 打开重新设置密码界面
     * @param userName
     * @param userId
     * @param mmp
     * @return
     */
    @GetMapping("/user/resetPwd/{userName}/{userId}")
    public String resetPwd(@PathVariable("userName")String userName,
                           @PathVariable("userId")Integer userId,
                           ModelMap mmp){
        mmp.put("userName",userName);
        mmp.put("userId",userId);
        return "back/system/back_user/reset_pwd";
    }

    /**
     * 打开重新分配角色页面
     * @param userId
     * @param mmp
     * @return
     */
    @GetMapping("/user/assignRole/{userId}")
    public String assignRole(@PathVariable("userId")Integer userId,ModelMap mmp){
        mmp.put("userId",userId);
        return "back/system/back_user/assign_role";
    }

    /**
     *  完善分配角色页面信息
     * @param userId
     * @return
     */
    @GetMapping("/user/assignRoleInput/{userId}")
    @ResponseBody
    public SysUser assignRoleFullInput(@PathVariable("userId")Long userId){
        return userWebService.findUserByUserId(userId);
    }

    @GetMapping("/user/delete/{ids}")
    @ResponseBody
    public ResponseResult batchDelete(@PathVariable("ids")String ids){
        return userWebService.batchDelete(ids)?success("删除成功"):error("删除成功");
    }

    @PostMapping("/user/edit")
    @ResponseBody
    public ResponseResult update(@RequestBody String userJson){
        SysUser sysUser = JSONObject.parseObject(userJson, SysUser.class);
        if (sysUser.getIphone()!=null&&UserConstants.USER_PHONE_NOT_UNIQUE.equalsIgnoreCase(userWebService.checkPhoneUnique(sysUser))){
            return error("新增账号："+sysUser.getLoginName()+"失败，电话号码已经被注册");
        }
        if (sysUser.getIphone()!=null&&UserConstants.USER_PHONE_NOT_UNIQUE.equalsIgnoreCase(userWebService.checkEmailUnique(sysUser))){
            return error("新增账号："+sysUser.getLoginName()+"失败，邮箱已经被注册");
        }
        return userWebService.update(sysUser)?success("修改成功"):error("修改失败");
    }

    /**
     * 重置密码
     * @param userJson
     * @return
     */
    @PostMapping("/user/resetPwd")
    @ResponseBody
    public ResponseResult resetPwd(@RequestBody String userJson){
        SysUser sysUser = JSONObject.parseObject(userJson, SysUser.class);
        return userWebService.resetPwd(sysUser)?success("密码修改成功"):error("密码修改失败");
    }

    /**
     * 用户分配角色
     * @param userJson
     * @return
     */
    @PostMapping("/user/assignRoles")
    @ResponseBody
    public ResponseResult assignRoles(@RequestBody String userJson){
        SysUser sysUser = JSONObject.parseObject(userJson, SysUser.class);
        return userWebService.update(sysUser)?success("分配角色成功"):error("分配角色失败");
    }
}
