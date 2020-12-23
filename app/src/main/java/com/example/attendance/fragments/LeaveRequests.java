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
import java.util.List;


public class LeaveRequests extends Fragment {
    RecyclerView leaveRecyclerView;
    List<LeaveRequestsData> leaveRequestsDataList;
    LeaveRequestsAdapter leaveRequestsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_leave_requests, container, false);
        leaveRecyclerView =view.findViewById(R.id.leave_requests_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        leaveRecyclerView.setLayoutManager(linerLayoutManager);
        leaveRequestsDataList= new ArrayList<>();
        leaveRequestsAdapter= new LeaveRequestsAdapter(leaveRequestsDataList,getContext());
        leaveRecyclerView.setAdapter(leaveRequestsAdapter);
        return view;
    }
}