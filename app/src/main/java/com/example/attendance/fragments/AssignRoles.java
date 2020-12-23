package com.example.attendance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.activities.LoginAndRegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AssignRoles extends Fragment {

    private Spinner empSpinner;
    private Spinner typeSpinner;
    private Button updateRole;
    ArrayList<String> getEmpName;
    ArrayList<String> getType;
    String textEmpName=null;
    String textType=null;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_roles, container, false);
        empSpinner=view.findViewById(R.id.spinner_emp_name);
        typeSpinner=view.findViewById(R.id.spinner_type);
        updateRole=view.findViewById(R.id.update_role);
        getEmpName=new ArrayList<>();
        getType = new ArrayList<>();
        databaseReference =FirebaseDatabase.getInstance().getReference("Users");
        getEmpName();

        empSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textEmpName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getType.add("Admin");
        getType.add("Head");
        getType.add("Employee");
        getTypes();
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        updateRole.setOnClickListener(onClickListener);
        return view;
    }
    private void getEmpName(){
        Query query6 = FirebaseDatabase.getInstance().getReference("Users").orderByChild("name");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getEmpName.clear();
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            String department=snapshot.child("name").getValue(String.class);
                            getEmpName.add(department);
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getEmpName){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view= LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
                                TextView textView=view.findViewById(R.id.text);
                                textView.setText(getEmpName.get(position));
                                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                textView.setTextSize(20);

                                return view;
                            }
                        };
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        empSpinner.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void getTypes(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getType){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
                TextView textView=view.findViewById(R.id.text);
                textView.setText(getType.get(position));
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                textView.setTextSize(20);

                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseDatabase.getInstance().getReference("Users").orderByChild("name").equalTo(textEmpName).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child: dataSnapshot.getChildren()) {
                                child.getRef().child("type").setValue(textType);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

        }
    };

}