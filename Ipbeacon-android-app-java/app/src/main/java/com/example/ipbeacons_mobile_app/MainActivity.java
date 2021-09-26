package com.example.ipbeacons_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ipbeacons_mobile_app.api.LoginResponse;
import com.example.ipbeacons_mobile_app.api.User;
import com.example.ipbeacons_mobile_app.ui.Home.HomeActivity;
import com.example.ipbeacons_mobile_app.ui.Login.LoginActivity;
import com.example.ipbeacons_mobile_app.ui.Register.RegisterActivity;
import com.example.ipbeacons_mobile_app.ui.Storage.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private Button LogOut;
    private Button gotohome;
    // private Button update;
    TextView welcomeUser;
    LoginResponse loginResponse;
    User userObject;
    TextInputEditText user;
    TextInputEditText number;
    TextInputEditText email;
    SharedPrefManager sharedPrefManager;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());


        user = findViewById(R.id.name);
        number = findViewById(R.id.num);
        email = findViewById(R.id.mail);
        welcomeUser = findViewById(R.id.welcomeUser);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        Intent intent = getIntent();
        //  if(intent.getExtras() != null){
        //  sharedPrefManager = (SharedPrefManager) intent.getSerializableExtra("data");
        user.setText(sharedPrefManager.getUser().getUsername());
        number.setText(sharedPrefManager.getUser().getYourNumberinSchool());
        email.setText(sharedPrefManager.getUser().getEmail());
        welcomeUser.setText(sharedPrefManager.getUser().getUsername());


        //  }
        LogOut = (Button) findViewById(R.id.logout);
        gotohome = (Button) findViewById(R.id.ipbeacon);
        //update = (Button) findViewById(R.id.update);

        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home();
            }
        });
       /* update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });*/

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        progressBar = findViewById(R.id.progress);
        sharedPrefManager.clear();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        progressBar.setVisibility(View.VISIBLE);
        startActivity(intent);
        Toast.makeText(this, "LogOut successful", Toast.LENGTH_LONG).show();
    }

    public void Home() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

