package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

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
}