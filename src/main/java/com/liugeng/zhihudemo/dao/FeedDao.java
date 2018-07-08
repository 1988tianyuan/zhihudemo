package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.Feed;
import com.liugeng.zhihudemo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FeedDao {
    String INSERT_FIELDS = "user_id,data,create_date,type ";
    String TABLE_NAME = "feed";

    @Insert({"insert into ",TABLE_NAME,"(", INSERT_FIELDS ,") values(#{userId},#{data},#{createDate},#{type})"})
    int addFeed(Feed feed);

    @Select({"select * from ",TABLE_NAME," where id = #{id}"})
    Feed getFeedById(int id);

    @Update({"update ",TABLE_NAME," set data = #{data} where id = #{id}"})
    void updateFeedById(Feed feed);

    @Delete({"delete from ",TABLE_NAME," where where user_id = #{userId}"})
    int deleteFeed(@Param("userId")int userId);

    @Select({"select * from ",TABLE_NAME," where user_id = #{userId} limit #{offset},#{limit}"})
    List<Feed> getFeedByUser(@Param("userId")int userId, @Param("offset")int offset, @Param("limit")int limit);

    List<Feed> getFeedByUsers(@Param("users")List<Integer> users, @Param("maxId")int maxId, @Param("count")int count);

    @Select({"select * from ",TABLE_NAME," order by create_date"})
    List<Feed> getLatestFeed();


}
