package com.example.queenlivestock;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserClass> {
    private Context context;
    private List<UserClass> users;

    public UserAdapter(Context context, List<UserClass> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }



// ...

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ...

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_user, parent, false);
        }
        UserClass user = users.get(position);

//        LinearLayout post_inner_box = convertView.findViewById(R.id.post_inner_box);
        ImageView imageView = convertView.findViewById(R.id.admin_user_image);
        TextView nameTextView = convertView.findViewById(R.id.admin_user_name);
        TextView priceTextView = convertView.findViewById(R.id.admin_user_email);

        // Set the name and price values from the PostClass object
        nameTextView.setText(user.getName());
        priceTextView.setText(user.getEmail());

//        int colorResource = R.color.de_active;
//        Drawable backgroundDrawable = ContextCompat.getDrawable(context, colorResource);


//        if(post.getActive().trim().matches("1")){
//
//        }else{
//            post_inner_box.setBackground(backgroundDrawable);
//        }
        // Load the image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                .centerCrop(); // Scale and crop the image

        Glide.with(context)
                .load(user.getImage())
                .apply(requestOptions)
                .into(imageView);

        return convertView;
    }
}
