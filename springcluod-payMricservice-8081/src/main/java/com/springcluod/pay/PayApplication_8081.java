package com.springcluod.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PayApplication_8081 {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication_8081.class, args);
    }
}
