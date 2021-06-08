package com.small.studyingweb.controller.system;

import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysDictWebService;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 与字典有关的Controller
 * @author Liang
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictController extends BaseController {

    @Autowired
    private SysDictWebService sysDictWebService;

    @GetMapping("/type")
    public String dictTypeIndex(){
        return "back/system/back_dictType";
    }

    /**
     * 字典类型table查询
     * @param dictTypeName
     * @param dictTypeCode
     * @param dictTypeStatus
     * @param createStartTime
     * @param createEndTime
     * @param offset
     * @param limit
     * @param order
     * @param orderStrategy
     * @return
     */
    @GetMapping("/type/find")
    @ResponseBody
    public ResponseResult dictTypeFind(@RequestParam(value = "dictTypeName",required = false) String dictTypeName,
                                       @RequestParam(value = "dictTypeCode",required = false) String dictTypeCode,
                                       @RequestParam(value = "dictTypeStatus",required = false) Integer dictTypeStatus,
                                       @RequestParam(value = "createStartTime",required = false) String createStartTime,
                                       @RequestParam(value = "createEndTime",required = false) String createEndTime,
                                       @RequestParam(value = "offset")Integer offset,
                                       @RequestParam(value = "limit")Integer limit,
                                       @RequestParam(value = "order",required = false)String  order,
                                       @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysDictTypeQuery sysDictTypeQuery = new SysDictTypeQuery();
        sysDictTypeQuery.setDictTypeName(dictTypeName);
        sysDictTypeQuery.setDictTypeCode(dictTypeCode);
        sysDictTypeQuery.setDictTypeStatus(dictTypeStatus);
        sysDictTypeQuery.setCreateStartDate(DateUtils.parseDate(createStartTime));
        sysDictTypeQuery.setCreateEndDate(DateUtils.parseDate(createEndTime));
        sysDictTypeQuery.setOffset(offset);
        sysDictTypeQuery.setLimit(limit);
        sysDictTypeQuery.setOrder(order);
        sysDictTypeQuery.setOrderStrategy(orderStrategy);
        return success("success",sysDictWebService.findType(sysDictTypeQuery));
    }

    /**
     * 字典细节table所需数据
     * @param dictDetailStatus
     * @param dictLabel
     * @param dictType
     * @param offset
     * @param limit
     * @param order
     * @param orderStrategy
     * @return
     */
    @GetMapping("/detail/find")
    @ResponseBody
    public ResponseResult dictDetailList(@RequestParam(value = "dictDetailStatus",required = false) Integer dictDetailStatus,
                                         @RequestParam(value = "dictLabel",required = false) String dictLabel,
                                         @RequestParam(value = "dictType",required = false) String dictType,
                                         @RequestParam(value = "offset")Integer offset,
                                         @RequestParam(value = "limit")Integer limit,
                                         @RequestParam(value = "order",required = false)String  order,
                                         @RequestParam(value = "orderStrategy",required = false)String  orderStrategy){
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictDetailStatus(dictDetailStatus);
        query.setDictLabel(dictLabel);
        query.setDictType(dictType);
        query.setLimit(limit);
        query.setOffset(offset);
        query.setOrder(order);
        query.setOrderStrategy(orderStrategy);
        return success("success",sysDictWebService.findDetail(query));
    }

    @GetMapping("/detail/{dictType}")
    public String deptDetailIndex(@PathVariable("dictType") String dictType, ModelMap mmap){
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictType(dictType);
        mmap.put("dictType",dictType);
        mmap.put("dictDetails",sysDictWebService.findDetail(query));
        return "back/system/back_dictDetail";
    }

    @GetMapping("/dictType/add")
    public String dictTypeAdd(){
        return "back/system/back_dictType/back_dictType_add";
    }
    @GetMapping("/dictDetail/add")
    public String dictDetailAdd(@RequestParam("dictType")String dictType,ModelMap mmp){
        mmp.put("dictType",dictType);
        return "back/system/back_dictDetail/back_dictDetail_add";
    }
}
