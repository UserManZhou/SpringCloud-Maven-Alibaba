package com.alibaba.seata.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StroageMapper {

    @Update(value = "UPDATE `t_storage` SET `used` = `used` + #{count},`residue` = `residue` - #{count} WHERE `product_id` = #{productId}")
    @Options(useGeneratedKeys = true)
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
