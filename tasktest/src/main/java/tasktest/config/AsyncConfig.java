package tasktest.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
public class AsyncConfig {

  @Bean(name = "asyncExecutor")
  public Executor asyncExecutor() {

    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(50); 
    executor.setMaxPoolSize(200); 
    executor.setQueueCapacity(500); 
    executor.setThreadNamePrefix("AsyncThread-");
    executor.initialize();
    return executor;
  }
}