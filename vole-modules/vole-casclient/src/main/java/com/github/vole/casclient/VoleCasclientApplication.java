package com.github.vole.casclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VoleCasclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoleCasclientApplication.class, args);
    }
}
