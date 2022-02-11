package com.opfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(value = "PAY-8080-MRICSERIVCE")
public interface OpfeignService {

    @GetMapping("/pay/lb")
    public String lb();

    @GetMapping("/pay/timeout")
    public String timeOut();
}
