package com.small.system.service.impl;

import com.small.system.domain.SysRoleDept;
import com.small.system.mapper.SysRoleDeptMapper;
import com.small.system.service.ISysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysRoleDept service层的具体实现
 * @author Liang
 */
@Service
public class SysRoleDeptServiceImpl implements ISysRoleDeptService {

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    @Override
    public Integer batchDeleteByRoleId(List<Long> roleId) {
        return roleDeptMapper.batchDeleteByRoleId(roleId);
    }

    @Override
    public Integer batchInsert(List<SysRoleDept> sysRoleDepts) {
        return roleDeptMapper.batchInsert(sysRoleDepts);
    }
}
