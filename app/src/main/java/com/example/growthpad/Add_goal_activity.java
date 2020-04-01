package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class Add_goal_activity extends AppCompatActivity {

    EditText goal_name_edittext , add_goal_description_edittext ;

    ImageButton goal_close_btn;

    Button goal_save_btn , set_goal_time_btn;

    Spinner add_goal_tag_spinner , add_goal_duration_spinner , add_goal_frequency_spinner;
    String[] goal_tags_array = {"Personal" , "Work" , "Office"  };
    String[] goal_duration_array = {"2 Months" , "3 Months" , "4 Months"};
    String[] goal_frequency_array = {"Every Day" ,"Every 3 Hour" , "Every Hour"};

    TextView goal_time , goal_attachments;

    CheckBox goal_remind_me;

    Calendar calendar;
    Calendar timecalendar;


    SharedPreferences sp;

    String sReminder;


    private int year;
    private int month;
    private int day;
    int hour;
    int min;
    private String am_pm;


    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private static FragmentManager fragmentManager;

    String tag,duration,frequency;


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

        goal_attachments = findViewById(R.id.goal_attachment_file_textview);


        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);


        goal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new ConnectionDetector(Add_goal_activity.this).isConnectingToInternet())
                {
                    new goal_details().execute();
                }
                else
                {
                    new ConnectionDetector(Add_goal_activity.this).connectiondetect();
                }
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

        goal_remind_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(goal_remind_me.isChecked()){
                    sReminder = goal_remind_me.getText().toString();
                }
                else{
                    sReminder = "";
                }
            }
        });




        // spinner tags
        ArrayAdapter goal_tags_adapter = new ArrayAdapter(Add_goal_activity.this,android.R.layout.simple_list_item_1,goal_tags_array);
        add_goal_tag_spinner.setAdapter(goal_tags_adapter);
        add_goal_tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 tag = parent.getItemAtPosition(position).toString();
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
                duration = parent.getItemAtPosition(position).toString();
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
               frequency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        calendar = Calendar.getInstance();
        timecalendar = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour  = hourOfDay;
                min = minute;
                String sAMPM;

                if (hour==0)
                {
                    hour+=12;
                    sAMPM = "AM";
                }
                else if(hour==12)
                {
                    sAMPM = "PM";
                }
                else if(hour>12)
                {
                    hour-=12;
                    sAMPM = "PM";
                }
                else
                {
                    sAMPM = "AM";
                }

                String sMinute;
                if(min<10)
                {
                    sMinute = "0"+min;
                }
                else{
                    sMinute = String.valueOf(min);
                }


                goal_time.setText(hour+":"+min+" "+sAMPM);

            }
        };

        set_goal_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Add_goal_activity.this,time,hour,min,false).show();
            }
        });




    }

    private class goal_details extends AsyncTask<String,String,String> {

        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Add_goal_activity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap hashmap = new HashMap<>();
            hashmap.put("user_id",sp.getString(ConstantURL.ID,""));
            hashmap.put("goal_name",goal_name_edittext.getText().toString());
            hashmap.put("goal_description",add_goal_description_edittext.getText().toString());
            hashmap.put("goal_tag",tag);
            hashmap.put("goal_duration",duration);
            hashmap.put("goal_frequency",frequency);
            hashmap.put("goal_time",goal_time.getText().toString());
            hashmap.put("goal_reminder",sReminder);
            hashmap.put("goal_attachments"," ");
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"goal_details.php",MakeServiceCall.POST,hashmap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getString("status").equals("True"))
                {
                    Toast.makeText(Add_goal_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_goal_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(Add_goal_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
