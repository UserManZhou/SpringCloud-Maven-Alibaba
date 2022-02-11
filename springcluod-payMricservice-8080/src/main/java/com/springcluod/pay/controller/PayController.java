package com.springcluod.pay.controller;


import com.common.api.entity.CommonResult;
import com.common.api.entity.Pay;
import com.springcluod.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class PayController {

    @Autowired
    @Qualifier(value = "PayServiceimpl")
    private PayService payService;

    @Value("${server.port}")
    private String server_port;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/pay/save")
    public CommonResult save(@RequestBody Pay pay){
        CommonResult commonResult = new CommonResult();
        log.info(pay.toString());
        payService.save(pay);
        if(pay.getId() >= 1) {commonResult.setData(pay);commonResult.setCode(200);commonResult.setMessage("Success port:"+server_port);}
        else {
            commonResult.setMessage("error port:"+server_port);commonResult.setData(null);commonResult.setCode(400);
        }
        return commonResult;
    }

    @GetMapping(value = "/pay/delete/{id}")
    public CommonResult delete(@PathVariable(value = "id") int id){
        CommonResult commonResult = new CommonResult();
        log.info(String.valueOf(id));
        int delete = payService.delete(id);
        if(delete != 0){
            commonResult.setCode(200);commonResult.setMessage("Success port:"+server_port);
        }else {
            commonResult.setCode(400);commonResult.setMessage("error port:"+server_port);
        }
        commonResult.setData(null);
        return commonResult;
    }

    @GetMapping("/pay/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.forEach(x -> {
            log.info(x.toString());
        });

        List<ServiceInstance> instances = discoveryClient.getInstances("PAY-8080-MRICSERIVCE");
        instances.forEach(x -> {
            log.info("serviceID"+x.getServiceId()+"instanceID"+x.getInstanceId()+"port"+x.getPort()+"Host"+x.getHost());
        });
        return this.discoveryClient;
    }

    @GetMapping("/pay/rest")
    public StringBuffer rest(){
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nihao");
        return stringBuffer;
    }

    @GetMapping("/pay/zookeeper")
    public String zookeeper(){
        return server_port+ "\t"+ UUID.randomUUID();
    }

    @GetMapping("/pay/lb")
    public String lb(){
        return server_port;
    }

    @GetMapping("/pay/timeout")
    public String timeOut(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return server_port;
    }
}
