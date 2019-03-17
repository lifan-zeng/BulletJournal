package com.example.bulletjournal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DailyFragment extends Fragment implements DailyDialogBox.OnInputSelected {

    private Button openDialog;
    public TextView inputDisplay;

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);
        openDialog = rootView.findViewById(R.id.add_task);
        inputDisplay = rootView.findViewById(R.id.display_tasks);

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

    @Override
    public void sendInput(String input) {
        inputDisplay.setText(input);
    }
}

