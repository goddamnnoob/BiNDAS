package com.example.das.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.das.data.infoContract.infoEntry;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class infoDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = infoDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "info.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    public infoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_INFO_TABLE =  "CREATE TABLE " + infoEntry.TABLE_NAME + " ("
                + infoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + infoEntry.COLUMN_VEHICLE_NO + " TEXT NOT NULL , "
                + infoEntry.COLUMN_VEHICLE_NAME + " TEXT NOT NULL, "
                + infoEntry.COLUMN_DRIVER_PHNO + " TEXT NOT NULL, "
                + infoEntry.COLUMN_LATITUDE + " TEXT DEFAULT 0, "
                + infoEntry.COLUMN_LONGITUDE + " TEXT DEFAULT 0, "
                + infoEntry.COLUMN_VEHICLE_PHNO + " TEXT , "
                + infoEntry.COLUMN_DRIVER_STATE + " TEXT , "
                + infoEntry.COLUMN_TIME + " TEXT , "
                + infoEntry.COLUMN_SPEED + " TEXT , "
                + infoEntry.COLUMN_DRIVER_NAME + " TEXT NOT NULL);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
