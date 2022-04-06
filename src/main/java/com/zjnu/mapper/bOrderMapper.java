package com.zjnu.mapper;

import com.zjnu.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface bOrderMapper {
    @ResultMap("borderResultMap")
    @Insert("insert into tb_border values (null,#{buyer},#{seller},#{brandId},#{img},#{time},#{price},#{status},#{name},#{re_id} )")
    void addOrder(Order order);

    @ResultMap("borderResultMap")
    @Select("select * from tb_border where buyer = #{buyer}")
    List<Order> selectBuyerOrder(Order order);

    @ResultMap("borderResultMap")
    @Select("select * from tb_border where re_id = #{re_id}")
    Order selectOrderByReId(Order order);

    @ResultMap("borderResultMap")
    @Update("update tb_border set status='pay' where id = #{id}")
    void pay(Order order);

    @ResultMap("borderResultMap")
    @Select("select * from tb_border where id = #{id}")
    Order selectOrderById(Order order);
}
