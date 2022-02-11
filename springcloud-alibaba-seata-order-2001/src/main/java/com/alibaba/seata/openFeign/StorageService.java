package com.alibaba.seata.openFeign;

import com.alibaba.seata.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage")
public interface StorageService {

    @GetMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
