package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class AService {

    //  指定资源名，并指定降级方法
    @SentinelResource(value = "testA", blockHandler = "testAFallback")
    public String testA() {
        return "testA";
    }

    public String testAFallback(BlockException ex){
        System.out.println("ex.getMessage() = " + ex.getMessage().toString());
        return "testAFallback";
    }
}
