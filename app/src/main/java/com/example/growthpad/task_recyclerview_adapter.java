package com.example.growthpad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class task_recyclerview_adapter extends RecyclerView.Adapter<task_recyclerview_adapter.task_holder> {

        Context context;
        List<Task> tasks;
        SharedPreferences sp;
        String sTaskId,sTag;

    public task_recyclerview_adapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        sp = context.getSharedPreferences(ConstantURL.PREFERENCE,Context.MODE_PRIVATE);
    }


    @Override
    public task_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_task_card_view,parent,false);
        task_holder myholder = new task_holder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(task_holder holder, final int position) {
        holder.task_name.setText(tasks.get(position).getTask_name());
        holder.subtask.setText(tasks.get(position).getSubtask());
        holder.task_deadline.setText(tasks.get(position).getTask_deadline());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString(ConstantURL.task_id,tasks.get(position).getTask_id()).commit();
                sp.edit().putString(ConstantURL.task_name,tasks.get(position).getTask_name()).commit();
                sp.edit().putString(ConstantURL.task_tag,tasks.get(position).getSubtask()).commit();
                sp.edit().putString(ConstantURL.task_description,tasks.get(position).getTask_description()).commit();
                sp.edit().putString(ConstantURL.task_important,tasks.get(position).getTask_important()).commit();
                sp.edit().putString(ConstantURL.task_urgent,tasks.get(position).getTask_urgent()).commit();
                sp.edit().putString(ConstantURL.task_date,tasks.get(position).getTask_deadline()).commit();
                sp.edit().putString(ConstantURL.task_time,tasks.get(position).getTask_time()).commit();
                sp.edit().putString(ConstantURL.task_repeat,tasks.get(position).getTask_repeat()).commit();
                sp.edit().putString(ConstantURL.task_reminder,tasks.get(position).getTask_reminder()).commit();
                sp.edit().putString(ConstantURL.task_attachments,tasks.get(position).getTask_attachment()).commit();
                sp.edit().putString(ConstantURL.task_created_date,tasks.get(position).getTask_created_date()).commit();
                context.startActivity(new Intent(context,TaskDetails.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(context).isConnectingToInternet()) {
                    sTaskId = tasks.get(position).getTask_id();
                    new deleteData().execute();
                }
                else{
                    new ConnectionDetector(context).connectiondetect();
                }
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(context).isConnectingToInternet()) {
                    sTaskId = tasks.get(position).getTask_id();
                    sTag = tasks.get(position).getSubtask();
                    new shareData().execute();
                }
                else{
                    new ConnectionDetector(context).connectiondetect();
                }
            }
        });

        //holder.arrow.setImageResource(tasks.get(position).getArrow());
        //holder.dots.setImageResource(tasks.get(position).getDots());



    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class task_holder extends RecyclerView.ViewHolder{

        private TextView task_name;
        private TextView task_deadline;
        private TextView subtask;
        private CheckBox checkBox;
        private  ImageView delete;
        private ImageView share;

        public task_holder(View itemView) {
             super(itemView);

            task_name = itemView.findViewById(R.id.title_custom_task);
            task_deadline = itemView.findViewById(R.id.custom_task_deadline);
            subtask = itemView.findViewById(R.id.custom_sub_task);
            checkBox = itemView.findViewById(R.id.custom_task_checkbox);
            delete = itemView.findViewById(R.id.custom_task_delete);
            share  = itemView.findViewById(R.id.custom_task_share);

        }
    }

    private class deleteData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("task_id",sTaskId);
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"deleteTask.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getString("status").equals("True")){
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else{
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class shareData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("type","Task");
            hashMap.put("task_id",sTaskId);
            hashMap.put("tag",sTag);
            return new MakeServiceCall().MakeServiceCall(ConstantURL.URL+"addTaskCommunity.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getString("status").equals("True")){
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    //context.startActivity(new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else{
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
