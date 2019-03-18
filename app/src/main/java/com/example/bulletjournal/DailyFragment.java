package com.example.bulletjournal;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DailyFragment extends Fragment implements DailyDialogBox.OnInputSelected {

    private Button openDialog;
    public TextView inputDisplay;
    private ListView listView;
    private DatabaseHelper myDbase;
    private ArrayList<TaskData> arrayList;
    private Adapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);
        openDialog = rootView.findViewById(R.id.add_task);
        inputDisplay = rootView.findViewById(R.id.display_tasks);
        listView = rootView.findViewById(R.id.list_tasks);
        myDbase = new DatabaseHelper(getContext());
        arrayList = new ArrayList<>();


//        viewAll();
        loadDataListView();

        //update data
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                TaskData taskData = (TaskData) arg0.getItemAtPosition(pos);
                Cursor res = myDbase.getData();


                Bundle args = new Bundle();
                args.putString("num", taskData.getNum() + "");
                args.putString("task", taskData.getTask());
                args.putString("date", taskData.getBm());

                System.out.println("lifan");
                System.out.println(taskData.getNum());

                EditDataDialogBox dialog = new EditDataDialogBox();
                dialog.setArguments(args);
                dialog.setTargetFragment(DailyFragment.this, 1);
                dialog.show(getFragmentManager(), "DailyDialog");
//                dialog.editData();
                return false;
            }
        });

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                DailyDialogBox dialog = new DailyDialogBox();
                dialog.setTargetFragment(DailyFragment.this, 1);
                dialog.show(getFragmentManager(), "DailyDialog");
            }
        });
        return rootView;
    }



    private void loadDataListView() {
        arrayList = myDbase.getDataArray();

        System.out.println(arrayList.toString());

        myAdapter = new MyAdapter(getActivity(), arrayList);
        listView.setAdapter((BaseAdapter)myAdapter);
        ((BaseAdapter)myAdapter).notifyDataSetChanged();
        listView.invalidateViews();
    }

//    public void viewAll() {
//        Cursor res = myDbase.getData();
//
//        if (res.getCount() == 0) {
//            showMessage("Error", "No Tasks Found");
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()) {
//            buffer.append("Task: " + res.getString(1) + "\n");
//            buffer.append("Date: " + res.getString(2) + "\n");
//            buffer.append("Bookmark: " + res.getString(3) + "\n\n");
//        }
//        showMessage("Data", buffer.toString());
//    }
//
//    public void showMessage(String title, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }

    @Override
    public void sendInput(String input) {
        inputDisplay.setText(input);
    }
}

