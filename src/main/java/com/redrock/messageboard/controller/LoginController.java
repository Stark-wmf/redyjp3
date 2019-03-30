package com.redrock.messageboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.redrock.messageboard.enums.ResultEnum;
import com.redrock.messageboard.exception.DefinedException;
import com.redrock.messageboard.model.User;
import com.redrock.messageboard.service.UserService;
import com.redrock.messageboard.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "/registe" )
    public Map<Integer, String> register(HttpServletRequest request) {
        Map<Integer, String> mapStatus = new HashMap<>();
        mapStatus.clear();
        User user = new User();
        String username = request.getParameter("username");
        Map<String,Object> map = userService.queryInfoByUsername(username);
        if(map != null){
            mapStatus.put(3, "注册失败，该用户已经存在");
        }
        String password = Register.register(username, request.getParameter("password"));
        String role = "user";
        String company = request.getParameter("company");
        String nickname = request.getParameter("nickname");
        if(StringUtil.isEmpty(role) || StringUtil.isEmpty(company) || StringUtil.isEmpty(nickname)){
            throw new DefinedException(ResultEnum.PARAM_ERROR);
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
//        System.out.println(user);

        String verification = request.getParameter("verification");
        HttpSession session = request.getSession(false);
        if(session.getAttribute("verification") == null){
            throw new DefinedException(ResultEnum.WRONG_CODE,"未发送验证码");
        }
        String veri = (String) session.getAttribute("verification");
//        System.out.println(veri);
        if (!verification.equals(veri)) {
            mapStatus.put(2, "注册失败，验证码错误");
        } else {
            mapStatus = userService.insertUser(user);
        }
        return mapStatus;

    }
    /**
     * 登陆接口
     */
    @PostMapping(value = "/doLogin")
    public Map<String, Object> doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> mapStatus = new HashMap<>();
        mapStatus.clear();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vali = request.getParameter("vali");
        // 创建Subject实例
        Subject currentUser = SecurityUtils.getSubject();
        // 将用户名及密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        HttpSession session = request.getSession(false);
        if(session.getAttribute("vali") == null){
            throw new DefinedException(ResultEnum.WRONG_CODE,"未获取验证码");
        }
        String icode = (String) session.getAttribute("vali");
        if (!vali.equalsIgnoreCase(icode)) {
            mapStatus.put("2", "登录失败，验证码错误");
            return mapStatus;
        } else {
            try {
//                currentUser.login(token);
                //返回第二天该时间
                mapStatus.put(userService.queryRoleByUsername(username),
                        userService.queryUserByUsername(username));
                mapStatus.put("loginTime", DateUtil.dateString(new Date()));
                return mapStatus;

            } catch (AuthenticationException e) {
                e.printStackTrace();
                mapStatus.put("1", "登录失败，用户名或密码错误");
                return mapStatus;
            }
        }
    }

    /**
     * 注销接口
     */
    @PostMapping(value = "/doLogout",produces = "application/json")
    public JSONObject doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResultUtil.success("退出成功");
    }

    /**
     * 判断是否登陆接口
     */
    @GetMapping("/isLogin")
    public boolean isLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }

    /**
     * 登陆验证码接口
     */
    @GetMapping("/valicode")
    public JSONObject valicode(HttpServletRequest request, HttpServletResponse response,
                               HttpSession session) throws Exception {
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session = request.getSession();
        session.setAttribute("vali", objs[0]);
        System.out.println(objs[0]);
        BufferedImage image = (BufferedImage) objs[1];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
//        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = Base64.encodeBase64String(outputStream.toByteArray());

        String imgStr = "data:image/png;base64," + base64Img;
        return ResultUtil.success(imgStr);
    }
}
