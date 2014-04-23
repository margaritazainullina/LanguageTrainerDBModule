package com.samples.dbcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DictionaryDbHelper extends SQLiteOpenHelper implements BaseColumns {

	public static final String TABLE_NAME = "dictionary";
	public static final String ID = "id";
	public static final String KANJI = "kanji";
	public static final String LEVEL = "level";
	public static final String TRANSCRIPTION = "transcription";
	public static final String ROMAJI = "romaji";
	public static final String TRANSLATIONS = "translations";
	public static final String EXAMPLES = "examples";
	public static final String PERCENTAGE = "percentage";
	public static final String SHOWNTIMES = "timesshown";
	public static final String LASTVIEW = "lastview";

	public DictionaryDbHelper(Context context) {
		super(context, FlightProvider.DB_DICTIONARY, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KANJI
				+ " TEXT, " + LEVEL + " TEXT, " + TRANSCRIPTION + " TEXT, "
				+ ROMAJI + " TEXT, " + TRANSLATIONS + " TEXT, " + EXAMPLES
				+ " TEXT, " + PERCENTAGE + " REAL, " + SHOWNTIMES
				+ " INTEGER, " + LASTVIEW + " DATETIME);");
		ContentValues values = new ContentValues();
		
		values.put(KANJI, "会�?�");
		values.put(LEVEL, 5);
		values.put(TRANSCRIPTION, "�?��?�");
		values.put(ROMAJI, "au");
		values.put(TRANSLATIONS, "в�?тречать");
		values.put(EXAMPLES, "");
		values.put(PERCENTAGE, 0);
		values.put(SHOWNTIMES, 0);		
		values.put(LASTVIEW, "");//2013-10-07 08:23:19
		db.insert(TABLE_NAME, KANJI, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}