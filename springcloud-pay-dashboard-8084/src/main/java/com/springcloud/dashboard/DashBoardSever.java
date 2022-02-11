package com.springcloud.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class DashBoardSever {
    public static void main(String[] args) {
        SpringApplication.run(DashBoardSever.class, args);
    }
}
