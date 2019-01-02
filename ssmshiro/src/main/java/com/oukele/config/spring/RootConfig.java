package com.oukele.config.spring;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Configurable
@ComponentScan(basePackages = "com.oukele.dao")
@Import({SpringDaoConfig.class,SpringService.class,ShiroConfig.class})//导入其他配置
public class RootConfig {
}
