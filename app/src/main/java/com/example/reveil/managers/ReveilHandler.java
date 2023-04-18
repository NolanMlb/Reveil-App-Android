package com.example.reveil.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reveil.models.Reveil;
import com.example.reveil.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ReveilHandler extends SQLiteHelper{
    private SQLiteHelper dbHelper;
    
    public ReveilHandler(Context context) {
        super(context);
        dbHelper = new SQLiteHelper(context);
    }

    public int count() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM " + dbHelper.TABLE_REVEILS;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }

    public int addReveil (Reveil reveil) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_HOUR, reveil.getHour());
        values.put(dbHelper.KEY_MINUTE, reveil.getMinute());
        values.put(dbHelper.KEY_MONDAY, reveil.isMonday());
        values.put(dbHelper.KEY_TUESDAY, reveil.isTuesday());
        values.put(dbHelper.KEY_WEDNESDAY, reveil.isWednesday());
        values.put(dbHelper.KEY_THURSDAY, reveil.isThursday());
        values.put(dbHelper.KEY_FRIDAY, reveil.isFriday());
        values.put(dbHelper.KEY_SATURDAY, reveil.isSaturday());
        values.put(dbHelper.KEY_SUNDAY, reveil.isSunday());
        values.put(dbHelper.KEY_START, reveil.isStart());
        long insertId = db.insert(dbHelper.TABLE_REVEILS, null, values);
        db.close();
        return (int)insertId;
    }

    public List<Reveil> getAllReveils() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Reveil> reveilList = new ArrayList<Reveil>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_REVEILS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Reveil reveil = null;
                try {
                    reveil = new Reveil(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            Integer.parseInt(cursor.getString(3)) != 0,
                            Integer.parseInt(cursor.getString(4)) != 0,
                            Integer.parseInt(cursor.getString(5)) != 0,
                            Integer.parseInt(cursor.getString(6)) != 0,
                            Integer.parseInt(cursor.getString(7)) != 0,
                            Integer.parseInt(cursor.getString(8)) != 0,
                            Integer.parseInt(cursor.getString(9)) != 0,
                            Integer.parseInt(cursor.getString(10)) != 0
                    );
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (reveil != null) {
                    reveilList.add(reveil);
                }
            } while (cursor.moveToNext());
        }
        return reveilList;
    }

    public void deleteReveil(Reveil reveil) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_REVEILS, dbHelper.KEY_ID + " = ?",
                new String[] { String.valueOf(reveil.getId()) });
        db.close();
    }

    public void updateStatusReveil(Reveil reveil) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_START, reveil.isStart());
        db.update(dbHelper.TABLE_REVEILS,values,
                dbHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(reveil.getId())});
        db.close();
    }

    public int updateReveil(Reveil reveil){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_HOUR, reveil.getHour());
        values.put(dbHelper.KEY_MINUTE, reveil.getMinute());
        values.put(dbHelper.KEY_MONDAY, reveil.isMonday());
        values.put(dbHelper.KEY_TUESDAY, reveil.isTuesday());
        values.put(dbHelper.KEY_WEDNESDAY, reveil.isWednesday());
        values.put(dbHelper.KEY_THURSDAY, reveil.isThursday());
        values.put(dbHelper.KEY_FRIDAY, reveil.isFriday());
        values.put(dbHelper.KEY_SATURDAY, reveil.isSaturday());
        values.put(dbHelper.KEY_SUNDAY, reveil.isSunday());
        values.put(dbHelper.KEY_START, reveil.isStart());
        long updateId=db.update(dbHelper.TABLE_REVEILS,values,
                dbHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(reveil.getId())});
        db.close();
        return (int)updateId;
    }
}
