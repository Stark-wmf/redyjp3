package com.redrock.messageboard.model;

import lombok.Data;

import java.util.List;
@Data
public class Message {
    //这条留言的id
    private int id;

    //父节点的id
    private int pid;

    //留言属性 normal 为 1 ， secrte 为 2
    private int type;
// status 为1正常，2建议修改（不可见）
    private int status;

    private static final  int Normal =1;

    private static final  int Secret =2;

    //用户名
    private String username;

    //留言的内容
    private String text;

    public int uid;

    public int textid;



    //子节点
    private List<Message> childContent;

    //自己实现了一个构造方法后编译器不提供默认的无参构造器，这里补上
    public Message() {
    }

    //构造方法
    public Message(int uid, String text, int pid, int type,int status) {
        this.textid=textid;
        this.uid=uid;
        this.text = text;
        this.pid = pid;
        this.type = type;

    }

}
