package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.sentinel.openfeign.OpenFeignService;
import com.sun.deploy.security.BlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.IllegalFormatException;

@RestController
public class SentinelRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    private static String URL="http://sentinel-nacos-privoder/sentinel/client/test";


    @GetMapping("/sentinel/ribbon/test")
    @SentinelResource(
            value = "fallbackException",
            fallback = "fallbackHandler",
            blockHandler = "blockHandler",
            exceptionsToIgnore = {IllegalArgumentException.class}
    )
    public String test(@RequestParam(value = "p1",required = false) String p1){
        if (p1 != null){
            if (p1.equals("1")){
                throw new IllegalArgumentException("参数异常");
            }
        }
        return restTemplate.getForObject(URL, String.class);
    }


    public String fallbackHandler(String p1,Throwable e){
       return "参数异常";
    }

    public String blockHandler(String p1, BlockException e){
        return "blockHandler";
    }

//      OpenFeign
    @Autowired
    private OpenFeignService openFeignService;


    @GetMapping("/openFeign/test")
    public String test(){
      return  openFeignService.test();
    }
}
