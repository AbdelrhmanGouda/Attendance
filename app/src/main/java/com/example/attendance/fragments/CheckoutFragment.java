package com.example.attendance.fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.model.ReportModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckoutFragment extends Fragment {
    FirebaseAuth auth;
    Button checkout;
    int startHour,startMinutes;
    String name,department;
    String timeHours,timeMinutes;
    CharSequence currentMonth,currentYear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_checkout,container,false);
        auth=FirebaseAuth.getInstance();
        checkout=view.findViewById(R.id.btn_checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });
        if(getArguments()!=null){
            startHour=getArguments().getInt("startHour1");
            startMinutes=getArguments().getInt("StartMinutes1");

          //  Toast.makeText(getContext(), startHour+" "+startMinutes, Toast.LENGTH_SHORT).show();

        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateHours(startHour,startMinutes);
                setHoursOfDay();
            }
        });



        return  view;
    }

    private void calculateHours(int StartHour,int Startminute){

        Calendar calendar=Calendar.getInstance();
        Calendar calenda1=Calendar.getInstance();

        final int hour =calendar.get(Calendar.HOUR_OF_DAY);
        int munites=calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,munites);

        calenda1.set(Calendar.HOUR_OF_DAY,StartHour);
        calenda1.set(Calendar.MINUTE,Startminute);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
        timeHours =simpleDateFormat.format(getCalender(calendar,calenda1).getTime());

        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("mm");
        timeMinutes =simpleDateFormat1.format(getCalender(calendar,calenda1).getTime());



        Toast.makeText(getActivity(), timeHours + "\n"+ getCalender(calendar,calenda1).getTime()+"\n"+timeMinutes, Toast.LENGTH_SHORT).show();

    }

    private Calendar getCalender(Calendar calendar, Calendar calenda1) {
        Calendar result=Calendar.getInstance();

        result.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-calenda1.get(Calendar.HOUR_OF_DAY));
        result.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)-calenda1.get(Calendar.MINUTE));
        return result;
    }


    private void disconnect() {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        String id=firebaseUser.getUid();
        Query query6 = FirebaseDatabase.getInstance().getReference().child("Employee Available").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    private void setHoursOfDay(){
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            name=snapshot.child("name").getValue(String.class);
                            department=snapshot.child("department").getValue(String.class);



                            int totalHours=0;
                            totalHours=+Integer.parseInt(timeHours);
                            int totalMinutes=0;
                            totalMinutes=+Integer.parseInt(timeMinutes);

                            Calendar calendar=Calendar.getInstance();
                            final int month=calendar.get(Calendar.MONTH);
                            final int year=calendar.get(Calendar.YEAR);
                            calendar.set(Calendar.MONTH,month);
                            calendar.set(Calendar.YEAR,year);
                            currentMonth= DateFormat.format("MMM",calendar);
                            currentYear= DateFormat.format("yyyy",calendar);

                            ReportModel  reportModel=new ReportModel(name,department,"emp",String.valueOf(currentMonth),String.valueOf(totalHours),String.valueOf(totalMinutes));
                              DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Reports");
                                   reference.child(String.valueOf(currentMonth+" "+currentYear)).child(id).setValue(reportModel);
                           // reference.child(String.valueOf(currentMonth+" "+currentYear)).child("month").setValue(currentMonth);
                         // getToalTime(currentMonth,currentYear,id);

                            }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getToalTime(final CharSequence month, final CharSequence year, final String getID){
        Query query6 = FirebaseDatabase.getInstance().getReference().child("Reports").child(currentMonth+" "+currentYear).child(getID);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            int totalHours=snapshot.child("totalHours").getValue(Integer.class);
                            int totalMinutes=snapshot.child("totalMintues").getValue(Integer.class);
                          totalHours+=Integer.parseInt(timeHours);
                          totalMinutes+=Integer.parseInt(timeMinutes);
                          timeMinutes+=totalMinutes;
                            ReportModel  reportModel=new ReportModel(name,"se","emp",String.valueOf(month),String.valueOf(totalHours),String.valueOf(totalMinutes));
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Reports");
                            reference.child(String.valueOf(month+" "+year)).child(getID).setValue(reportModel);
                            reference.child(String.valueOf(month+" "+year)).child("month").setValue(month);


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
