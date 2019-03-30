package com.redrock.messageboard.dao;

import com.redrock.messageboard.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

    @Insert("Insert into user set username = #{username}, password = #{password}," +
            "role = #{role}")
    void addUser(User user);

    @Select("Select * from user where username = #{username}")
    User getPassword(String username);

    @Select("Select role from user where username = #{username}")
    String getRole(String username);

    @Insert(" INSERT INTO user SET username = #{username}, password = #{password}, role = #{role}")
    int register(User user);
}
