package com.alibaba.sentinel.openfeign;

import com.alibaba.sentinel.openfeign.service.OpenFeignServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sentinel-nacos-privoder",fallback = OpenFeignServiceimpl.class)
public interface OpenFeignService {

    @GetMapping("/sentinel/client/test")
    public String test();
}
