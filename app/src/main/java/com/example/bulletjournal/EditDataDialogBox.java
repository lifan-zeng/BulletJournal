package com.example.bulletjournal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.bulletjournal.DatabaseHelper.STATE_FALSE;
import static com.example.bulletjournal.DatabaseHelper.STATE_TRUE;


public class EditDataDialogBox extends DialogFragment {

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;

    private EditText inputTitle;
    private EditText inputDate;
    private TextView actButtonOk;
    private TextView actButtonCancel;
    private Button actButtonDelete;
    private Switch switchBookmark;
    private String setNum;
    private int switchState;

    DatabaseHelper myDbase;

    private DailyFragment frag;
    private CalendarDailyFragment fragCalendar;
    private BookmarksFragment fragBm;


    @SuppressLint("ValidFragment")
    public EditDataDialogBox(DailyFragment frag) {
        super();
        this.frag = frag;
    }
    @SuppressLint("ValidFragment")
    public EditDataDialogBox(CalendarDailyFragment fragCalendar) {
        super();
        this.fragCalendar = fragCalendar;
    }

    @SuppressLint("ValidFragment")
    public EditDataDialogBox(BookmarksFragment fragBm) {
        super();
        this.fragBm = fragBm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_dialog_daily, container, false);
        actButtonOk = view.findViewById(R.id.action_ok);
        actButtonCancel = view.findViewById(R.id.action_cancel);
        inputTitle = view.findViewById(R.id.editTitle);
        inputDate = view.findViewById(R.id.editDate);
        switchBookmark = view.findViewById(R.id.switch_bookmark);
        myDbase = new DatabaseHelper(getContext());
        actButtonDelete = view.findViewById(R.id.deleteTask);

        Bundle args = getArguments();
        if (args != null) {
            setNum = args.getString("num");
            String setName = args.getString("task");
            String setDate = args.getString("date");
        }

        editData();
        deleteTask();



        final Calendar myCalendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        inputDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        switchBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "Bookmarked", LENGTH_SHORT).show();
                    switchState = STATE_TRUE;
                } else {
                    Toast.makeText(getActivity(), "Removed Bookmark", LENGTH_SHORT).show();
                    switchState = STATE_FALSE;
                }
            }
        });

        actButtonCancel.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }


    public void editData() {
        actButtonOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v){
                Context context = getActivity();
                boolean isUpdated;
                if (inputTitle.getText().toString().equals("") || inputDate.getText().toString().equals("") || String.valueOf(switchState).equals("")) {
                    Toast.makeText(context, "Failed to Edit Task: Empty Textbox", LENGTH_SHORT).show();
                } else {
                    isUpdated = myDbase.updateData(setNum, inputTitle.getText().toString(), inputDate.getText().toString(), String.valueOf(switchState));

                    try {
                        frag.loadDataListView();
                    } catch (NullPointerException e) {
                        try {
                            fragCalendar.loadDataListView();
                        } catch (NullPointerException f) {
                            fragBm.loadDataListView();
                        }
                    }

                    if (isUpdated = true) {
                        Toast.makeText(context, "Edited Task", LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to Edit Task", LENGTH_SHORT).show();
                    }
                    getDialog().dismiss();
                }
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
    public void deleteTask() {
        actButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRow = myDbase.deleteData(setNum);

                try {
                    frag.loadDataListView();
                } catch (NullPointerException e) {
                    try {
                        fragCalendar.loadDataListView();
                    } catch (NullPointerException f) {
                        fragBm.loadDataListView();
                    }
                }

                if (deletedRow > 0) {
                    Toast.makeText(getActivity(), "Deleted Task", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to Delete Task", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }
        });
    }

}
