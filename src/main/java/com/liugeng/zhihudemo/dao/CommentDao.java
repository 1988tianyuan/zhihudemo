package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface CommentDao {
    String INSERT_FIELDS = "content,user_id,create_date,entity_id,entity_type,status ";
    String TABLE_NAME = "comment";

    @Insert({"insert into ",TABLE_NAME,"(", INSERT_FIELDS ,") values(#{content},#{userId},#{createDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    List<Comment> getCommentsByEntity(@Param("entityId")int entityId, @Param("entityType")int entityType, @Param("offset")int offset, @Param("limit")int limit);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id = #{entityId} and entity_type = #{entityType} and status = 0"})
    int getCommentsCount(@Param("entityId")int entityId, @Param("entityType")int entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{id}"})
    int updateStatus(int id, int status);

    @Select({"select * from ", TABLE_NAME ," where id = #{commentId}"})
    Comment getCommentById(@Param("commentId")int commentId);

    @Select({"select count(id) from ",TABLE_NAME," where user_id = #{uid} and status = 0"})
    int getUserCommentCount(@Param("uid")int uid);

    @Select({"select id from ", TABLE_NAME ," where status = 0"})
    List<Integer> getIdList();
}
