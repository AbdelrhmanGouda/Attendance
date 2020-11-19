package com.example.attendance.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;

public class RequestLeaveFragment extends Fragment  implements TimePickerDialog.OnTimeSetListener {
    Button requestTime,sendRequest;
    EditText reason;
    TextView hours;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_leave_request,container,false);
        requestTime=view.findViewById(R.id.btn_open_timepicker);
        sendRequest=view.findViewById(R.id.btn_send_request);
        reason=view.findViewById(R.id.edit_leave_request);
        hours=view.findViewById(R.id.text_timepicker);
        requestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DialogFragment timepicker= new TimeRequestFragment();
                timepicker.show(getActivity().getSupportFragmentManager(),"time picker");

            }
        });

        return  view;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        this.hours.setText("you want to leave after "+hourOfDay);

        Toast.makeText(getActivity(),"this is "+hourOfDay,Toast.LENGTH_LONG).show();
    }
}
