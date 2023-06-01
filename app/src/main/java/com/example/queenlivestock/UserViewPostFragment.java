package com.example.queenlivestock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserViewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserViewPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserViewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserViewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserViewPostFragment newInstance(String param1, String param2) {
        UserViewPostFragment fragment = new UserViewPostFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_view_post, container, false);
        ListView yours_post_list = view.findViewById(R.id.view_post_list);


        SharedPreferences sharedPreferences = context.getSharedPreferences("QueenLiveStockPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("id", 0);
        String s_userId = String.valueOf(userId);

        Database db = new Database(context);
        List<PostClass> yours_posts = db.get_your_posts(s_userId);

        PostAdapter view_post_adapter = new PostAdapter(context, yours_posts);
        yours_post_list.setAdapter(view_post_adapter);


        yours_post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected post
                PostClass selectedPost = yours_posts.get(position);

                // Get the post ID
                String postId = selectedPost.getId();

                // Navigate to the next activity with the post ID
                Intent intent = new Intent(getActivity(), UpdatePostActivity.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });


        return view;
    }
}