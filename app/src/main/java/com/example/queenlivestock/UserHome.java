package com.example.queenlivestock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    BottomNavigationView bottomnavigation;
    UserHomeFragment userhomeFragment;
    UserProfileFragment userprofileFragment;
    UserSearchFragment usersearchFragment;
    UserAddPostFragment useraddpostFragment;
    UserViewPostFragment userviewpostFragment;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bottomnavigation = findViewById(R.id.bottom_navigation);

        bottomnavigation.setOnItemSelectedListener(this);


            SharedPreferences sharedPreferences = getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
            int last_page = sharedPreferences.getInt("home_page", 0);
            switch (last_page){
                case 1:
                    bottomnavigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 2:
                    bottomnavigation.setSelectedItemId(R.id.navigation_search);
                    break;
                case 3:
                    bottomnavigation.setSelectedItemId(R.id.navigation_add_post);
                    break;
                case 4:
                    bottomnavigation.setSelectedItemId(R.id.navigation_view_post);
                    break;
                case 5:
                    bottomnavigation.setSelectedItemId(R.id.navigation_profile);
                    break;

        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//        if (item.getItemId() == R.id.navigation_home) {
//
////            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
//
//            userhomeFragment = new UserHomeFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.fragment_container, userhomeFragment, "MyFragment");
//            transaction.commit();
//            return true;
//        } else if (item.getItemId() == R.id.navigation_search) {
//            usersearchFragment = new UserSearchFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.fragment_container, usersearchFragment, "MyFragment");
//            transaction.commit();
////            Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();
//            return true;
//        } else if (item.getItemId() == R.id.navigation_profile) {
//            userprofileFragment = new UserProfileFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.fragment_container, userprofileFragment, "MyFragment");
//            transaction.commit();
////            Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
//            return true;
//        }
//        return false;
        if (userhomeFragment != null) {
            transaction.hide(userhomeFragment);
        }
        if (usersearchFragment != null) {
            transaction.hide(usersearchFragment);
        }
        if (userprofileFragment != null) {
            transaction.hide(userprofileFragment);
        }
        if (useraddpostFragment != null) {
            transaction.hide(useraddpostFragment);
        }
        if (userviewpostFragment != null) {
            transaction.hide(userviewpostFragment);
        }

        if (item.getItemId() == R.id.navigation_home) {
            if (userhomeFragment == null) {
                userhomeFragment = new UserHomeFragment();
                transaction.add(R.id.fragment_container, userhomeFragment, "MyFragment");
            } else {
                transaction.show(userhomeFragment);
            }

            // Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.navigation_search) {
            if (usersearchFragment == null) {
                usersearchFragment = new UserSearchFragment();
                transaction.add(R.id.fragment_container, usersearchFragment, "MyFragment");
            } else {
                transaction.show(usersearchFragment);
            }

            // Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.navigation_profile) {
            if (userprofileFragment == null) {
                userprofileFragment = new UserProfileFragment();
                transaction.add(R.id.fragment_container, userprofileFragment, "MyFragment");
            } else {
                transaction.show(userprofileFragment);
            }

        } else if (item.getItemId() == R.id.navigation_add_post) {
            if (useraddpostFragment == null) {
                useraddpostFragment = new UserAddPostFragment();
                transaction.add(R.id.fragment_container, useraddpostFragment, "MyFragment");
            } else {
                transaction.show(useraddpostFragment);
            }

        } else if (item.getItemId() == R.id.navigation_view_post) {
            if (userviewpostFragment == null) {
                userviewpostFragment = new UserViewPostFragment();
                transaction.add(R.id.fragment_container, userviewpostFragment, "MyFragment");
            } else {
                transaction.show(userviewpostFragment);
            }

        }

        transaction.commit();
        return true;
    }
}