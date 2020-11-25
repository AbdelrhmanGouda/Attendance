package com.example.attendance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.data.AvailableEmployeeData;

import java.util.ArrayList;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.ViewHolder> {
    ArrayList<AvailableEmployeeData> availableEmployeeDataArrayList;
    Context context;
    public AvailableAdapter(ArrayList<AvailableEmployeeData> availableEmployeeData ,Context context){
    this.availableEmployeeDataArrayList =availableEmployeeData;
    this.context =context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.veiw_available_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.availableEmployeeName.setText(availableEmployeeDataArrayList.get(position).getAvailableEmployeeName());
    }

    @Override
    public int getItemCount() {
        return availableEmployeeDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView availableEmployeeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            availableEmployeeName=itemView.findViewById(R.id.available_employee_name);
        }
    }
}
