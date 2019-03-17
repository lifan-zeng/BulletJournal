package com.example.bulletjournal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class DailyDialogBox extends DialogFragment {

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;

    private EditText inputTitle;
    private EditText inputDate;
    private TextView actButtonOk;
    private TextView actButtonCancel;
    private Switch switchBookmark;
    DatabaseHelper myDbase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_daily, container, false);
        actButtonOk = view.findViewById(R.id.action_ok);
        actButtonCancel = view.findViewById(R.id.action_cancel);
        inputTitle = view.findViewById(R.id.editTitle);
        inputDate = view.findViewById(R.id.editDate);
        switchBookmark = view.findViewById(R.id.switch_bookmark);
        insertData();

        actButtonCancel.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

//        actButtonOk.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                String textInput = inputTitle.getText().toString();
//                if (!textInput.equals("")){
//                    onInputSelected.sendInput(textInput);
//
//                }
//                getDialog().dismiss();
//            }
//        });
        return view;
    }

    public void insertData() {
        actButtonOk.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                boolean isAdded;
                myDbase = new DatabaseHelper(getContext());
                isAdded = myDbase.addData(inputTitle.getText().toString(), inputDate.getText().toString(), switchBookmark.getText().toString() );
                if (isAdded = true) {
                    Toast.makeText(context, "Added Task", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to Add Task", Toast.LENGTH_LONG).show();
                }
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {

        }
    }
}
