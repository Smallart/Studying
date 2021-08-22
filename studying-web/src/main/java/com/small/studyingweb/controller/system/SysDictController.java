package com.small.studyingweb.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.small.common.utils.DateUtils;
import com.small.common.utils.ResponseResult;
import com.small.common.utils.ShiroUtils;
import com.small.studyingweb.controller.common.BaseController;
import com.small.studyingweb.service.SysDictWebService;
import com.small.system.domain.SysDictDetail;
import com.small.system.domain.SysDictType;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequiresPermissions("system:dict:view")
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
    @RequiresPermissions("system:dict:list")
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
    @RequiresPermissions("system:dict:list")
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
        mmap.put("dictType",dictType);
        return "back/system/back_dictDetail";
    }

    @GetMapping("/dictType/add")
    @RequiresPermissions("system:dict:add")
    public String dictTypeAddIndex(){
        return "back/system/back_dictType/add";
    }

    @PostMapping("/dictType/add")
    @RequiresPermissions("system:dict:add")
    @ResponseBody
    public ResponseResult dictTypeAdd(@RequestBody String dictTypeJson){
        SysDictType sysDictType = JSONObject.parseObject(dictTypeJson, SysDictType.class);
        if (sysDictWebService.checkDictName(sysDictType)){
            return ResponseResult.error("字典名重复");
        }

        if (sysDictWebService.checkDictType(sysDictType)){
            return ResponseResult.error("字典类型重复");
        }
        sysDictType.setCreateBy(ShiroUtils.getLoginName());
        boolean flag = sysDictWebService.addDictType(sysDictType);
        return ResponseResult.success(flag?"添加字典值类型成功":"添加字典值类型失败");
    }


    @GetMapping("/dictDetail/add")
    @RequiresPermissions("system:dict:add")
    public String dictDetailAdd(@RequestParam("dictType")String dictType,ModelMap mmp){
        mmp.put("dictType",dictType);
        return "back/system/back_dictDetail/add";
    }

    @GetMapping("/checkDictName")
    @ResponseBody
    public ResponseResult checkDictName(@RequestParam("dictName") String dictName){
        SysDictType sysDictType = new SysDictType();
        sysDictType.setDictName(dictName);
        return ResponseResult.success("success",sysDictWebService.checkDictName(sysDictType));
    }

    @GetMapping("/checkDictType")
    @ResponseBody
    public ResponseResult checkDictType(@RequestParam("dictType") String dictType){
        SysDictType sysDictType = new SysDictType();
        sysDictType.setDictType(dictType);
        return ResponseResult.success("success",sysDictWebService.checkDictType(sysDictType));
    }

    @GetMapping("/findDictTypeById")
    @ResponseBody
    public ResponseResult findDictTypeById(@RequestParam("dictId") Long dictId){
        return ResponseResult.success("success",sysDictWebService.findDictTypeById(dictId));
    }

    @GetMapping("/dictType/edit")
    public String dictTypeEditIndex(@RequestParam("dictId") Long dictId,ModelMap mmp){
        mmp.put("dictId",dictId);
        return "back/system/back_dictType/edit";
    }

    @PostMapping("/dictType/edit")
    @RequiresPermissions("system:dict:edit")
    @ResponseBody
    public ResponseResult dictTypeEdit(@RequestBody String dictTypeJson){
        SysDictType sysDictType = JSONObject.parseObject(dictTypeJson, SysDictType.class);
        if (sysDictWebService.checkDictName(sysDictType)){
            return ResponseResult.error("字典名重复");
        }

        if (sysDictWebService.checkDictType(sysDictType)){
            return ResponseResult.error("字典类型重复");
        }
        sysDictType.setUpdateBy(ShiroUtils.getLoginName());
        return ResponseResult.success(sysDictWebService.update(sysDictType)?"修改成功":"修改失败");
    }


    @GetMapping("/dictType/Delete/{ids}")
    @RequiresPermissions("system:dict:remove")
    @ResponseBody
    public ResponseResult dictDatasDelete(@PathVariable("ids") String ids){
        return ResponseResult.success(sysDictWebService.delete(ids));
    }

    @GetMapping("/data/checkDictLabel/{dictType}")
    @ResponseBody
    public ResponseResult checkDictLabel(@RequestParam("dictLabel") String dictLabel,
                                         @PathVariable("dictType") String dictType,
                                         @RequestParam(value = "dictCode",required = false) Long dictCode){
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictLabel(dictLabel);
        query.setDictType(dictType);
        query.setDictCode(dictCode);
        return ResponseResult.success("success",sysDictWebService.checkDetailUnique(query));
    }

    @GetMapping("/data/checkDictValue/{dictType}")
    @ResponseBody
    public ResponseResult checkDictValue(@RequestParam("dictValue") String dictValue,
                                         @PathVariable("dictType") String dictType,
                                         @RequestParam(value = "dictCode",required = false) Long dictCode){
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictValue(dictValue);
        query.setDictType(dictType);
        query.setDictCode(dictCode);
        return ResponseResult.success("success",sysDictWebService.checkDetailUnique(query));
    }

    @PostMapping("/dictData/add")
    @ResponseBody
    public ResponseResult dictDataAdd(@RequestBody String dictDataJson){
        SysDictDetail sysDictDetail = JSONObject.parseObject(dictDataJson, SysDictDetail.class);
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictType(sysDictDetail.getDictType());
        query.setDictLabel(sysDictDetail.getDictLabel());
        if (sysDictWebService.checkDetailUnique(query)){
            return ResponseResult.error("字典标签重复");
        }
        query.setDictLabel(null);
        query.setDictValue(sysDictDetail.getDictValue());
        if (sysDictWebService.checkDetailUnique(query)){
            return ResponseResult.error("字典键值重复");
        }
        sysDictDetail.setCreateBy(ShiroUtils.getLoginName());
        return ResponseResult.success(sysDictWebService.saveDictDetail(sysDictDetail)?"添加成功":"添加失败");
    }

    @GetMapping("/dictData/edit")
    public String dictDataEdit(@RequestParam("dictCode") Long dictCode,ModelMap mmp){
        mmp.put("dictCode",dictCode);
        return "back/system/back_dictDetail/edit";
    }

    @GetMapping("/dictData/allDictTypeName")
    @ResponseBody
    public List<SysDictType> getAllDictTypeNames(){
        return sysDictWebService.getAllDictDataNames();
    }


    @GetMapping("/dictDetail/batchDelete/{ids}")
    @ResponseBody
    public ResponseResult batchDeleteDictDetail(@PathVariable String ids){
        return ResponseResult.success(sysDictWebService.batchDeleteDictDetail(ids)?"删除成功":"删除失败");
    }

    @GetMapping("/dictDetail/editIndex/{dictCode}")
    public String dictDetailEditIndex(@PathVariable Long dictCode,ModelMap mmp){
        mmp.put("dictCode",dictCode);
        return "back/system/back_dictDetail/edit";
    }

    @GetMapping("/dictDetail/edit/{dictCode}")
    @ResponseBody
    public ResponseResult dictDetailEdit(@PathVariable Long dictCode){
        return ResponseResult.success("success",sysDictWebService.findDictDataById(dictCode));
    }

    @PostMapping("/dictDetail/update")
    @ResponseBody
    public ResponseResult dictDetailEUpdate(@RequestBody String dictDataJson){
        SysDictDetail sysDictDetail = JSONObject.parseObject(dictDataJson, SysDictDetail.class);
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictType(sysDictDetail.getDictType());
        query.setDictCode(sysDictDetail.getDictCode());
        query.setDictLabel(sysDictDetail.getDictLabel());
        if (sysDictWebService.checkDetailUnique(query)){
            return ResponseResult.error("字典标签重复");
        }
        query.setDictLabel(null);
        query.setDictValue(sysDictDetail.getDictValue());
        if (sysDictWebService.checkDetailUnique(query)){
            return ResponseResult.error("字典键值重复");
        }
        sysDictDetail.setUpdateBy(ShiroUtils.getLoginName());
        return ResponseResult.success(sysDictWebService.updateDetailData(sysDictDetail)?"修改成功":"修改失败");
    }
}
