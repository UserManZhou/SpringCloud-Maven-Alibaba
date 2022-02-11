package nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosController {

    @Value("${server.port}")
    private String port;


    @GetMapping("/nacos/server")
    public String server(){
        return port;
    }

}
