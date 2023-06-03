package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        fragmentManager = getSupportFragmentManager();

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.three_lines); // Replace with your menu icon

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.admin_menu_users) {
                    replaceFragment(new AdminUsersFragment());
                } else if (id == R.id.admin_menu_posts) {
                    replaceFragment(new AdminPostsFragment());
                } else if (id == R.id.admin_menu_logout) {
//                    Toast.makeText(AdminHome.this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent logout =new Intent(getApplicationContext(), Login.class);
                    showNotification("Logged Out");
                    startActivity(logout);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Load the initial fragment
        replaceFragment(new AdminUsersFragment());

        // Preselect the "Users" menu item
        MenuItem usersMenuItem = navigationView.getMenu().findItem(R.id.admin_menu_users);
        usersMenuItem.setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                finishAffinity();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
        }

    private void showNotification(String message) {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);

        // Inflate the custom layout
        View view = getLayoutInflater().inflate(R.layout.custom_register_toast, null);
        TextView toastText = view.findViewById(R.id.toast_text);
        toastText.setText(message);

        toast.setView(view);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        // Delay the toast dismissal after 3 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 3000); // Adjust the duration as needed (in milliseconds)
    }
}
