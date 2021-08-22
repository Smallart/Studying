package com.small.frame.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.small.common.utils.SpringUtils;
import com.small.frame.shiro.filter.CaptchaFilter;
import com.small.frame.shiro.filter.OnlineSessionFilter;
import com.small.frame.shiro.filter.StudyingLogoutFilter;
import com.small.frame.shiro.filter.SyncOnlineSessionFilter;
import com.small.frame.shiro.realm.ShiroRealm;
import com.small.frame.shiro.session.StudyingSessionDao;
import com.small.frame.shiro.session.StudyingSessionFactory;
import com.small.frame.shiro.session.StudyingSessionManager;
import com.small.frame.shiro.session.StudyingSessionValidationScheduler;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author Liang
 */
@Configuration
public class ShiroConfig {

    @Value("${shiro.session.expireTime}")
    private int expireTime;

    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;
    @Value("${shiro.user.indexUrl}")
    private String indexUrl;

    @Value("${shiro.cookie.domain}")
    private String domain;
    @Value("${shiro.cookie.path}")
    private String path;
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;
    @Value("${shiro.cookie.cipherKey}")
    private String cipherKey;

    /**
     * 缓存管理器 使用Ehcache实现
     * @return
     */
    @Bean
    public EhCacheManager createCacheManager(){
        CacheManager cacheManager = CacheManager.getCacheManager("studying");
        EhCacheManager em = new EhCacheManager();
        if (cacheManager==null){
            em.setCacheManager(new CacheManager(loadCacheConfig()));
        }else {
            em.setCacheManager(cacheManager);
        }
        return em;
    }

    public InputStream loadCacheConfig(){
        String configFile = "classpath:ehcache.xml";
        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] b = IOUtils.toByteArray(inputStream);
            return new ByteArrayInputStream(b);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration Error");
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 创建SessionManager
     * @return
     */
    @Bean
    public StudyingSessionManager createSessionManager(){
        StudyingSessionManager sessionManager = new StudyingSessionManager();
        // 配置自定义SessionFactory
        sessionManager.setSessionFactory(createStudyingSessionFactory());
        // 配置自定义CacheManager
        sessionManager.setCacheManager(createCacheManager());
        // 配置自定义SessionDao
        sessionManager.setSessionDAO(createSessionDao());
        // 配置全局Session 过期时间
        sessionManager.setGlobalSessionTimeout(expireTime*60*1000);
        // 是否删除无效session
        sessionManager.setDeleteInvalidSessions(true);
        // 去掉 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 使用定时器检测session
        sessionManager.setSessionValidationScheduler(SpringUtils.getBean(StudyingSessionValidationScheduler.class));
        //是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 创建RemmemberMe
     * @return
     */
    @Bean
    public RememberMeManager createRememberMeManager(){
        CookieRememberMeManager rememberMeManager =new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey(Base64.decode(cipherKey));
        return rememberMeManager;
    }

    /**
     * Cookie属性设置
     * @return
     */
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    /**
     * 创建SessionFactory
     * @return
     */
    @Bean
    public SessionFactory createStudyingSessionFactory(){
        return new StudyingSessionFactory();
    }

    /**
     * 创建SessionDao
     * @return
     */
    @Bean
    public StudyingSessionDao createSessionDao(){
        StudyingSessionDao sessionDAO = new StudyingSessionDao();
        sessionDAO.setActiveSessionsCacheName("shiro-sessionCache");
        return sessionDAO;
    }

    /**
     * 创建自定义Realm
     * @return
     */
    @Bean
    public Realm createRealm(){
        ShiroRealm realm = new ShiroRealm();
        return realm;
    }

    /**
     * 创建SecurityManager
     * @return
     */
    @Bean
    public SecurityManager createSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(createRealm());
        securityManager.setSessionManager(createSessionManager());
        // 设置 cacheManager
        securityManager.setCacheManager(createCacheManager());
        // 设置 rememberMeManager
        securityManager.setRememberMeManager(createRememberMeManager());
        return securityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean createShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(createSecurityManager());
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("captcha",captchaFilter());
        filters.put("logout",createStudyingLogoutFilter());
        filters.put("sync",createSyncOnlineSessionFilter());
        filters.put("online",createOnlineSessionFilter());
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/layui/**","anon");
        filterChainDefinitionMap.put("/systemresouces/**","anon");
        filterChainDefinitionMap.put("/expandjs/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/clock","anon");
        filterChainDefinitionMap.put("/sysLogin/login","anon,captcha");
        filterChainDefinitionMap.put("/captcha","anon");
        filterChainDefinitionMap.put("/logout","logout");
//        filterChainDefinitionMap.put("/sysLogin/backIndex","anon");

        filterChainDefinitionMap.put("/**","user,online,sync");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建onlineSessionFilter，将user的相关信息补充到Session中
     * @return
     */
    public OnlineSessionFilter createOnlineSessionFilter(){
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setSessionDao(createSessionDao());
        return onlineSessionFilter;
    }

    /**
     * 创建异步session到DB的filter
     * @return
     */
    public SyncOnlineSessionFilter createSyncOnlineSessionFilter(){
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        syncOnlineSessionFilter.setStudyingSessionDao(createSessionDao());
        return syncOnlineSessionFilter;
    }

    /**
     * 自定义登出Filter
     * @return
     */
    public StudyingLogoutFilter createStudyingLogoutFilter(){
        return new StudyingLogoutFilter();
    }

    /**
     * 创建验证码Filter
     * @return
     */
    public CaptchaFilter captchaFilter(){
        return new CaptchaFilter();
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 开启shiro注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
