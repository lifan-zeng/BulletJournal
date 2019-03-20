package com.example.bulletjournal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MonthlyFragment extends Fragment {

    private CalendarView calendar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        calendar = view.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String newMonth;
                if (9 > month) {
                    newMonth = "0" + (month + 1);
                } else {
                    newMonth = (month + 1) + "";
                }

                String date = newMonth + "/" + dayOfMonth + "/" + year; //date format: mm/dd/yyyy

                Bundle dateBundle = new Bundle();
                dateBundle.putString("date", date);

                CalendarDailyFragment calendarDailyFragment = new CalendarDailyFragment();
                calendarDailyFragment.setArguments(dateBundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, calendarDailyFragment );
                transaction.commit();


            }
        });

        return view;
    }

}
