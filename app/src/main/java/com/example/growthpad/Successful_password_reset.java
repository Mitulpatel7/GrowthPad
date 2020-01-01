package com.example.growthpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Successful_password_reset extends AppCompatActivity {
    Button successful_reset_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_password_reset);
        successful_reset_btn = findViewById(R.id.successfull_reset_login_btn);

        successful_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Successful_password_reset.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
