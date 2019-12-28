package com.example.growthpad;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {

    ImageView growthpad_logo;
    TextView Growth , pad , growthpadapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash__screen);
        growthpad_logo = findViewById(R.id.growthpad_logo);
        Growth = findViewById(R.id.growth);
        pad = findViewById(R.id.pad);
        growthpadapp = findViewById(R.id.growthpad_app);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
