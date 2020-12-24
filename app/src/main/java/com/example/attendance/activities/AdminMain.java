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
import android.view.View;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.fragments.AssignRoles;
import com.example.attendance.fragments.CheckinFragment;
import com.example.attendance.fragments.CheckoutFragment;
import com.example.attendance.fragments.EditProfileFragment;
import com.example.attendance.fragments.EmployeesList;
import com.example.attendance.fragments.EmployeesReports;
import com.example.attendance.fragments.LeaveRequests;
import com.example.attendance.fragments.ManageDepartment;
import com.example.attendance.fragments.RequestLeaveFragment;
import com.example.attendance.fragments.SignUpRequests;
import com.example.attendance.fragments.ViewAvailable;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    String name,hay,userName,userDepartment,userImage;
    CircleImageView imgHead;
    View view;
    TextView textName,textDepartment;

    FirebaseAuth auth;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        auth= FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);


            getHeader();
        drawerLayout =findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView=findViewById(R.id.admin_navigation);
        view =navigationView.getHeaderView(0);
        imgHead=view.findViewById(R.id.header_image);
        textName=view.findViewById(R.id.header_name);
        textDepartment=view.findViewById(R.id.header_department);

        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new ViewAvailable()).commit();
            navigationView.setCheckedItem(R.id.view_available);
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
            case R.id.view_available :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new ViewAvailable()).commit();
                break;
            case R.id.assign_roles :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new AssignRoles()).commit();
                break;
            case R.id.signup_requests :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new SignUpRequests()).commit();
                break;
            case R.id.admin_leave_requests :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new LeaveRequests()).commit();
                break;
            case R.id.employees_list :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new EmployeesList()).commit();
                break;
            case R.id.employees_reports :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new EmployeesReports()).commit();
                break;
            case R.id.manage_department :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new ManageDepartment()).commit();
                break;
            case R.id.admin_logout :
                Intent intent=new Intent(AdminMain.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                auth.signOut();
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getHeader() {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        final String id=firebaseUser.getUid();

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            userName=dataSnapshot.child("name").getValue(String.class);
                            userDepartment=dataSnapshot.child("department").getValue(String.class);
                            userImage=dataSnapshot.child("image").getValue(String.class);
                            textName.setText(userName);
                            textDepartment.setText(userDepartment);
                            Picasso.get().load(userImage).into(imgHead);

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