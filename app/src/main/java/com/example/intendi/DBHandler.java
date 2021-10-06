package com.example.intendi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler sInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "intendiDB.db";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_UID = "user_id";
    public static final String COLUMN_UNAME = "name";
    public static final String COLUMN_UDATEBIRTH = "date_birth";
    public static final String COLUMN_UAVATAR = "avatar";

    public static final String TABLE_RESULTS = "results";
    public static final String COLUMN_RID = "result_id";
    public static final String COLUMN_RUSER = "result_user";
    public static final String COLUMN_RGAME = "result_game";
    public static final String COLUMN_RSCORE = "result_score";
    public static final String COLUMN_RDATE = "result_date";


    public static synchronized DBHandler getInstance(Context context){
        if (sInstance == null)
            sInstance = new DBHandler(context.getApplicationContext());
        return sInstance;
    }
    private DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "(" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UNAME + " TEXT, " +
                COLUMN_UDATEBIRTH + " STRING, " +
                COLUMN_UAVATAR + " INTEGER)";

        String CREATE_RESULTS_TABLE = "CREATE TABLE " +
                TABLE_RESULTS + "(" +
                COLUMN_RID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUSER + " INTEGER, " +
                COLUMN_RGAME + " TEXT, " +
                COLUMN_RSCORE + " INTEGER, " +
                COLUMN_RDATE + " STRING)";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
    }

    public void addUser(String name, String dateBirth, int avatar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNAME, name);
        values.put(COLUMN_UDATEBIRTH, dateBirth);
        values.put(COLUMN_UAVATAR, avatar);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    public void addResult(String user, String game, int score, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RUSER, user);
        values.put(COLUMN_RGAME, game);
        values.put(COLUMN_RSCORE, score);
        values.put(COLUMN_RDATE, date);
        db.insert(TABLE_RESULTS, null, values);
        db.close();
    }
    public ArrayList<User> getAllUsers(){
        ArrayList users = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "DELETE FROM " + TABLE_USERS;
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                User cursorUser = new User(cursor.getInt(0), cursor.getString(1), cursor.getInt(3), cursor.getString(2));
                users.add(cursorUser);
                // System.out.println(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        db.close();
        return users;
    }
    public void deleteAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERS);
        db.close();
    }
}
