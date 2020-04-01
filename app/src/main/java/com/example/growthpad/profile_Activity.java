package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class profile_Activity extends AppCompatActivity {

    ImageButton back_btn_profile;

    ImageView profile_image;

    TextView profile_name,  profile_email  , manage_profile , setting , about , logout;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);

        profile_image = findViewById(R.id.profile_photo);

        manage_profile = findViewById(R.id.manage_profile_textview);
        setting = findViewById(R.id.setting_textview);
        about = findViewById(R.id.about_textview);
        logout = findViewById(R.id.logout_textview);

        back_btn_profile = findViewById(R.id.back_profile_btn);


        //back button click event
        back_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        manage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_Activity.this,ManageProfile.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_Activity.this,SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().commit();
                Intent intent = new Intent(profile_Activity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });





        profile_name.setText(sp.getString(ConstantURL.NAME,""));
        profile_email.setText(sp.getString(ConstantURL.EMAIL,""));

    }



    }

