package com.springcloud.consul.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PayConsulController {

    @Value("${server.port}")
    private String server_port;

    @GetMapping("/pay/consul")
    public String consul(){
        return server_port+ "\t"+UUID.randomUUID();
    }
}
