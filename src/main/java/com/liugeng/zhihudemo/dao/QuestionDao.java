package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionDao {
    String INSERT_FIELDS = "title,content,user_id,create_date,comment_count ";
    String TABLE_NAME = "question";

    @Insert({"insert into ",TABLE_NAME,"(", INSERT_FIELDS ,") values(#{title},#{content},#{userId},#{createDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select * from ",TABLE_NAME," where id = #{id}"})
    Question getQuestionById(int id);

    @Update({"update ",TABLE_NAME," set content = #{content} where id = #{id}"})
    void updateQuestion(Question question);

    @Update({"update ",TABLE_NAME," set comment_count = #{commentCount} where id = #{id}"})
    int updateCommentCount(@Param("commentCount")int commentCount, @Param("id")int qid);

    List<Question> getLatestQuestion(@Param("userId")int userId, @Param("offset")int offset, @Param("limit")int limit);
}
