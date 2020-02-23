package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Add_goal_activity extends AppCompatActivity {

    EditText goal_name_edittext , add_goal_description_edittext ;

    ImageButton goal_close_btn;

    Button goal_save_btn , set_goal_time_btn;

    Spinner add_goal_tag_spinner , add_goal_duration_spinner , add_goal_frequency_spinner;
    String[] goal_tags_array = {"Personal" , "Work" , "Office"  };
    String[] goal_duration_array = {"2 Months" , "3 Months" , "4 Months"};
    String[] goal_frequency_array = {"Every Day" ,"Every 3 Hour" , "Every Hour"};

    TextView goal_time;

    CheckBox goal_remind_me;



    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String am_pm;


    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal_activty);
        goal_name_edittext = findViewById(R.id.add_goal_edittext);
        add_goal_description_edittext = findViewById(R.id.add_goal_description_edittext);
        goal_close_btn = findViewById(R.id.close_goal_btn);
        add_goal_tag_spinner = findViewById(R.id.goal_add_tag_spinner);
        add_goal_duration_spinner = findViewById(R.id.goal_set_duration_spinner);
        add_goal_frequency_spinner = findViewById(R.id.goal_set_frequency_spinner);
        goal_time = findViewById(R.id.goal_time_textview);
        set_goal_time_btn = findViewById(R.id.goal_set_time_btn);
        goal_remind_me = findViewById(R.id.goal_checkbox_remind_me);
        goal_save_btn = findViewById(R.id.goal_save_btn);



        goal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_goal_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        goal_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_goal_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        // spinner tags
        ArrayAdapter goal_tags_adapter = new ArrayAdapter(Add_goal_activity.this,android.R.layout.simple_list_item_1,goal_tags_array);
        add_goal_tag_spinner.setAdapter(goal_tags_adapter);
        add_goal_tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter goal_duration_adapter = new ArrayAdapter(Add_goal_activity.this,android.R.layout.simple_list_item_1,goal_duration_array);
        add_goal_duration_spinner.setAdapter(goal_duration_adapter);
        add_goal_duration_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter goal_frequency_adapter = new ArrayAdapter(Add_goal_activity.this,android.R.layout.simple_list_item_1,goal_frequency_array);
        add_goal_frequency_spinner.setAdapter(goal_frequency_adapter);
        add_goal_frequency_spinner  .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
