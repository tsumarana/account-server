package com.zjnu.mapper;

import com.zjnu.pojo.Goods;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsMapper {
    @ResultMap("goodsResultMap")
    @Select("select * from tb_goods where id = #{id} and status = 'true'")
    Goods selectInfo(int id);

    //查询所有
    @ResultMap("goodsResultMap")
    @Select("select * from tb_goods where status = 'true'")
    List<Goods> selectAll();
    //新增
    @ResultMap("goodsResultMap")
    @Insert("insert into tb_goods values(null,#{title},#{price},#{grade},#{type},#{accountGrade},#{decorate},#{rank},#{adult},#{seller},'true',#{re_id})")
    void addGoods(Goods goods);
    //通过id删除
    @Delete("delete from tb_goods where id = #{id}")
    void deleteById(Goods goods);
    //通过id查找
    @ResultMap("goodsResultMap")
    @Select("select * from tb_goods where id = #{id} and status = 'true'")
    Goods selectById(int id);
    //通过reId查找
    @ResultMap("goodsResultMap")
    @Select("select * from tb_goods where re_id = #{re_id} ")
    Goods selectByReId(Goods goods);
    //分页查询
    @ResultMap("goodsResultMap")
    @Select("select * from tb_goods limit #{begin}, #{size}")
    List<Goods> selectByPage(@Param("begin") int begin,@Param("size") int size);
    //数目查询
    @Select("select count(*) from tb_goods where status = 'true'")
    int selectTotalCount();
    //根据用户名删除
    @Delete("delete from tb_goods where seller = #{seller}")
    void deleteBySeller(Goods goods);
    //分页条件查询
    @ResultMap("goodsResultMap")
    List<Goods> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("goods") Goods goods);

    //数目查询
    int selectTotalCountByCondition(Goods goods);
    //通过用户名删除
    @Delete("delete from tb_goods where seller = #{username}")
    void deleteGoodsByUsername(String username);
    //冻结
    @Update("update tb_goods set status = 'false' where seller = #{username}")
    void freezeUser(String username);
    //下架
    @Delete("delete from tb_goods where id = #{id}")
    void deleteGoodsById(int id);
}
