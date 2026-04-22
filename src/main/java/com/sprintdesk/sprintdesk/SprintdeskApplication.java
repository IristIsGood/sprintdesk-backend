package com.sprintdesk.sprintdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.sprintdesk.sprintdesk.repository",
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
        type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
        classes = com.sprintdesk.sprintdesk.repository.ActivityLogRepository.class))
@EnableMongoRepositories(basePackageClasses =
    com.sprintdesk.sprintdesk.repository.ActivityLogRepository.class)
public class SprintdeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(SprintdeskApplication.class, args);
    }
}