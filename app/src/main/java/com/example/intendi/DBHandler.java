package com.example.intendi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
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
    //create only one instance of current database
    @Override
    public void onCreate(SQLiteDatabase db){ //create database tables
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "(" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UNAME + " VARCHAR, " +
                COLUMN_UDATEBIRTH + " VARCHAR, " +
                COLUMN_UAVATAR + " INTEGER)";

        String CREATE_RESULTS_TABLE = "CREATE TABLE " +
                TABLE_RESULTS + "(" +
                COLUMN_RID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUSER + " INTEGER, " +
                COLUMN_RGAME + " VARCHAR, " +
                COLUMN_RSCORE + " INTEGER, " +
                COLUMN_RDATE + " VARCHAR," +
                "FOREIGN KEY ("+ COLUMN_RUSER + ") REFERENCES " +
                TABLE_USERS + "("+ COLUMN_UID + "))";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ //drop database tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
    }

    public User addUser(String name, String dateBirth, int avatar){//add user method
        //Base64 encodification
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        String encodedName = Base64.encodeToString(nameBytes, Base64.DEFAULT);
        SQLiteDatabase db = this.getWritableDatabase();
        //store the values to add in contentvalues
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNAME, encodedName);
        values.put(COLUMN_UDATEBIRTH, dateBirth);
        values.put(COLUMN_UAVATAR, avatar);
        //insert the new user
        db.insert(TABLE_USERS, null, values);
        //search the new user into database
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        //decode Base64 stored value
        byte[] curUserDecoded = Base64.decode(cursor.getString(1), Base64.DEFAULT);
        String username = new String(curUserDecoded);
        //instantiate new user from the database search
        User newUser = new User(cursor.getInt(0), username, cursor.getInt(3), cursor.getString(2));
        cursor.close();
        db.close();
        return newUser;
    }
    public void addResult(int idUser, String game, int score, String date){
        int maxScore, idToDelete;
        ContentValues values = new ContentValues();
        values.put(COLUMN_RUSER, idUser);
        values.put(COLUMN_RGAME, game);
        values.put(COLUMN_RSCORE, score);
        values.put(COLUMN_RDATE, date);
        SQLiteDatabase db = this.getWritableDatabase();
        //search all result from user in current game
        String query = "SELECT * FROM " + TABLE_RESULTS +
                " WHERE " + COLUMN_RUSER + "=" + Integer.toString(idUser)
                + " AND " + COLUMN_RGAME + " = '" + game + "'";
        Cursor cursor = db.rawQuery(query, null);
        //1 -> 1 previous result, 2 -> 2 previous results
        if(cursor.getCount() == 1){
            //move to first row
            cursor.moveToFirst();
            //get the score
            maxScore = cursor.getInt(3);
            if (maxScore <= score){
                //new or equal record, so delete record row and insert new result
                idToDelete = cursor.getInt(0);
                db.execSQL("DELETE FROM " + TABLE_RESULTS + " WHERE " + COLUMN_RID + " = " + idToDelete);
                db.insert(TABLE_RESULTS, null, values);
            }
            //dos resultados previos
        }else if (cursor.getCount() == 2) {
            //move to last row
            cursor.moveToLast();
            //get the result id
            idToDelete = cursor.getInt(0);
            //delete row from results searching given result_id
            db.execSQL("DELETE FROM " + TABLE_RESULTS + " WHERE " + COLUMN_RID + " = " + idToDelete);
            //move to first row
            cursor.moveToFirst();
            //get the score
            maxScore = cursor.getInt(3);
            if (maxScore <= score) {
                //new or equal record, so delete record row and insert new result
                idToDelete = cursor.getInt(0);
                db.execSQL("DELETE FROM " + TABLE_RESULTS + " WHERE " + COLUMN_RID + " = " + idToDelete);
                db.insert(TABLE_RESULTS, null, values);
            }
        }
        //insert new result once again despite the above options
        db.insert(TABLE_RESULTS, null, values);
        cursor.close();
        db.close();
    }
    public ArrayList<User> getAllUsers(){ //get all users from database
        //User class objects array
        ArrayList users = new ArrayList();
        //search all users
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        //fill the User class objects array with each row found
        if(cursor.moveToFirst()) {
            do {
                String cursorName = cursor.getString(1);
                //base64 decoder
                byte[] curUserDecoded = Base64.decode(cursorName, Base64.DEFAULT);
                String username = new String(curUserDecoded, StandardCharsets.UTF_8);
                //instantiate a new user and add it to array
                User cursorUser = new User(cursor.getInt(0), username, cursor.getInt(3), cursor.getString(2));
                users.add(cursorUser);
                // System.out.println(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }
    public int getUsersCount(){ //get all user count by selecting all of them and counting the rows
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount;
    }
    public ArrayList<Result> getResultsFromGame(int idUser, String game){ //get all user results from given game
        //Result class objects array
        ArrayList resultsFromGame = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        //seaarch results
        String query = "SELECT * FROM " + TABLE_RESULTS +
                " WHERE " + COLUMN_RUSER + "=" + Integer.toString(idUser)
                + " AND " + COLUMN_RGAME + " = '" + game + "'";
        Cursor cursor = db.rawQuery(query, null);
        //fill the Result class objects array with each row found
        if(cursor.moveToFirst()) {
            do{
                Result cursorResult = new Result(cursor.getInt(3), cursor.getString(4));
                resultsFromGame.add(cursorResult);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultsFromGame;
    }
    public User getCurrentUser(int curUserId){ //find the current user in database from given user_id
        User curUser = new User(0,"0", R.drawable.delphi, "00/00/00");
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS
                + " WHERE " + COLUMN_UID + " = " + Integer.toString(curUserId);
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            byte[] curUserDecoded = Base64.decode(cursor.getString(1), Base64.DEFAULT);
            String username = new String(curUserDecoded, StandardCharsets.UTF_8);
            curUser = new User(cursor.getInt(0), username, cursor.getInt(3), cursor.getString(2));
            return curUser;
        }
        cursor.close();
        db.close();
        return curUser;
    }
    public void updateCurrentUser(User curUser){ //update current user values in database
        //Base64 encoder
        byte[] nameBytes = curUser.getUsername().getBytes(StandardCharsets.UTF_8);
        String encodedName = Base64.encodeToString(nameBytes, Base64.DEFAULT);
        String uid = Integer.toString(curUser.getUser_id());
        SQLiteDatabase db = this.getWritableDatabase();
        //store values in content values
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNAME, encodedName);
        values.put(COLUMN_UDATEBIRTH, curUser.getDateBirth());
        values.put(COLUMN_UAVATAR, curUser.getImageSource());
        //update user with stored values in content values
        db.update(TABLE_USERS, values, COLUMN_UID + " = ?", new String[]{uid});
        db.close();
    }
    public void deleteUser(int user_id){ //given an user id, delete current user from database
        SQLiteDatabase db = this.getWritableDatabase();
        //delete all user results
        db.execSQL("DELETE FROM "+ TABLE_RESULTS + " WHERE " + COLUMN_RUSER + " = " + Integer.toString(user_id));
        db.execSQL("DELETE FROM "+ TABLE_USERS+ " WHERE " + COLUMN_UID + " = " + Integer.toString(user_id));
        db.close();
    }
}
