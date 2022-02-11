package com.alibaba.seata.service.serviceImpl;

import com.alibaba.seata.domain.Order;
import com.alibaba.seata.mapper.OrderMapper;
import com.alibaba.seata.openFeign.AccountService;
import com.alibaba.seata.openFeign.StorageService;
import com.alibaba.seata.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "default_create_group",rollbackFor = Exception.class)
    public void create(Order order) {

        // 添加商品

        orderMapper.create(order);

        // 减少商品库存

        storageService.decrease(order.getProductId(), order.getCount());

        // 扣钱

        accountService.decrease(order.getUserId(),order.getMoney());

        // 改用户状态

        orderMapper.update(order.getUserId(),0);

    }
}
