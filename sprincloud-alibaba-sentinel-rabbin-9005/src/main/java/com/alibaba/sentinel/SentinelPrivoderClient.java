package com.alibaba.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SentinelPrivoderClient {
    public static void main(String[] args) {
        SpringApplication.run(SentinelPrivoderClient.class, args);
    }
}
