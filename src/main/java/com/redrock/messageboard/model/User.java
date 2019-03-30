package com.redrock.messageboard.model;

import lombok.Data;

@Data
public class User {
    private String username;

    private int uid;

    private String password;

    private String role;

    public User() {

    }
}
