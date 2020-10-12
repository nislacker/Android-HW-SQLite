package com.demo.hwsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "peopleDb";
    public static final String TABLE_PEOPLE = "people";

    public static final String KEY_ID = "_id";
    public static final String KEY_FNAME = "fist_name";
    public static final String KEY_SNAME = "second_name";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PEOPLE + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FNAME + " TEXT, " + KEY_SNAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        onCreate(db);
    }
}
