package com.redrock.messageboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.redrock.messageboard.model.Agree;
import com.redrock.messageboard.model.Message;
import com.redrock.messageboard.model.User;
import com.redrock.messageboard.service.AgreeService;
import com.redrock.messageboard.service.MessageService;
import com.redrock.messageboard.service.UserService;
import com.redrock.messageboard.util.ResultUtil;
import com.redrock.messageboard.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private AgreeService agreeService;

    @PostMapping(value = "/insertMessage" ,produces = "application/json")
    public JSONObject insertMessage(String text, int type, int pid){
        String username= ShiroUtil.getLoginUser();
        User user=userService.queryUserByUsername(username);
        int uid = user.getUid();
        Message message =new Message(uid,text,pid,type,1);
        messageService.insertMessage(message);
        return ResultUtil.success();
    }

    @PostMapping(value = "/agree")
    public JSONObject agree(int textid){
        String username= ShiroUtil.getLoginUser();
        User user=userService.queryUserByUsername(username);
        int uid = user.getUid();
        Agree agree =new Agree(uid,textid,1);
        agreeService.insertAgree(agree);
        return ResultUtil.success();
    }

    @PostMapping(value = "/notagree")
    public JSONObject notagree(int textid){
        String username= ShiroUtil.getLoginUser();
        User user=userService.queryUserByUsername(username);
        int uid = user.getUid();
        agreeService.deleteAgree(uid,textid);
        return ResultUtil.success();
    }
    @PostMapping(value = "/selectMessage" ,produces = "application/json")
    public JSONObject selectMessgae() throws SQLException {
        List<Message> list= messageService.findAllMessages();
        return ResultUtil.success(list);
    }

}
