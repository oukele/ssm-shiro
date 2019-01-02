package com.oukele.config.spring;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

@Configuration
@PropertySource(value = "classpath:jdbc.properties")//加载资源文件
public class SpringDaoConfig implements EnvironmentAware {

    @Autowired
    private Environment env;

    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    //配置数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }
    //配置 mybatis
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException, PropertyVetoException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setTypeAliasesPackage("com.oukele.entity");//实体类
        //factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));// 映射 sql文件
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));//mybatis配置
        factoryBean.setDataSource(dataSource());
        return factoryBean;
    }
    // 简化调用 将 dao 放置 容器中  使用：比如
    //@Autowired
    //xxxMapper xxmaper ;
    //也可以在类上面 直接使用 @ComponentScan 扫描接口
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.oukele.dao");//扫描接口
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return configurer;
    }
}
