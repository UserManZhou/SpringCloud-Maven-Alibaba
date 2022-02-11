package com.alibaba.seata.mapper;

import com.alibaba.seata.domain.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderMapper {

//    @Insert(
//            value = "INSERT INTO t_order(`user_id`,`product_id`,`count`,`money`,`status`) VALUES (#{userId},#{productId},#{count},#{money},#{status})"
//    )
//    @Options(useGeneratedKeys = true)
    void create(Order order);

//    @Update(value = "UPDATE `t_order` SET `status` = 1 WHERE `user_id` = #{userId} AND `status` = #{status}")
//    @Options(useGeneratedKeys = true)
    void update(@Param("userId") Long userId,@Param("status") Integer status);


}
