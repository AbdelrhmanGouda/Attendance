package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.adapters.EmployeesListAdapter;
import com.example.attendance.adapters.EmployeesReportsAdapter;
import com.example.attendance.adapters.LeaveRequestsAdapter;
import com.example.attendance.data.AvailableEmployeeData;
import com.example.attendance.data.EmployeesReportsData;
import com.example.attendance.data.LeaveRequestsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmployeesReports extends Fragment {
    RecyclerView reportRecyclerView;
    List<EmployeesReportsData> employeesReportsDataList;
    EmployeesReportsAdapter employeesReportsAdapter;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_employees_reports, container, false);
        reportRecyclerView =view.findViewById(R.id.emp_reports_recycler);
        reportRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        employeesReportsDataList = new ArrayList<>();
        employeesReportsAdapter= new EmployeesReportsAdapter(employeesReportsDataList,getContext());
        reportRecyclerView.setAdapter(employeesReportsAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Reports").child("Dec 2020");

        Query query = FirebaseDatabase.getInstance().getReference("Reports").child("Dec 2020").orderByChild("name");
        query.addListenerForSingleValueEvent(valueEventListener);

        return view;

    }
    ValueEventListener valueEventListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            employeesReportsDataList.clear();
            if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EmployeesReportsData employeesReportsData =snapshot.getValue(EmployeesReportsData.class);
                    employeesReportsDataList.add(employeesReportsData);
                }
                employeesReportsAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}