package com.small.frame.aspectj;

import com.small.common.anno.DataScope;
import com.small.common.base.BaseQuery;
import com.small.common.base.enitity.SysRole;
import com.small.common.base.enitity.SysUser;
import com.small.common.utils.ShiroUtils;
import com.small.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataScope注解的切面解析类
 * @author ruoyi
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";
    /**
     * 自定义数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";
    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";
    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";
    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    @Pointcut("@annotation(com.small.common.anno.DataScope)")
    public void dataScopePointCut(){

    }

    @Before("dataScopePointCut()")
    public void beforeDBOperation(JoinPoint point){
        handleMethod(point);
    }

    public void handleMethod(JoinPoint point){
        DataScope anno = getAnno(point);
        if (anno==null){
            return;
        }
        SysUser user = ShiroUtils.getPrincipal();
        if (user!=null){
            if (!user.isAdmin()){
                dataScopeFilter(anno,user,point);
            }
        }
    }

    private void dataScopeFilter(DataScope anno,SysUser user,JoinPoint joinPoint){
        String deptAlias = anno.deptAlias();
        String userAlias = anno.userAlias();
        List<SysRole> sysRoles = user.getSysRoles();
        StringBuilder sqlString = new StringBuilder();
        for (SysRole sysRole : sysRoles) {
            if (sysRole.getDataScope().equals(DATA_SCOPE_ALL)){

            }else if (sysRole.getDataScope().equals(DATA_SCOPE_CUSTOM)){
                sqlString.append(StringUtils.format("or {}.dept_id in (select dept_id from sys_role_dept where role_id = {})",deptAlias,sysRole.getRoleId().toString()));
            }else if (sysRole.getDataScope().equals(DATA_SCOPE_DEPT)){
                sqlString.append(StringUtils.format("or {}.dept_id = {}",deptAlias,user.getDeptId().toString()));
            }else if (sysRole.getDataScope().equals(DATA_SCOPE_DEPT_AND_CHILD)){
                sqlString.append(StringUtils.format("or {}.dept_in IN (select dept_id from sys_dept where dept_id = {} or find_in_set({},ancestors))",deptAlias,user.getDeptId().toString(),user.getDeptId().toString()));
            }else if (sysRole.getDataScope().equals(DATA_SCOPE_SELF)){
                if (StringUtils.isNotBlank(userAlias)){
                    sqlString.append(StringUtils.format("or {}.user_id = {}",userAlias,user.getUserId().toString()));
                }else{
                    // 数据权限仅为本人且没有userAlias别名不查询任何数据
                    sqlString.append("and 1=0");
                }
            }
        }
        if (sqlString.length()>0){
            Object param = joinPoint.getArgs()[0];
            if (param!=null&&param instanceof BaseQuery){
                BaseQuery query=(BaseQuery)param;
                Map<String, Object> params = query.getParams();
                params.put("dataScope",sqlString.toString());
            }
        }
    }

    public DataScope getAnno(JoinPoint point){
        MethodSignature signature =(MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        if (method!=null){
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
