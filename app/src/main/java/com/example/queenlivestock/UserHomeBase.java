package com.example.queenlivestock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHomeBase extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomnavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_base);

        bottomnavigation = findViewById(R.id.bottom_navigation);

        bottomnavigation.setOnItemSelectedListener(this);
        bottomnavigation.setSelectedItemId(R.id.navigation_home);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_home) {
            // Handle home action
            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.navigation_search) {
            // Handle search action
            Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.navigation_profile) {
            // Handle profile action
            Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}