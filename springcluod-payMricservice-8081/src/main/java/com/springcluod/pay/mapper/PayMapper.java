package com.springcluod.pay.mapper;

import com.common.api.entity.Pay;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value = "PayMapper")
public interface PayMapper {

    @Insert(value = "INSERT INTO `pay`(name,sarl) VALUES (#{name},#{sarl})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public void save(Pay pay);


    public int delete(int id);
}
