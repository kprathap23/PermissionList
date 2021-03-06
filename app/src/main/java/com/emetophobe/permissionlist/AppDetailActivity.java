/*
 * Copyright (C) 2013-2014 Mike Cunningham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emetophobe.permissionlist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.MenuItem;

import com.emetophobe.permissionlist.providers.PermissionContract.Permissions;


public class AppDetailActivity extends AbstractDetailActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	public static final String PACKAGE_NAME_EXTRA = "package_name";

	private static final int PERMISSION_LIST = 0;
	private static final int APP_DATA = 1;

	private PermissionListAdapter mAdapter;
	private String mPackageName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the package name from the intent extras
		Bundle extras = getIntent().getExtras();
		mPackageName = extras != null ? extras.getString(PACKAGE_NAME_EXTRA) : null;
		if (mPackageName == null) {
			throw new IllegalArgumentException("Missing intent extra PACKAGE_NAME_EXTRA.");
		}

		// Set up the permission list adapter
		mAdapter = new PermissionListAdapter(this);
		mListView.setAdapter(mAdapter);

		// Load the application data and the permission list
		getSupportLoaderManager().initLoader(APP_DATA, null, this);
		getSupportLoaderManager().initLoader(PERMISSION_LIST, null, this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id == PERMISSION_LIST) {
			return new CursorLoader(this, Permissions.CONTENT_URI, new String[]{Permissions._ID,
					Permissions.PERMISSION_NAME}, Permissions.PACKAGE_NAME + "=?", new String[]{mPackageName},
					Permissions.PERMISSION_NAME + " ASC");
		} else {
			return new CursorLoader(this, Permissions.CONTENT_URI, new String[]{Permissions._ID,
					Permissions.APP_NAME, Permissions.PACKAGE_NAME, "Count(permission) as count"},
					Permissions.PACKAGE_NAME + "=?", new String[]{mPackageName}, Permissions.PERMISSION_NAME + " ASC");
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (loader.getId() == PERMISSION_LIST) {
			mAdapter.swapCursor(cursor);
		} else if (loader.getId() == APP_DATA && cursor != null && cursor.moveToFirst()) {
			// Display the app name in the toolbar title
			setTitle(cursor.getString(cursor.getColumnIndex(Permissions.APP_NAME)));

			// Set the package namd and permission count
			mDescriptionView.setText(cursor.getString(cursor.getColumnIndex(Permissions.PACKAGE_NAME)));
			String count = cursor.getString(cursor.getColumnIndex("count"));
			mCountView.setText(String.format(getString(R.string.permission_count), count));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == PERMISSION_LIST) {
			mAdapter.swapCursor(null);
		}
	}
}
