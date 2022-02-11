package order.controller;

import com.common.api.entity.CommonResult;
import com.common.api.entity.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private static final String HTTP_URL="http://pay-zookeeper-mircserver/pay/zookeeper";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/roder/zookeeper")
    public String zookeeper(){
        return restTemplate.getForObject(HTTP_URL, String.class);
    }
}
