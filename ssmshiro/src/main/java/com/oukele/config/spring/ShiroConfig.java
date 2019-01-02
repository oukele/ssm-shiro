package com.oukele.config.spring;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@Configurable
public class ShiroConfig {

    //设置 安全管理器
    @Bean
    DefaultWebSecurityManager securityManager(){
        // shiro 从 realm 中 获取 验证数据
        return new DefaultWebSecurityManager(new JdbcRealm());
    }

    @Bean
    ShiroFilterFactoryBean shiroFilter(){
        // 设置 shiro 管理器
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //加载 安全管理器
        bean.setSecurityManager(securityManager());
        //没有登陆，会跳转到 这个 URL
        bean.setLoginUrl("/login");
        //登陆成功后，跳转此 url
        bean.setSuccessUrl("/home");
        //访问没有权限的url 跳转此url
        bean.setUnauthorizedUrl("/500");

        // 规则设定
        // 基于 url 的规则
        HashMap<String, String> rules = new HashMap<>();
        //访问此URL /admin 下面的所有方法 都需要进行 权限验证
        rules.put("/admin/**", "authc");
        //访问此URL /policy 下面的所有方法 都需要进行 权限验证
        rules.put("/policy/**", "authc");
        //访问此URL /test/view 不需要进行 权限验证
        rules.put("/test/view", "anon");
        //用户信息 注销
        rules.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(rules);

        return bean;
    }


}
