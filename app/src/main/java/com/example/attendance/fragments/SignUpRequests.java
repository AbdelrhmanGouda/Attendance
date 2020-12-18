package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.adapters.SignUpRequestsAdapter;
import com.example.attendance.data.SignUpRequestsData;

import java.util.ArrayList;

public class SignUpRequests extends Fragment {
    RecyclerView requestRecyclerView;
    ArrayList<SignUpRequestsData>signUpRequestsData;
    SignUpRequestsAdapter signUpRequestsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_sign_up_requests, container, false);
        requestRecyclerView =view.findViewById(R.id.signup_requests_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        requestRecyclerView.setLayoutManager(linerLayoutManager);
        signUpRequestsData = new ArrayList<SignUpRequestsData>();
        signUpRequestsData.add(new SignUpRequestsData("Abdelrhman Gouda","Gouda@gmail.com","SE"));
        signUpRequestsData.add(new SignUpRequestsData("Mohamed Nasr","Nasr@gmail.com","CS"));
        signUpRequestsData.add(new SignUpRequestsData("Khaled Nabil","Nabil@gmail.com","IT"));
        signUpRequestsAdapter = new SignUpRequestsAdapter(signUpRequestsData,getContext());
        requestRecyclerView.setAdapter(signUpRequestsAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}