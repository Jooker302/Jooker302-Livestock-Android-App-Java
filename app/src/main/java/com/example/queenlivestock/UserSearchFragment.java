package com.example.queenlivestock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    List<PostClass> search_posts;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSearchFragment newInstance(String param1, String param2) {
        UserSearchFragment fragment = new UserSearchFragment();
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("home_page", Integer.parseInt("2"));
        editor.apply();

        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        EditText search_post = view.findViewById(R.id.search_post);
        Button search_post_button = view.findViewById(R.id.search_post_button);
        ListView view_post_search_list = view.findViewById(R.id.view_post_search_list);


        search_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_post.getText().toString().trim().matches("")){
                    search_post.setError("Empty Search");
                }else{
                    Database db = new Database(context);
                    search_posts = db.search_posts(search_post.getText().toString().trim());

                    PostAdapter search_post_adapter = new PostAdapter(context,search_posts);
                    view_post_search_list.setAdapter(search_post_adapter);

                }


            }
        });

        view_post_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected post
                PostClass selectedPost = search_posts.get(position);

                // Get the post ID
                String postId = selectedPost.getId();

                // Navigate to the next activity with the post ID
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });

        return view;
    }
}