package com.rbi.security.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@ServletComponentScan(basePackages = {"com.rbi.security.web.config"})
@SpringBootApplication
public class SecurityWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityWebApplication.class, args);
    }

}
