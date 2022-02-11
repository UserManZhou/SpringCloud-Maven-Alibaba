package com.springcluod.order.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class LoadBalancerImpl implements LoadBalancer{

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Object getInstance(Object intances) {
        int increment = atomicInteger.get();
        int next;
        List<ServiceInstance> listintances = (List<ServiceInstance>) intances;
        do {
            next = increment >= Integer.MAX_VALUE ? 0 :increment + 1;
        }while (!atomicInteger.compareAndSet(increment, next));
        log.info(String.valueOf(next));
        return next % listintances.size();
    }

}
