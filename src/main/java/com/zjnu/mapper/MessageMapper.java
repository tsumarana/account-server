package com.zjnu.mapper;

import com.zjnu.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MessageMapper {
    @ResultMap("messageResultMap")
    @Select("select * from tb_message where (from_name = #{fromName} and to_name = #{toName}) OR (from_name = #{toName} and to_name = #{fromName})")
    List<Message> selectMessage(Message message);

    @ResultMap("messageResultMap")
    @Insert("insert into tb_message values (null,#{fromName},#{toName},#{time},#{message},'true')")
    void addMessage(Message message);
    @Delete("delete from tb_message where to_name = #{username} OR from_name = #{username}")
    void deleteMessageByUsername(String username);
    //冻结
    @Update("update tb_message set status = 'false' where to_name = #{username} OR from_name = #{username}")
    void freezeUser(String username);
}
