package com.springcluod.pay.controller;


import com.common.api.entity.CommonResult;
import com.common.api.entity.Pay;
import com.springcluod.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
public class PayController {

    @Autowired
    @Qualifier(value = "PayServiceimpl")
    private PayService payService;

    @Value("${server.port}")
    private String server_port;


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
