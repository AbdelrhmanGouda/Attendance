package com.example.attendance.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;

public class CheckinFragment extends Fragment {
    Button checkin;
    String name,id;
    FirebaseAuth auth;
    UserData userData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_checkin,container,false);
        checkin=view.findViewById(R.id.btn_checkin);
        auth=FirebaseAuth.getInstance();
        if(getArguments()!=null){
            name= this.getArguments().getString("name");
           id= getArguments().getString("id");
            Toast.makeText(getActivity(), name+" "+id, Toast.LENGTH_SHORT).show();

        }


        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keepAvailable();
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;

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
                            String data=dataSnapshot.child("name").getValue(String.class);
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
