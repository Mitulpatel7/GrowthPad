package com.example.growthpad;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

   /*     Alpha
        Rotate
        Scale
        Translate
        Zoom In
        Zoom Out*/


        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2000);
        /*logo.startAnimation(animation);*/



        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);*/
    }
}
