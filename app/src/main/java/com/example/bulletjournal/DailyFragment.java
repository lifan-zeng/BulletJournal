package com.example.bulletjournal;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DailyFragment extends Fragment implements DailyDialogBox.OnInputSelected {

    private static final String TAG = "DailyFragment";
    private Button openDialog;
    public TextView inputDisplay;
    private TextView headingDate;
    private ListView listView;
    private DatabaseHelper myDbase;
    private ArrayList<TaskData> arrayList;
    private Adapter myAdapter;
    final DailyFragment thisThing = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);
        openDialog = rootView.findViewById(R.id.add_task);
        inputDisplay = rootView.findViewById(R.id.display_tasks);
        listView = rootView.findViewById(R.id.list_tasks);
        myDbase = new DatabaseHelper(getContext());
        headingDate = rootView.findViewById(R.id.heading_date);
        arrayList = new ArrayList<>();

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

                EditDataDialogBox dialog = new EditDataDialogBox(thisThing);
                dialog.setArguments(args);
                dialog.setTargetFragment(DailyFragment.this, 1);
                dialog.show(getFragmentManager(), "DailyDialog");
                loadDataListView();
                return false;
            }
        });

        //add task
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                DailyDialogBox dialog = new DailyDialogBox(thisThing);
                dialog.setTargetFragment(DailyFragment.this, 1);
                dialog.show(getFragmentManager(), "DailyDialog");
                loadDataListView();
            }
        });

        loadDataListView();

        return rootView;
    }

    public void loadDataListView() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        String currentDate = sdf.format(new Date());

        Log.d(TAG, "loadDataListView: " + currentDate);

        headingDate.setText(currentDate);
        arrayList = myDbase.getDatesDataArray(currentDate);

        myAdapter = new MyAdapter(getActivity(), arrayList);
        listView.setAdapter((BaseAdapter)myAdapter);
        ((BaseAdapter)myAdapter).notifyDataSetChanged();
        listView.invalidateViews();
    }

    @Override
    public void sendInput(String input) {
        inputDisplay.setText(input);
    }

}

