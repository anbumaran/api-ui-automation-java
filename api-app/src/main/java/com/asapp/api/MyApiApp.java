package com.asapp.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.asapp.api"})
public class MyApiApp {

    private static final Logger LOGGER = LogManager.getLogger(MyApiApp.class);
    public static void main(String[] args) {
        SpringApplication.run(MyApiApp.class, args);
        LOGGER.info("Application Start...");
    }

}