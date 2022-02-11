package com.order.hystrix.servcie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(value = "PAY-HYSTRIX-8083",fallback = OrderServiceImpl.class)
public interface OrderService {

    @GetMapping("/pay/hystrix")
    public String hystrix();

    @GetMapping("/pay/timeout/hystrix")
    public String timeout();
}
