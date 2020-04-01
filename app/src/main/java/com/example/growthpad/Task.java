package com.example.growthpad;

import android.widget.CheckBox;

public class Task {


    private String task_name;
    private  String subtask;
    private  String task_deadline;
    private  String task_id;
    String task_description,task_important,task_urgent,task_time,task_repeat,task_reminder,task_attachment,task_created_date;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public String getTask_deadline() {
        return task_deadline;
    }

    public void setTask_deadline(String task_deadline) {
        this.task_deadline = task_deadline;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public String getTask_important() {
        return task_important;
    }

    public void setTask_important(String task_important) {
        this.task_important = task_important;
    }

    public String getTask_urgent() {
        return task_urgent;
    }

    public void setTask_urgent(String task_urgent) {
        this.task_urgent = task_urgent;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public String getTask_repeat() {
        return task_repeat;
    }

    public void setTask_repeat(String task_repeat) {
        this.task_repeat = task_repeat;
    }

    public String getTask_reminder() {
        return task_reminder;
    }

    public void setTask_reminder(String task_reminder) {
        this.task_reminder = task_reminder;
    }

    public String getTask_attachment() {
        return task_attachment;
    }

    public void setTask_attachment(String task_attachment) {
        this.task_attachment = task_attachment;
    }

    public String getTask_created_date() {
        return task_created_date;
    }

    public void setTask_created_date(String task_created_date) {
        this.task_created_date = task_created_date;
    }

    public Task(String id, String exam, String s, String s1, String task_description, String task_important, String task_urgent, String task_time, String task_repeat, String task_reminder, String task_attachment, String task_created_date) {
        this.task_id = id;
        this.task_name = exam;
        this.subtask = s;
        this.task_deadline = s1;
        this.task_description=task_description;
        this.task_important=task_important;
        this.task_urgent=task_urgent;
        this.task_time=task_time;
        this.task_repeat=task_repeat;
        this.task_reminder=task_reminder;
        this.task_attachment=task_attachment;
        this.task_created_date=task_created_date;
    }

}
