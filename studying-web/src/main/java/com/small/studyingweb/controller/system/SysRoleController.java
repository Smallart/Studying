package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.small.common.constant.UserConstants;
import com.small.common.exceptions.BusinessException;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.util.AuthorizationUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysRoleWebService;
import com.small.studyingweb.service.SysUserWebService;
import com.small.system.domain.SysMenu;
import com.small.common.base.enitity.SysRole;
import com.small.system.domain.SysUserRole;
import com.small.system.query.SysRoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysRole controller 层
 * @author Liang
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleWebService roleWebService;

    @Autowired
    private SysUserWebService userWebService;

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
        return "back/system/back_role/add";
    }

    @GetMapping("/selectRolesByUserId/{userId}")
    @ResponseBody
    public List<SysRole> selectRolesByUserId(@PathVariable("userId")Long userId){
        return roleWebService.tagRoles(userId);
    };

    @GetMapping("/assignRoleTable/{userId}")
    @ResponseBody
    public ResponseResult assignRoleTable(@PathVariable("userId") Long userId){
        return success("success",roleWebService.assignRoleTable(userId));
    }

    /**
     * 数据权限范围页面
     * @param roleId
     * @param mmap
     * @return
     */
    @GetMapping("/dataScope/{roleId}")
    public String dataScopeIndex(@PathVariable("roleId")Long roleId, ModelMap mmap){
        mmap.put("SysRole",roleWebService.findRoleById(roleId));
        return "back/system/back_role/data_scope";
    }

    /**
     * 分配用户页面
     * @param roleId
     * @return
     */
    @GetMapping("/assignUsers/{roleId}")
    public String assignUsers(@PathVariable("roleId")Long roleId,ModelMap mmp){
        mmp.put("roleId",roleId);
        return "back/system/back_role/assign_users";
    }

    @GetMapping("/assignUsers/add/{roleId}")
    public String assignUsersAdd(@PathVariable("roleId")Long roleId,ModelMap mmp){
        mmp.put("roleId",roleId);
        return "back/system/back_role/assign_users_add";
    }

    @PostMapping("/authUser")
    public ResponseResult authUser(@RequestBody String json){
        return success();
    }

    /**
     * 检查注册的roleName是否唯一
     * @param roleName
     * @return
     */
    @GetMapping("/checkRoleNameUnique")
    @ResponseBody
    public ResponseResult checkRoleNameUnique(@RequestParam("roleName")String roleName){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        return success("success",roleWebService.checkRoleNameUnique(sysRole));
    }

    /**
     * 检查注册的roleKey是否唯一
     * @param roleKey
     * @return
     */
    @GetMapping("/checkRoleKeyUnique/{roleKey}")
    @ResponseBody
    public ResponseResult checkRoleKeyUnique(@PathVariable("roleKey")String roleKey){
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(roleKey);
        return success("success",roleWebService.checkRoleKeyUnique(sysRole));
    }

    /**
     * 保存
     * @param sysRoleJson
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResponseResult save(@RequestBody String sysRoleJson){
        SysRole sysRole = JSONObject.parseObject(sysRoleJson, SysRole.class);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equalsIgnoreCase(roleWebService.checkRoleNameUnique(sysRole)?UserConstants.ROLE_NAME_NOT_UNIQUE:UserConstants.ROLE_NAME_UNIQUE)){
            return error("注册角色"+sysRole.getRoleName()+"失败，角色名称已存在");
        }
        if (UserConstants.ROLE_KEY_NOT_UNIQUE.equalsIgnoreCase(roleWebService.checkRoleKeyUnique(sysRole)?UserConstants.ROLE_KEY_NOT_UNIQUE:UserConstants.ROLE_KEY_UNIQUE)){
            return error("注册角色"+sysRole.getRoleName()+"失败，角色权限已存在");
        }
        sysRole.setCreateBy(ShiroUtils.getLoginName());
        boolean save = roleWebService.save(sysRole);
        //清除所有对象缓存
        AuthorizationUtils.clearAllCacheAuthorizationInfo();
        return save?success("保存成功"):error("保存失败");
    }

    /**
     * 对页面进行赋值和修改相关页面
     * @return
     */
    @GetMapping("/modify/{roleId}")
    public String modify(@PathVariable("roleId")Long roleId,ModelMap mmp){
        mmp.put("roleId",roleId);
        return "back/system/back_role/edit";
    }

    /**
     * 删除
     * @param roleIds
     * @return
     */
    @GetMapping("/delete/{roleIds}")
    @ResponseBody
    public ResponseResult delete(@PathVariable("roleIds") String roleIds){
        return roleWebService.delete(roleIds)?success():error();
    }

    @GetMapping("/edit/{roleId}")
    @ResponseBody
    public ResponseResult roleInfo(@PathVariable("roleId") Long roleId){
        return success("success",roleWebService.findRoleById(roleId));
    }

    @GetMapping("/edit/menuTree/{roleId}")
    @ResponseBody
    public List<SysMenu> tagMenusTree(@PathVariable("roleId") Long roleId){
        return roleWebService.tagMenusTree(roleId);
    }

    /**
     * 修改
     * @param roleJson
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseResult edit(@RequestBody String roleJson){
        SysRole sysRole = JSONObject.parseObject(roleJson, SysRole.class);
        if (SysRole.isAdmin(sysRole.getRoleId())){
            throw new BusinessException("不允许操作超级管理员角色");
        }
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equalsIgnoreCase(roleWebService.checkRoleNameUnique(sysRole)?UserConstants.ROLE_NAME_NOT_UNIQUE:UserConstants.ROLE_NAME_UNIQUE)){
            return error("注册角色"+sysRole.getRoleName()+"失败，角色名称已存在");
        }
        if (UserConstants.ROLE_KEY_NOT_UNIQUE.equalsIgnoreCase(roleWebService.checkRoleKeyUnique(sysRole)?UserConstants.ROLE_KEY_NOT_UNIQUE:UserConstants.ROLE_KEY_UNIQUE)){
            return error("注册角色"+sysRole.getRoleName()+"失败，角色权限已存在");
        }
        sysRole.setUpdateBy(ShiroUtils.getLoginName());
        AuthorizationUtils.clearAllCacheAuthorizationInfo();
        return roleWebService.update(sysRole)?success("修改角色成功"):error("修改角色失败");
    }

    /**
     * 分配数据范围
     * @param roleJson
     * @return
     */
    @PostMapping("/dataScope")
    @ResponseBody
    public ResponseResult assignDataScope(@RequestBody String roleJson){
        SysRole sysRole = JSONObject.parseObject(roleJson, SysRole.class);
        if (SysRole.isAdmin(sysRole.getRoleId())){
            throw new BusinessException("不能对超级管理员进行操作");
        }
        sysRole.setUpdateBy(ShiroUtils.getLoginName());
        boolean result = roleWebService.updateDataSCope(sysRole);
        if (result){
            ShiroUtils.setPrincipal(userWebService.findUserByUserId(ShiroUtils.getUserId()));
        }
        return result?success("修改"+sysRole.getRoleName()+"数据范围成功"):error("修改"+sysRole.getRoleName()+"数据范围失败");
    }


    /**
     * 分配用户
     * @param roleId
     * @param userIds
     * @return
     */
    @PostMapping("/assignUser/{roleId}")
    @ResponseBody
    public ResponseResult assignUser(@PathVariable("roleId") Long roleId,@RequestBody String userIds){
        return roleWebService.assignUser(roleId,userIds)?success("分配用户成功"):error("分配用户失败");
    }

    /**
     * 取消授权
     * @param roleUserJson
     * @return
     */
    @PostMapping("/cancelAuthUser")
    @ResponseBody
    public ResponseResult cancelAuthUser(@RequestBody String roleUserJson){
        SysUserRole sysUserRole = JSONObject.parseObject(roleUserJson, SysUserRole.class);
        return roleWebService.cancelAuthUser(sysUserRole)?success("取消授权成功"):error("取消授权失败");
    }

    /**
     * 批量取消授权
     * @param roleId
     * @param userIds
     * @return
     */
    @PostMapping("cancelAuthAllUser/{roleId}")
    @ResponseBody
    public ResponseResult cancelAuthAllUser(@PathVariable("roleId") Long roleId,@RequestBody String userIds){
        return roleWebService.cancelAuthAllUser(roleId,userIds)?success("批量取消授权成功"):error("批量取消授权失败");
    }

    @GetMapping("/changeStatus/{roleId}")
    @ResponseBody
    public ResponseResult changeStatus(@PathVariable("roleId") Long roleId,
                                       @RequestParam("status") String status){
        if (SysRole.isAdmin(roleId)){
            throw new BusinessException("超级管理员角色不能进行修改");
        }
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setStatus(status);
        return roleWebService.changeStatus(sysRole)?success():error();
    }
}
