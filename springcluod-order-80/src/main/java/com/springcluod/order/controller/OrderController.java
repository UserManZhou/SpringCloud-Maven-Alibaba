package com.springcluod.order.controller;

import com.common.api.entity.CommonResult;
import com.common.api.entity.Pay;
import com.springcluod.order.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private static final String HTTP_URL="http://PAY-8080-MRICSERIVCE/pay/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancer balancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/order/save")
    public CommonResult save(Pay pay){
        return restTemplate.postForObject(HTTP_URL+"save",pay,CommonResult.class);
    }

    @GetMapping("/order/delete/{id}")
    public CommonResult delete(@PathVariable("id") int id){
        return restTemplate.getForObject(HTTP_URL+"delete/"+id, CommonResult.class);
    }
    @GetMapping("/order/rest")
    public StringBuffer rest(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_URL + "rest", String.class);
        String body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        HttpHeaders headers = responseEntity.getHeaders();
        StringBuffer result = new StringBuffer();
        result.append("responseEntity.getBody()：").append(body).append("<hr>")
                .append("responseEntity.getStatusCode()：").append(statusCode).append("<hr>")
                .append("responseEntity.getStatusCodeValue()：").append(statusCodeValue).append("<hr>")
                .append("responseEntity.getHeaders()：").append(headers).append("<hr>");
        return result;
    }

    @GetMapping("/roder/zookeeper")
    public String zookeeper(){
        return restTemplate.getForObject(HTTP_URL+"zookeeper", String.class);
    }

        @GetMapping("/order/lb")
    public String lb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("PAY-8080-MRICSERIVCE");
        int index = (int)balancer.getInstance(instances);
        ServiceInstance serviceInstance = instances.get(index);
        System.out.println("serviceInstance.toString() = " +serviceInstance.toString());
        String url = "http://127.0.0.1"+":"+serviceInstance.getPort()+"/pay/lb";
        return restTemplate.getForObject(url,String.class);
    }
}
