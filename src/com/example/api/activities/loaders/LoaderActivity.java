package com.example.api.activities.loaders;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.helloandroidtest.R;

/**
 * @author wh api LoaderCursor
 */
public class LoaderActivity extends Activity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loader);

		loadContacts();
	}

	private SimpleCursorAdapter mAdapter;

	private final void loadContacts() {
		getLoaderManager().initLoader(0, null, this);
		
		ListView contactsList = (ListView) this.findViewById(R.id.contactsList);
		
		mAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, null, new String[] {
						Contacts.DISPLAY_NAME, Contacts._ID }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		
		contactsList.setAdapter(mAdapter);
	}

	private static final String[] CONTACT_PROJECTION = new String[] {
			Contacts._ID, Contacts.DISPLAY_NAME };

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, Contacts.CONTENT_URI, CONTACT_PROJECTION,
				null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}
}
