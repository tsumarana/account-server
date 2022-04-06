package com.zjnu.mapper;

import com.zjnu.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface bOrderMapper {
    @ResultMap("borderResultMap")
    @Insert("insert into tb_border values (null,#{buyer},#{seller},#{brandId},#{img},#{time},#{price},#{status},#{name})")
    void addOrder(Order order);

    @ResultMap("borderResultMap")
    @Select("select * from tb_border where buyer = #{buyer}")
    List<Order> selectBuyerOrder(Order order);

    @ResultMap("borderResultMap")
    @Update("update tb_border set status='pay' where brandid = #{brandId}")
    void pay(Order order);
}
