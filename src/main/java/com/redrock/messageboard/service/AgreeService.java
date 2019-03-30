package com.redrock.messageboard.service;

import com.redrock.messageboard.dao.AgreeDao;
import com.redrock.messageboard.dao.MessageDao;
import com.redrock.messageboard.enums.ResultEnum;
import com.redrock.messageboard.exception.DefinedException;
import com.redrock.messageboard.model.Agree;
import com.redrock.messageboard.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

public class AgreeService {
    @Autowired
    private AgreeDao agreeDao;

    @Autowired
    private MessageDao messageDao;


    public void insertAgree(Agree agree){
        if(messageDao.IfMessageExist(agree.getTextid())==0){
            throw new DefinedException(ResultEnum.PARAM_ERROR,"回复的信息不存在");}
        if(agreeDao.IfMeetsExist(agree.getUid(),agree.getTextid())!=0){
            throw new DefinedException(ResultEnum.PARAM_ERROR,"你已经点赞过了");
        }
        agreeDao.agree(agree);
    }

    public void deleteAgree(int uid,int textid){
        if(agreeDao.IfMeetsExist(uid,textid)==0){
            throw new DefinedException(ResultEnum.PARAM_ERROR,"你还没有点赞过");
        }
        Agree agree =new Agree();
        agree=agreeDao.getAgree(uid, textid);
        agreeDao.deleteAgree(agree);
    }

}
