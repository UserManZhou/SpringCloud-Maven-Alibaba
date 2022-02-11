package com.alibaba.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    private final String URL = "http://nacos-provider/nacos/server";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/server")
    public String server(){
        return restTemplate.getForObject(URL,String.class);
    }

}
