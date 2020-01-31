package com.example.growthpad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {

    ImageView growthpad_logo;
    TextView growth , pad , growthpadapp;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        growthpad_logo = findViewById(R.id.growthpad_logo);
        growth = findViewById(R.id.growth);
        pad = findViewById(R.id.pad);
        growthpadapp = findViewById(R.id.growthpad_app);
        sp = getSharedPreferences(ConstantURL.PREFERENCE, MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getString(ConstantURL.ID,"").equals(""))
                {
                    Intent intent = new Intent(Splash_Screen.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(Splash_Screen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);


    }
}
