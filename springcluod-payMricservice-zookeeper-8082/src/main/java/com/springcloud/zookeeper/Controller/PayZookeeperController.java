package com.springcloud.zookeeper.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PayZookeeperController {

    @Value("${server.port}")
    private String server_port;

    @GetMapping("/pay/zookeeper")
    public String zookeeper(){
        return server_port+ "\t"+UUID.randomUUID();
    }
}
