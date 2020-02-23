package com.example.growthpad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    private RecyclerView task_recyclerview;
    private List<Task> task_list;



    public TaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        // Inflate the layout for this fragment
        task_recyclerview = v.findViewById(R.id.task_recycler_view);
        task_list = new ArrayList<>();
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
        task_list.add(new Task("Project","2","05/02/2020"));
        task_recyclerview_adapter recyclerviewAdapter =
                new task_recyclerview_adapter(getContext(),task_list);
        task_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        task_recyclerview.setAdapter(recyclerviewAdapter);
        return v;
    }
}