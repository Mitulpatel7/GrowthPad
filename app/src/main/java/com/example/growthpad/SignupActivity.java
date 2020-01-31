package com.example.growthpad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    EditText username,phone,email,password;
    Button signup_btn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.signup_full_name);
        phone = findViewById(R.id.signup_phone_number);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_create_password);
        signup_btn = findViewById(R.id.signup_create_account_btn);
        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals(""))
                {
                    username.setError("Username is Required");
                }
                else if (phone.getText().toString().equals(""))
                {
                    phone.setError("Phone is Required");
                }
                else if (password.getText().toString().equals(""))
                {
                    password.setError("Password must be between 6 to 10 letters");
                }
                else{
                    if (new ConnectionDetector(SignupActivity.this).isConnectingToInternet())
                    {
                        new signupdata().execute();
                    }
                    else
                    {
                        new ConnectionDetector(SignupActivity.this).connectiondetect();
                    }
                }
            }
        });


    }

    private class signupdata extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignupActivity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("username",username.getText().toString());
            hashMap.put("contact",phone.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"signup.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try{
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True"))
                {
                  Toast.makeText(SignupActivity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignupActivity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }

            }catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
