package com.rbi.security.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@MapperScan("com.rbi.security.web.DAO")
public class SecurityWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityWebApplication.class, args);
    }

}
