package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginTicketDao {
    String INSERT_FIELDS = "user_id, ticket, expired, status ";
    String TABLE_NAME = "login_ticket ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId},#{ticket},#{expired},#{status})"})
    void addTicket(LoginTicket loginTicket);

    @Select({"select * from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket getTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status = #{status} where ticket = #{ticket}"})
    void updateTicket(@Param("ticket") String ticket, @Param("status") int status);
}
