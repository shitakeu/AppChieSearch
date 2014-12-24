package jp.takesin.appchiesearch;

import java.util.ArrayList;

import jp.takesin.appchiesearch.data.ChieData;
import jp.takesin.appchiesearch.task.ChieTask;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;

public class ChieListActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = ChieListActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chie_list);

		final Bundle ext = getIntent().getExtras();
		String appName = "";
		if (ext != null) {
			appName = ext.getString(Const.INTENT_KEY_APPNAME);
		}

		final TextView txtView = (TextView) findViewById(R.id.TextApp);
		txtView.setText("「" + appName + "」で検索した結果");

		final ListView list = (ListView) findViewById(R.id.ListApps);

		final ChieTask task = new ChieTask() {
			private ProgressDialog mDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mDialog = new ProgressDialog(ChieListActivity.this);
				mDialog.setTitle("Please wait");
				mDialog.setMessage("Uploading...");
				mDialog.show();
			}

			@Override
			public void onPostExecute(ArrayList<ChieData> chieDataList) {

				if (chieDataList == null) {
					if (mDialog != null) {
						mDialog.dismiss();
					}
					final String[] text = { "通信できませんでした。" };
					final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_row_error,
							text);
					list.setAdapter(adapter);
				} else if (chieDataList.size() == 0) {
					final String[] text = { "ごめんなさい！!¥n該当データがありませんでした。" };
					final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_row_error,
							text);
					list.setAdapter(adapter);
				} else {
					final ChieAdapter chieAdapter = new ChieAdapter(
							getApplicationContext(), R.layout.list_row,
							chieDataList);
					list.setAdapter(chieAdapter);
				}

				if (mDialog != null) {
					mDialog.dismiss();
				}
			}
		};
		task.execute(appName);
	}

}
