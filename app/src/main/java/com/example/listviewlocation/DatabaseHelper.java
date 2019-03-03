package com.example.listviewlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE = "locationdb";
    static String LOCATION = "locationtbl";
    static String COL_ID = "id";
    static String COL_LOC = "name";
    static String COL_LAT = "latitude";
    static String COL_LNG = "longitude";


    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    //create table
    private static final String CREATE_LOCATION_TABLE = "CREATE TABLE " + LOCATION + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LOC + " TEXT, " + COL_LAT + " TEXT, " + COL_LNG + " TEXT);";

    //call CREATE_LOCATION_TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + LOCATION +"' ");
    }

    //insert data
    public long addLocation(String name, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();

        //creating contentvalues
        ContentValues values = new ContentValues();
        values.put(COL_LOC, name);
        values.put(COL_LAT, latitude);
        values.put(COL_LNG, longitude);

        //insert rows in table
        long insert = db.insert(LOCATION,null,values);
        db.close();
        return insert;
    }

    //update data
    public int updateLocation(int id, String name, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();

        //creating contentvalues
        ContentValues values = new ContentValues();
        values.put(COL_LOC, name);
        values.put(COL_LAT, latitude);
        values.put(COL_LNG, longitude);

        //update rows in table
        return db.update(LOCATION, values, COL_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    //delete data
    public void deleteLocation(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOCATION, COL_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    //display all data
    public ArrayList<Places> getAll(){
        ArrayList<Places> list = new ArrayList<Places>();

        String selectQuery = "SELECT * FROM " + LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        //looping through all rows and adding to listview
        if(c.moveToFirst()){
            do{
                Places places = new Places();
                places.setId(c.getInt(c.getColumnIndex(COL_ID)));
                places.setName(c.getString(c.getColumnIndex(COL_LOC)));
                places.setLatitude(c.getString(c.getColumnIndex(COL_LAT)));
                places.setLongitude(c.getString(c.getColumnIndex(COL_LNG)));
                //adding to places list
                list.add(places);
            }while (c.moveToNext());
        }
            db.close();
            return list;
    }
}
