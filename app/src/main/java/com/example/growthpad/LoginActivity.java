package com.example.growthpad;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String GOOGLE_ACCOUNT = "google_account";
    EditText login_email , login_password;
    TextView forgotpassword ;
    Button login_btn , createaccount;

    private static final String TAG = "Growthpad";
    private SignInButton googleSignInButton;
    GoogleSignInClient googleSignInClient;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_emailid);
        login_password  = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        forgotpassword = findViewById(R.id.login_forgot_password_txtview);
        createaccount = findViewById(R.id.create_account_btn);
        googleSignInButton = findViewById(R.id.sign_in_button);
        sp = getSharedPreferences(ConstantURL.PREFERENCE,MODE_PRIVATE);

        //create account click event
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });




        // frogot password click event
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });




        // login button click event
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login_email.getText().toString().equals(""))
                {
                    login_email.setError("Email or Phone is Required");
                }

                else if (login_password.getText().toString().equals(""))
                {
                    login_password.setError("Password must be between 6 to 10 letters");
                }
                else{
                    if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet())
                    {
                        new logindata().execute();
                    }
                    else
                    {
                        new ConnectionDetector(LoginActivity.this).connectiondetect();
                    }
                }
            }
        });

        // google button
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

    private void onLoggedIn(GoogleSignInAccount account) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra(LoginActivity.GOOGLE_ACCOUNT, account);
        startActivity(intent);
        finish();


    }

    // Login DataBase Connection
    private class logindata extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd =new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("email",login_email.getText().toString());
            hashMap.put("password",login_password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"login.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try
            {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True")) {
                    Toast.makeText(LoginActivity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    JSONArray array = object.getJSONArray("response");
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject = array.getJSONObject(i);
                        sp.edit().putString(ConstantURL.ID,jsonObject.getString("id")).commit();
                        sp.edit().putString(ConstantURL.NAME,jsonObject.getString("username")).commit();
                        sp.edit().putString(ConstantURL.PASSWORD,jsonObject.getString("password")).commit();
                        sp.edit().putString(ConstantURL.EMAIL,jsonObject.getString("email")).commit();
                        sp.edit().putString(ConstantURL.CONTACT,jsonObject.getString("phone")).commit();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                }else
                {
                    Toast.makeText(LoginActivity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
