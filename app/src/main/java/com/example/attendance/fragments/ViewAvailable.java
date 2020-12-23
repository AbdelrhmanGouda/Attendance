package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.attendance.R;
import com.example.attendance.adapters.AvailableAdapter;
import com.example.attendance.data.AvailableEmployeeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAvailable extends Fragment {

    private RecyclerView availableRecyclerView;
    private List<AvailableEmployeeData> availableEmployeeDataList;
    private  AvailableAdapter availableAdapter;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view__available, container, false);

        availableRecyclerView =view.findViewById(R.id.view_available_recycler);
        availableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        availableRecyclerView.setHasFixedSize(true);
        availableEmployeeDataList =new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());
        availableRecyclerView.addItemDecoration(dividerItemDecoration);
        availableAdapter =new AvailableAdapter(availableEmployeeDataList,getContext());
        availableRecyclerView.setAdapter(availableAdapter);

        databaseReference =FirebaseDatabase.getInstance().getReference("Employee Available");

        Query query = FirebaseDatabase.getInstance().getReference("Employee Available").orderByChild("Name");
        query.addListenerForSingleValueEvent(valueEventListener);

        return view;

    }
    ValueEventListener valueEventListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        availableEmployeeDataList.clear();
            if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AvailableEmployeeData availableEmployeeData =snapshot.getValue(AvailableEmployeeData.class);
                        availableEmployeeDataList.add(availableEmployeeData);
                    }
                    availableAdapter.notifyDataSetChanged();
                }
            }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
