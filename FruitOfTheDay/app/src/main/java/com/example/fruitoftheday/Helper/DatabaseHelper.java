package com.example.fruitoftheday.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "fruit_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "Name";
    private static final String COL2 = "LikePercent";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT, "
                + COL2 +" INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS  " + TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public boolean addData(String name, int likepercent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, likepercent);

        Log.d(TAG, "addData: Adding " + name + ", Like Percent:" + likepercent + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result == -1 ? false : true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase(); // TODO: See if it works with getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +  COL0 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
               " = '" + newName + "' WHERE " + COL0 + " = '" + id + "'" +
               " AND " + COL1 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query:" + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "'" + " AND " + COL1 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query:" + query);
        Log.d(TAG, "deleteName: Deleting " + name);
        db.execSQL(query);
    }

}
