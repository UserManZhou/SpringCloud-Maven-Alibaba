package com.order.hystrix.servcie;

public class OrderServiceImpl implements OrderService{
    @Override
    public String hystrix() {
        return "------------OrderServiceImpl------------------";
    }

    @Override
    public String timeout() {
        return "------------OrderServiceImpl------------------";
    }
}
