package com.example.queenlivestock;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


        ImageView user_p_image = view.findViewById(R.id.user_p_image);
        EditText user_p_address = view.findViewById(R.id.user_p_adderess);
        EditText user_p_phoneno = view.findViewById(R.id.user_p_phoneno);
        EditText user_p_name = view.findViewById(R.id.user_p_name);
        EditText user_p_email = view.findViewById(R.id.user_p_email);
        EditText user_p_password = view.findViewById(R.id.user_p_password);

        SharedPreferences sharedPreferences = context.getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);

        Database db = new Database(context);
        UserClass user = db.get_user(id);

        user_p_address.setText(user.getAddress());
        user_p_email.setText(user.getEmail());
        user_p_password.setText(user.getPassword());
        user_p_name.setText(user.getName());
        user_p_phoneno.setText(user.getPhone_no());

        String image_check = user.getImage();

        if(image_check.matches("")){

        }else{
            user_p_image.setImageURI(Uri.parse(user.getImage()));
            user_p_image.setClipToOutline(true);
            user_p_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }




        TextView change_image = view.findViewById(R.id.change_image);

        String text = getString(R.string.change_image);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);

        change_image.setText(spannableString);



        return view;
    }
}