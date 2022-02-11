package com.erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationServer.class, args);
    }
}
