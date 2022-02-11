package com.alibaba.sentinel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelClientController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/sentinel/client/test")
    public String test(){
        return "test"+port;
    }
}
