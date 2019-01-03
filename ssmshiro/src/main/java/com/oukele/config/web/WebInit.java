package com.oukele.config.web;

import com.oukele.config.spring.RootConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    //父容器
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }
    //子容器
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    //配置过滤器
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8");
        //这个类的好处是可以通过spring容器来管理filter的生命周期，还有就是，可以通过spring注入的形式，来代理一个filter执行，如shiro
        // 动态委托 给 名为 shiroFilter 的bean
        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy("shiroFilter");
        //引用 原目标的 销毁方法。
        shiroFilter.setTargetFilterLifecycle(true);
        
        return new Filter[]{encodingFilter,shiroFilter};
    }

}
