package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.adapters.LeaveRequestsAdapter;
import com.example.attendance.adapters.SignUpRequestsAdapter;
import com.example.attendance.data.LeaveRequestsData;
import com.example.attendance.data.SignUpRequestsData;

import java.util.ArrayList;


public class LeaveRequests extends Fragment {
    RecyclerView leaveRecyclerView;
    ArrayList<LeaveRequestsData> leaveRequestsData;
    LeaveRequestsAdapter leaveRequestsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_leave_requests, container, false);
        leaveRecyclerView =view.findViewById(R.id.leave_requests_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        leaveRecyclerView.setLayoutManager(linerLayoutManager);
        leaveRequestsData= new ArrayList<LeaveRequestsData>();
        leaveRequestsData.add(new LeaveRequestsData("Abdelrhman Gouda","SE","8:01","Because Ana 3L2"));
        leaveRequestsData.add(new LeaveRequestsData("Khaled Nabil","IS","1:30","adddddddddddddddddddddddddddddddddddddddddddddddddddddd"));
        leaveRequestsData.add(new LeaveRequestsData("Mohamed Nasr","SE","10:20","sdkfjhfdsghdxnzbdksguhasdfgdsjkxvvdzxvndjbdsbvbdajkkdjalsbvjlkdsdvjsklasbdk;bdva;bjkdkdsvldbjkvvdsbajbkdsbjvdsa;bavcx nvjdhvcnk"));
        leaveRequestsAdapter= new LeaveRequestsAdapter(leaveRequestsData,getContext());
        leaveRecyclerView.setAdapter(leaveRequestsAdapter);
        return view;
    }
}