package com.example.work_space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WorkSpaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkSpaceApplication.class, args);
    }

}
