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
import com.example.attendance.data.AvailableEmployeeData;
import com.example.attendance.data.EmployeesListData;

import java.util.ArrayList;

public class EmployeesListAdapter extends RecyclerView.Adapter<EmployeesListAdapter.EmployeesListAdapterViewHolder> {
    ArrayList<EmployeesListData> employeesListData;
    Context context;

    public EmployeesListAdapter(ArrayList<EmployeesListData> employeesListData, Context context) {
        this.employeesListData = employeesListData;
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
        holder.employeeName.setText(employeesListData.get(position).getEmployeeName());
        holder.deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(view.getContext());
                deleteBuilder.setMessage("Do you want to Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                employeesListData.remove(position);
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
        return employeesListData.size();
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
}
