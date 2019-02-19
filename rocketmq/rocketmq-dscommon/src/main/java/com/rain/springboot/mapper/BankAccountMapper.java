package com.rain.springboot.mapper;

import com.rain.springboot.entity.BankAccount;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface BankAccountMapper {
    @Select("select * from bankaccount where user_id=#{userId}")
    @Results(id = "bankAccountMap", value = {@Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "user_id", property = "userId", javaType = Integer.class),
            @Result(column = "money", property = "money", javaType = Integer.class)})
    public BankAccount selectById(int userId);
    @Update("update bankaccount set money = money + #{money,jdbcType=INTEGER} where id = #{id,jdbcType=INTEGER}")
    public int updatePayAccount(Map<String,Object> param);
}
