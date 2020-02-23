package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

public class Add_habit_activity extends AppCompatActivity {

    EditText add_habit_edittext , add_habit_description_edittext;

    Spinner add_tag_habit_spinner , set_duration_habit_spinner;
    String[] habit_tag_array = {"Personal" , "Work" ,"Home"};
    String[] habit_duration_array = {"21 Days" , "42 Days" , "2 Months"};

    TextView set_habit_time;

    CheckBox remind_me_habit;

    Button  save_habit_btn , change_habit_time_btn;

    ImageButton close_habit_imgbtn;



    public static FragmentManager fragmentManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_activity);
        add_habit_edittext = findViewById(R.id.add_habit_edittext);
        add_habit_description_edittext = findViewById(R.id.add_habit_description_edittext);
        add_tag_habit_spinner = findViewById(R.id.habit_add_tag_spinner);
        set_duration_habit_spinner = findViewById(R.id.habit_set_duration_spinner);
        set_habit_time = findViewById(R.id.habit_time_textview);
        remind_me_habit = findViewById(R.id.habit_checkbox_remind_me);
        change_habit_time_btn = findViewById(R.id.habit_set_time_btn);
        save_habit_btn = findViewById(R.id.habit_save_btn);
        close_habit_imgbtn = findViewById(R.id.close_habit_btn);


        // spinner code
        ArrayAdapter habit_tag_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,habit_tag_array);
        add_tag_habit_spinner.setAdapter(habit_tag_adapter);
        add_tag_habit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter habit_duration_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,habit_duration_array);
        set_duration_habit_spinner.setAdapter(habit_duration_adapter);
        set_duration_habit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        save_habit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_habit_activity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        close_habit_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_habit_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
