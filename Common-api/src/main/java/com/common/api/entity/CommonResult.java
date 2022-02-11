package com.common.api.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommonResult<T> {

    private T code;
    private T message;
    private T data;

}
