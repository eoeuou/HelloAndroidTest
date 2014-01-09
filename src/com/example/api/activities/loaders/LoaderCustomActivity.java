package com.example.api.activities.loaders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.api.activities.loaders.LoaderCustomActivity.AppEntry;
import com.example.helloandroidtest.BaseActivity;
import com.example.helloandroidtest.R;

public class LoaderCustomActivity extends BaseActivity implements
		LoaderManager.LoaderCallbacks<List<AppEntry>> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loader);

		loadApps();
	}

	private AppListAdapter mAdapter;

	private final void loadApps() {

		ListView contactsList = (ListView) this.findViewById(R.id.contactsList);

		mAdapter = new AppListAdapter(this);

		contactsList.setAdapter(mAdapter);

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Class getActivityClass() {
		return LoaderCustomActivity.class;
	}

	@Override
	public Loader<List<AppEntry>> onCreateLoader(int id, Bundle args) {
		return new AppListLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<AppEntry>> loader,
			List<AppEntry> data) {
		mAdapter.clear();
		if (data != null) {
			mAdapter.addAll(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<AppEntry>> loader) {
		mAdapter.clear();
	}

	public static class AppEntry {
		private final AppListLoader mLoader;
		private final ApplicationInfo mInfo;
		private final File mApkFile;
		private String mLabel;
		private Drawable mIcon;
		private boolean mMounted;

		public AppEntry(AppListLoader loader, ApplicationInfo info) {
			this.mLoader = loader;
			this.mInfo = info;
			this.mApkFile = new File(info.sourceDir);
		}

		public ApplicationInfo getApplicationInfo() {
			return mInfo;
		}

		public String getmLabel() {
			if (mLabel == null) {
				if (mApkFile.exists()) {
					mLabel = (String) mInfo.loadLabel(mLoader.mPm);
					return mLabel;
				}
			}
			return null;
		}

		public Drawable getmIcon() {
			if (mIcon == null) {
				if (mApkFile.exists()) {
					mIcon = mInfo.loadIcon(mLoader.mPm);
					return mIcon;
				}
			}
			return mLoader.getContext().getResources()
					.getDrawable(android.R.drawable.sym_def_app_icon);
		}

	}

	public static class AppListLoader extends AsyncTaskLoader<List<AppEntry>> {
		final PackageManager mPm;
		final Context mContext;

		public AppListLoader(Context context) {
			super(context);
			mContext = getContext();
			mPm = getContext().getPackageManager();
		}

		@Override
		public List<AppEntry> loadInBackground() {
			// TODO Auto-generated method stub
			List<ApplicationInfo> apps = mPm
					.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
							| PackageManager.GET_DISABLED_COMPONENTS);
			if (apps == null) {
				apps = new ArrayList<ApplicationInfo>();
			}

			List<AppEntry> entries = new ArrayList<LoaderCustomActivity.AppEntry>(
					apps.size());

			for (int i = 0; i < apps.size(); i++) {
				AppEntry appEntry = new AppEntry(this, apps.get(i));
				entries.add(appEntry);
			}

			return entries;
		}

		@Override
		public void deliverResult(List<AppEntry> data) {
			// TODO Auto-generated method stub
			super.deliverResult(data);
		}
	}

	public static class AppListAdapter extends ArrayAdapter<AppEntry> {
		private final LayoutInflater mInflater;

		public AppListAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_2);
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;

			if (convertView == null) {
				view = mInflater.inflate(R.layout.list_item_icon_text, parent,
						false);
			} else {
				view = convertView;
			}

			AppEntry item = getItem(position);
			((ImageView) view.findViewById(R.id.icon)).setImageDrawable(item
					.getmIcon());
			((TextView) view.findViewById(R.id.text)).setText(item.getmLabel());

			return view;
		}

	}
}
