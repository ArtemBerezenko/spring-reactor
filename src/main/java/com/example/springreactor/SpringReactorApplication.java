package com.example.springreactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactorApplication.class, args);
    }

}