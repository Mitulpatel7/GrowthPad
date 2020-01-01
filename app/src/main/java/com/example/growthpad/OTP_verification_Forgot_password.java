package com.example.growthpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OTP_verification_Forgot_password extends AppCompatActivity {

    TextView edit_details_forgot_password,resend_otp_forgot_password;
    Button submit_btn_otp_verification_forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification__forgot_password);
        edit_details_forgot_password = findViewById(R.id.forgot_password_edit_detail);
        resend_otp_forgot_password = findViewById(R.id.resend_otp_forgot_password);
        submit_btn_otp_verification_forgot_password = findViewById(R.id.otp_verification_forgot_password_submit_btn);

        edit_details_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTP_verification_Forgot_password.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        submit_btn_otp_verification_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTP_verification_Forgot_password.this,Create_new_password.class);
                startActivity(intent);
            }
        });
    }
}
