package com.example.growthpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText username,phone,password;
    Button submit,google_singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        username = findViewById(R.id.signup_email);
        phone = findViewById(R.id.signup_phone);
        password = findViewById(R.id.signup_password);
        google_singup = findViewById(R.id.google_signup);
        submit = findViewById(R.id.signup_submit_btn );

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin@gmail.com")&& password.getText().toString().equals("123456") && phone.getText().toString().equals("1234567890"))
                {
                    Toast.makeText(SignupActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();

                }
                else if(username.getText().toString().equals(""))
                {
                    username.setError("Email id Required");
                }
                else if(password.getText().toString().equals("") | password.length()<6)
                {
                    password.setError("Password Cannot be less than 6 Character");
                }
                else if (phone.getText().toString().equals("") | phone.length()<10)
                {
                    phone.setError("Phone Number Must Be Greater Than 10 Digit");
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Sign Up Unsuccessfully", Toast.LENGTH_SHORT).show();
                }

            }
        });



        google_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "Re-directing To Google Sign Up Page", Toast.LENGTH_SHORT).show();
            }
        });




    }

}
