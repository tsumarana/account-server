package com.zjnu.mapper;

import com.zjnu.pojo.Chart;
import com.zjnu.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface OrderMapper {
    //查询所有
    @ResultMap("orderResultMap")
    @Select("select * from tb_order where seller =#{seller} ")
    List<Order> selectSellerOrder(Order order);

    @Delete("delete from tb_order where seller = #{username}")
    void deleteOrderByUsername(String username);
    //冻结
    @Update("update tb_order set status = 'false' where seller = #{username}")
    void freezeUser(String username);
    //下架处理
    @Delete("delete from tb_order where brand_id = #{id}")
    void deleteOrderById(int id);

    @ResultMap("orderResultMap")
    @Insert("insert into tb_order values(null,'',#{seller},#{time},#{name},#{brandId},'true',#{price},#{img})")
    void addOrder(Order order);

    @ResultMap("orderResultMap")
    @Update("update tb_order set status='finish' , buyer = #{buyer} where brand_id = #{brandId}")
    void confirm(Order order);
    @ResultMap("orderResultMap")
    @Update("update tb_order set status='true' , buyer = #{buyer} where brand_id = #{brandId}")
    void cancel(Order order);
//    select count(*),substr(time,1,10)  from tb_order where  time like '2022-04%' group by substr(time,1,10)
    @Select("select count(*) as count,substr(time,1,10) as data from tb_border where time < #{time} and status ='finish' group by substr(time,1,10)")
    List<Chart> getChart(String time);
}
