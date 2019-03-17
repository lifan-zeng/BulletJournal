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
import android.widget.TextView;


public class DailyDialogBox extends DialogFragment {

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;

    private EditText input;
    private TextView actButtonOk;
    private TextView actButtonCancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_daily, container, false);
        actButtonOk = view.findViewById(R.id.action_ok);
        actButtonCancel = view.findViewById(R.id.action_cancel);
        input = view.findViewById(R.id.editTitle);


        actButtonCancel.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        actButtonOk.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String textInput = input.getText().toString();
                if (!textInput.equals("")){
                    onInputSelected.sendInput(textInput);
//                    DailyFragment fragment = (DailyFragment) getActivity().getFragmentManager().findFragmentByTag("DailyDialog");
//                    fragment.inputDisplay.setText(textInput);
                }
                getDialog().dismiss();
            }
        });
        return view;
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
