package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmployeesList extends Fragment {
    private RecyclerView empListRecyclerView;
    private List<EmployeesListData> employeesListDataList;
    private EmployeesListAdapter employeesListAdapter;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employees_list, container, false);
        empListRecyclerView =view.findViewById(R.id.emp_list_recycler);
        empListRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        empListRecyclerView.setHasFixedSize(true);
        employeesListDataList =new ArrayList<>();
        employeesListAdapter =new EmployeesListAdapter(employeesListDataList,getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation());
        empListRecyclerView.addItemDecoration(dividerItemDecoration);
        empListRecyclerView.setAdapter(employeesListAdapter);


        databaseReference =FirebaseDatabase.getInstance().getReference("Users");

        Query query1 = FirebaseDatabase.getInstance().getReference("Users").orderByChild("type").equalTo("Employee");
        query1.addListenerForSingleValueEvent(valueEventListener);

        return view;

}
    ValueEventListener valueEventListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            employeesListDataList.clear();
            if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EmployeesListData employeesListData =snapshot.getValue(EmployeesListData.class);
                    employeesListDataList.add(employeesListData);

                }
                employeesListAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}