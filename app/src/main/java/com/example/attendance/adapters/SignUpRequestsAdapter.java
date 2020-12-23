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
import com.example.attendance.model.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SignUpRequestsAdapter extends RecyclerView.Adapter<SignUpRequestsAdapter.SignUpRequestsViewHolder> {
    List<SignUpRequestsData> signUpRequestsDataList;
    Context context;
    DatabaseReference databaseReference;
    UserData userData;

    public SignUpRequestsAdapter(List<SignUpRequestsData> signUpRequestsDataList,Context context) {
        this.context=context;
        this.signUpRequestsDataList=signUpRequestsDataList;
    }

    @NonNull
    @Override
    public SignUpRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sigin_up_requests_row,parent,false);
        return new SignUpRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignUpRequestsViewHolder holder, final int position) {
        holder.empName.setText(signUpRequestsDataList.get(position).getName());
        holder.empEmail.setText(signUpRequestsDataList.get(position).getEmail());
        holder.empDept.setText(signUpRequestsDataList.get(position).getDepartment());
        final boolean isExpended = signUpRequestsDataList.get(position).isExpended();
        holder.expandableLayout.setVisibility(isExpended ? View.VISIBLE : View.GONE);

        holder.expandableLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpRequestsDataList.get(position).setExpended(!isExpended);
                notifyDataSetChanged();

            }
        });

        holder.requestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference =FirebaseDatabase.getInstance().getReference("Register Request");
                Query query = FirebaseDatabase.getInstance().getReference("Register Request").orderByChild("name").equalTo(signUpRequestsDataList.get(position).getName());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 userData= snapshot.getValue(UserData.class);

                                databaseReference=FirebaseDatabase.getInstance().getReference();
                                UserData userData2=new UserData(userData.getName(),userData.getEmail(),userData.getPassword(),userData.getImage(),userData.getType(),userData.getDepartment(),userData.getPhone());
                                databaseReference.child("Users").push().setValue(userData2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                });
                                FirebaseDatabase.getInstance().getReference("Register Request").orderByChild("email").equalTo(signUpRequestsDataList.get(position).getEmail()).addListenerForSingleValueEvent(
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
                                signUpRequestsDataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, getItemCount());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(context, "accepted", Toast.LENGTH_SHORT).show();

            }
        });
        holder.requestDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "declined", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("Register Request").orderByChild("email").equalTo(signUpRequestsDataList.get(position).getEmail()).addListenerForSingleValueEvent(
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
                signUpRequestsDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, getItemCount());
            }
        });


    }

    @Override
    public int getItemCount() {
        return signUpRequestsDataList.size();
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
