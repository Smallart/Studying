package com.small.studyingweb.controller.system;

import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysPostWebService;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位管理 controller层
 * @author Liang
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

    @Autowired
    private SysPostWebService sysPostWebService;

    @GetMapping
    public String index(){
        return "back/system/back_post";
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "postName",required = false) String postName,
                               @RequestParam(value = "postCode",required = false) String postCode,
                               @RequestParam(value = "postStatus",required = false) Integer status,
                               @RequestParam(value = "createStartTime",required = false) String createStartTime,
                               @RequestParam(value = "createEndTime",required = false) String createEndTime,
                               @RequestParam(value = "offset")Integer offset,
                               @RequestParam(value = "limit")Integer limit,
                               @RequestParam(value = "order",required = false)String  order,
                               @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysPostQuery sysPostQuery = new SysPostQuery();
        sysPostQuery.setPostName(postName);
        sysPostQuery.setPostCode(postCode);
        sysPostQuery.setPostStatus(status);
        sysPostQuery.setCreateStartDate(DateUtils.parseDate(createStartTime));
        sysPostQuery.setCreateEndDate(DateUtils.parseDate(createEndTime));
        sysPostQuery.setOffset(offset);
        sysPostQuery.setLimit(limit);
        sysPostQuery.setOrder(order);
        sysPostQuery.setOrderStrategy(orderStrategy);
        return success("success",sysPostWebService.find(sysPostQuery));
    }

    @GetMapping("/add")
    public String add(){
        return "back/system/back_post/back_post_add";
    }

    @GetMapping("/markingPostById")
    @ResponseBody
    public List<SysPost> MarkingPostByUserId(@RequestParam(value = "userId",required = false)Integer userId){
        return sysPostWebService.findPostByUserId(userId);
    }
}
