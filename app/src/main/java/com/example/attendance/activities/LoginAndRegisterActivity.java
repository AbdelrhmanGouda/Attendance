package com.example.attendance.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompatExtras;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.fragments.CheckinFragment;
import com.example.attendance.fragments.SignUpRequests;
import com.example.attendance.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class LoginAndRegisterActivity extends AppCompatActivity {
EditText email,password,phone,name;
Spinner department;
Button login,signup;
RadioButton admin,employee;
RadioGroup radioGroup;
TextView textRegister,text,textLogin;
    FirebaseAuth auth;
    String textType=null;
    String textDepartment=null;
    FirebaseDatabase database;
    DatabaseReference reference;
    UserData userData;
    StorageReference sRefrence;
    FirebaseUser firebaseUser;
    Button btnImage;
    ArrayList<String> getDepartments;
    Uri fileUri,uri1;
    String id,uId;
    String data;
    String CHANNAL_ID = "channal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        email=findViewById(R.id.edit_email);
        password=findViewById(R.id.edit_password);
        phone=findViewById(R.id.edit_phone);
        name=findViewById(R.id.edit_fullname);
        department=findViewById(R.id.spinner_depart);
        login=findViewById(R.id.btn_login);
        signup=findViewById(R.id.btn_signup);
        text=findViewById(R.id.text1);
        textLogin=findViewById(R.id.text_login);
        admin=findViewById(R.id.radio_admin);
        radioGroup=findViewById(R.id.radio_group);
        employee=findViewById(R.id.radio_employee);
        textRegister=findViewById(R.id.text_signup);
        auth=FirebaseAuth.getInstance();
        sRefrence= FirebaseStorage.getInstance().getReference();
        btnImage=findViewById(R.id.btn_image);
        getDepartments=new ArrayList<>();

        firebaseUser=auth.getCurrentUser();
        if(firebaseUser!=null){
            id=firebaseUser.getUid();
            keepLogin(id);
        }


        getDepartments();

                department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        textDepartment=parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRegister();
            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLogin();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=email.getText().toString();
                String textPassword=password.getText().toString();
                if(TextUtils.isEmpty(textPassword)||TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginAndRegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    login(textEmail, textPassword);
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  textName=name.getText().toString();
             String   textEmail=email.getText().toString();
                String textPassword=password.getText().toString();
                String  textPhone=phone.getText().toString();
                String image=null;
                if(admin.isChecked()){
                    textType=admin.getText().toString();

                }else if (employee.isChecked()){
                    textType=employee.getText().toString();
                }


                if(TextUtils.isEmpty(textName)||TextUtils.isEmpty(textPassword)||TextUtils.isEmpty(textEmail)
                        ||TextUtils.isEmpty(textPhone)){
                    Toast.makeText(LoginAndRegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else if(textPassword.length()<6){
                    Toast.makeText(LoginAndRegisterActivity.this, "Password must be equal or more than six ", Toast.LENGTH_SHORT).show();
                }else if(uri1==null){
                    Toast.makeText(LoginAndRegisterActivity.this, "Please, choose your profile image ", Toast.LENGTH_SHORT).show();

                }
                else {

                  signupNotification(textName);
                   registerRequest(textName,textPassword,textEmail,textPhone,textType,textType,textDepartment,image);
                }
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent.createChooser(intent,"Select Image "),2);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
            fileUri = data.getData();
            Log.d("FileUri", fileUri.toString());
                getData();
        }
    }
    private void getDepartments(){
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
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(LoginAndRegisterActivity.this,android.R.layout.simple_spinner_item,getDepartments){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view= LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
                                TextView textView=view.findViewById(R.id.text);
                                textView.setText(getDepartments.get(position));
                                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                textView.setTextSize(20);

                                return view;
                            }
                        };
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        department.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getData(){

        if(fileUri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(LoginAndRegisterActivity.this);
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            StorageReference storageReference = sRefrence.child("UserImages/"+ UUID.randomUUID().toString());
            storageReference.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            uri1=uri.getResult();
                        //databaserefrence70
                            progressDialog.dismiss();
                            Toast.makeText(LoginAndRegisterActivity.this,"upload done",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginAndRegisterActivity.this,"error, "+e.getMessage(),Toast.LENGTH_LONG).show();

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
            Toast.makeText(LoginAndRegisterActivity.this,"upload error",Toast.LENGTH_LONG).show();

        }
    }

    private void keepLogin( String id) {
        FirebaseAuth  firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        final String n=user.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            userData=new UserData();
                            //  userData=snapshot.getValue(UserData.class);
                            data=dataSnapshot.child("type").getValue(String.class);

                        }
                        Log.i("Employee",data);
                        Log.i("Employee1",n);

                        if(data.equals("Employee")){

                            Intent intent=new Intent(LoginAndRegisterActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else {

                            Intent intent=new Intent(LoginAndRegisterActivity.this,AdminMain.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void signupNotification(String textName) {


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            String name = "mychannal";
            String description = "this is decsreption";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;


            NotificationChannel channel =new NotificationChannel(CHANNAL_ID ,name,importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        String message = textName+" want to join our system ";
                    NotificationCompat.Builder builder=new NotificationCompat.Builder(
                            LoginAndRegisterActivity.this,CHANNAL_ID
                    )
                     .setSmallIcon(R.drawable.notification_background)
                      .setContentTitle("Register notification")
                        .setContentText(message)
                          .setAutoCancel(true);
                    Intent intent =new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("name","mohamed");
                    PendingIntent pendingIntent=PendingIntent.getActivity(LoginAndRegisterActivity.this,
                            0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0,builder.build());
        Toast.makeText(LoginAndRegisterActivity.this, "Notification sent", Toast.LENGTH_SHORT).show();

                    //if(type==(admin || head) to see this noti only)


    }


    private void makeLogin() {
        text.setVisibility(View.VISIBLE);
        textRegister.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        email.setText("");
        password.setText("");

        name.setVisibility(View.GONE);
        name.setText("");
        phone.setVisibility(View.GONE);
        phone.setText("");

        department.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        btnImage.setVisibility(View.GONE);
        textLogin.setVisibility(View.GONE);
        admin.setVisibility(View.GONE);
        employee.setVisibility(View.GONE);

    }

    private void makeRegister() {
       text.setVisibility(View.GONE);
        textRegister.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        email.setText("");
        password.setText("");

        name.setVisibility(View.VISIBLE);
        name.setText("");
        phone.setVisibility(View.VISIBLE);
        name.setText("");
        department.setVisibility(View.VISIBLE);
        btnImage.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);
        textLogin.setVisibility(View.VISIBLE);

    }
    private void login(final String email, String password) {
        FirebaseUser firebaseUser=auth.getCurrentUser();

        if(firebaseUser!=null){
            uId=firebaseUser.getUid();
        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseAuth  firebaseAuth=FirebaseAuth.getInstance();
                    FirebaseUser user=auth.getCurrentUser();
                    final String n=user.getUid();
                    Query query6 = FirebaseDatabase.getInstance().getReference("Users").child(n);
                    query6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot!=null){
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                        userData=new UserData();
                                        //  userData=snapshot.getValue(UserData.class);
                                        data=dataSnapshot.child("type").getValue(String.class);

                                    }
                                    Log.i("Employee",data);
                                    Log.i("Employee1",n);

                                    if(data.equals("Employee")){

                                        Intent intent=new Intent(LoginAndRegisterActivity.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else if(data.equals("Head")){

                                        Intent intent=new Intent(LoginAndRegisterActivity.this,AdminMain.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("type","Head");
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Intent intent=new Intent(LoginAndRegisterActivity.this,AdminMain.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }else {
                    Toast.makeText(LoginAndRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void register(final String textName, final String textPassword, final String textEmail, final String textPhone, final String textType,
                          final String type, final String textDepartment, final String image) {
        auth.createUserWithEmailAndPassword(textEmail,textPassword).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userData=new UserData(textName,textEmail,textPassword,uri1.toString(),type,textDepartment,textPhone);
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    String id=firebaseUser.getUid();
                    database=FirebaseDatabase.getInstance();
                    reference=database.getReference("Users").child(id);
                    reference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent=new Intent(LoginAndRegisterActivity.this, AdminMain.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else {
                    Toast.makeText(LoginAndRegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void registerRequest(final String textName, final String textPassword, final String textEmail, final String textPhone, final String textType,
                          final String type, final String textDepartment, final String image) {
        userData=new UserData(textName,textEmail,textPassword, uri1.toString(),"Employee",textDepartment,textPhone);
      //  FirebaseUser firebaseUser=auth.getCurrentUser();
        //String id=firebaseUser.getUid();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Register Request").push();
        reference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               /* Intent intent=new Intent(LoginAndRegisterActivity.this, AdminMain.class);
                startActivity(intent);
                finish();*/
                name.setText("");
                password.setText("");
                email.setText("");
                phone.setText("");

            }
        });
    }

}