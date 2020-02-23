package com.example.growthpad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class task_recyclerview_adapter extends RecyclerView.Adapter<task_recyclerview_adapter.task_holder> {

    Context context;
    List<Task> tasks;

    public task_recyclerview_adapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }


    @Override
    public task_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_task_card_view,parent,false);
        task_holder myholder = new task_holder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(task_holder holder, int position) {
        holder.task_name.setText(tasks.get(position).getTask_name());
        holder.subtask.setText(tasks.get(position).getSubtask());
        holder.task_deadline.setText(tasks.get(position).getTask_deadline());
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
        private ImageView arrow;
        private  ImageView dots;

        public task_holder(View itemView) {
            super(itemView);

            task_name = itemView.findViewById(R.id.title_custom_task);
            task_deadline = itemView.findViewById(R.id.custom_task_deadline);
            subtask = itemView.findViewById(R.id.custom_sub_task);
            checkBox = itemView.findViewById(R.id.custom_task_checkbox);
            arrow = itemView.findViewById(R.id.custom_task_extends_arrow);
            dots = itemView.findViewById(R.id.custom_task_dots_3);

        }
    }
}
