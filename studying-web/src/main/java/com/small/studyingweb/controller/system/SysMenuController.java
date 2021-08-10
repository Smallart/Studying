package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.util.AuthorizationUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysMenuWebService;
import com.small.system.domain.SysMenu;
import com.small.system.query.SysMenuQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单Controller
 * @author Liang
 */
@Controller
@RequestMapping("/system")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuWebService menuWebService;

    @GetMapping("/menu")
    @RequiresPermissions("system:menu:view")
    public String menuIndex(){
        return "back/system/back_menu";
    }

    /**
     * 获得菜单栏
     * @return
     */
    @GetMapping("/menus")
    @RequiresPermissions("system:menu:list")
    @ResponseBody
    public ResponseResult menus(){
        return success("success",menuWebService.findMenus());
    }

    /**
     * 菜单页面的table
     * @return
     */
    @GetMapping("/menusTable")
    @RequiresPermissions("system:menu:list")
    @ResponseBody
    public ResponseResult menusTable(@RequestParam(value = "menuName",required = false)String menuName,
                                     @RequestParam(value = "visible",required = false)Integer visible){
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        sysMenuQuery.setMenuName(menuName);
        sysMenuQuery.setVisible(visible);
        return success("success",menuWebService.find(sysMenuQuery));
    }

    /**
     * 通过Id来动态查询前端树所需要的数据
     * @return
     */
    @GetMapping("/menu/menusZtree")
    @RequiresPermissions("system:menu:list")
    @ResponseBody
    public List<SysMenu> menusZtree(){
        return menuWebService.menusZtree();
    }

    @GetMapping("/menu/add")
    @RequiresPermissions("system:menu:add")
    public String add(@RequestParam(value = "menuId",required = false)Long menuId,ModelMap mmp){
        if (menuId!=null){
            mmp.put("menuId",menuId);
        }
        return "back/system/back_menu/add";
    }

    /**
     * 请求menu的edit页面
     * @return
     */
    @GetMapping("/menu/edit")
    @RequiresPermissions("system:menu:edit")
    public String editIndex(@RequestParam("menuId") Long menuId,
                       ModelMap mmp){
        mmp.put("menuId",menuId);
        return "back/system/back_menu/edit";
    }

    /**
     * selectMenu弹出框
     * @return
     */
    @GetMapping("/menu/selectMenu")
    public String selectTree(){
        return "back/system/back_menu/add_supMenu";
    }

    /**
     *
     * @return
     */
    @GetMapping("/menu/selectMenuZtree")
    @ResponseBody
    public List<SysMenu> selectMenuZtree(@RequestParam(value = "paramName",required = false) String menuName){
        return menuWebService.selectMenusZtree();
    }

    /**
     * 更具menuId查询相关菜单
     * @param menuId
     * @return
     */
    @GetMapping("/menu/init")
    @ResponseBody
    public ResponseResult initValue(@RequestParam("menuId")Long menuId){
        return success("success",menuWebService.findMenuById(menuId));
    }

    @GetMapping("/menu/checkMenuInSameParent")
    @ResponseBody
    public boolean checkMenuInSameParent(@RequestParam(value = "menuId",required = false) Long menuId,
                                         @RequestParam("parentId") Long parentId,
                                         @RequestParam("menuName") String menuName){
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        sysMenuQuery.setId(parentId);
        sysMenuQuery.setMenuName(menuName);
        sysMenuQuery.setMenuId(menuId);
        return menuWebService.checkMenuInSameParent(sysMenuQuery);
    }

    @PostMapping("/menu/save")
    @RequiresPermissions("system:menu:add")
    @ResponseBody
    public ResponseResult save(@RequestBody String json){
        SysMenu sysMenu = JSONObject.parseObject(json, SysMenu.class);
        sysMenu.setCreateBy(ShiroUtils.getLoginName());
        return menuWebService.save(sysMenu)?success("添加菜单成功"):error("添加菜单失败");
    }

    @PostMapping("/menu/edit")
    @RequiresPermissions("system:menu:edit")
    @ResponseBody
    public ResponseResult edit(@RequestBody String json){
        SysMenu sysMenu = JSONObject.parseObject(json,SysMenu.class);
        sysMenu.setUpdateBy(ShiroUtils.getLoginName());
        return menuWebService.update(sysMenu)?success("修改菜单成功"):error("修改菜单失败");
    }

    /**
     * 删除
     * @param menuId
     * @return
     */
    @GetMapping("/menu/del/{menuId}")
    @RequiresPermissions("system:menu:remove")
    @ResponseBody
    public ResponseResult delete(@PathVariable("menuId")Long menuId){
        if (menuWebService.findChildMenuByMenuId(menuId).size()>0){
            return error("该菜单下包含子菜单无法删除");
        }
        if (menuWebService.alreadyShare(menuId)){
            return error("该菜单已经被分配，无法删除");
        }
        boolean result = menuWebService.delete(menuId);
        AuthorizationUtils.clearAllCacheAuthorizationInfo();
        return result?success("删除成功"):error("删除失败");
    }
}
