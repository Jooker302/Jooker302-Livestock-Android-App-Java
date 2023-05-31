package com.example.queenlivestock;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        }

        PostClass post = posts.get(position);

        ImageView imageView = convertView.findViewById(R.id.post_image);
        TextView nameTextView = convertView.findViewById(R.id.post_name);
        TextView priceTextView = convertView.findViewById(R.id.post_price);

        // Set the name and price values from the PostClass object
        nameTextView.setText(post.getTitle());
        priceTextView.setText(post.getPrice());

        // Set the image using its local file URI
        Uri imageUri = Uri.parse(post.getImage());
        imageView.setImageURI(imageUri);

        return convertView;
    }

}
