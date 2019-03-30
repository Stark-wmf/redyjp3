package com.redrock.messageboard.service;

import com.redrock.messageboard.dao.MessageDao;
import com.redrock.messageboard.enums.ResultEnum;
import com.redrock.messageboard.exception.DefinedException;
import com.redrock.messageboard.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    @Autowired
    private MessageDao messageDao;

    public void insertMessage(Message message) {
        if (message.getUsername() == null || message.getText() == null) {
            throw new DefinedException(ResultEnum.PARAM_ERROR, "该留言未填完");
        }
        messageDao.addMessage(message);
    }

    private List<Message> findContentChild(Message content) throws SQLException {
        //找该条评论的子节点 即pid为该条评论id的评论
        List<Message> list = messageDao.findMessagesByPid(content.getId());
        for (Message message : list) {
            List<Message> childList = findContentChild(message);
            message.setChildContent(childList);
        }
        return list;
    }

    public List<Message> findAllMessages() throws SQLException {
        List<Message> list = messageDao.findMessagesByPid(0);
        //然后找每条留言的评论 并赋值
        for (Message message : list) {
            List<Message> childList = findContentChild(message);
            message.setChildContent(childList);
        }
        return list;
    }

    public void deleteMessage(int id) {
        if (messageDao.IfMessageExist(id) == 0) {
            throw new DefinedException(ResultEnum.PARAM_ERROR, "该留言不存在");
        }
        if(messageDao.status(id)==2){
            throw new DefinedException(ResultEnum.PARAM_ERROR, "该留言已被建议修改");
        }
        messageDao.deleteMessage(id);
    }

    public void returnMessage(int id) {
        if (messageDao.IfMessageExist(id) == 0) {
            throw new DefinedException(ResultEnum.PARAM_ERROR, "该留言不存在");
        }
        if(messageDao.status(id)==1){
            throw new DefinedException(ResultEnum.PARAM_ERROR, "该留言已被回复");
        }
        messageDao.returnMessage(id);
    }
}
