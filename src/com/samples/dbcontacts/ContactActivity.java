package com.samples.dbcontacts;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends ListActivity {
	private static final int IDM_ADD = 101;
	private static final int IDM_EDIT = 102;
	private static final int IDM_DELETE = 103;
	private static int selectedIndex;

	private Cursor mCursor;
	private ListAdapter mAdapter;

	private static final String[] mContent = new String[] {
			ContactDbHelper._ID, ContactDbHelper.NAME, ContactDbHelper.PHONE };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCursor = managedQuery(ContactProvider.CONTENT_URI, mContent, null,
				null, null);

		mAdapter = new SimpleCursorAdapter(this, R.layout.main, mCursor,
				new String[] { ContactDbHelper.NAME, ContactDbHelper.PHONE },
				new int[] { R.id.name, R.id.phone });
		setListAdapter(mAdapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View view, int position,
					long id) {
				selectedIndex = (int) id;
			}
		});
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

		switch (item.getItemId()) {
		case IDM_ADD: {
			CallAddContactDialog();
		}
			break;
		case IDM_EDIT: {
			CallEditContactDialog(id);
		}
			break;
		case IDM_DELETE: {
			CallDeleteContactDialog(id);
		}
			break;
		}
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
				getContentResolver().delete(ContactProvider.CONTENT_URI,
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

	private void CallEditContactDialog(long id) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View root = inflater.inflate(R.layout.dialog, null);

		final EditText textName = (EditText) root.findViewById(R.id.name);
		final EditText textPhone = (EditText) root.findViewById(R.id.phone);

		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setView(root);
		b.setTitle(R.string.title_edit);
		String[] col = {"_ID"};
		Cursor name = getContentResolver().query(ContactProvider.CONTENT_URI, col,"_ID=selectedIndex",
				null, null, null);
		String n = name.getString(0);
	
		b.setPositiveButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
												
						ContentValues values = new ContentValues(2);

						values.put(ContactDbHelper.NAME, textName.getText()
								.toString());
						values.put(ContactDbHelper.PHONE, textPhone.getText()
								.toString());

						getContentResolver().update(
								ContactProvider.CONTENT_URI, values, "_ID=" + selectedIndex, null);
						mCursor.requery();
					}
				});
		b.setNegativeButton(R.string.btn_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		b.show();
	}

	private void CallAddContactDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View root = inflater.inflate(R.layout.dialog, null);

		final EditText textName = (EditText) root.findViewById(R.id.name);
		final EditText textPhone = (EditText) root.findViewById(R.id.phone);

		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setView(root);
		b.setTitle(R.string.title_add);
		b.setPositiveButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ContentValues values = new ContentValues(2);

						values.put(ContactDbHelper.NAME, textName.getText()
								.toString());
						values.put(ContactDbHelper.PHONE, textPhone.getText()
								.toString());

						getContentResolver().insert(
								ContactProvider.CONTENT_URI, values);
						mCursor.requery();
					}
				});
		b.setNegativeButton(R.string.btn_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		b.show();
	}

}
