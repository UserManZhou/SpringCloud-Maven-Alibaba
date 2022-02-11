package com.alibaba.seata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class CommonResult<T> {

    private T code;
    private T message;
    private T data;
}
