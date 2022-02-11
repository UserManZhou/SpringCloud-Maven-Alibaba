package com.springcluod.order.lb;

import org.springframework.cloud.client.ServiceInstance;

public interface LoadBalancer<T> {

    public T getInstance(T t);

}
