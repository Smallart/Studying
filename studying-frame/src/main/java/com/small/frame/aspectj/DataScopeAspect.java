package com.small.frame.aspectj;

import com.small.common.anno.DataScope;
import com.small.common.base.BaseQuery;
import com.small.common.base.enitity.SysRole;
import com.small.common.base.enitity.SysUser;
import com.small.common.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 声明一个切面，解析数据范围注解
 * @author Liang
 */
@Aspect
@Component
public class DataScopeAspect {
    //全部数据权限
    private static final String ALL = "1";

    //自定义
    private static final String SELF_CONFIG = "2";

    //本部门
    private static final String DEPARTMENT = "3";

    //本部门及以下
    private static final String DEPARTMENT_WITHSUB = "4";

    //自己
    private static final String SELF = "5";

    private static final String DATA_SCOPE = "dataScope";

    @Pointcut("@annotation(com.small.common.anno.DataScope)")
    public void dataScope(){}

    @Before("dataScope()")
    public void getDataPretreatment(JoinPoint joinPoint){
        DataScope anno = getAnno(joinPoint);
        if (anno==null){
            return;
        }
        SysUser user = ShiroUtils.getPrincipal();
        if (!user.isAdmin()){
            buildDataScopeSql(joinPoint,user,anno);
        }
    }

    private void buildDataScopeSql(JoinPoint joinPoint,SysUser user,DataScope anno){
        StringBuffer sqlString = new StringBuffer();
        List<SysRole> sysRoles = user.getSysRoles();
        for (SysRole sysRole : sysRoles) {
            if (sysRole.getDataScope().equalsIgnoreCase(ALL)){
                // 啥也不干
            }else if (sysRole.getDataScope().equalsIgnoreCase(SELF_CONFIG)){
                sqlString.append("and dept.dept_in in (select dept_id from sys_role_dept where role_id ="+sysRole.getRoleId()+")");
            }else if (sysRole.getDataScope().equalsIgnoreCase(DEPARTMENT)){
                sqlString.append("and dept.dept_id ="+user.getDeptId());
            }else if (sysRole.getDataScope().equalsIgnoreCase(DEPARTMENT_WITHSUB)){
                sqlString.append("and (dept.dept_id="+user.getDeptId()+" or FIND_IN_SET("+user.getDeptId()+",dept.ancestors))");
            }else if (sysRole.getDataScope().equalsIgnoreCase(SELF)){
                String userAlias = anno.userAlias();
                if (userAlias!=null){
                    sqlString.append("u.user_id="+user.getUserId());
                }
            }
        }
        if (sqlString.length()>0){
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof BaseQuery){
                    BaseQuery query = (BaseQuery)arg;
                    query.getParams().put(DATA_SCOPE,sqlString.toString());
                }
            }
        }
    }

    public DataScope getAnno(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(DataScope.class);
    }
}
