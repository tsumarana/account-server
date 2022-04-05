package com.zjnu.mapper;

import com.zjnu.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface OrderMapper {
    //查询所有
    @ResultMap("orderResultMap")
    @Select("select * from tb_order where seller =#{seller} and status = 'true'")
    List<Order> selectSellerOrder(Order order);

    @Delete("delete from tb_order where seller = #{username}")
    void deleteOrderByUsername(String username);
    //冻结
    @Update("update tb_order set status = 'false' where seller = #{username}")
    void freezeUser(String username);
    //下架处理
    @Delete("delete from tb_order where brand_id = #{id}")
    void deleteOrderById(int id);
}
