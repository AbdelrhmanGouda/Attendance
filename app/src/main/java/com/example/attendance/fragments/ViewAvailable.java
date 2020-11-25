package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.attendance.R;
import com.example.attendance.adapters.AvailableAdapter;
import com.example.attendance.data.AvailableEmployeeData;

import java.util.ArrayList;

public class ViewAvailable extends Fragment {
    RecyclerView availableRecyclerView;
    ArrayList<AvailableEmployeeData> availableEmployeeDataArrayList;
    AvailableAdapter availableAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view__available, container, false);
        availableRecyclerView =view.findViewById(R.id.view_available_recycler);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        availableRecyclerView.setLayoutManager(linerLayoutManager);

        availableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        availableEmployeeDataArrayList =new ArrayList<AvailableEmployeeData>();
        availableEmployeeDataArrayList.add(new AvailableEmployeeData("Mohamed Nasr"));
        availableEmployeeDataArrayList.add(new AvailableEmployeeData("Abdelrhman Gouda"));
        availableEmployeeDataArrayList.add(new AvailableEmployeeData("Khaled Nabil"));
        availableAdapter =new AvailableAdapter(availableEmployeeDataArrayList,getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),linerLayoutManager.getOrientation());
        availableRecyclerView.addItemDecoration(dividerItemDecoration);

        availableRecyclerView.setAdapter(availableAdapter);
        availableRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;

    }
}