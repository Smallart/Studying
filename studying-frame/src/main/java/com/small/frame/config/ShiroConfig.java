package com.small.frame.config;

import com.small.frame.shiro.realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/layui/**","anon");
        filterChainDefinitionMap.put("/system/**","anon");
        filterChainDefinitionMap.put("/index","anon");
        filterChainDefinitionMap.put("/clock","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/captcha","anon");

        filterChainDefinitionMap.put("/**","authc");
        return shiroFilterFactoryBean;
    }
}
