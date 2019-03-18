package com.example.bulletjournal;

public class TaskData {
    private int num;
    private String task;
    private String date;
    private String bm;

    public TaskData(int num, String task, String date, String bm) {
        this.num = num;
        this.task = task;
        this.date = date;
        this.bm = bm;
    }

//    public TaskData(){}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }
}
