package com.travelsky.ypb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * create ThreadPool by huc
 * 2017/12/8  下午2:53
 */
@Configuration
@EnableAsync
public class ThreadPool {


    @Autowired
    private AppConfig appConfig;

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(appConfig.getCorePoolSize());
        executor.setMaxPoolSize(appConfig.getMaxPoolSize());
        executor.setQueueCapacity(appConfig.getQueueCapacity());
        executor.setThreadNamePrefix(appConfig.getMqExecutor());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(appConfig.getKeepAlive());
        executor.initialize();
        return executor;
    }
}
