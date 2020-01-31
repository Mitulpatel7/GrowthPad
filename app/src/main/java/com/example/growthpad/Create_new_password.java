package com.example.growthpad;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Create_new_password extends AppCompatActivity {
    EditText enter_new_password , confirm_new_password;
    Button submit_create_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        submit_create_new_password = findViewById(R.id.create_new_password_submit_btn);
        submit_create_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Create_new_password.this, Successful_password_reset.class);
                startActivity(intent);

            }
        });

    }
}
