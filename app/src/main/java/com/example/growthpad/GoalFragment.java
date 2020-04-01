package com.example.growthpad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GoalFragment extends Fragment {

    RecyclerView goal_recycler_view;
    List<Goal> goal_list;

    SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        sp = getActivity().getSharedPreferences(ConstantURL.PREFERENCE, Context.MODE_PRIVATE);

        goal_recycler_view = v.findViewById(R.id.goal_recycler_view);
        goal_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
           new getGoal().execute();
        }
        else
        {
            new ConnectionDetector(getActivity()).connectiondetect();
        }


        goal_list = new ArrayList<>();
        goal_list.add(new Goal("1","Top in Class","Personal","5th may 2020"));


        return v;
    }

    private class getGoal extends AsyncTask<String,String,String> {

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
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"getGoal.php",MakeServiceCall.POST,hashmap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try
            {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equals("True"))
                {
                    JSONArray array = object.getJSONArray("response");
                    goal_list = new ArrayList<>();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject = array.getJSONObject(i);
                        goal_list.add(new Goal(jsonObject.getString("goal_id"),jsonObject.getString("goal_name"),jsonObject.getString("goal_tag"),jsonObject.getString("goal_time")));
                    }
                    goal_recyclerview_adapter recyclerviewAdapter = new goal_recyclerview_adapter(getContext(),goal_list);
                    goal_recycler_view.setAdapter(recyclerviewAdapter);
                }
                else
                {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}