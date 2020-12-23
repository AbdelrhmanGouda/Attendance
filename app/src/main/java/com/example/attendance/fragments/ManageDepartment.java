package com.example.attendance.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.activities.LoginAndRegisterActivity;
import com.example.attendance.activities.MainActivity;
import com.example.attendance.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageDepartment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageDepartment extends Fragment {
    EditText department;
    ImageView save;
    ListView listView;
    ArrayList<String> getDepartments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manage_department, container, false);
        department=view.findViewById(R.id.edit_info_subject);
        save=view.findViewById(R.id.img_pdf_info);
        listView=view.findViewById(R.id.list_info_prof);
        getDepartments=new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDepartment();
            }
        });
        getDepartments();

        return view;
    }

    private void setDepartment() {
        if(department.getText().toString().length()>0){
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Departments");
            reference.child(department.getText().toString()).child("department").setValue(department.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            department.setText("");
                            getDepartments();
                        }
                    });

        }else {
            Toast.makeText(getActivity(), "Please enter department!", Toast.LENGTH_SHORT).show();
        }


    }

    private void getDepartments() {
        Query query6 = FirebaseDatabase.getInstance().getReference().child("Departments");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getDepartments.clear();
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            String department=snapshot.child("department").getValue(String.class);
                                getDepartments.add(department);
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getDepartments){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view=LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
                                TextView textView=view.findViewById(R.id.text);
                                textView.setText(getDepartments.get(position));
                                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                textView.setTextSize(20);

                                return view;
                            }
                        };

                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageDepartment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageDepartment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageDepartment newInstance(String param1, String param2) {
        ManageDepartment fragment = new ManageDepartment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


}