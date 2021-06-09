package com.code.mypage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

@Configuration
public class AgentConfig  {

    public static final String DATASOURCE_MAIN = "dataSource1";
    public static final String TRANSACTION_MANAGER_MAIN = "transactionManager1";
    public static final String TRANSACTION_SESSION_FACTORY_MAIN = "sqlSessionFactory1";

    Logger logger = LoggerFactory.getLogger(AgentConfig.class);

}
