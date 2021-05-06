package com.small.frame.config;

import com.small.frame.shiro.filter.CaptchaFilter;
import com.small.frame.shiro.realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;

/**
 *
 * @author Liang
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm createRealm(){
        ShiroRealm realm = new ShiroRealm();
        return realm;
    }

    @Bean
    public SecurityManager createSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(createRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean createShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(createSecurityManager());
        shiroFilterFactoryBean.setUnauthorizedUrl("/clock");
        shiroFilterFactoryBean.setLoginUrl("/sysLogin/backIndex");

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("captcha",captchaFilter());

        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/layui/**","anon");
        filterChainDefinitionMap.put("/system/**","anon");
        filterChainDefinitionMap.put("/expandjs/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/clock","anon");
        filterChainDefinitionMap.put("/sysLogin/login","anon,captcha");
        filterChainDefinitionMap.put("/captcha","anon");
        filterChainDefinitionMap.put("/sysLogin/backIndex","anon");
        filterChainDefinitionMap.put("/sysLogin/backMain","anon");
        filterChainDefinitionMap.put("/sysLogin/index","anon");

        filterChainDefinitionMap.put("/**","authc");
        return shiroFilterFactoryBean;
    }

    @Bean
    public CaptchaFilter captchaFilter(){
        return new CaptchaFilter();
    }
}
