package com.zjnu.mapper;

import com.zjnu.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper {

    List<User> selectAll();
    //login校验
    User selectUser(User user);

    User selectUserByUserInfo(User user);
    void insertUser(User user);


    List<User> selectUserByPage(@Param("user") User user,@Param("begin") int begin, @Param("size") int size);

    @ResultMap("userResultMap")
    int selectTotalCount(@Param("username") String username,@Param("email") String email, @Param("idCard") String idCard,@Param("name") String name);

    @Delete("delete from tb_user where id = #{id} ")
    void deleteById(User user);

    //清空token
    @Update("update tb_user set token = '' where id = #{id}")
    void cleanToken(User user);

    User selectByUsername(User user);

    @ResultMap("userResultMap")
    void alterUserInfo(User user);

    @ResultMap("userResultMap")
    @Select(" select token,vip from tb_user where user_name = #{username} ")
    User selectTokenByUsername(User user);

    @ResultMap("userResultMap")
    void addToken(User user);

    @ResultMap("userResultMap")
    @Select("select user_name from tb_user where id = #{id} ")
    User selectUserById(User user);

    @Delete("delete from tb_user where id=#{id} ")
    void logoffUser(User user);

    @Update("update tb_user set status = 'false' where id=#{id}")
    void freezeUser(User user);
}
