package com.alibaba.seata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stroage {

    private int id;
    private Long productId;
    private int total;
    private int used;
    private int residue;
}
