package com.small.frame.shiro.service;

import com.small.common.base.enitity.SysUser;
import com.small.common.constant.Constants;
import com.small.common.exceptions.user.UserPasswordNotMatchException;
import com.small.common.exceptions.user.UserPasswordRetryLimitExceedException;
import com.small.frame.manager.AsyncManager;
import com.small.frame.manager.facotry.AsyncFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对登录的密码进行校验
 * @author liang
 */
@Component
public class SysPasswordService {
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value("${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init(){
        this.loginRecordCache = cacheManager.getCache(Constants.LOGINRECORDCACHE);
    }

    /**
     * 校验密码
     */
    public void validate(SysUser user, String password){
        AtomicInteger atomicInteger = loginRecordCache.get(user.getLoginName());
        if (atomicInteger==null){
            atomicInteger = new AtomicInteger(0);
            loginRecordCache.put(user.getLoginName(),atomicInteger);
        }
        if (atomicInteger.incrementAndGet()>=Integer.valueOf(maxRetryCount)){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(user.getLoginName(), Constants.LOGIN_FAIL,""));
            throw new UserPasswordRetryLimitExceedException();
        }
        // 对密码进行加密操作之后再进行比对
        if (!matches(user,password)){
            AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(user.getLoginName(), Constants.LOGIN_FAIL, ""));
            atomicInteger.incrementAndGet();
            throw new UserPasswordNotMatchException();
        }else{
            loginRecordCache.remove(user.getLoginName());
        }
    }

    private boolean matches(SysUser user,String password){
        Md5Hash md5Hash = new Md5Hash(user.getLoginName()+password+user.getSalt());
        return md5Hash.toHex().equals(user.getPassword());
    }
}
