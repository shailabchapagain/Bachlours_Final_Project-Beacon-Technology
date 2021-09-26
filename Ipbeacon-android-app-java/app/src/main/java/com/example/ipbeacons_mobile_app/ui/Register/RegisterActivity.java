package com.example.ipbeacons_mobile_app.ui.Register;

import android.content.Intent;
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
import com.example.ipbeacons_mobile_app.api.RegisterRequest;
import com.example.ipbeacons_mobile_app.api.RegisterResponse;
import com.example.ipbeacons_mobile_app.ui.Login.LoginActivity;
import com.example.ipbeacons_mobile_app.ui.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btnSignUp;
   EditText  regNum, edUsername, edEmail, edPassword, password2;
   Button btnLogin;
   ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSignUp =findViewById(R.id.signup);
        regNum =findViewById(R.id.registerednumber);
        edUsername =findViewById(R.id.username);
        edEmail=findViewById(R.id.email);
        edPassword=findViewById(R.id.password);
        password2=findViewById(R.id.password2);
        btnLogin=findViewById(R.id.loginbutton);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) || TextUtils.isEmpty(password2.getText().toString()) || TextUtils.isEmpty(regNum.getText().toString())){
                    String message = "All input required...";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                }else {
                    progressBar = findViewById(R.id.progress);

                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(edEmail.getText().toString());
                    registerRequest.setPassword((edPassword.getText().toString()));
                    registerRequest.setPassword2((password2.getText().toString()));
                    registerRequest.setUsername((edUsername.getText().toString()));
                    registerRequest.setYourNumberinSchool(regNum.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(registerRequest);
                    String message = "User registered successfully...";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }


        });



    }
    private void Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



   public  void registerUser(RegisterRequest registerRequest){

       String email = edEmail.getText().toString().trim();
       String password = edPassword.getText().toString().trim();
       String confirmpassword = password2.getText().toString().trim();


       if (email.isEmpty()) {
           edEmail.setError("Email is required");
           edEmail.requestFocus();
           return;
       }

       if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           edEmail.setError("Enter a valid email");
           edEmail.requestFocus();
           return;
       }


       if (password.length() < 6) {
           edPassword.setError("Password should be atleast 6 character long");
           edPassword.requestFocus();
           return;
       }
       if (confirmpassword.length() < 6) {
           password2.setError("Password should be atleast 6 character long");
           password2.requestFocus();
           return;
       }
       if(!password.equals(confirmpassword)){
           password2.setError("Password should be same");
           password2.requestFocus();
           return;
       }



       Call<RegisterResponse> registerResponseCall = ApiClient.getServices().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
               if(response.isSuccessful()){
                   String message = "Successfully Registered...";
                   Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                   startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                   finish();

               }else{

                   String message = "An error occurred please try again later...";
                   Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });

   }

}

