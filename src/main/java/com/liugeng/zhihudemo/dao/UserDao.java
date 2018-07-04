package com.liugeng.zhihudemo.dao;

import com.liugeng.zhihudemo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {
    String INSERT_FILEDS = "name,password,salt,head_url ";
    String TABLE_NAME = "user";

    @Insert({"insert into ",TABLE_NAME,"(", INSERT_FILEDS ,") values(#{name},#{password},#{salt},#{headUrl})"})
    void addUser(User user);

    @Select({"select * from ",TABLE_NAME," where id = #{id}"})
    User getUserById(int id);

    @Update({"update ",TABLE_NAME," set password = #{password} where id = #{id}"})
    void updatePswd(User user);

    @Select({"select * from ",TABLE_NAME," where name = #{name}"})
    User getUserByName(String name);
}
