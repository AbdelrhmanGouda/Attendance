package com.example.attendance.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.attendance.R;
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
import java.util.HashMap;

public class CheckinFragment extends Fragment {
    Button checkin;
    String name,id,data;
    FirebaseAuth auth;
    UserData userData;
    Chronometer chronometer;
    boolean running;
    long pauseOffSet;
    TextView welcome;
    CharSequence currenthour,currentminutes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_checkin,container,false);
        checkin=view.findViewById(R.id.btn_checkin);
        welcome=view.findViewById(R.id.checkint_text);
        chronometer=view.findViewById(R.id.chronometer_start);

        auth=FirebaseAuth.getInstance();
        if(getArguments()!=null){
            name= this.getArguments().getString("name");
           id= getArguments().getString("id");
            Toast.makeText(getActivity(), name+" "+id, Toast.LENGTH_SHORT).show();

        }


        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJob();
                keepAvailable();
              //  Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;

    }

    private void startJob() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffSet);
         //   chronometer.setFormat("HH:MM:SS");
            chronometer.start();
            running=true;
        }
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            userData=new UserData();
                            data=snapshot.child("name").getValue(String.class);
                            Bundle bundle=new Bundle();
                            CheckoutFragment checkoutFragment=new CheckoutFragment();
                            Calendar calendar=Calendar.getInstance();
                            final int hour =calendar.get(Calendar.HOUR_OF_DAY);
                            int munites=calendar.get(Calendar.MINUTE);
                            bundle.putInt("startHour1",hour);
                            bundle.putInt("StartMinutes1",munites);
                            currenthour= DateFormat.format("HH",calendar);
                            currentminutes=DateFormat.format("mm",calendar);
                            bundle.putCharSequence("startHour",currenthour);
                            bundle.putCharSequence("StartMinutes",currentminutes);

                          //  Toast.makeText(getContext(), currenthour+" "+ currentminutes, Toast.LENGTH_SHORT).show();
                            checkoutFragment.setArguments(bundle);
                             FragmentManager manager=getFragmentManager();
                            manager.beginTransaction().replace(R.id.fragment_container,checkoutFragment).commit();

                            welcome.setText("hello " +data+", \n"+" you start working at "+currenthour+" : "+currentminutes);


                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void endJob() {
        if (running) {
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void pauseJob(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet=0;
    }
    private void keepAvailable() {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            userData=new UserData();
                            data=snapshot.child("name").getValue(String.class);
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Employee Available");
                            reference.child(id).child("Name").setValue(data);

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
