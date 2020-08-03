package com.kemp.acpsd;

import android.app.Application;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static com.kemp.acpsd.db.DbManager.DB_NAME;
import static com.kemp.acpsd.db.DbManager.DB_PATH;

public class App extends Application {

    private static final String TAG = "APP";

    @Override
    public void onCreate() {
        super.onCreate();

//        Log.d(TAG, "DB path: " + getDatabasePath(DB_NAME).getAbsolutePath());
    }

    @Override
    public File getDatabasePath(String name) {
        File parentFile = new File(DB_PATH);
        boolean success;
        if (!parentFile.exists()) {
            success = parentFile.mkdirs();
        } else {
            success = true;
        }

        if (!success) {
            Log.d(TAG, "db file path create faild");
            throw new RuntimeException("db file path create faild");
        }

        File dbFile = new File(parentFile, name);
        if (!dbFile.exists()) {
            try {
                success = dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (!success) {
                Log.d(TAG, "db file create faild");
                throw new RuntimeException("db file create faild");
            }
        }
        return dbFile;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        if (getDatabasePath(name) == null) {
            return null;
        }

        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        if (getDatabasePath(name) == null) {
            return null;
        }

        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }


}
