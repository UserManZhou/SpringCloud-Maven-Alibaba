package com.springcluod.pay.service.impl;

import com.common.api.entity.Pay;
import com.springcluod.pay.mapper.PayMapper;
import com.springcluod.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "PayServiceimpl")
public class PayServiceimpl implements PayService {

    @Autowired
    @Qualifier(value = "PayMapper")
    private PayMapper payMapper;

    public void save(Pay pay) {
        payMapper.save(pay);
    }

    public int delete(int id) {
        return payMapper.delete(id);
    }
}
