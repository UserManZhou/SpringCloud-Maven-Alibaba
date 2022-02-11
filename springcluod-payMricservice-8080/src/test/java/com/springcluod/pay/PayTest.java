package com.springcluod.pay;

import com.common.api.entity.CommonResult;
import com.common.api.entity.Pay;
import com.springcluod.pay.service.PayService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PayTest {


    @Autowired
    private PayService payService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test(){
        Pay pay = new Pay();
        pay.setName("dawdaw");
        pay.setSarl(123);
        payService.save(pay);
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMessage("Success");
        commonResult.setData(pay);
        System.out.println("commonResult.toString() = " + commonResult.toString());
    }

    @Test
    public void test2(){
        int delete = payService.delete(1);
        System.out.println("delete = " + delete);
    }
}
