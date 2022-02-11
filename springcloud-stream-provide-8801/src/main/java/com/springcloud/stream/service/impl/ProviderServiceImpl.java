package com.springcloud.stream.service.impl;

import com.springcloud.stream.service.ProviderService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)
public class ProviderServiceImpl implements ProviderService {

    @Resource
    MessageChannel output;

    @Override
    public void send() {
        String s = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(s).build());
        System.out.println("s = " + s);
    }
}
