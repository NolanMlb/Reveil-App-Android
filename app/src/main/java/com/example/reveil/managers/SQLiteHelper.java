package com.example.reveil.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Reveil";
    protected static final String TABLE_REVEILS = "reveils";
    public static final String KEY_ID = "id";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_MINUTE = "minute";
    public static final String KEY_MONDAY = "monday";
    public static final String KEY_TUESDAY = "tuesday";
    public static final String KEY_WEDNESDAY = "wednesday";
    public static final String KEY_THURSDAY = "thursday";
    public static final String KEY_FRIDAY = "friday";
    public static final String KEY_SATURDAY = "saturday";
    public static final String KEY_SUNDAY = "sunday";
    public static final String KEY_START = "start";



    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_REVEILS +
                " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_HOUR + " INTEGER," +
                KEY_MINUTE + " INTEGER," +
                KEY_MONDAY + " BOOLEAN," +
                KEY_TUESDAY + " BOOLEAN," +
                KEY_WEDNESDAY + " BOOLEAN," +
                KEY_THURSDAY + " BOOLEAN," +
                KEY_FRIDAY + " BOOLEAN," +
                KEY_SATURDAY + " BOOLEAN," +
                KEY_SUNDAY + " BOOLEAN," +
                KEY_START + " BOOLEAN)";
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVEILS);
        onCreate(db);
    }
}
