package com.example.bulletjournal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class DailyFragment extends Activity {

    @Nullable
//    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);
        return rootView;
    }

    private static final String TAG = "DailyFragment";

    private Button mOpenDialog;
    public TextView mInputDisplay;

    public String mInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daily);
        mOpenDialog = findViewById(R.id.add_task);
        mInputDisplay = findViewById(R.id.display_tasks);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog. ");
            }

        });
    }
}

