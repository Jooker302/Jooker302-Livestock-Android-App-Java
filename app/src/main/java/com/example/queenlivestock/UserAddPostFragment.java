package com.example.queenlivestock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAddPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    String imageUriString;
    EditText post_title;
    EditText post_description;
    EditText post_price;
    ImageView post_image;
    Button post_submit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserAddPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAddPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAddPostFragment newInstance(String param1, String param2) {
        UserAddPostFragment fragment = new UserAddPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_add_post, container, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        String role = sharedPreferences.getString("role","");

        imageUriString = "";
        post_image = view.findViewById(R.id.add_post_image);
        post_title = view.findViewById(R.id.post_title);
        post_description = view.findViewById(R.id.post_description);
        post_price = view.findViewById(R.id.post_price);
        post_submit = view.findViewById(R.id.post_submit);




        post_image.setOnClickListener(new View.OnClickListener() {
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
                        ImageView imageView = view.findViewById(R.id.add_post_image);
                        imageView.setImageURI(selectedImageUri);
                        imageView.setClipToOutline(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
        );



        post_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"Test",Toast.LENGTH_SHORT).show();
                if(post_title.getText().toString().matches("")
                || post_description.getText().toString().matches("")
                || post_price.getText().toString().matches("")
                || imageUriString.matches("")){

                    if(post_title.getText().toString().matches("")){
                        post_title.setError("Empty Title");
                    }
                    if(post_description.getText().toString().matches("")){
                        post_description.setError("Empty Description");
                    }
                    if(post_price.getText().toString().matches("")){
                        post_price.setError("Empty Price");
                    }
                    if(imageUriString.matches("")){
//                        post_title.setError("Empty Title");
                        Toast.makeText(context,"Upload Image",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    PostClass add_post = new PostClass("",post_title.getText().toString().trim(),post_description.getText().toString().trim(),post_price.getText().toString().trim(),String.valueOf(id),"",imageUriString);
                    Database new_post = new Database(context);
                    boolean check = new_post.add_post(add_post);
                    if (check){
                        showNotification("Post Added");
                    }else{
                        Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        return view;
    }

    private void showNotification(String message) {
        Toast toast = new Toast(context);
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