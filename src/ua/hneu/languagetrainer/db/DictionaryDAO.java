package ua.hneu.languagetrainer.db;

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

public class DictionaryDAO extends ContentProvider {

	public static final String DB_DICTIONARY = "dictionary.db";

	public static final Uri CONTENT_URI = Uri
			.parse("content://ua.hneu.languagetrainer.db/dictionary");
	public static final int URI_CODE = 1;
	public static final int URI_CODE_ID = 2;

	private static final UriMatcher mUriMatcher;

	private static HashMap<String, String> mContactMap;

	private static SQLiteDatabase db;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI("ua.hneu.languagetrainer.db",
				DictionaryDbHelper.TABLE_NAME, URI_CODE);
		mUriMatcher.addURI("ua.hneu.languagetrainer.db",
				DictionaryDbHelper.TABLE_NAME + "/#", URI_CODE_ID);

		mContactMap = new HashMap<String, String>();
		mContactMap.put(DictionaryDbHelper._ID, DictionaryDbHelper._ID);

		ContentValues values = new ContentValues();
		mContactMap.put(DictionaryDbHelper.KANJI, DictionaryDbHelper.KANJI);
		mContactMap.put(DictionaryDbHelper.LEVEL, DictionaryDbHelper.LEVEL);
		mContactMap.put(DictionaryDbHelper.TRANSCRIPTION,
				DictionaryDbHelper.TRANSCRIPTION);
		mContactMap.put(DictionaryDbHelper.ROMAJI, DictionaryDbHelper.ROMAJI);
		mContactMap.put(DictionaryDbHelper.TRANSLATIONS,
				DictionaryDbHelper.TRANSLATIONS);
		mContactMap.put(DictionaryDbHelper.EXAMPLES, DictionaryDbHelper.EXAMPLES);
		mContactMap.put(DictionaryDbHelper.PERCENTAGE, DictionaryDbHelper.PERCENTAGE);
		mContactMap.put(DictionaryDbHelper.SHOWNTIMES, DictionaryDbHelper.SHOWNTIMES);
		mContactMap.put(DictionaryDbHelper.LASTVIEW, DictionaryDbHelper.LASTVIEW);

	}

	public String getDbName() {
		return (DB_DICTIONARY);
	}

	public static SQLiteDatabase getDb() {
		return db;
	}

	@Override
	public boolean onCreate() {
		db = (new DictionaryDbHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = DictionaryDbHelper.LEVEL;
		} else {
			orderBy = sort;
		}

		Cursor c = db.query(DictionaryDbHelper.TABLE_NAME, projection, selection,
				selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	@Override
	public Uri insert(Uri url, ContentValues inValues) {

		ContentValues values = new ContentValues(inValues);

		long rowId = db.insert(DictionaryDbHelper.TABLE_NAME,
				DictionaryDbHelper.KANJI, values);
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
		int retVal = db.delete(DictionaryDbHelper.TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int retVal = db.update(DictionaryDbHelper.TABLE_NAME, values, where,
				whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
}
