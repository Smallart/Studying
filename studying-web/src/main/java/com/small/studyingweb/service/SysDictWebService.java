package com.small.studyingweb.service;

import com.small.system.domain.SysDictDetail;
import com.small.system.domain.SysDictType;
import com.small.system.query.SysDictDetailQuery;
import com.small.system.query.SysDictTypeQuery;

import java.util.List;
import java.util.Map;

/**
 * SysDictType web层service实现
 * @author Liang
 */
public interface SysDictWebService {
    /**
     * 查询table字典类型所需数据
     * @param query
     * @return
     */
    Map<String,Object> findType(SysDictTypeQuery query);

    /**
     * 查询table字典细节所需数据
     * @param query
     * @return
     */
    Map<String,Object> findDetail(SysDictDetailQuery query);

    /**
     * 根据Type查询字典相关细节
     * @param type
     * @return
     */
    List<SysDictDetail> findDetailByType(String type);

    /**
     * 检查字典名是否存在
     * @param sysDictType
     * @return
     */
    Boolean checkDictName(SysDictType sysDictType);

    /**
     * 检查字典类型是否存在
     * @param sysDictType
     * @return
     */
    Boolean checkDictType(SysDictType sysDictType);

    /**
     * 添加sysDictType
     * @param sysDictType
     * @return
     */
    Boolean addDictType(SysDictType sysDictType);

    /**
     * 通过Id获得SysDictType
     * @param dictId
     * @return
     */
    SysDictType findDictTypeById(Long dictId);

    /**
     * 更新
     * @param sysDictType
     * @return
     */
    boolean update(SysDictType sysDictType);

    /**
     * 根据ids删除
     * @param ids
     * @return
     */
    String delete(String ids);

    /**
     * 检查字典标签是否唯一
     * @param query
     */
    boolean checkDetailUnique(SysDictDetailQuery query);

    /**
     * 保存DictDetail
     * @param sysDictDetail
     * @return
     */
    boolean saveDictDetail(SysDictDetail sysDictDetail);

    /**
     * 查询所有的字典类型
     * @return
     */
    List<SysDictType> getAllDictDataNames();


    /**
     * 批量删除DictDetail
     * @return
     */
    boolean batchDeleteDictDetail(String idsStr);

    /**
     * 通过id查询DictData
     * @param id
     * @return
     */
    SysDictDetail findDictDataById(Long id);

    /**
     * 更新DetailData
     * @param dictDetail
     * @return
     */
    boolean updateDetailData(SysDictDetail dictDetail);
}
