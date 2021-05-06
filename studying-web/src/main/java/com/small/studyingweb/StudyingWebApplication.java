package com.small.studyingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.small.*"})
public class StudyingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyingWebApplication.class, args);
    }

}
