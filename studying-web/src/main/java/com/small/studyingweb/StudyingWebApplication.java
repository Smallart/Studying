package com.small.studyingweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(value = {"com.small.*"})
@EnableAspectJAutoProxy
@MapperScan("com.small.system.mapper")
public class StudyingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyingWebApplication.class, args);
    }

}
