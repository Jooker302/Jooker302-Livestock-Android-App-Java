package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registernowlink = findViewById(R.id.registernowlink);

        String text = getString(R.string.registernow);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);

        registernowlink.setText(spannableString);

        registernowlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //register page
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });


    }

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
}