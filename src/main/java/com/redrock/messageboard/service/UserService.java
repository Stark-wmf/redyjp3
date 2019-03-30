package com.redrock.messageboard.service;

import com.redrock.messageboard.dao.UserDao;
import com.redrock.messageboard.enums.ResultEnum;
import com.redrock.messageboard.exception.DefinedException;
import com.redrock.messageboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    @Autowired
    private UserDao userDao;

    public Map<String, Object> queryInfoByUsername(String username) {
        User user = userDao.getPassword(username);
        HashMap<String, Object> map = null;
        if (user != null) {
            map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("password", user.getPassword());
        }else {
            throw new DefinedException(ResultEnum.PARAM_ERROR,"该用户不存在");
        }
        return map;
    }

    public Map<Integer, String> insertUser(User user) {
        int insert = userDao.register(user);
        Map<Integer, String> mapStatus = new HashMap<>();
        if (insert != 0) {
            mapStatus.put(0, "注册成功");
        } else {
            mapStatus.put(1, "注册失败");
        }
        return mapStatus;
    }

    public String queryRoleByUsername(String username) {
        return userDao.getRole(username);
    }

    public User queryUserByUsername(String username) {
        User user = null;
        try {
            user = userDao.getPassword(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefinedException(ResultEnum.DATABASE_ERROR);
        }
        return user;
    }
}
