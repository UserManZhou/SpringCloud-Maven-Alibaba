package com.alibaba.seata.controller;

import com.alibaba.seata.domain.CommonResult;
import com.alibaba.seata.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    @PostMapping("/account/decrease")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException {
        Thread.sleep(2000);
        accountMapper.decease(userId,money);
        return new CommonResult(200, "扣除账户余额成功", null);
    };
}
