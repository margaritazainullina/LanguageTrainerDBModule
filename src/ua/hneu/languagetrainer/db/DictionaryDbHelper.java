package ua.hneu.languagetrainer.db;

import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DictionaryDbHelper extends SQLiteOpenHelper implements BaseColumns {

	public DictionaryDbHelper(Context context) {
		super(context, VocabularyDAO.DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);*/
	}


	
	
}