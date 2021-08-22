package com.small.studyingweb.service.impl;

import com.small.common.utils.StringUtils;
import com.small.studyingweb.service.SysDictWebService;
import com.small.system.domain.SysDictDetail;
import com.small.system.domain.SysDictType;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;
import com.small.system.service.ISysDictDetailService;
import com.small.system.service.ISysDictTypeService;
import jdk.jfr.StackTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SysDictType web层service具体实现
 * @author Liang
 */
@Service
public class SysDictWebServiceImpl implements SysDictWebService {

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Autowired
    private ISysDictDetailService sysDictDetailService;

    @Override
    public Map<String, Object> findType(SysDictTypeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysDictTypeService.find(query));
        map.put("total",sysDictTypeService.count(query));
        return map;
    }

    @Override
    public Map<String, Object> findDetail(SysDictDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",sysDictDetailService.find(query));
        map.put("total",sysDictDetailService.count(query));
        return map;
    }

    @Override
    public List<SysDictDetail> findDetailByType(String type) {
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictType(type);
        return sysDictDetailService.find(query);
    }

    @Override
    public Boolean checkDictName(SysDictType sysDictType) {
        SysDictTypeQuery query = new SysDictTypeQuery();
        query.setDictTypeName(sysDictType.getDictName());
        List<SysDictType> sysDictTypes = sysDictTypeService.checkSysDictTypeExistByQuery(query);
        if ((sysDictTypes.size()>2)
                ||(sysDictType.getDictId()==null&&sysDictTypes.size()>0)
                ||(sysDictTypes.size()==1&&sysDictTypes.get(0).getDictId().longValue()!=sysDictType.getDictId())){
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkDictType(SysDictType sysDictType) {
        SysDictTypeQuery query = new SysDictTypeQuery();
        query.setDictTypeCode(sysDictType.getDictType());
        List<SysDictType> sysDictTypes = sysDictTypeService.checkSysDictTypeExistByQuery(query);
        if ((sysDictTypes.size()>2)
                ||(sysDictType.getDictId()==null&&sysDictTypes.size()>0)
                ||(sysDictTypes.size()==1&&sysDictTypes.get(0).getDictId().longValue()!=sysDictType.getDictId())){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean addDictType(SysDictType sysDictType) {
        return sysDictTypeService.addDictType(sysDictType)>0?true:false;
    }

    @Override
    public SysDictType findDictTypeById(Long dictId) {
        SysDictTypeQuery query = new SysDictTypeQuery();
        query.setDictId(dictId);
        List<SysDictType> sysDictTypes = sysDictTypeService.checkSysDictTypeExistByQuery(query);
        return sysDictTypes.size()>0?sysDictTypes.get(0):null;
    }

    @Override
    @Transactional
    public boolean update(SysDictType sysDictType) {
        SysDictTypeQuery query = new SysDictTypeQuery();
        query.setDictId(sysDictType.getDictId());
        SysDictType sysDict = sysDictTypeService.checkSysDictTypeExistByQuery(query).get(0);
        Integer update = sysDictTypeService.update(sysDictType);
        if (!sysDict.getDictType().equalsIgnoreCase(sysDictType.getDictType())){
            sysDictDetailService.updateDictTypeAndDictDetail(sysDict.getDictType(),sysDictType.getDictType());
        }
        return update>0?true:false;
    }

    @Override
    public String delete(String ids) {
        List<Long> idList = Stream.of(ids.split(",")).map(item -> Long.parseLong(item)).collect(Collectors.toList());
        List<Long> invalidData = new ArrayList<>();
        for (Long id : idList) {
            SysDictType sysDictType = sysDictTypeService.findDictTypeById(id);
            int size = sysDictDetailService.findDictDetailByType(sysDictType.getDictType()).size();
            if (size>0){
                invalidData.add(id);
            }
        }
        List<Long> validData = idList.stream().filter(item -> !invalidData.contains(item)).collect(Collectors.toList());
        if (validData.size()>0){
            sysDictTypeService.batchDelete(validData);
        }
        if (invalidData.size()>0){
             return "id为"+StringUtils.join(invalidData,",")+"，已被分配，无法删除";
        }
        return "删除成功";
    }

    @Override
    public boolean checkDetailUnique(SysDictDetailQuery query) {
        List<SysDictDetail> sysDictDetails = sysDictDetailService.checkUnique(query);
        if ((sysDictDetails.size()>2)
                ||(query.getDictCode()==null&&sysDictDetails.size()>0)
                ||(sysDictDetails.size()==1&&sysDictDetails.get(0).getDictCode().longValue()!=query.getDictCode())){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveDictDetail(SysDictDetail sysDictDetail) {
        return sysDictDetailService.save(sysDictDetail)>0?true:false;
    }

    @Override
    public List<SysDictType> getAllDictDataNames() {
        return sysDictTypeService.getAllDictTypeNames();
    }

    @Override
    @Transactional
    public boolean batchDeleteDictDetail(String idsStr) {
        List<Long> ids = Arrays.stream(idsStr.split(",")).map(item -> Long.parseLong(item)).collect(Collectors.toList());
        return sysDictDetailService.batchDeleteDictDetail(ids);
    }

    @Override
    public SysDictDetail findDictDataById(Long id) {
        SysDictDetailQuery query = new SysDictDetailQuery();
        query.setDictCode(id);
        return sysDictDetailService.findDictDetailByQuery(query);
    }

    @Override
    public boolean updateDetailData(SysDictDetail dictDetail) {
        return sysDictDetailService.update(dictDetail)>0?true:false;
    }


}
