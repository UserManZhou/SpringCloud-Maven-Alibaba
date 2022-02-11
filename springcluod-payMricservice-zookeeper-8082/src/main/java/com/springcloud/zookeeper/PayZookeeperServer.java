package com.springcloud.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PayZookeeperServer {
    public static void main(String[] args) {
        SpringApplication.run(PayZookeeperServer.class,args);
    }
}
