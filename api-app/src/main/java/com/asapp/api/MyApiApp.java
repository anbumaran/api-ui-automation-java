package com.asapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.asapp.api"})
public class MyApiApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApiApp.class, args);
    }

}