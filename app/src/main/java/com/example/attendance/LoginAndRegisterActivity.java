package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class LoginAndRegisterActivity extends AppCompatActivity {
EditText email,password,phone,name;
Spinner department;
Button login,signup;
TextView textRegister,text,textLogin;
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
        textRegister=findViewById(R.id.text_signup);
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

    }

    private void makeRegister() {
       text.setVisibility(View.GONE);
        textRegister.setVisibility(View.GONE);
        login.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        department.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);
        textLogin.setVisibility(View.VISIBLE);



    }
}