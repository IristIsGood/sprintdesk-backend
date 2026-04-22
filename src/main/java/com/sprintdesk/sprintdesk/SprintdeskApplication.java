package com.sprintdesk.sprintdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SprintdeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(SprintdeskApplication.class, args);
    }
}