package com.alibaba.sentinel.openfeign.service;

import com.alibaba.sentinel.openfeign.OpenFeignService;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignServiceimpl implements OpenFeignService {
    @Override
    public String test() {
        return "error";
    }
}
