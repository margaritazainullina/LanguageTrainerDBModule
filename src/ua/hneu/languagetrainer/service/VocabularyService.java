package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class VocabularyService {
	public static String TABLE_NAME = "vocabulary";

	public void insert(String kanji, int level, String transcription,
			String romaji, String translations, String examples,
			double percentage, String lastview, int showntimes,
			ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, kanji);
		values.put(VocabularyDAO.LEVEL, level);
		values.put(VocabularyDAO.TRANSCRIPTION, transcription);
		values.put(VocabularyDAO.ROMAJI, romaji);
		values.put(VocabularyDAO.TRANSLATIONS, translations);
		values.put(VocabularyDAO.EXAMPLES, examples);
		values.put(VocabularyDAO.PERCENTAGE, percentage);
		values.put(VocabularyDAO.LASTVIEW, lastview);// 2013-10-07 08:23:19
		values.put(VocabularyDAO.SHOWNTIMES, showntimes);
		cr.insert(VocabularyDAO.CONTENT_URI, values);
	}

	public void edit(int id, String kanji, int level, String transcription,
			String romaji, String translations, String examples,
			double percentage, String lastview, int showntimes,
			ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, kanji);
		values.put(VocabularyDAO.LEVEL, level);
		values.put(VocabularyDAO.TRANSCRIPTION, transcription);
		values.put(VocabularyDAO.ROMAJI, romaji);
		values.put(VocabularyDAO.TRANSLATIONS, translations);
		values.put(VocabularyDAO.EXAMPLES, examples);
		values.put(VocabularyDAO.PERCENTAGE, percentage);
		values.put(VocabularyDAO.LASTVIEW, lastview);
		values.put(VocabularyDAO.SHOWNTIMES, showntimes);
		cr.insert(VocabularyDAO.CONTENT_URI, values);
		cr.update(VocabularyDAO.CONTENT_URI, values, "_ID=" + id, null);
	}

	public void setPercentage(int id, double percentage, ContentResolver cr) {

		String[] col = { "KANJI", "LEVEL", "TRANSCRIPTION", "ROMAJI",
				"TRANSLATIONS", "EXAMPLES", "PERCENTAGE", "LASTVIEW",
				"SHOWNTIMES" };
		Cursor c = cr.query(VocabularyDAO.CONTENT_URI, col, "_ID=" + id, null,
				null, null);
		c.moveToFirst();

		String kanji = "";
		int level = 0;
		String transcription = "";
		String romaji = "";
		String translations = "";
		String examples = "";
		String lastview = "";
		String showntimes = "";

		while (!c.isAfterLast()) {
			kanji = c.getString(0);
			level = c.getInt(1);
			transcription = c.getString(2);
			romaji = c.getString(3);
			translations = c.getString(4);
			examples = c.getString(5);
			lastview = c.getString(7);
			showntimes = c.getString(8);
			c.moveToNext();
		}

		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, kanji);
		values.put(VocabularyDAO.LEVEL, level);
		values.put(VocabularyDAO.TRANSCRIPTION, transcription);
		values.put(VocabularyDAO.ROMAJI, romaji);
		values.put(VocabularyDAO.TRANSLATIONS, translations);
		values.put(VocabularyDAO.EXAMPLES, examples);
		values.put(VocabularyDAO.PERCENTAGE, percentage);
		values.put(VocabularyDAO.LASTVIEW, lastview);
		values.put(VocabularyDAO.SHOWNTIMES, showntimes);
		cr.insert(VocabularyDAO.CONTENT_URI, values);
		cr.update(VocabularyDAO.CONTENT_URI, values, "_ID=" + id, null);
	}

	public void emptyTable() {
		VocabularyDAO.getDb().execSQL("delete from " + TABLE_NAME);
	}

	public void createTable() {
		VocabularyDAO.getDb().execSQL(
				"CREATE TABLE " + TABLE_NAME
						+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ VocabularyDAO.KANJI + " TEXT, " + VocabularyDAO.LEVEL
						+ " TEXT, " + VocabularyDAO.TRANSCRIPTION + " TEXT, "
						+ VocabularyDAO.ROMAJI + " TEXT, "
						+ VocabularyDAO.TRANSLATIONS + " TEXT, "
						+ VocabularyDAO.EXAMPLES + " TEXT, "
						+ VocabularyDAO.PERCENTAGE + " REAL, "
						+ VocabularyDAO.LASTVIEW + " DATETIME,"
						+ VocabularyDAO.SHOWNTIMES + " INTEGER);");
	}

	public void dropTable() {
		VocabularyDAO.getDb().execSQL("DROP TABLE " + TABLE_NAME + ";");
	}
}
