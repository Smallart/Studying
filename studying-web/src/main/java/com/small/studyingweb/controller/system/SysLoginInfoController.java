package com.small.studyingweb.controller.system;

import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysLoginInfoWebService;
import com.small.system.query.SysUserLoginInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录信息Controller
 * @author Liang
 */
@Controller
@RequestMapping("system/loginInfo")
public class SysLoginInfoController extends BaseController {

    @Autowired
    private SysLoginInfoWebService loginInfoWebService;

    @GetMapping
    public String index(){
        return "back/system/back_logininfo";
    }

    /**
     *
     * @param ipAddr
     * @param loginName
     * @param status
     * @param createStartTime
     * @param createEndTime
     * @param offset
     * @param limit
     * @param order
     * @param orderStrategy
     * @return
     */
    @GetMapping("/find")
    @ResponseBody
    public ResponseResult find(@RequestParam(value = "ipAddr",required = false) String ipAddr,
                               @RequestParam(value = "loginName",required = false) String loginName,
                               @RequestParam(value = "status",required = false) String status,
                               @RequestParam(value = "createStartTime",required = false) String createStartTime,
                               @RequestParam(value = "createEndTime",required = false) String createEndTime,
                               @RequestParam(value = "offset")Integer offset,
                               @RequestParam(value = "limit")Integer limit,
                               @RequestParam(value = "order",required = false)String  order,
                               @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){

        SysUserLoginInfoQuery query = new SysUserLoginInfoQuery();
        query.setIpAddr(ipAddr);
        query.setLoginName(loginName);
        query.setStatus(status);
        query.setBeginTime(DateUtils.parseDate(createStartTime));
        query.setEndTime(DateUtils.parseDate(createEndTime));
        query.setOffset(offset);
        query.setLimit(limit);
        query.setOrder(order);
        query.setOrderStrategy(orderStrategy);
        return success("success",loginInfoWebService.find(query));
    }

    @GetMapping("/del/{ids}")
    @ResponseBody
    public ResponseResult batchDelete(@PathVariable String ids){
        return success("success",loginInfoWebService.batchDelete(ids)?"删除成功":"删除失败");
    }

    @GetMapping("/loginInfoService")
    @ResponseBody
    public ResponseResult loginInfoService(){
        String info = "清除成功";
        try{
            loginInfoWebService.cleanLogininfo();
            return success(info);
        }catch (Exception e){
            info = "清除失败";
        }
        return error(info);
    }
}
