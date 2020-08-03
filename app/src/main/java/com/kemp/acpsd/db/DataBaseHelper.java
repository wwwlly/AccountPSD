package com.kemp.acpsd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "acntPsd.db";
    private static final String TABLE_NAME = "acntPsd";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ACCOUNT = "account";
    private static final String COLUMN_PSD = "password";
    private static final String COLUMN_REMARKS = "remarks";

    private SQLiteDatabase db;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table if not exists " + TABLE_NAME + " (id integer primary key, " + COLUMN_NAME + " text, " + COLUMN_ACCOUNT + " text, " + COLUMN_PSD + " text, " + COLUMN_REMARKS + " text)";
//        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initDb() {
        db = getReadableDatabase();
    }

    public void insert() {
        if (db == null) {
            initDb();
        }
    }
}
