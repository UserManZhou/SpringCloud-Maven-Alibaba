package com.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.hystrix.FallbackHandler;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import javafx.beans.DefaultProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.Map;

@RestController
//@DefaultProperties(defaultFallback = "timeoutError")
public class PayControllerHystrix{

    @Value("${server.port}")
    private String server_port;


    @GetMapping("/pay/hystrix")
    public String hystrix(){
        return server_port;
    }

    @GetMapping("/pay/timeout/hystrix/{id}")
    @HystrixCommand(fallbackMethod = "timeoutError",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3")
        // 断路器是否使能。
        @HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),
        // 在使用统计数据打开/关闭决策之前，一个统计窗口内必须发出的请求数
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
        // default => sleepWindow: 5000 = 5秒，我们将在跳闸电路再次尝试之前睡觉
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "6000"),
        // default => errorThresholdPercentage = 50 =如果10秒内50%+的请求是失败或潜在的，那么我们将触发电路
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String timeout(@PathVariable("id") int id) throws InterruptedException {
//        Thread.sleep(5000);
        if (id <= 0 ){
            throw new RuntimeException("负数");
        }
        return server_port+Thread.currentThread().getName();
    }

    public String timeoutError(int id){
        return "系统繁忙"+server_port+":"+id;
    }
}
