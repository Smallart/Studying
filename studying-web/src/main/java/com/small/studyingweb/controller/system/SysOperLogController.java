package com.small.studyingweb.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/operlog")
public class SysOperLogController {

    @GetMapping
    public String operLogIndex(){
        return "back/system/back_operlog";
    }
}
