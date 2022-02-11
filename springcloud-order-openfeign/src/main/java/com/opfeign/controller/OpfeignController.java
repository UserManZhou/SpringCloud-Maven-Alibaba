package com.opfeign.controller;

import com.opfeign.service.OpfeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OpfeignController {

    @Resource
    private OpfeignService opfeignService;

    @GetMapping("/order/openfeign")
    public String openfeign(){
        return opfeignService.lb();
    }

    @GetMapping("/order/timeout")
    public String timeout(){
        return opfeignService.timeOut();
    }
}
