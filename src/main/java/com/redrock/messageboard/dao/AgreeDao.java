package com.redrock.messageboard.dao;

import com.redrock.messageboard.model.Agree;
import com.redrock.messageboard.model.Message;
import com.redrock.messageboard.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AgreeDao {

    @Insert("insert into agree  uid=#{uid},textid=#{textid},click=1")
    void agree(Agree agree);

    @Select("SELECT COUNT(click) FROM agree WHERE uid =#{uid} AND textid =#{textid}")
    int IfMeetsExist(int uid,int textid);

    @Select("Select * from agree where uid = #{uid} And textid =#{textid}")
    Agree getAgree(int uid,int textid);

    @Update("Update agree set click =0 where id=#{id}")
    void deleteAgree(Agree agree);
}
