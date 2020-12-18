package com.example.attendance.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompatExtras;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    department.setVisibility(View.VISIBLE);

                }else {
                    department.setVisibility(View.GONE);

                }
            }
        });

               /* department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        textDepartment=parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                */

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
                login(textEmail,textPassword);

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
                        ||TextUtils.isEmpty(textPhone)||TextUtils.isEmpty(textType)){
                    Toast.makeText(LoginAndRegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                  /*  String message = textName+" want to join our system ";
                    NotificationCompat.Builder builder=new NotificationCompat.Builder(
                            LoginAndRegisterActivity.this
                    )
                     .setSmallIcon(R.drawable.notification_background)
                      .setContentTitle("Register notification")
                        .setContentText(message)
                          .setAutoCancel(true);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SignUpRequests()).commit();
                    PendingIntent pendingIntent=PendingIntent.getActivity(LoginAndRegisterActivity.this,
                            0, ,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                   */
                    register(textName,textPassword,textEmail,textPhone,textType,textType,textDepartment,image);
                }
            }
        });

    }



    private void makeLogin() {
        text.setVisibility(View.VISIBLE);
        textRegister.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);

        name.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        department.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        textLogin.setVisibility(View.GONE);
        admin.setVisibility(View.GONE);
        employee.setVisibility(View.GONE);

    }

    private void makeRegister() {
       text.setVisibility(View.GONE);
        textRegister.setVisibility(View.GONE);
        login.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);


        signup.setVisibility(View.VISIBLE);
        textLogin.setVisibility(View.VISIBLE);
        admin.setVisibility(View.VISIBLE);
        employee.setVisibility(View.VISIBLE);

    }
    private void login(final String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(LoginAndRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
                    userData=new UserData(textName,textEmail,textPassword,image,type,textDepartment,textPhone);
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


}