package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysPostWebService;
import com.small.system.domain.SysPost;
import com.small.system.query.SysPostQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    @RequiresPermissions("system:post:view")
    public String index(){
        return "back/system/back_post";
    }

    @GetMapping("/find")
    @RequiresPermissions("system:post:list")
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
    @RequiresPermissions("system:post:add")
    public String add(){
        return "back/system/back_post/add";
    }

    @GetMapping("/edit/{postId}")
    @RequiresPermissions("system:post:edit")
    public String edit(@PathVariable("postId")Long postId, ModelMap mmp){
        mmp.put("postId",postId);
        return "back/system/back_post/edit";
    }

    @PostMapping("/edit")
    @RequiresPermissions("system:post:edit")
    @ResponseBody
    public ResponseResult update(@RequestBody String json){
        SysPost sysPost = JSONObject.parseObject(json, SysPost.class);
        if (sysPostWebService.checkPostCodeUnique(sysPost.getPostCode(),sysPost.getPostId())){
            return error("岗位编码重复");
        }
        if (sysPostWebService.checkPostNameUnique(sysPost.getPostName(),sysPost.getPostId())){
            return error("岗位名称重复");
        }
        sysPost.setUpdateBy(ShiroUtils.getLoginName());
        return sysPostWebService.update(sysPost)?success("修改成功"):error("修改失败");
    }

    @GetMapping("/init/{postId}")
    @ResponseBody
    public SysPost initValue(@PathVariable("postId")Long postId){
        return sysPostWebService.initValue(postId);
    }

    @GetMapping("/markingPostById")
    @ResponseBody
    public List<SysPost> MarkingPostByUserId(@RequestParam(value = "userId",required = false)Integer userId){
        return sysPostWebService.findPostByUserId(userId);
    }

    @PostMapping("/save")
    @RequiresPermissions("system:post:save")
    @ResponseBody
    public ResponseResult save(@RequestBody String json){
        SysPost sysPost = JSONObject.parseObject(json, SysPost.class);
        if (sysPostWebService.checkPostCodeUnique(sysPost.getPostCode(),sysPost.getPostId())){
            return error("岗位编码重复");
        }
        if (sysPostWebService.checkPostNameUnique(sysPost.getPostName(),sysPost.getPostId())){
            return error("岗位名称重复");
        }
        sysPost.setCreateBy(ShiroUtils.getLoginName());
        return sysPostWebService.save(sysPost)?success("岗位添加成功"):error("岗位添加失败");
    }

    @GetMapping("/checkPostNameUnique")
    @ResponseBody
    public Boolean checkPostNameUnique(@RequestParam("postName")String postName,
                                       @RequestParam(value = "postId",required = false)Long postId){
        return sysPostWebService.checkPostNameUnique(postName,postId);
    }

    @GetMapping("/checkPostCodeUnique")
    @ResponseBody
    public Boolean checkPostCodeUnique(@RequestParam("postCode")String postCode,
                                       @RequestParam(value = "postId",required = false)Long postId){
        return sysPostWebService.checkPostCodeUnique(postCode,postId);
    }

    @GetMapping("/batchDelete")
    @RequiresPermissions("system:post:remove")
    @ResponseBody
    public ResponseResult batchDelete(@RequestParam("postIds") String postIds){
        return sysPostWebService.batchDelete(postIds)?success("删除成功"):error("删除失败");
    }
}
