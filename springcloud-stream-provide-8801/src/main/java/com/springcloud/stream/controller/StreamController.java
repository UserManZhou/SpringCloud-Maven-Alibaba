package com.springcloud.stream.controller;

import com.springcloud.stream.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

    @Autowired
    private ProviderService providerService;


    @GetMapping("/stream")
    public void stream(){
        providerService.send();
    }
}
