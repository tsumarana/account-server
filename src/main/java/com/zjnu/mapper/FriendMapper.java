package com.zjnu.mapper;

import com.zjnu.pojo.Friend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface FriendMapper {
    //查询所有
    @ResultMap("friendResultMap")
    @Select("select friend_name,img from tb_friend where user_name = #{username} and status = 'true'")
    List<Friend> selectAllByUser(Friend friend);

    @ResultMap("friendResultMap")
    @Insert("insert into tb_friend  values (null,#{friendname},#{username},#{img},'true' )")
    void addFriend(Friend friend);

    @Delete("delete from tb_friend where user_name = #{username} OR friend_name = #{username}")
    void deleteFriendByUsername(String username);
    //冻结
    @Update("update tb_friend set status = 'false' where user_name = #{username} OR friend_name = #{username}")
    void freezeUser(String username);
}
