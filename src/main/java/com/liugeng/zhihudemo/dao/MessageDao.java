package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface MessageDao {
    String INSERT_FIELDS = "from_id, to_id, content,conversation_id,create_date,has_read ";
    String TABLE_NAME = "message";

    @Insert({"insert into ",TABLE_NAME,"(", INSERT_FIELDS ,") values(#{fromId},#{toId},#{content},#{conversationId},#{createDate},#{hasRead})"})
    int addMessage(Message message);

    @Select({"select * from ",TABLE_NAME," where conversation_id = #{conversationId} and to_id != 0 order by create_date asc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId")String conversationId, @Param("offset")int offset, @Param("limit")int limit);

    @Select({"select * from (select * from " , TABLE_NAME , " where to_id = #{localId} or from_id = #{localId})tt group by conversation_id order by create_date desc"})
    List<Message> getConversationList(@Param("localId")int localId);

    @Select({"select count(*) from ", TABLE_NAME , " where has_read = 0 and to_id = #{toId} and conversation_id=#{conversationId}"})
    int unreadCount(@Param("toId")int toId, @Param("conversationId")String conversationId);

    @Update({"update ", TABLE_NAME ," set has_read = 1 where conversation_id = #{conversationId}"})
    int updateUnread(@Param("conversationId")String conversationId);

    @Delete({"update ", TABLE_NAME ," set from_id = 0 where from_id = #{targetId} and conversation_id = #{conversationId}"})
    void deleteFromConversation(@Param("conversationId")String conversationId, @Param("targetId")int targetId);

    @Delete({"update ", TABLE_NAME ," set to_id = 0 where to_id = #{targetId} and conversation_id = #{conversationId}"})
    void deleteToConversation(@Param("conversationId")String conversationId, @Param("targetId")int targetId);
}
