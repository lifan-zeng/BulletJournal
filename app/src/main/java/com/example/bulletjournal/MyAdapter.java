package com.example.bulletjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<TaskData> arrayList;

    public MyAdapter (Context context, ArrayList<TaskData> arrayList) {
        this.context =  context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.sub_list_view_layout, null);
        TextView t_task = convertView.findViewById(R.id.view_task);
        TextView t_date = convertView.findViewById(R.id.view_date);

        TaskData task = arrayList.get(position);

        t_task.setText(task.getTask());
        t_date.setText(task.getDate());

        return convertView;

    }
    @Override
    public int getCount(){
        return this.arrayList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }


}
