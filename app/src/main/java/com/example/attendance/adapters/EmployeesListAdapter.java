package com.example.attendance.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.data.EmployeesListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeesListAdapter extends RecyclerView.Adapter<EmployeesListAdapter.EmployeesListAdapterViewHolder> {
    List<EmployeesListData> employeesListDataList;
    Context context;
    DatabaseReference databaseReference;


    public EmployeesListAdapter(List<EmployeesListData> employeesListDataList, Context context) {
        this.employeesListDataList = employeesListDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeesListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_list_row, parent, false);
        return new EmployeesListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesListAdapterViewHolder holder, final int position) {
        holder.employeeName.setText(employeesListDataList.get(position).getName());
        holder.deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(view.getContext());
                deleteBuilder.setMessage("Do you want to Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference("Users").orderByChild("name").equalTo(employeesListDataList.get(position).getName()).addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot child: dataSnapshot.getChildren()) {
                                                    child.getRef().setValue(null);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });

                                employeesListDataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, getItemCount());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeesListDataList.size();
    }

    public class EmployeesListAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView employeeName;
        Button deleteEmployee;

        public EmployeesListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.emp_list_name);
            deleteEmployee = itemView.findViewById(R.id.emp_list_del_btn);

        }
    }
    private void delete(){
    }
}
