package com.example.queenlivestock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PostDetailActivity extends AppCompatActivity {
    TextView detail_post_title;
    TextView detail_post_description;
    TextView detail_post_price;
    ImageView detail_view_image;
    Button post_buy;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i = new Intent(this,UserHome.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        String postId = getIntent().getStringExtra("postId");
        Database db = new Database(this);
        PostClass get_post = db.get_post(Integer.parseInt(postId));

        detail_post_price = findViewById(R.id.detail_post_price);
        detail_post_description = findViewById(R.id.detail_post_description);
        detail_post_title = findViewById(R.id.detail_post_title);
        detail_view_image = findViewById(R.id.detail_post_image);
        post_buy = findViewById(R.id.post_buy);

        detail_post_title.setText(get_post.getTitle());
        detail_post_description.setText(get_post.getDescription());
        detail_post_price.setText("RS " + get_post.getPrice());

        String image_check = get_post.getImage();

        if(image_check.matches("")){

        }else{
            detail_view_image.setImageURI(Uri.parse(get_post.getImage()));
            detail_view_image.setClipToOutline(true);
            detail_view_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        post_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification("Purchased");
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