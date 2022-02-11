package com.alibaba.seata.controller;

import com.alibaba.seata.domain.CommonResult;
import com.alibaba.seata.mapper.StroageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StroageController {

    @Autowired
    private StroageMapper stroageMapper;

    @GetMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        stroageMapper.decrease(productId,count);
        return  new CommonResult(200, "扣除库存成功", null);
    };
}
