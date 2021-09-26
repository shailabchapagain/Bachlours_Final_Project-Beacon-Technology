package com.example.ipbeacons_mobile_app.api;

import java.io.Serializable;

public class User implements Serializable {

    private int user_id;
    private String email, username, YourNumberinSchool;

    public User(int user_id, String email, String username, String YourNumberinSchool) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.YourNumberinSchool = YourNumberinSchool;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYourNumberinSchool() {
        return YourNumberinSchool;
    }

    public void setYourNumberinSchool(String yourNumberinSchool) {
        YourNumberinSchool = yourNumberinSchool;
    }


}