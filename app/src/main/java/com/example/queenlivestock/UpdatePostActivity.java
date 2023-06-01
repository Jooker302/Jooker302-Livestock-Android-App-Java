package com.example.queenlivestock;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePostActivity extends AppCompatActivity {
    EditText update_post_title;
    EditText update_post_description;
    EditText update_post_price;
    Button update_post_submit;
    ImageView update_post_image;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    String imageUriString;
    CheckBox update_post_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        update_post_title = findViewById(R.id.update_post_title);
        update_post_description = findViewById(R.id.update_post_description);
        update_post_price = findViewById(R.id.update_post_price);
        update_post_submit = findViewById(R.id.update_post_submit);
        update_post_image = findViewById(R.id.update_post_image);
        update_post_active = findViewById(R.id.update_post_active);






        String postId = getIntent().getStringExtra("postId");
        Database db = new Database(this);
        PostClass update_post = db.get_post(Integer.parseInt(postId));


        update_post_title.setText(update_post.getTitle());
        update_post_description.setText(update_post.getDescription());
        update_post_price.setText(update_post.getPrice());
        imageUriString = update_post.getImage();

        if(update_post.getActive().matches("1")){
            update_post_active.setChecked(true);
        }else {
            update_post_active.setChecked(false);
        }

        String image_check = update_post.getImage();

        if(image_check.matches("")){

        }else{
            update_post_image.setImageURI(Uri.parse(update_post.getImage()));
            update_post_image.setClipToOutline(true);
            update_post_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        update_post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
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
                        ImageView imageView = findViewById(R.id.update_post_image);
                        imageView.setImageURI(selectedImageUri);
                        imageView.setClipToOutline(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
        );

        update_post_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update_post_title.getText().toString().matches("")
                        || update_post_description.getText().toString().matches("")
                        || update_post_price.getText().toString().matches("")
                        || imageUriString.matches("")){

                    if(update_post_title.getText().toString().matches("")){
                        update_post_title.setError("Empty Title");
                    }
                    if(update_post_description.getText().toString().matches("")){
                        update_post_description.setError("Empty Description");
                    }
                    if(update_post_price.getText().toString().matches("")){
                        update_post_price.setError("Empty Price");
                    }
                    if(imageUriString.matches("")){
//                        post_title.setError("Empty Title");
                        Toast.makeText(getApplicationContext(),"Upload Image",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    String active_status;
                    if(update_post_active.isChecked()){
                        active_status = "1";
                    }else{
                        active_status = "0";
                    }
                    PostClass up_post = new PostClass(postId,update_post_title.getText().toString().trim(),update_post_description.getText().toString().trim(),update_post_price.getText().toString().trim(),update_post.getUser_id(),active_status,imageUriString);
                    Database change_post = new Database(UpdatePostActivity.this);
                    boolean check = change_post.update_post(up_post);
                    if (check){
                        showNotification("Post Updated");
                    }else{
                        Toast.makeText(getApplicationContext(),"Some Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


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