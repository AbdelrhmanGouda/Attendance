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
import com.example.attendance.data.SignUpRequestsData;

import java.util.ArrayList;

public class SignUpRequestsAdapter extends RecyclerView.Adapter<SignUpRequestsAdapter.SignUpRequestsViewHolder> {
    ArrayList<SignUpRequestsData> signUpRequestsData;
    Context context;
    public SignUpRequestsAdapter(ArrayList<SignUpRequestsData> signUpRequestsData,Context context) {
        this.context=context;
        this.signUpRequestsData=signUpRequestsData;
    }

    @NonNull
    @Override
    public SignUpRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sigin_up_requests_row,parent,false);
        return new SignUpRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignUpRequestsViewHolder holder, final int position) {
        holder.empName.setText(signUpRequestsData.get(position).getEmpName());
        holder.empEmail.setText(signUpRequestsData.get(position).getEmpEmail());
        holder.empDept.setText(signUpRequestsData.get(position).getEmpDept());
        final boolean isExpanded = signUpRequestsData.get(position).isExpended();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.expandableLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpRequestsData.get(position).setExpended(!isExpanded);
                notifyDataSetChanged();

            }
        });

        holder.requestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "accepted", Toast.LENGTH_SHORT).show();
            }
        });
        holder.requestDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "declined", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return signUpRequestsData.size();
    }

    public class SignUpRequestsViewHolder extends RecyclerView.ViewHolder {
        TextView empName,empEmail,empDept;
        Button requestAccept,requestDecline;
        LinearLayout expandableLayout,expandableLayoutClick;
        public SignUpRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            empName=itemView.findViewById(R.id.request_emp_name);
            empEmail=itemView.findViewById(R.id.request_emp_email);
            empDept=itemView.findViewById(R.id.request_emp_dept);
            requestAccept=itemView.findViewById(R.id.request_accept_btn);
            requestDecline=itemView.findViewById(R.id.request_decline_btn);
            expandableLayout=itemView.findViewById(R.id.request_expendable);
            expandableLayoutClick=itemView.findViewById(R.id.request_expendable_click);
        }
    }
}
