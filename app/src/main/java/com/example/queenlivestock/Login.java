package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    Button signbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText login_email = findViewById(R.id.login_email);
        EditText login_password = findViewById(R.id.login_password);
        signbutton = findViewById(R.id.signinbutton);
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

        signbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(login_email.getText().toString().matches("") || login_password.getText().toString().matches("")){
                    if(login_email.getText().toString().matches("")){
                        login_email.setError("Empty Email");
                    }
                    if(login_password.getText().toString().matches("")){
                        login_password.setError("Empty Password");
                    }

                }else{

                    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
                    Pattern pattern = Pattern.compile(emailPattern);

                    Matcher email_verify = pattern.matcher(login_email.getText().toString());
                    boolean email_check = email_verify.matches();
                    boolean password_check = login_password.getText().toString().length() >= 6;

                    if(email_check && password_check){
                        Database login = new Database(Login.this);
                        int check_login = login.login(login_email.getText().toString(),login_password.getText().toString());
                        if (check_login != -1){
//                                Toast.makeText(getApplicationContext(),String.valueOf(check_login),Toast.LENGTH_LONG).show();
                            UserClass get_user = login.get_user(check_login);

                            SharedPreferences sharedPreferences = getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("id", Integer.parseInt(get_user.getId()));
                            editor.putString("role",get_user.getRole());
                            editor.putBoolean("isLoggedIn", true);
                            editor.putInt("home_page", Integer.parseInt("1"));
                            editor.apply();
                            if(get_user.getRole().matches("admin")){
                                Intent home = new Intent(getApplicationContext(), AdminHome.class);
                                startActivity(home);
                            }else {
                                Intent home = new Intent(getApplicationContext(), UserHome.class);
                                startActivity(home);
                            }

                        }else{
                            login_email.setError("Invalid Email");
                            login_password.setError("Invalid Password");
                        }
                    }else{
                        if(email_check){
                            login_email.setError("Enter a Email");
                        }
                        if(password_check){
                            login_password.setError("Password should be greater than 6");
                        }
                    }

                }

//                Intent home = new Intent(getApplicationContext(),UserHome.class);
//                startActivity(home);
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