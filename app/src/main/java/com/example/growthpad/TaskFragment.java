package com.example.growthpad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TaskFragment extends Fragment {

    private RecyclerView task_recyclerview;
    private List<Task> task_list;
    ImageView menu;

    SharedPreferences sp;


    public TaskFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        // Inflate the layout for this fragment

        sp = this.getActivity().getSharedPreferences(ConstantURL.PREFERENCE, Context.MODE_PRIVATE);

        task_recyclerview = v.findViewById(R.id.task_recycler_view);
        menu = v.findViewById(R.id.custom_task_delete);
        task_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(new ConnectionDetector(getActivity()).isConnectingToInternet()){
            new getTask().execute();
        }
        else{
            new ConnectionDetector(getActivity()).connectiondetect();
        }

        /*task_list = new ArrayList<>();
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));
        task_list.add(new Task("Exam","5","02/02/2020"));
        task_list.add(new Task("Practicals","4","04/02/2020"));
        task_list.add(new Task("Project","2","05/02/2020"));*/


        return v;
    }

    private class getTask extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("user_id",sp.getString(ConstantURL.ID,""));
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"getTask.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getString("status").equals("True")){
                    JSONArray array = object.getJSONArray("response");
                    task_list = new ArrayList<>();
                    for (int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        task_list.add(new Task(jsonObject.getString("task_id"),
                                jsonObject.getString("task_name"),
                                jsonObject.getString("task_tag"),
                                jsonObject.getString("task_date"),
                                jsonObject.getString("task_description"),
                                jsonObject.getString("task_important"),
                                jsonObject.getString("task_urgent"),
                                jsonObject.getString("task_time"),
                                jsonObject.getString("task_repeat"),
                                jsonObject.getString("task_reminder"),
                                jsonObject.getString("task_attachments"),
                                jsonObject.getString("task_created_date")));
                    }
                    task_recyclerview_adapter recyclerviewAdapter =
                            new task_recyclerview_adapter(getContext(),task_list);
                    task_recyclerview.setAdapter(recyclerviewAdapter);
                }
                else{
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}