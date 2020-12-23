package com.example.attendance.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.model.LeaveRequestModel;
import com.example.attendance.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RequestLeaveFragment extends Fragment {
    Button requestTime,sendRequest;
    EditText reason;
    FirebaseAuth auth;
    TextView hours;
    CharSequence currentMonth,currentYear;
    String name;
    int hourChoose,minuteChoose;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_leave_request,container,false);
        requestTime=view.findViewById(R.id.btn_open_timepicker);
        sendRequest=view.findViewById(R.id.btn_send_request);
        reason=view.findViewById(R.id.edit_leave_request);
        hours=view.findViewById(R.id.text_timepicker);
        auth=FirebaseAuth.getInstance();
        requestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogFragment timepicker= new TimeRequestFragment();
                //timepicker.show(getActivity().getSupportFragmentManager(),"time picker");
                leaveRequest();

            }
        });
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             pushData(currentYear,currentMonth,hourChoose,minuteChoose);
            }
        });
        return  view;
    }

    private void leaveRequest() {
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int date=calendar.get(Calendar.DATE);
        final int hour =calendar.get(Calendar.HOUR_OF_DAY);
        int munites=calendar.get(Calendar.MINUTE);
        boolean is24Hours=DateFormat.is24HourFormat(getContext());

       TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {
               Calendar calendar1=Calendar.getInstance();
               calendar1.set(Calendar.MONTH,month);
               calendar1.set(Calendar.YEAR,year);
                currentMonth= DateFormat.format("MMM",calendar1);
                currentYear=DateFormat.format("yyyy",calendar1);

             //  pushData(currentMonth,hourOfDay,minute);
               hourChoose=hourOfDay;
               minuteChoose=minute;

               hours.setText("You want to leave at "+hourOfDay+" : "+minute);

           }
       },hour,munites,is24Hours);
        timePickerDialog.show();



    }

    private void pushData(final CharSequence currentyear,final CharSequence currentMonth, final int hourOfDay, final int minute) {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            name=dataSnapshot.child("name").getValue(String.class);
                        }
                        if(minute!=0&&hourOfDay!=0&&
                                String.valueOf(currentMonth)!=null&&reason.getText().toString().length()>0){
                            LeaveRequestModel leaveRequestModel=new LeaveRequestModel(name,String.valueOf(hourOfDay),
                                    String.valueOf(minute),String.valueOf(currentMonth),reason.getText().toString());

                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Leave Request");
                            reference.child(currentMonth+" "+currentyear).child(id).push().setValue(leaveRequestModel);

                        }else {
                            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
   /* DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.MONTH,month);
            CharSequence currentMonth= DateFormat.format("MMM",calendar1);
            //hours.setText(charSequence);
            hours.setText(currentMonth+" "+hourOfDay+" "+minute);
            Log.i("sada",currentMonth+" "+hourOfDay+" "+minute);

        }
    },year,month,date);
// datePickerDialog.show();

    */
