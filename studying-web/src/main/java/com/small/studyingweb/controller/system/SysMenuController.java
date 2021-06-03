package com.small.studyingweb.controller.system;

import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysMenuWebService;
import com.small.system.query.SysMenuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String menuIndex(){
        return "back/system/back_menu";
    }

    /**
     * 获得菜单栏
     * @return
     */
    @GetMapping("/menus")
    @ResponseBody
    public ResponseResult menus(){
        return success("success",menuWebService.findMenus());
    }

    /**
     * 菜单页面的table
     * @return
     */
    @GetMapping("/menusTable")
    @ResponseBody
    public ResponseResult menusTable(){
        return success("success",menuWebService.find());
    }
}
