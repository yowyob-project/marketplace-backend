package com.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class   AppConfig {
    @Bean
    public TaskExecutor applicationTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // Taille de pool de base
        executor.setMaxPoolSize(10);  // Taille maximale du pool
        executor.setQueueCapacity(25); // Taille de la file d'attente
        executor.setWaitForTasksToCompleteOnShutdown(true);  // Attendre que les tâches terminent avant d'arrêter
        executor.setAwaitTerminationSeconds(60);  // Temps d'attente avant d'arrêter de manière forcée
        return executor;
    }
}
