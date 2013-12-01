package com.samples.dbcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class FlightDbHelper extends SQLiteOpenHelper 
        implements BaseColumns {
    
    public static final String TABLE_NAME = "flight";
    public static final String DESTINATION = "destination";
    public static final String FLIGHT_NUM = "flight_num";
    public static final String PLANE_TYPE = "plane_type";
    
    public FlightDbHelper(Context context) {
        super(context, FlightProvider.DB_FLIGHTS, null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME 
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + DESTINATION + " TEXT, " + FLIGHT_NUM + " TEXT, " + PLANE_TYPE + " TEXT);");
        
        ContentValues values = new ContentValues();
                      
        values.put(DESTINATION, "Kiev");
        values.put(FLIGHT_NUM, "232");
        values.put(PLANE_TYPE, "Boeing 787");
        db.insert(TABLE_NAME, DESTINATION, values);  
        
        values.put(DESTINATION, "Moskov");
        values.put(FLIGHT_NUM, "186");
        values.put(PLANE_TYPE, "‎Airbus A380");
        db.insert(TABLE_NAME, DESTINATION, values); 
        
        values.put(DESTINATION, "Donetsk");
        values.put(FLIGHT_NUM, "790");
        values.put(PLANE_TYPE, "‎Airbus A380");
        db.insert(TABLE_NAME, DESTINATION, values); 

    }

    
    @Override
    public void onUpgrade(
            SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }   
}