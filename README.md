# ssm-shiro
待更新，简单实现而已。

#### 简单版本

#### URL 级别验证
~~~
    @Bean
    ShiroFilterFactoryBean shiroFilter(){
        // 设置 shiro 管理器
        //ShiroFilterFactoryBean 简化了 shiro 配置
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
        // 不需要校验。
        rules.put("/**","anon");
        //用户信息 注销
        rules.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(rules);

        return bean;
    }
~~~

#### 方法级别验证
~~~
@RequiresAuthentication
要求当前Subject 已经在当前的session 中被验证通过才能被访问或调用

@RequiresGuest 
必须是在之前的session 中没有被验证或被记住才能被访问或调用

@RequiresPermissions("account:create")
要求当前的Subject 被允许一个或多个权限

@RequiresRoles("administrator")
要求当前的Subject 拥有所有指定的角色。如果他们没有，则该方法将不会被执行，而且AuthorizationException 异常将会被抛出

@RequiresUser
当前的Subject 是一个应用程序用户才能被注解的类/实例/方法访问或调用

// 使用 spring jar 中的两个类。 ShiroAnnotationProcessorConfiguration类 ShiroBeanConfiguration 类
   重写 ShiroAnnotationProcessorConfiguration类 --> 继承 AbstractShiroAnnotationProcessorConfiguration类
   所以..
   ~~~
        package com.oukele.config.spring;
        import org.apache.shiro.mgt.SecurityManager;
        import org.apache.shiro.realm.jdbc.JdbcRealm;
        import org.apache.shiro.spring.config.AbstractShiroAnnotationProcessorConfiguration;
        import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
        import org.apache.shiro.spring.config.ShiroBeanConfiguration;
        import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
        import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
        import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
        import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
        import org.springframework.beans.factory.annotation.Configurable;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.DependsOn;
        import org.springframework.context.annotation.Import;

        import java.util.HashMap;

        @Configurable
        @Import({ ShiroBeanConfiguration.class})//导入 ShiroBeanConfiguration类
        public class ShiroConfig extends AbstractShiroAnnotationProcessorConfiguration {

            //设置 安全管理器
            @Bean
            DefaultWebSecurityManager securityManager(){
                // shiro 从 realm 中 获取 验证数据
                return new DefaultWebSecurityManager(new JdbcRealm());
            }

            @Bean
            ShiroFilterFactoryBean shiroFilter(){
                // 设置 shiro 管理器
                //ShiroFilterFactoryBean 简化了 shiro 配置
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
                // 不需要校验。
                rules.put("/**","anon");
                //用户信息 注销
                rules.put("/logout", "logout");
                bean.setFilterChainDefinitionMap(rules);

                return bean;
            }

            @Bean
            @DependsOn("lifecycleBeanPostProcessor")
            protected DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
                return super.defaultAdvisorAutoProxyCreator();
            }

            @Bean
            protected AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
                return super.authorizationAttributeSourceAdvisor(securityManager);
            }

        }

   ~~~
        //使用
        @RequestMapping(method = RequestMethod.GET,path = "/test/1",produces = "application/json;charset=utf-8")
        @RequiresAuthentication //访问时 进行校验
        public String test1(){
            return "注解形式。。。。";
        }


~~~

#### 编程式验证
#### 页面内部验证
