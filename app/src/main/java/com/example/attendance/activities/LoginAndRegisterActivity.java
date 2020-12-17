package com.example.attendance.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
                Intent intent=new Intent(LoginAndRegisterActivity.this, AdminMain.class);
                startActivity(intent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName=name.getText().toString();
                String textEmail=email.getText().toString();
                String textPassword=password.getText().toString();
                String textPhone=phone.getText().toString();
                String image=null;
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
                userData=new UserData(textName,textEmail,textPassword,image,textType,textDepartment,textPhone);

                if(admin.isChecked()){
                    textType=admin.getText().toString();
                }else if (employee.isChecked()){
                    textType=employee.getText().toString();
                }
                register();
            }
        });
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
    private void register() {

        Intent intent=new Intent(LoginAndRegisterActivity.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(employee.isChecked()){
                department.setVisibility(View.VISIBLE);
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        }else if(admin.isChecked()){
                department.setVisibility(View.GONE);
                Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();

            };

    }
}