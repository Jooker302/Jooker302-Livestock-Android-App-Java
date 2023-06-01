package com.example.queenlivestock;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String USERS = "users";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE_NO = "phone_no";
    public static final String ADDRESS = "address";
    public static final String IMAGE1 = "image";
    public static final String IMAGE = IMAGE1;
    public static final String ROLE = "role";
    public static final String POSTS = "posts";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String USER_ID = "user_id";
    public static final String ACTIVE = "active";

    public Database(@Nullable Context context) {
        super(context, "QueenLiveStock.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CreateUsersTable = "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                EMAIL + " TEXT, " +
                ADDRESS + " TEXT, " +
                PHONE_NO + " TEXT, " +
                ROLE + " TEXT, " +
                IMAGE + " TEXT, " +
                PASSWORD + " TEXT)";

        String CreatePostTable = "CREATE TABLE " + POSTS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                PRICE + " TEXT, " +
                IMAGE + " TEXT, " +
                USER_ID + " TEXT, " +
                ACTIVE + " TEXT DEFAULT '1')";

        sqLiteDatabase.execSQL(CreateUsersTable);
        sqLiteDatabase.execSQL(CreatePostTable);


//        ContentValues cv = new ContentValues();
//
//        cv.put(PASSWORD,"123456789");
//        cv.put(EMAIL,"admin@gmail.com");
//        cv.put(ROLE,"admin");
//        SQLiteDatabase db = this.getWritableDatabase();
//        long check = db.insert(USERS, null, cv);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean register(UserClass new_user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME,new_user.getName());
        cv.put(EMAIL,new_user.getEmail());
        cv.put(ADDRESS,new_user.getAddress());
        cv.put(IMAGE,new_user.getImage());
        cv.put(PHONE_NO,new_user.getPhone_no());
        cv.put(PASSWORD,new_user.getPassword());
        cv.put(ROLE,new_user.getRole());



        String query = "SELECT * FROM " + USERS + " WHERE " + EMAIL + " = '" + new_user.getEmail() + "'";
        Cursor check_user = db.rawQuery(query,null);

        if(check_user.moveToFirst()){
            return false;
        }else {

            long check = db.insert(USERS, null, cv);
            if (check == -1) {
                return false;
            } else {
//                db.lastInsertRowId();
//                long insertedId = check;
//                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

//                String get_query = "SELECT * FROM " + USERS + " WHERE " + EMAIL + " = '" + new_user.getEmail() + "' AND " + PASSWORD + " = '" + new_user.getPassword() + "'";
//                Cursor result = db.rawQuery(get_query,null);
//                int id_index = result.getColumnIndex("id");
//                int user_id = result.getInt(id_index);
//                int role_index = result.getColumnIndex("role");
//                String user_role = result.getString(role_index);
//
//                return user_id;

                return true;
            }
        }
//        return 99;
    }


    public int login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS + " WHERE " + EMAIL + " = '" + email + "' AND " + PASSWORD + " = '" + password + "'";

        Cursor result = db.rawQuery(query, null);

        int id = -1; // Default value indicating no user found

        if (result.moveToFirst()) {
            // Get the user ID from the result cursor
            int index = result.getColumnIndex(ID);
            id = result.getInt(index);
        }

        result.close();
        db.close();

        return id;
    }

    public UserClass get_user(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS + " WHERE " + ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        UserClass user = null;

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int emailIndex = cursor.getColumnIndex(EMAIL);
            int addressIndex = cursor.getColumnIndex(ADDRESS);
            int imageIndex = cursor.getColumnIndex(IMAGE);
            int phoneNoIndex = cursor.getColumnIndex(PHONE_NO);
            int passwordIndex = cursor.getColumnIndex(PASSWORD);
            int roleIndex = cursor.getColumnIndex(ROLE);

            // Check if all column indexes are valid
            if (nameIndex >= 0 && emailIndex >= 0 && addressIndex >= 0 && imageIndex >= 0 &&
                    phoneNoIndex >= 0 && passwordIndex >= 0 && roleIndex >= 0) {

                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                String address = cursor.getString(addressIndex);
                String image = cursor.getString(imageIndex);
                String phoneNo = cursor.getString(phoneNoIndex);
                String password = cursor.getString(passwordIndex);
                String role = cursor.getString(roleIndex);


                user = new UserClass(String.valueOf(id), name, email, phoneNo, address, image, role, password);
            }
        }

        cursor.close();

        return user;
    }

    public boolean update_user(UserClass updated_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, updated_user.getName());
        cv.put(ADDRESS, updated_user.getAddress());
        cv.put(IMAGE, updated_user.getImage());
        cv.put(PHONE_NO, updated_user.getPhone_no());
        cv.put(PASSWORD, updated_user.getPassword());
        cv.put(ROLE, updated_user.getRole());

        String whereClause = ID + " = ?";
        String[] whereArgs = { String.valueOf(updated_user.getId()) };

        int rowsAffected = db.update(USERS, cv, whereClause, whereArgs);
        db.close();

        return true;
    }

    public boolean add_post(PostClass new_post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TITLE,new_post.getTitle());
        cv.put(DESCRIPTION,new_post.getDescription());
        cv.put(PRICE,new_post.getPrice());
        cv.put(ACTIVE,new_post.getActive());
        cv.put(IMAGE,new_post.getImage());
        cv.put(USER_ID,new_post.getUser_id());


        long check = db.insert(POSTS, null, cv);

        if (check == -1) {
            return false;
        } else {
            return true;
        }


    }


    public List<PostClass> get_all_posts() {
        List<PostClass> posts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID, TITLE, DESCRIPTION, PRICE, IMAGE, USER_ID, ACTIVE};
        String selection = ACTIVE + " = ?";
        String[] selectionArgs = {"1"};
