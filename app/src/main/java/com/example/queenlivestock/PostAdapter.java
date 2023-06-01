package com.example.queenlivestock;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.queenlivestock.PostClass;
import com.example.queenlivestock.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends ArrayAdapter<PostClass> {

    private Context context;
    private List<PostClass> posts;

    public PostAdapter(Context context, List<PostClass> posts) {
        super(context, 0, posts);
        this.context = context;
        this.posts = posts;
    }



// ...

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ...

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_post, parent, false);
        }
        PostClass post = posts.get(position);

        LinearLayout post_inner_box = convertView.findViewById(R.id.post_inner_box);
        ImageView imageView = convertView.findViewById(R.id.post_image);
        TextView nameTextView = convertView.findViewById(R.id.post_name);
        TextView priceTextView = convertView.findViewById(R.id.post_price);

        // Set the name and price values from the PostClass object
        nameTextView.setText(post.getTitle());
        priceTextView.setText("Rs " + post.getPrice());

        int colorResource = R.color.de_active;
        Drawable backgroundDrawable = ContextCompat.getDrawable(context, colorResource);


        if(post.getActive().trim().matches("1")){

        }else{
            post_inner_box.setBackground(backgroundDrawable);
        }
        // Load the image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                .centerCrop(); // Scale and crop the image

        Glide.with(context)
                .load(post.getImage())
                .apply(requestOptions)
                .into(imageView);

        return convertView;
    }


}
