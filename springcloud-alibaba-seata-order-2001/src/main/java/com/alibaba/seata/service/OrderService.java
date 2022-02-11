package com.alibaba.seata.service;

import com.alibaba.seata.domain.Order;

//@FeignClient()
public interface OrderService {

    public void create(Order order);

}
