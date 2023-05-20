package com.example.queenlivestock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String USERS = "Users";
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



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
