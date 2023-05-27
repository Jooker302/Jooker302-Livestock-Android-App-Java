package com.example.queenlivestock;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    String imageUriString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideBottomNavigationBar();
        setContentView(R.layout.activity_register);

        TextView loginlink = findViewById(R.id.loginlink);
        ImageView register_user_image = findViewById(R.id.register_user_image);
        Button register_button = findViewById(R.id.register_button);
        EditText register_email = findViewById(R.id.register_email);
        EditText register_name = findViewById(R.id.register_name);
        EditText register_address = findViewById(R.id.register_address);
        EditText register_password = findViewById(R.id.register_password);
        RadioGroup register_user_role = findViewById(R.id.register_user_role);



        String text = getString(R.string.login_now);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);

        loginlink.setText(spannableString);

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        // Handle the selected image data here
                        Uri selectedImageUri = data.getData();
                        imageUriString = selectedImageUri.toString();
//                        Toast.makeText(getApplicationContext(),imageUriString,Toast.LENGTH_LONG).show();
                        ImageView imageView = findViewById(R.id.register_user_image);
                        imageView.setImageURI(selectedImageUri);
                        imageView.setClipToOutline(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
        );
        register_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = register_user_role.getCheckedRadioButtonId();
//                Toast.makeText(getApplicationContext(),register_email.getText().toString().trim().length(),Toast.LENGTH_SHORT).show();
                if (selectedId == -1 || register_email.getText().toString().matches("")
                        || register_address.getText().toString().matches("")
                        || register_name.getText().toString().matches("")
                        || register_password.getText().toString().matches("")){

//                    Toast.makeText(getApplicationContext(),"Empty Feilds",Toast.LENGTH_SHORT).show();
                    if (register_email.getText().toString().matches("")){
                        register_email.setError("Invalid Email");
                    }
                    if (register_name.getText().toString().matches("")){
                        register_name.setError("Invalid Name");
                    }
                    if (register_address.getText().toString().matches("")){
                        register_address.setError("Invalid Address");
                    }
                    if (register_password.getText().toString().matches("")){
                        register_password.setError("Invalid Password");
                    }

                } else {
                    RadioButton radioButton = findViewById(selectedId);
                    String selected_role = radioButton.getText().toString().toLowerCase();
//                    Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
                    UserClass new_user = new UserClass("",register_name.getText().toString().trim(),register_email.getText().toString().trim(),"",register_address.getText().toString().trim(),imageUriString,selected_role,register_password.getText().toString().trim());
//                    Toast.makeText(getApplicationContext(),new_user.toString(),Toast.LENGTH_LONG).show();
                    Database register = new Database(Register.this);
//                    register.register(new_user);

                }
            }
        });
    }

    private void hideBottomNavigationBar() {
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
    }
}