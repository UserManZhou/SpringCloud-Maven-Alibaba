package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.sentinel.myhandler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class SentinelController {

    @Autowired
    private AService aService;

    @GetMapping("/sentinel/test")
    public String test(){
        return aService.testA();
    }


    @GetMapping("/sentinel/test2")
    public String test2(){
        return "test2";
    }

    @GetMapping("/sentinel/test3")
    public String test3() throws InterruptedException {
        Thread.sleep(2000);
        return "test3";
    }

    @GetMapping("/sentinel/test4")
    public String test4(){
        int in = 10/0;
        return "Test4";
    }

    @GetMapping("/sentinel/test5")
    @SentinelResource(value = "test5",blockHandler = "dealHandler")
    public String test5(
            @RequestParam(value = "p1",required = false) String p1,
            @RequestParam(value = "p2",required = false) String p2
    ){
        return "test5";
    }

    public String dealHandler(String p1,String p2,BlockException blockException){
        return "dealHandler";
    }

    @GetMapping("/sentinel/test6")
    @SentinelResource(value = "test6")
    public String test6(){
        return "test6";
    }


    @GetMapping("/sentinel/test7")
    @SentinelResource(
            value = "customerHandler",
            blockHandlerClass = CustomerHandler.class,
            blockHandler = "customerMethod2"
    )
    public String test7(){
        return "test7";
    }
}
