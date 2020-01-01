package com.example.growthpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText login_email , login_password;
    TextView forgotpassword ;
    Button login_btn , createaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_emailid);
        login_password  = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        forgotpassword = findViewById(R.id.login_forgot_password_txtview);
        createaccount = findViewById(R.id.create_account_btn);

        //create account click event
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });




        // frogot password click event
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });




        // login button click event
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_email.getText().toString().equals(""))
                {
                    login_email.setError("Email, Phone number or Username Required");
                }
                if(login_password.getText().toString().equals(""))
                {
                    login_password.setError("Password Required");
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
