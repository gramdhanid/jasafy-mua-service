package com.jasafy.muaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jasafy")
public class MuaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuaServiceApplication.class, args);
    }

}
