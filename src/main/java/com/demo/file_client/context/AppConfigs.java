package com.demo.file_client.context;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class AppConfigs {
	
	public static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();
	
	@Bean("taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_COUNT * 2);					//核心线程数
        executor.setMaxPoolSize(CORE_COUNT * 3);					//最大线程数
        executor.setQueueCapacity(10);								//队列容量
        executor.setKeepAliveSeconds(60);							//非核心线程允许空闲的最大时间，超过之后会被销毁
        executor.setThreadNamePrefix("TaskExecutor-");				//线程名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());	//任务的拒接策略
        return executor;
    }

}
