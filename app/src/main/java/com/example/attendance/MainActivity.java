package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.attendance.activities.LoginAndRegisterActivity;
import com.example.attendance.fragments.CheckinFragment;
import com.example.attendance.fragments.CheckoutFragment;
import com.example.attendance.fragments.EditProfileFragment;
import com.example.attendance.fragments.RequestLeaveFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

       if(savedInstanceState==null){
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CheckinFragment()).commit();
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
                Intent intent=new Intent(MainActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.leave_request :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RequestLeaveFragment()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}