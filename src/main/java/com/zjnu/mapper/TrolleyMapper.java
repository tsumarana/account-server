package com.zjnu.mapper;

import com.zjnu.pojo.Trolley;
import com.zjnu.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TrolleyMapper {
    @ResultMap("trolleyResultMap")
    @Select("select * from tb_trolley where username = #{username} and status = 'true'")
    List<Trolley> selectTrolley(Trolley trolley);

    @Delete("delete from tb_trolley where id = #{id}")
    void deleteTrolley(Trolley trolley);

    @Update("update tb_trolley set count = #{count} where id = #{id}")
    void saveCount(Trolley trolley);
    //新增
    @ResultMap("trolleyResultMap")
    @Insert("insert into tb_trolley values (null,#{brandId},#{name},1,#{username},#{price},#{seller},'true')")
    void addTrolley(Trolley trolley);
    //删除
    @Delete("delete from tb_trolley where username = #{username} OR seller = #{username}")
    void deleteTrolleyByUsername(String username);
    //冻结
    @Update("update tb_trolley set status = 'false' where seller=#{username}")
    void freezeUser(String username);
    //下架
    @Delete("delete from tb_trolley where brand_id = #{id}")
    void deleteTrolleyById(int id);
}
