package com.code.mypage.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

@Configuration
@MapperScan(basePackages = {"com.sudapet.main.mapper", "com.sudapet.main.admin.mapper"}, sqlSessionFactoryRef = AgentConfig.TRANSACTION_SESSION_FACTORY_MAIN)
public class AgentDataSourceConfig {

    @Autowired
    private MybatisConfigurationSupport mybatisConfigurationSupport;

    @Bean(name= AgentConfig.DATASOURCE_MAIN, destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        DataSource ds = DataSourceBuilder.create().build();
        return  ds;
    }

    @Bean(name =AgentConfig.TRANSACTION_MANAGER_MAIN)
    @Primary
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = AgentConfig.TRANSACTION_SESSION_FACTORY_MAIN)
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier(AgentConfig.DATASOURCE_MAIN) DataSource ds) throws  Exception{
        return mybatisConfigurationSupport.build(ds);
    }

    @Bean(name="MAIN_SESSION_TEMPLATE")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(AgentConfig.TRANSACTION_SESSION_FACTORY_MAIN)SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
