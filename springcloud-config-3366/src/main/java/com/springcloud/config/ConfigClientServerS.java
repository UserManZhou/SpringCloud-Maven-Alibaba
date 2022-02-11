package com.springcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConfigClientServerS {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientServerS.class, args);
    }
}
