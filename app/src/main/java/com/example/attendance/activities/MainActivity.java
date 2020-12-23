package com.example.attendance.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.fragments.CheckinFragment;
import com.example.attendance.fragments.CheckoutFragment;
import com.example.attendance.fragments.EditProfileFragment;
import com.example.attendance.fragments.RequestLeaveFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Boolean currentState =false;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String name,hay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        auth=FirebaseAuth.getInstance();

        firebaseUser=auth.getCurrentUser();
        name=getIntent().getStringExtra("name");
        hay=getIntent().getStringExtra("hoursWork");

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);


       if(savedInstanceState==null){
           if(name!=null){
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CheckoutFragment()).commit();
               Toast.makeText(this, "tam", Toast.LENGTH_SHORT).show();

           }else{
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CheckinFragment()).commit();
               Toast.makeText(this, "mfe4", Toast.LENGTH_SHORT).show();

           }
           navigationView.setCheckedItem(R.id.checkin);

       }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.checkin :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CheckinFragment()).commit();
                break;
            case R.id.checkout :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CheckoutFragment()).commit();
                break;
            case R.id.edit_profile :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EditProfileFragment()).commit();

                break;
            case R.id.logout :
                currentState=true;
                disconnect();
                auth.signOut();
            Intent intent=new Intent(MainActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.leave_request :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RequestLeaveFragment()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        if(!currentState){
            disconnect();
        }
    }

 */

    private void disconnect() {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        String id=firebaseUser.getUid();
        Query query6 = FirebaseDatabase.getInstance().getReference().child("Employee Available").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();

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