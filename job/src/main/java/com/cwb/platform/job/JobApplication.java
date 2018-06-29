package com.cwb.platform.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
