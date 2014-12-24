package jp.takesin.appchiesearch;

import java.util.ArrayList;
import java.util.List;

import jp.takesin.appchiesearch.data.AppData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppListActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		
		final ArrayList<AppData> listApp = Utiles.getAppsData(getApplicationContext());
		
		final CustomAdapter customAdapater = new CustomAdapter(this, 0, listApp);
		
		final ListView list = (ListView)findViewById(R.id.ListApps);
		list.setAdapter(customAdapater);
		
	}



	public class CustomAdapter extends ArrayAdapter<AppData> {
		private LayoutInflater layoutInflater_;

		public CustomAdapter(Context context, int textViewResourceId,
				List<AppData> objects) {
			super(context, textViewResourceId, objects);
			layoutInflater_ = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final AppData item = (AppData) getItem(position);

			if (null == convertView) {
				convertView = layoutInflater_.inflate(R.layout.list_app, null);
			}

			final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
			imageView.setImageDrawable(item.getIcon());

			final TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(item.getName());

			convertView.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					final Intent intent = new Intent(getApplicationContext(), ChieListActivity.class);
					intent.putExtra(Const.INTENT_KEY_APPNAME, item.getName());
					startActivity(intent);
				}
			});
			
			return convertView;
		}
	}

}
