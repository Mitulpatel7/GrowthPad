package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

import static com.example.growthpad.ConstantURL.habit_attachments;
import static com.example.growthpad.ConstantURL.habit_description;
import static com.example.growthpad.ConstantURL.habit_duration;
import static com.example.growthpad.ConstantURL.habit_name;
import static com.example.growthpad.ConstantURL.habit_reminder;
import static com.example.growthpad.ConstantURL.habit_tag;
import static com.example.growthpad.ConstantURL.habit_time;
import static com.example.growthpad.ConstantURL.task_reminder;

public class Add_habit_activity extends AppCompatActivity {

    EditText add_habit_edittext , add_habit_description_edittext;

    Spinner add_tag_habit_spinner , set_duration_habit_spinner;
    String[] habit_tag_array = {"Personal" , "Work" ,"Home"};
    String[] habit_duration_array = {"21 Days" , "42 Days" , "2 Months"};

    TextView set_habit_time , habit_attachments;

    CheckBox remind_me_habit;

    Button  save_habit_btn , change_habit_time_btn;

    ImageButton close_habit_imgbtn;

    String habit_tag;
    String habit_duration;
    String sRemindMe;


    SharedPreferences sp;



    public static FragmentManager fragmentManager ;

    Calendar calendar;
    Calendar timeCalender;
    int hour,min;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_activity);
        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);


        add_habit_edittext = findViewById(R.id.add_habit_edittext);
        add_habit_description_edittext = findViewById(R.id.add_habit_description_edittext);
        add_tag_habit_spinner = findViewById(R.id.habit_add_tag_spinner);
        set_duration_habit_spinner = findViewById(R.id.habit_set_duration_spinner);
        set_habit_time = findViewById(R.id.habit_time_textview);
        remind_me_habit = findViewById(R.id.habit_checkbox_remind_me);
        change_habit_time_btn = findViewById(R.id.habit_set_time_btn);
        save_habit_btn = findViewById(R.id.habit_save_btn);
        close_habit_imgbtn = findViewById(R.id.close_habit_btn);
        habit_attachments = findViewById(R.id.habit_attachment_file_textview);


        // spinner code
        ArrayAdapter habit_tag_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,habit_tag_array);
        add_tag_habit_spinner.setAdapter(habit_tag_adapter);
        add_tag_habit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habit_tag = parent.getItemAtPosition(position).toString();
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
                habit_duration = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         calendar = Calendar.getInstance();
        timeCalender = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                String sAMPM;
                /*String sAMPM;
                int sHour = 0;
                if(hour>12){
                    sHour-=12;
                    sAMPM = "PM";
                }
                else if(hour==12){
                    sHour = 12;
                    sAMPM = "PM";
                }
                else if(hour==0){
                    sHour += 12;
                    sAMPM = "AM";
                }
                else{
                    sHour+=12;
                    sAMPM = "AM";
                }*/
                if(hour == 0){
                    hour += 12;
                    sAMPM = "AM";
                } else if(hour == 12){
                    sAMPM = "PM";
                } else if(hour > 12){
                    hour -= 12;
                    sAMPM = "PM";
                } else {
                    sAMPM = "AM";
                }


                String sMinute;
                if(min<10){
                    sMinute = "0"+min;
                }
                else{
                    sMinute = String.valueOf(min);
                }

                set_habit_time.setText(hour+" : "+sMinute+" "+sAMPM);

            }
        };



        change_habit_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Add_habit_activity.this,time,hour,min,false).show();
            }
        });







        save_habit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remind_me_habit.isChecked())
                {
                     sRemindMe = remind_me_habit.getText().toString();
                }
                else{
                    sRemindMe = "";
                }
                if(new ConnectionDetector(Add_habit_activity.this).isConnectingToInternet())
                {
                    new habit_details().execute();
                }
                else
                {
                    new ConnectionDetector(Add_habit_activity.this).connectiondetect();
                }

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

    private class habit_details extends AsyncTask<String,String,String> {

        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Add_habit_activity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashmap = new HashMap<>();
            hashmap.put("user_id",sp.getString(ConstantURL.ID,""));
            hashmap.put("habit_name",add_habit_edittext.getText().toString());
            hashmap.put("habit_description",add_habit_description_edittext.getText().toString());
            hashmap.put("habit_tag",habit_tag);
            hashmap.put("habit_duration",habit_duration);
            hashmap.put("habit_time",set_habit_time.getText().toString());
            hashmap.put("habit_reminder",remind_me_habit.getText().toString());
            hashmap.put("habit_attachments",habit_attachments.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"habit_details.php",MakeServiceCall.POST,hashmap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True"))
                {
                    Toast.makeText(Add_habit_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_habit_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Add_habit_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
