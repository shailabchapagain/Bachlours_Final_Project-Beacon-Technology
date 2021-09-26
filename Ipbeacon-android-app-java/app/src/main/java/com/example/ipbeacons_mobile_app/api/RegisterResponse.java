package com.example.ipbeacons_mobile_app.api;

public class RegisterResponse {
    private String response;
    private String email;
    private String username;
    private String YourNumberinSchool;
    private String token;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
