package com.samples.dbcontacts;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class FlightProvider extends ContentProvider {

	public static final String DB_FLIGHTS = "flights.db";

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.samples.dbcontacts.flightsprovider/flights");
	public static final int URI_CODE = 1;
	public static final int URI_CODE_ID = 2;

	private static final UriMatcher mUriMatcher;

	private static HashMap<String, String> mContactMap;

	private static SQLiteDatabase db;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI("com.samples.dbcontacts.flightsprovider",
				FlightDbHelper.TABLE_NAME, URI_CODE);
		mUriMatcher.addURI("com.samples.dbcontacts.flightsprovider",
				FlightDbHelper.TABLE_NAME + "/#", URI_CODE_ID);

		mContactMap = new HashMap<String, String>();
		mContactMap.put(FlightDbHelper._ID, FlightDbHelper._ID);
		mContactMap.put(FlightDbHelper.DESTINATION,
				FlightDbHelper.DESTINATION);
		mContactMap.put(FlightDbHelper.FLIGHT_NUM, FlightDbHelper.FLIGHT_NUM);
		mContactMap.put(FlightDbHelper.PLANE_TYPE, FlightDbHelper.PLANE_TYPE);
	}

	public String getDbName() {
		return (DB_FLIGHTS);
	}

	public static SQLiteDatabase getDb() {
		return db;
	}

	@Override
	public boolean onCreate() {
		db = (new FlightDbHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = FlightDbHelper.DESTINATION;
		} else {
			orderBy = sort;
		}

		Cursor c = db.query(FlightDbHelper.TABLE_NAME, projection, selection,
				selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	@Override
	public Uri insert(Uri url, ContentValues inValues) {

		ContentValues values = new ContentValues(inValues);

		long rowId = db.insert(FlightDbHelper.TABLE_NAME,
				FlightDbHelper.DESTINATION, values);
		if (rowId > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		} else {
			throw new SQLException("Failed to insert row into " + url);
		}
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int retVal = db.delete(FlightDbHelper.TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int retVal = db.update(FlightDbHelper.TABLE_NAME, values, where,
				whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
}
