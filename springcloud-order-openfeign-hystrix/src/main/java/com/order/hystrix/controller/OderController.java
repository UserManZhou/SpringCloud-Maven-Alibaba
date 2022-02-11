package com.order.hystrix.controller;

import com.order.hystrix.OderHystrixClient;
import com.order.hystrix.servcie.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/hystrix")
    public String hystrix(){
        return orderService.hystrix();
    }

    @GetMapping("/order/timeout/hystrix")
    public String timeout(){
        return orderService.timeout();
    }

}
