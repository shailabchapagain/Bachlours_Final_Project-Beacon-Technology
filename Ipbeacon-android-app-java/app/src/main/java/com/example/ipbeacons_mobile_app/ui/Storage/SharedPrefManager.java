package com.example.ipbeacons_mobile_app.ui.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ipbeacons_mobile_app.api.LoginResponse;
import com.example.ipbeacons_mobile_app.api.User;

import java.io.Serializable;

public class SharedPrefManager  {
    private static  String SHARED_PREF_NAME = "my_shared_preff";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static SharedPrefManager mInstance;
    Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        editor.putInt("user_id", user.getUser_id());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("YourNumberinSchool", user.getYourNumberinSchool());
        editor.putBoolean("logged",true);
        editor.apply();

    }

    public boolean isLoggedIn() {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    public User getUser() {
         sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("user_id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("YourNumberinSchool", null)
        );
    }

    public void clear() {
       sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
