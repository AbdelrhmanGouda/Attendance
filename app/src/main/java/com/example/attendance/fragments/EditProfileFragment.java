package com.example.attendance.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.activities.LoginAndRegisterActivity;
import com.example.attendance.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    EditText name,email,password,phone;
    Button image,edit;
    UserData userData;
    Uri imageURI,uri1;
    StorageReference sRefrence;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_edit_profile,container,false);
        name=view.findViewById(R.id.fragment_edit_fullname);
        email=view.findViewById(R.id.fragment_edit_email);
        password=view.findViewById(R.id.fragment_edit_password);
        phone=view.findViewById(R.id.fragment_edit_phone);
        image=view.findViewById(R.id.fragment_btn_edit_picture);
        sRefrence= FirebaseStorage.getInstance().getReference();
        edit=view.findViewById(R.id.fragment_btn_edit_profile);
        auth=FirebaseAuth.getInstance();
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPhoto();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(password.getText().toString())||TextUtils.isEmpty(email.getText().toString())
                            ||TextUtils.isEmpty(phone.getText().toString())){
                        Toast.makeText(getActivity(), "Enter all fields", Toast.LENGTH_SHORT).show();
                    }else {
                        updateProfile(name.getText().toString(),email.getText().toString(),password.getText().toString(),phone.getText().toString(),uri1.toString());

                    }

                }
            });
        return view;

    }

    private void editPhoto() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent.createChooser(intent,"Select Image "),2);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
            imageURI = data.getData();
            Log.d("FileUri", image.toString());
            getData();
        }
    }
    private void getData(){

        if(imageURI!=null){
            final ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            StorageReference storageReference = sRefrence.child("UserImages/"+ UUID.randomUUID().toString());
            storageReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            uri1=uri.getResult();
                            //databaserefrence70
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"upload done",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"error, "+e.getMessage(),Toast.LENGTH_LONG).show();

                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double prog=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Upload "+(int)prog+"%");
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"upload error",Toast.LENGTH_LONG).show();

        }
    }




    private void updateProfile(final String name, final String email, final String password, final String phone, final String image) {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Users").child(id);

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            userData=new UserData();
                            String getType=dataSnapshot.child("type").getValue(String.class);
                            String getDepartment=dataSnapshot.child("department").getValue(String.class);
                            userData=new UserData(name,email,password,image,getType,getDepartment,phone);
                            reference.setValue(userData);

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