//        Cursor cursor = db.query(POSTS, columns, selection, selectionArgs, null, null, null);
        Cursor cursor = db.query(POSTS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id_index = cursor.getColumnIndex(ID);
                String id = cursor.getString(id_index);
                int title_index = cursor.getColumnIndex(TITLE);
                String title = cursor.getString(title_index);
                int description_index = cursor.getColumnIndex(DESCRIPTION);
                String description = cursor.getString(description_index);
                int price_index = cursor.getColumnIndex(PRICE);
                String price = cursor.getString(price_index);
                int image_index = cursor.getColumnIndex(IMAGE);
                String image = cursor.getString(image_index);
                int user_id_index = cursor.getColumnIndex(USER_ID);
                String userId = cursor.getString(user_id_index);
                int active_index = cursor.getColumnIndex(ACTIVE);
                String active = cursor.getString(active_index);

                PostClass post = new PostClass(id, title, description, price, userId, active, image);
                posts.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return posts;
    }


    public List<PostClass> get_your_posts(String your_id) {
        List<PostClass> posts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID, TITLE, DESCRIPTION, PRICE, IMAGE, USER_ID, ACTIVE};
        String selection = USER_ID + " = ?";
        String[] selectionArgs = {your_id};
//        Cursor cursor = db.query(POSTS, columns, selection, selectionArgs, null, null, null);
        Cursor cursor = db.query(POSTS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id_index = cursor.getColumnIndex(ID);
                String id = cursor.getString(id_index);
                int title_index = cursor.getColumnIndex(TITLE);
                String title = cursor.getString(title_index);
                int description_index = cursor.getColumnIndex(DESCRIPTION);
                String description = cursor.getString(description_index);
                int price_index = cursor.getColumnIndex(PRICE);
                String price = cursor.getString(price_index);
                int image_index = cursor.getColumnIndex(IMAGE);
                String image = cursor.getString(image_index);
                int user_id_index = cursor.getColumnIndex(USER_ID);
                String userId = cursor.getString(user_id_index);
                int active_index = cursor.getColumnIndex(ACTIVE);
                String active = cursor.getString(active_index);

                PostClass post = new PostClass(id, title, description, price, userId, active, image);
                posts.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return posts;
    }



    public PostClass get_post(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + POSTS + " WHERE " + ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        PostClass post = null;

        if (cursor.moveToFirst()) {
            int id_index = cursor.getColumnIndex(ID);

            int title_index = cursor.getColumnIndex(TITLE);

            int description_index = cursor.getColumnIndex(DESCRIPTION);

            int price_index = cursor.getColumnIndex(PRICE);

            int image_index = cursor.getColumnIndex(IMAGE);

            int user_id_index = cursor.getColumnIndex(USER_ID);

            int active_index = cursor.getColumnIndex(ACTIVE);


            // Check if all column indexes are valid
            if (id_index >= 0 && title_index >= 0 && description_index >= 0 && price_index >= 0 &&
                    image_index >= 0 && user_id_index >= 0 && active_index >= 0) {



                String title = cursor.getString(title_index);

                String description = cursor.getString(description_index);

                String price = cursor.getString(price_index);

                String image = cursor.getString(image_index);

                String userId = cursor.getString(user_id_index);

                String active = cursor.getString(active_index);


                post = new PostClass(String.valueOf(id), title, description ,price ,userId, active, image);
            }
        }

        cursor.close();

        return post;
    }



}
