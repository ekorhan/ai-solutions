package com.ekorhan.aisolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AiSolutionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSolutionsApplication.class, args);
    }
}
