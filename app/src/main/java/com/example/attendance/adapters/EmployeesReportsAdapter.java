package com.example.attendance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.data.EmployeesReportsData;

import java.util.List;

public class EmployeesReportsAdapter extends RecyclerView.Adapter<EmployeesReportsAdapter.EmployeesReportsViewHolder>  {
    List<EmployeesReportsData> employeesReportsDataList;
    Context context;
    public EmployeesReportsAdapter( List<EmployeesReportsData> employeesReportsDataList,Context context) {
        this.context=context;
        this.employeesReportsDataList=employeesReportsDataList;
    }
    @NonNull
    @Override
    public EmployeesReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_reports_row,parent,false);
        return new EmployeesReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesReportsViewHolder holder, final int position) {
        holder.reportEmpName.setText(employeesReportsDataList.get(position).getName());
        holder.reportEmpDept.setText(employeesReportsDataList.get(position).getDepartment());
        holder.reportEmpMonth.setText(employeesReportsDataList.get(position).getMonth());
        holder.reportEmpAbsence.setText(employeesReportsDataList.get(position).getTotalHours() + " : "+employeesReportsDataList.get(position).getTotalMintues());
        final boolean isExpended = employeesReportsDataList.get(position).isExpended();
        holder.expandableLayout.setVisibility(isExpended ? View.VISIBLE : View.GONE);
        holder.expandableLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeesReportsDataList.get(position).setExpended(!isExpended);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeesReportsDataList.size();
    }

    public class EmployeesReportsViewHolder extends RecyclerView.ViewHolder {
        TextView reportEmpName,reportEmpDept,reportEmpMonth,reportEmpAbsence;
        LinearLayout expandableLayout,expandableLayoutClick;
        public EmployeesReportsViewHolder(@NonNull View itemView) {
            super(itemView);
            reportEmpName=itemView.findViewById(R.id.reports_emp_name);
            reportEmpDept=itemView.findViewById(R.id.reports_emp_dept);
            reportEmpMonth=itemView.findViewById(R.id.reports_month);
            reportEmpAbsence=itemView.findViewById(R.id.reports_hour_absence);
            expandableLayout=itemView.findViewById(R.id.reports_expendable);
            expandableLayoutClick=itemView.findViewById(R.id.reports_expendable_click);
        }
    }
}
