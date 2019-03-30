package com.redrock.messageboard.dao;

import com.redrock.messageboard.model.Agree;
import com.redrock.messageboard.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageDao {
    @Insert("insert into message  uid=#{uid},text=#{text},pid=#{pid},type=#{type},status =1")
    void addMessage(Message message);

     @Select("SELECT COUNT(id) FROM message WHERE id =#{id}")
    int IfMessageExist(int id);

    @Select("SELECT status FROM message WHERE id =#{id}")
    int status(int id);

     @Select("SELECT * FROM message WHERE pid = ? and status!=2")
    List<Message> findMessagesByPid(int pid);

    @Update("Update message set status =2 where id=#{id}")
    void deleteMessage(int id);

    @Update("Update message set status =1 where id=#{id}")
    void returnMessage(int id);
}
