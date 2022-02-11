package com.springcluod.pay.service;


import com.common.api.entity.Pay;

public interface PayService {

    void save(Pay pay);

    int delete(int id);

}
