package ua.hneu.languagetrainer.db;

import ua.hneu.languagetrainer.db.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
	private static final int IDM_ADD = 101;
	private static final int IDM_EDIT = 102;
	private static final int IDM_DELETE = 103;
	private static int selectedIndex;

	private Cursor mCursor;
	private ListAdapter mAdapter;

	private static final String[] mContent = new String[] {
			DictionaryDbHelper._ID, DictionaryDbHelper.KANJI,
			DictionaryDbHelper.LEVEL, DictionaryDbHelper.TRANSCRIPTION,
			DictionaryDbHelper.ROMAJI, DictionaryDbHelper.TRANSLATIONS,
			DictionaryDbHelper.EXAMPLES, DictionaryDbHelper.PERCENTAGE,
			DictionaryDbHelper.SHOWNTIMES, DictionaryDbHelper.LASTVIEW };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SQLiteDatabase db = DictionaryDAO.getDb();
		DictionaryDbHelper d = new DictionaryDbHelper(getBaseContext());
		d.onUpgrade(db, 0, 0);

		mCursor = managedQuery(DictionaryDAO.CONTENT_URI, mContent, null, null,
				null);

		mAdapter = new SimpleCursorAdapter(this, R.layout.main, mCursor,
				new String[] { DictionaryDbHelper.KANJI,
						DictionaryDbHelper.LEVEL,
						DictionaryDbHelper.TRANSCRIPTION,
						DictionaryDbHelper.ROMAJI,
						DictionaryDbHelper.TRANSLATIONS,
						DictionaryDbHelper.EXAMPLES,
						DictionaryDbHelper.PERCENTAGE,
						DictionaryDbHelper.SHOWNTIMES,
						DictionaryDbHelper.LASTVIEW }, new int[] { R.id.t1,
						R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7,
						R.id.t8, R.id.t9 });

		setListAdapter(mAdapter);

		// insert values
		d.insert("警官", 5, "けいかん", "keikan", "полицейский", "", 0.9, 1, "2013-10-07 08:23:19", getContentResolver());
		mCursor.requery();

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View view, int position,
					long id) {
				selectedIndex = (int) id;
			}
		});

		
		
		// edit
		/*String[] col = { "DESTINATION", "FLIGHT_NUM", "PLANE_TYPE" };
		Cursor c = getContentResolver().query(DictionaryDAO.CONTENT_URI, col,
				"_ID=" + selectedIndex, null, null, null);
		c.moveToFirst();

		String dest_q = "";
		String flight_num_q = "";
		String plane_type_q = "";
		while (!c.isAfterLast()) {
			dest_q = c.getString(0);
			flight_num_q = c.getString(1);
			plane_type_q = c.getString(2);
			c.moveToNext();
		}

		destET.setText(dest_q);
		flightNumET.setText(flight_num_q);
		planeTypeET.setText(plane_type_q);*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, IDM_ADD, Menu.NONE, R.string.menu_add)
				.setIcon(R.drawable.ic_menu_add).setAlphabeticShortcut('a');

		menu.add(Menu.NONE, IDM_EDIT, Menu.NONE, R.string.menu_edit)
				.setIcon(R.drawable.ic_menu_edit).setAlphabeticShortcut('e');

		menu.add(Menu.NONE, IDM_DELETE, Menu.NONE, R.string.menu_delete)
				.setIcon(R.drawable.ic_menu_delete).setAlphabeticShortcut('d');

		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final long id = this.getSelectedItemId();

		/*
		 * switch (item.getItemId()) { case IDM_ADD: { CallAddContactDialog(); }
		 * break; case IDM_EDIT: { CallEditContactDialog(id); } break; case
		 * IDM_DELETE: { CallDeleteContactDialog(id); } break; }
		 */
		return (super.onOptionsItemSelected(item));
	}

	private void CallDeleteContactDialog(final long id) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View root = inflater.inflate(R.layout.dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Confirm");
		builder.setMessage("Are you sure want to delete?");

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getContentResolver().delete(DictionaryDAO.CONTENT_URI,
						"_ID=" + selectedIndex, null);
				mCursor.requery();
			}
		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	/*
	 * private void CallEditContactDialog(long id) { LayoutInflater inflater =
	 * LayoutInflater.from(this); View root = inflater.inflate(R.layout.dialog,
	 * null);
	 * 
	 * final EditText destET = (EditText) root.findViewById(R.id.destET); final
	 * EditText flightNumET = (EditText) root .findViewById(R.id.flightNumET);
	 * final EditText planeTypeET = (EditText) root
	 * .findViewById(R.id.planeTypeET);
	 * 
	 * AlertDialog.Builder b = new AlertDialog.Builder(this); b.setView(root);
	 * b.setTitle(R.string.title_edit); String[] col = { "DESTINATION",
	 * "FLIGHT_NUM", "PLANE_TYPE" }; Cursor c =
	 * getContentResolver().query(FlightProvider.CONTENT_URI, col, "_ID=" +
	 * selectedIndex, null, null, null); c.moveToFirst();
	 * 
	 * String dest_q = ""; String flight_num_q = ""; String plane_type_q = "";
	 * while (!c.isAfterLast()) { dest_q = c.getString(0); flight_num_q =
	 * c.getString(1); plane_type_q = c.getString(2); c.moveToNext(); }
	 * 
	 * destET.setText(dest_q); flightNumET.setText(flight_num_q);
	 * planeTypeET.setText(plane_type_q);
	 * 
	 * b.setPositiveButton(R.string.btn_ok, new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int whichButton) {
	 * 
	 * ContentValues values = new ContentValues(2);
	 * 
	 * values.put(FlightDbHelper.DESTINATION, destET.getText() .toString());
	 * values.put(FlightDbHelper.FLIGHT_NUM, flightNumET .getText().toString());
	 * values.put(FlightDbHelper.FLIGHT_NUM, planeTypeET .getText().toString());
	 * 
	 * getContentResolver().update(FlightProvider.CONTENT_URI, values, "_ID=" +
	 * selectedIndex, null); mCursor.requery(); } });
	 * b.setNegativeButton(R.string.btn_cancel, new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int whichButton) { } }); b.show(); }
	 */
	/*
	 * private void CallAddContactDialog() { LayoutInflater inflater =
	 * LayoutInflater.from(this); View root = inflater.inflate(R.layout.dialog,
	 * null);
	 * 
	 * final EditText destET = (EditText) root.findViewById(R.id.destET); final
	 * EditText flightNumET = (EditText) root .findViewById(R.id.flightNumET);
	 * final EditText planeTypeET = (EditText) root
	 * .findViewById(R.id.planeTypeET);
	 * 
	 * AlertDialog.Builder b = new AlertDialog.Builder(this); b.setView(root);
	 * b.setTitle(R.string.title_add); b.setPositiveButton(R.string.btn_ok, new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int whichButton) { ContentValues values = new ContentValues(2);
	 * 
	 * values.put(FlightDbHelper.DESTINATION, destET.getText() .toString());
	 * values.put(FlightDbHelper.FLIGHT_NUM, flightNumET .getText().toString());
	 * values.put(FlightDbHelper.PLANE_TYPE, planeTypeET .getText().toString());
	 * 
	 * getContentResolver().insert(FlightProvider.CONTENT_URI, values);
	 * mCursor.requery(); } }); b.setNegativeButton(R.string.btn_cancel, new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int whichButton) { } }); b.show(); }
	 */

}
