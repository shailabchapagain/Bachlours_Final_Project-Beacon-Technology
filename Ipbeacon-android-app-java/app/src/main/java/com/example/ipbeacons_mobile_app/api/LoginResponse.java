package com.example.ipbeacons_mobile_app.api;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String token;
    private String name;


    private User user;

    public LoginResponse(String token, String name, User user) {
        this.token = token;
        this.name = name;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}