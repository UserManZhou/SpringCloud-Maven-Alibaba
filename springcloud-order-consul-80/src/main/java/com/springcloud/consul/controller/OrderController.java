package com.springcloud.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private static final String HTTP_URL="http://service-product/pay/consul";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/order/consul")
    public String zookeeper(){
        return restTemplate.getForObject(HTTP_URL, String.class);
    }
}
