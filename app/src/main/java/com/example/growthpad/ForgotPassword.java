package com.example.growthpad;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    EditText enter_number_email_forgot_password;
    Button submit_btn_forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        enter_number_email_forgot_password = findViewById(R.id.forgot_password_enter_phone_text);
        submit_btn_forgot_password = findViewById(R.id.forgot_password_submit_btn);

        submit_btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this,OTP_verification_Forgot_password.class);
                startActivity(intent);
            }
        });
    }
}
