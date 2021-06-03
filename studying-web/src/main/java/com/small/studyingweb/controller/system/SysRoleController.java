package com.small.studyingweb.controller.system;

import com.small.common.base.BaseObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SysRole controller 层
 * @author Liang
 */
@Controller
@RequestMapping("/system")
public class SysRoleController extends BaseObject {
    @GetMapping("/role")
    public String index(){
        return "back/system/back_role";
    }
}
