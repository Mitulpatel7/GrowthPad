package com.example.growthpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.example.growthpad.ConstantURL.user_id;

public class Add_task_activity extends AppCompatActivity {
    EditText add_task_edittext ,add_descriprion_edittext;

    Spinner add_tag_spinner;

    CheckBox important , urgent , repeat_task , remind_me;

    Button save_task_btn , set_date , set_time_btn;

    ImageButton close_task_imgbtn;

    TextView task_date_textview , task_attachments , task_time_textview;

    String[] task_tags = {"Personal", "Home" , "Office"};

    private int year, month,day, hour, minute;
    String add_tag;

    private Context _context;


    public static FragmentManager fragmentManager;
    SharedPreferences sp;
    String sImportant,sUrgent,sRepeatTast,sRemindMe;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_activity);
        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);
        add_task_edittext = findViewById(R.id.add_task_edittext);
        add_descriprion_edittext = findViewById(R.id.add_task_description_edittext);
        add_tag_spinner = findViewById(R.id.task_add_tag_spinner);
        important = findViewById(R.id.task_checkbox_important);
        urgent = findViewById(R.id.task_checkbox_urgent);
        remind_me = findViewById(R.id.task_checkbox_remind_me);
        task_date_textview = findViewById(R.id.task_date_textview);
        task_time_textview = findViewById(R.id.task_time_textview);
        set_time_btn = findViewById(R.id.task_set_time_btn);
        repeat_task = findViewById(R.id.task_checkbox_repeat_task);
        save_task_btn = findViewById(R.id.task_save_btn);
        set_date = findViewById(R.id.task_set_date_btn);
        close_task_imgbtn = findViewById(R.id.close_task_btn);
        task_attachments = findViewById(R.id.task_attachment_file_textview);

        important.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(important.isChecked()){
                    sImportant = important.getText().toString();
                }
                else{
                    sImportant = "";
                }
            }
        });

        urgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(important.isChecked()){
                    sUrgent = urgent.getText().toString();
                }
                else{
                    sUrgent = "";
                }
            }
        });

        repeat_task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(repeat_task.isChecked()){
                    sRepeatTast = repeat_task.getText().toString();
                }
                else{
                    sRepeatTast = "";
                }
            }
        });

        remind_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(remind_me.isChecked()){
                    sRemindMe = remind_me.getText().toString();
                }
                else{
                    sRemindMe = "";
                }
            }
        });

        save_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(Add_task_activity.this).isConnectingToInternet())
                {
                    new task_details().execute();
                }
                else
                {
                    new ConnectionDetector(Add_task_activity.this).connectiondetect();
                }

            }
        });


        close_task_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_task_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





        // add_tag spinner code
        ArrayAdapter tag_adapter = new ArrayAdapter(Add_task_activity.this,android.R.layout.simple_list_item_1,task_tags);
        add_tag_spinner.setAdapter(tag_adapter);
        add_tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_tag =parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                task_date_textview.setText(format.format(calendar.getTime()));

            }
        };

        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*final Calendar c = Calendar.getInstance();
               year = c.get(Calendar.YEAR);
               month = c.get(Calendar.MONTH);
               day = c.get(Calendar.DAY_OF_MONTH);
               hour = c.get(Calendar.HOUR_OF_DAY);
               minute = c.get(Calendar.MINUTE);*/

               new DatePickerDialog(Add_task_activity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });












    }

//task add code
    private class task_details extends AsyncTask<String,String,String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Add_task_activity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.dismiss();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashmap = new HashMap<>();
            hashmap.put("user_id",sp.getString(ConstantURL.ID,""));
            hashmap.put("task_name",add_task_edittext.getText().toString());
            hashmap.put("task_description",add_descriprion_edittext.getText().toString());
            hashmap.put("task_tag",add_tag);
            hashmap.put("task_priority",sImportant);
            hashmap.put("task_repeat",sRepeatTast);
            hashmap.put("task_reminder",sRemindMe);
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"task_details.php",MakeServiceCall.POST,hashmap);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True"))
                {
                    Toast.makeText(Add_task_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_task_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(Add_task_activity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
