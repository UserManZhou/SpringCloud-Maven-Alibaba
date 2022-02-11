package com.alibaba.seata.controller;

import com.alibaba.seata.domain.CommonResult;
import com.alibaba.seata.domain.Order;
import com.alibaba.seata.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/create")
//    @GlobalTransactional
//    @Transactional
    public CommonResult create(Order order) throws InterruptedException {
        orderService.create(order);
        return new CommonResult(200,"OKay", order);
    }

}
