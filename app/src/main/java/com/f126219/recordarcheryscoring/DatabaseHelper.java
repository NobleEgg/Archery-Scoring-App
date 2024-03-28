package com.f126219.recordarcheryscoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

    //OVERVIEW: Class used to access locally stored SQL database
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "RecordArcheryScoring.db", null, 1);
    }

    //Called the first time a database is accessed to create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createScoresTableStatement = "CREATE TABLE SCORES (ID INTEGER PRIMARY KEY AUTOINCREMENT, Round TEXT, BowStyle TEXT, Location TEXT, Date TEXT, Score INTEGER, Record BOOL)";
        sqLiteDatabase.execSQL(createScoresTableStatement);
    }

    //Called whenever the version number of the database changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //Writes results and details of a completed round to the database
    //Takes all values associated with the round | returns a long indicating whether an error has occured
    public long writeResults(String round, String bowStyle, String location, String date, int score, boolean record){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Round", round);
        values.put("BowStyle", bowStyle);
        values.put("Location", location);
        values.put("Date", date);
        values.put("Score", score);
        values.put("Record", record);

        long error = sqLiteDatabase.insert("SCORES", null, values);
        return error;
    }

    //Reads all round information stored in the database
    //Return rounds as an array list of string arrays
    public ArrayList<String[]> getAllScores(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("SCORES", null, null, null, null, null, null);

        ArrayList<String[]> userRecords = new ArrayList<String[]>();

        while(cursor.moveToNext()){ //While there are records to read
            String round = cursor.getString(cursor.getColumnIndexOrThrow("Round"));
            String bowStyle = cursor.getString(cursor.getColumnIndexOrThrow("BowStyle"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("Location"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("Date"));
            long score = cursor.getLong(cursor.getColumnIndexOrThrow("Score"));
            long record = cursor.getLong(cursor.getColumnIndexOrThrow("Record"));

            String stringScore = Long.toString(score);
            String stringRecord = Long.toString(record);

            String[] userRecord = {round, bowStyle, location, date, stringScore, stringRecord};
            userRecords.add(userRecord); //add record to array list
        }
        cursor.close();
        return userRecords;

    }
}
