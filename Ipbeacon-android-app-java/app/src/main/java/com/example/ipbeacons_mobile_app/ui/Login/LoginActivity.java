package com.example.ipbeacons_mobile_app.ui.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipbeacons_mobile_app.MainActivity;
import com.example.ipbeacons_mobile_app.R;
import com.example.ipbeacons_mobile_app.api.ApiClient;
import com.example.ipbeacons_mobile_app.api.LoginRequest;
import com.example.ipbeacons_mobile_app.api.LoginResponse;
import com.example.ipbeacons_mobile_app.api.User;
import com.example.ipbeacons_mobile_app.ui.Home.HomeActivity;
import com.example.ipbeacons_mobile_app.ui.Register.RegisterActivity;
import com.example.ipbeacons_mobile_app.ui.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button registerButton;
    Button btnLogin;
    EditText edEmail, edPassword;
    Intent intent;
    SharedPrefManager sharedPrefManager;
    ProgressBar progressBar;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.loginbutton);
        edEmail= findViewById(R.id.emaillogin);
        edPassword= findViewById(R.id.passwordlogin);
        registerButton = (Button) findViewById(R.id.register);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())) {
                    String message = "All input required...";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                } else {
                    LoginRequest loginRequest = new LoginRequest();
                    progressBar = findViewById(R.id.progress);
                    loginRequest.setUsername(edEmail.getText().toString() );
                    loginRequest.setPassword(edPassword.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(loginRequest);


                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }


        });
    }
    /*@Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }*/
    private void Register() {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginUser(LoginRequest loginRequest) {


        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Enter a valid email");
            edEmail.requestFocus();
            return;
        }



        Call<LoginResponse> loginResponseCall = ApiClient.getServices().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(response.isSuccessful()){

                  //  assert loginResponse != null;
                    assert loginResponse != null;
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(loginResponse.getUser());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();


                }else{
                    String message = "An error occurred or User is not registered...";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
    protected void onStart(){
        super.onStart();
        if(sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}

