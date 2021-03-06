package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.adapters.SignUpRequestsAdapter;
import com.example.attendance.data.AvailableEmployeeData;
import com.example.attendance.data.SignUpRequestsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUpRequests extends Fragment {
    private RecyclerView requestRecyclerView;
    private List<SignUpRequestsData> signUpRequestsDataList;
    private SignUpRequestsAdapter signUpRequestsAdapter;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_sign_up_requests, container, false);
        requestRecyclerView =view.findViewById(R.id.signup_requests_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        requestRecyclerView.setLayoutManager(linerLayoutManager);
        signUpRequestsDataList = new ArrayList<>();

        signUpRequestsAdapter = new SignUpRequestsAdapter(signUpRequestsDataList,getContext());
        requestRecyclerView.setAdapter(signUpRequestsAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Register Request");

        Query query = FirebaseDatabase.getInstance().getReference("Register Request").orderByChild("email");
        query.addListenerForSingleValueEvent(valueEventListener);

        return view;

    }
    ValueEventListener valueEventListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            signUpRequestsDataList.clear();
            if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SignUpRequestsData signUpRequestsData =snapshot.getValue(SignUpRequestsData.class);
                    signUpRequestsDataList.add(signUpRequestsData);
                }
                signUpRequestsAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}