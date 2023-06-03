package com.example.queenlivestock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPostsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminPostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPostsFragment newInstance(String param1, String param2) {
        AdminPostsFragment fragment = new AdminPostsFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_posts, container, false);
        ListView allPostsList = view.findViewById(R.id.admin_posts_list);
        Database db = new Database(context);

        List<PostClass> posts = db.get_all_posts_admin();

        PostAdapter admin_post_adapter = new PostAdapter(context, posts);
        allPostsList.setAdapter(admin_post_adapter);

        allPostsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected post
                PostClass selectedPost = posts.get(position);

                // Get the post ID
                String postId = selectedPost.getId();

                showDeleteConfirmationDialog(postId);
                // Perform the long press action here (e.g., show a dialog, delete the post, etc.)
                // ...

                return true; // Return true to indicate that the long press event is consumed
            }
        });

        return view;
    }


    private void showDeleteConfirmationDialog(String postId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the delete action here
                        deletePost(postId);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the deletion or perform any other desired action
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deletePost(String postId) {
        // Perform the delete action here
        // ...
//        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
        Database db = new Database(context);
        boolean check = db.delete_post(postId);
        if(check){
            showNotification("Deleted Successfully");
            Intent i = new Intent(context,UserHome.class);
            startActivity(i);
        }else{
            showErrorNotification("Error Occurred");
        }
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

    private void showErrorNotification(String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        // Inflate the custom layout
        View view = getLayoutInflater().inflate(R.layout.custom_error_toast, null);
        TextView toastText = view.findViewById(R.id.toast_error_text);
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