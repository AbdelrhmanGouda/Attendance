package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.adapters.AvailableAdapter;
import com.example.attendance.adapters.EmployeesListAdapter;
import com.example.attendance.data.AvailableEmployeeData;
import com.example.attendance.data.EmployeesListData;

import java.util.ArrayList;


public class EmployeesList extends Fragment {
    RecyclerView empListRecyclerView;
    ArrayList<EmployeesListData> employeesListData;
    EmployeesListAdapter employeesListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employees_list, container, false);
        empListRecyclerView =view.findViewById(R.id.emp_list_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        empListRecyclerView.setLayoutManager(linerLayoutManager);
        employeesListData =new ArrayList<EmployeesListData>();
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListData.add(new EmployeesListData("Mohamed Nasr"));
        employeesListData.add(new EmployeesListData("Abdelrhman Gouda"));
        employeesListData.add(new EmployeesListData("Khaled Nabil"));
        employeesListAdapter =new EmployeesListAdapter(employeesListData,getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),linerLayoutManager.getOrientation());
        empListRecyclerView.addItemDecoration(dividerItemDecoration);
        empListRecyclerView.setAdapter(employeesListAdapter);
        return view;
    }
}