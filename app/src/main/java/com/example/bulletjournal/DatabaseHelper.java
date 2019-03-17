package com.example.bulletjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "task.db";
    public static final String TABLE_NAME = "task_table";
    public static final String COL_1 = "NUM";
    public static final String COL_2 = "TASK";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "BOOKMARK";


    public DatabaseHelper(@androidx.annotation.Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (NUM INTEGER PRIMARY KEY AUTOINCREMENT,TASK TEXT,DATE TEXT,BOOKMARK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String task, String date, String bookmark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, task);
        values.put(COL_3, date);
        values.put(COL_4, bookmark);
        long answer = db.insert(TABLE_NAME, null, values);
//        if (answer == -1) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }
}
