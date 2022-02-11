package com.alibaba.sentinel.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class CustomerHandler {

    public static String customerMethod(BlockException e){
        return "customerMethod 1";
    }

    public static String customerMethod2(BlockException e){
        return "customerMethod 2";
    }
}
