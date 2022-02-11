package com.alibaba.seata.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper {

    @Update(value = "UPDATE `t_account` SET `residue` = `residue` - #{money} , `used` = `used` + #{money} WHERE `user_id` = #{userId}")
    @Options(useGeneratedKeys = true)
    void decease(@Param("userId") Long userId, @Param("money")BigDecimal money);
}
