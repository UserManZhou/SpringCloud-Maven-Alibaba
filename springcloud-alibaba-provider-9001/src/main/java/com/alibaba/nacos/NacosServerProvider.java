package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosServerProvider {
    public static void main(String[] args) {
        SpringApplication.run(NacosServerProvider.class, args);
    }
}
