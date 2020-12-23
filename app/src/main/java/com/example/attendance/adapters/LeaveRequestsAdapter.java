package com.example.attendance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.data.LeaveRequestsData;


import java.util.ArrayList;
import java.util.List;

public class LeaveRequestsAdapter extends RecyclerView.Adapter<LeaveRequestsAdapter.LeaveRequestsViewHolder> {
    List<LeaveRequestsData> leaveRequestsDataList;
    Context context;
    public LeaveRequestsAdapter( List<LeaveRequestsData> leaveRequestsDataList,Context context) {
        this.context=context;
        this.leaveRequestsDataList=leaveRequestsDataList;
    }
    @NonNull
    @Override
    public LeaveRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_requests_row,parent,false);
        return new LeaveRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestsViewHolder holder, final int position) {
        holder.empName.setText(leaveRequestsDataList.get(position).getLeaveEmpName());
        holder.empDept.setText(leaveRequestsDataList.get(position).getLeaveEmpDept());
        holder.leaveTime.setText(leaveRequestsDataList.get(position).getLeaveEmpTime());
        holder.empReason.setText(leaveRequestsDataList.get(position).getLeaveEmpReason());
        final boolean isExpanded = leaveRequestsDataList.get(position).isExpended();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.expandableLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveRequestsDataList.get(position).setExpended(!isExpanded);
                notifyDataSetChanged();

            }
        });

        holder.leaveAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "accepted", Toast.LENGTH_SHORT).show();
            }
        });
        holder.leaveDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "declined", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return leaveRequestsDataList.size();
    }

    public class LeaveRequestsViewHolder extends RecyclerView.ViewHolder {
        TextView empName,empDept,leaveTime,empReason;
        Button leaveAccept,leaveDecline;
        LinearLayout expandableLayout,expandableLayoutClick;
        public LeaveRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            empName=itemView.findViewById(R.id.leave_emp_name);
            empDept=itemView.findViewById(R.id.leave_emp_dept);
            leaveTime=itemView.findViewById(R.id.leave_emp_time);
            empReason=itemView.findViewById(R.id.leave_emp_reason);
            leaveAccept=itemView.findViewById(R.id.leave_accept_btn);
            leaveDecline=itemView.findViewById(R.id.leave_decline_btn);
            expandableLayout=itemView.findViewById(R.id.leave_expendable);
            expandableLayoutClick=itemView.findViewById(R.id.leave_expendable_click);
        }
    }
}
