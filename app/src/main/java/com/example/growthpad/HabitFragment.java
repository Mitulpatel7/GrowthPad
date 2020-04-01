package com.example.growthpad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HabitFragment extends Fragment {

    RecyclerView habit_recyclerview;
    private List<Habit> habit_list;
    ImageView delete;


    SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

         View v = inflater.inflate(R.layout.fragment_habit, container, false);
        // Inflate the layout for this fragment
        sp =this.getActivity().getSharedPreferences(ConstantURL.PREFERENCE, Context.MODE_PRIVATE);
        delete = v.findViewById(R.id.custom_habit_delete);
        habit_recyclerview = v.findViewById(R.id.habit_recycler_view);
        habit_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            new getHabit().execute();
        }
        else
        {
            new ConnectionDetector(getActivity()).connectiondetect();
        }
        return v;
    }

    private class getHabit extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashmap = new HashMap<>();
            hashmap.put("user_id",sp.getString(ConstantURL.ID,""));
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"getHabit.php",MakeServiceCall.POST,hashmap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {


                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True"))
                {
                    JSONArray array = object.getJSONArray("response");
                    habit_list = new ArrayList<>();
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject = array.getJSONObject(i);
                        habit_list.add(new Habit(jsonObject.getString("habit_id"),
                                jsonObject.getString("habit_name"),
                                jsonObject.getString("habit_tag"),
                                jsonObject.getString("habit_description"),
                                jsonObject.getString("habit_duration"),
                                jsonObject.getString("habit_time"),
                                jsonObject.getString("habit_reminder"),
                                jsonObject.getString("habit_attachments"),
                                jsonObject.getString("habit_created_date")));
                    }
                   habit_recyclerview_adapter recyclerviewAdapter =
                            new habit_recyclerview_adapter(getContext(),habit_list);
                    habit_recyclerview.setAdapter(recyclerviewAdapter);

                }
                else
                {
                    Toast.makeText(getActivity(),object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
