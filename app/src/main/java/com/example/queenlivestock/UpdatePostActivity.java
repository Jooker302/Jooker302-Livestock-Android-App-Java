package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UpdatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        String postId = getIntent().getStringExtra("postId");

    }
}