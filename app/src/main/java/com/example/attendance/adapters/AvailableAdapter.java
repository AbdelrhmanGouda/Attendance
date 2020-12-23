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

import java.util.List;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.ViewHolder> {
    private List<AvailableEmployeeData> availableEmployeeDataList;
    Context context;
    public AvailableAdapter(List<AvailableEmployeeData> availableEmployeeDataList ,Context context){
    this.availableEmployeeDataList =availableEmployeeDataList;
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
        holder.availableEmployeeName.setText("Name :  "+availableEmployeeDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return availableEmployeeDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView availableEmployeeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            availableEmployeeName=itemView.findViewById(R.id.available_employee_name);
        }
    }
}
