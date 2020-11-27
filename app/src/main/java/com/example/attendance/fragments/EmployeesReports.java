package com.example.attendance.fragments;

import android.os.Bundle;

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
import com.example.attendance.data.EmployeesReportsData;
import com.example.attendance.data.LeaveRequestsData;

import java.util.ArrayList;


public class EmployeesReports extends Fragment {
    RecyclerView reportRecyclerView;
    ArrayList<EmployeesReportsData> employeesReportsData;
    EmployeesReportsAdapter employeesReportsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_employees_reports, container, false);
        reportRecyclerView =view.findViewById(R.id.emp_reports_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        reportRecyclerView.setLayoutManager(linerLayoutManager);
        employeesReportsData= new ArrayList<EmployeesReportsData>();
        employeesReportsData.add(new EmployeesReportsData("Abdelrhman Gouda","SE","August","200"));
        employeesReportsData.add(new EmployeesReportsData("Khaled Nabil","IS","August","100"));
        employeesReportsData.add(new EmployeesReportsData("Mohamed Nasr","SE","August","10"));
        employeesReportsAdapter= new EmployeesReportsAdapter(employeesReportsData,getContext());
        reportRecyclerView.setAdapter(employeesReportsAdapter);
       return view;
    }
}