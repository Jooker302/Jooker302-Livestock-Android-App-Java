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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;

    ImageView user_p_image;
    EditText user_p_address;
    EditText user_p_phoneno;
    EditText user_p_name;
    EditText user_p_email;
    EditText user_p_password;
    Button user_profile_logout;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    String imageUriString;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        Button profile_update_button = view.findViewById(R.id.profile_update_button);
        user_p_image = view.findViewById(R.id.user_p_image);
        user_p_address = view.findViewById(R.id.user_p_adderess);
        user_p_phoneno = view.findViewById(R.id.user_p_phoneno);
        user_p_name = view.findViewById(R.id.user_p_name);
        user_p_email = view.findViewById(R.id.user_p_email);
        user_p_password = view.findViewById(R.id.user_p_password);
        user_profile_logout = view.findViewById(R.id.user_profile_logout);


        SharedPreferences sharedPreferences = context.getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        String role = sharedPreferences.getString("role","");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("home_page", Integer.parseInt("5"));
        editor.apply();


        Database db = new Database(context);
        UserClass user = db.get_user(id);

        user_p_address.setText(user.getAddress());
        user_p_email.setText(user.getEmail());
        user_p_password.setText(user.getPassword());
        user_p_name.setText(user.getName());
        user_p_phoneno.setText(user.getPhone_no());
        imageUriString = user.getImage();

        String image_check = user.getImage();

        if(image_check.matches("")){

        }else{
            user_p_image.setImageURI(Uri.parse(user.getImage()));
            user_p_image.setClipToOutline(true);
            user_p_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        // Handle the selected image data here
                        Uri selectedImageUri = data.getData();
                        imageUriString = selectedImageUri.toString();
//                        Toast.makeText(getApplicationContext(),imageUriString,Toast.LENGTH_LONG).show();
                        ImageView imageView = view.findViewById(R.id.user_p_image);
                        imageView.setImageURI(selectedImageUri);
                        imageView.setClipToOutline(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
        );




        TextView change_image = view.findViewById(R.id.change_image);

        String text = getString(R.string.change_image);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);

        change_image.setText(spannableString);

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });


        profile_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_p_email.getText().toString().matches("")
                || user_p_password.getText().toString().matches("")){
                    if (user_p_email.getText().toString().matches("")){
                        user_p_email.setError("Empty Email");
                    }
                    if(user_p_password.getText().toString().matches("")){
                        user_p_password.setError("Empty Password");
                    }
                }else{
                    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
                    Pattern pattern = Pattern.compile(emailPattern);

                    Matcher email_verify = pattern.matcher(user_p_email.getText().toString());
                    boolean email_check = email_verify.matches();
                    boolean password_check = user_p_password.getText().toString().length() >= 6;


                    if (email_check && password_check) {
                        UserClass update_user = new UserClass(String.valueOf(id),user_p_name.getText().toString().trim(),user_p_email.getText().toString().trim(),user_p_phoneno.getText().toString().trim(),user_p_address.getText().toString().trim(),imageUriString,role,user_p_password.getText().toString().trim());
                        Database update_db = new Database(context);
                        boolean check = update_db.update_user(update_user);
                        if (check){
                            showNotification("Profile Updated");
//                            refreshFragment();
                            Intent i = new Intent(context,UserHome.class);
                            startActivity(i);
                        }
                    }else{
                        if (!email_check){
                            user_p_email.setError("Enter Valid Email");
                        }
                        if(!password_check){
                            user_p_password.setError("Password should be greater than 6");
                        }
                    }
                }

            }
        });


        user_profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout =new Intent(context, Login.class);
                showNotification("Logged Out");
                startActivity(logout);
            }
        });

        return view;
    }


    private void refreshFragment() {
        // Reload the current fragment
        getParentFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
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