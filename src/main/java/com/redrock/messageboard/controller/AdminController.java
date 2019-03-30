package com.redrock.messageboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.redrock.messageboard.enums.ResultEnum;
import com.redrock.messageboard.service.AgreeService;
import com.redrock.messageboard.service.MessageService;
import com.redrock.messageboard.service.UserService;
import com.redrock.messageboard.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/deleteMessage" ,produces = "application/json")
    public JSONObject deleteMessage(int id){
        messageService.deleteMessage(id);
        return ResultUtil.success();
    }

    @PostMapping(value = "/returnMessage" ,produces = "application/json")
    public JSONObject returnMessage(int id){
        messageService.returnMessage(id);
        return ResultUtil.success();
    }

}
