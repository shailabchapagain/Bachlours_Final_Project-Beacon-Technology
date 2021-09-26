package com.example.ipbeacons_mobile_app.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("accounts/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("accounts/register")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest RegisterRequest);



}
