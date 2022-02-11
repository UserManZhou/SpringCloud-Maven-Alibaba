package com.alibaba.seata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Order {

    private BigInteger id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal money;
    private int status;
}
