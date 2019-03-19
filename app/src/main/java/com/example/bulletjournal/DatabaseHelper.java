package com.example.bulletjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "task.db";
    public static final String TABLE_NAME = "task_table";
    public static final String COL_1 = "NUM";
    public static final String COL_2 = "TASK";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "BOOKMARK";

    public static final int STATE_TRUE = 1;
    public static final int STATE_FALSE = 0;


    public DatabaseHelper(@androidx.annotation.Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (NUM INTEGER PRIMARY KEY AUTOINCREMENT,TASK TEXT,DATE TEXT,BOOKMARK BIT)");
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
        if (answer == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public ArrayList<TaskData> getDataArray() {
        ArrayList<TaskData> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int num = cursor.getInt(0);
            String task = cursor.getString(1);
            String date= cursor.getString(2);
            String bookmark = cursor.getString(3);
            TaskData newTask = new TaskData(num, task, date, bookmark);
            arrayList.add(newTask);
        }
        return arrayList;
    }

    public boolean updateData(String num, String task, String date, String bookmark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(COL_1, num);
        values.put(COL_2, task);
        values.put(COL_3, date);
        values.put(COL_4, bookmark);

        db.update(TABLE_NAME, values, "NUM = ?", new String[]{num});
        return true;
    }

    public Integer deleteData (String num) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NUM = ?", new String[] {num});
    }

    public ArrayList<TaskData> getDatesDataArray(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d(TAG, "getDatesDataArray: " + date);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DATE" + " =  '" + date + "'" , null);
        ArrayList<TaskData> dateList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int num = cursor.getInt(0);

                Log.d(TAG, "getDatesDataArray: " + num);

                String task = cursor.getString(1);
                String taskDate= cursor.getString(2);
                String bookmark = cursor.getString(3);
                TaskData newTask = new TaskData(num, task, taskDate, bookmark);
                dateList.add(newTask);

            } while (cursor.moveToNext());
        }
        return dateList;
    }

}
