package com.rain.springboot.mapper;

import com.rain.springboot.entity.PayAccount;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PayAccountMapper {
    @Select("select * from payaccount where user_id=#{userId}")
    @Results(id="payAccountMap",value = {@Result(column = "id",property = "id",javaType = Integer.class),
            @Result(column = "user_id",property = "userId",javaType = Integer.class),
            @Result(column = "money",property = "money",javaType = Integer.class)})
    public PayAccount selectById(int userId);

    @Update("update payaccount set money = money + #{money,jdbcType=INTEGER} where id = #{id,jdbcType=INTEGER}")
    public int updatePayAccount(Map<String,Object> param);
}
