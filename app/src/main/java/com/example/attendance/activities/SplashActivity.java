package com.example.attendance.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.attendance.R;
import com.example.attendance.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
String data;
FirebaseAuth auth;
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(firebaseUser!=null){
                    String id=firebaseUser.getUid();
                    keepLogin(id);
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this,LoginAndRegisterActivity.class);
                    startActivity(mainIntent);
                    finish();

                }

                /* Create an Intent that will start the Menu-Activity. */
            }
        }, 1000);

    }
    private void keepLogin( String id) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            final String n=user.getUid();
        }

        Query query6 = FirebaseDatabase.getInstance().getReference("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //  userData=snapshot.getValue(UserData.class);
                            data=dataSnapshot.child("type").getValue(String.class);

                        }
                        Log.i("Employee",data);
                       // Log.i("Employee1",n);

                        if(data.equals("Employee")){

                            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else {

                            Intent intent=new Intent(SplashActivity.this,AdminMain.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("type","Head");

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


}