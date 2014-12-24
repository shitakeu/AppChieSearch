package jp.takesin.appchiesearch;

import java.util.ArrayList;
import java.util.Random;

import jp.takesin.appchiesearch.data.AppData;
import jp.takesin.appchiesearch.task.RecommendTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText editText = (EditText) findViewById(R.id.edit_word);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					if (event.getAction() == KeyEvent.ACTION_UP) {
						final String searchWord = editText.getText().toString();
						startChieListActivity(searchWord);
					}
					return true;
				}
				return false;
			}
		});

		findViewById(R.id.btn_search).setOnClickListener(this);
		findViewById(R.id.btn_app).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setRecommendText();
	}

	private void setRecommendText() {
		final ArrayList<AppData> listApp = Utiles
				.getAppsData(getApplicationContext());
		final Random rnd = new Random();
		final int ran = rnd.nextInt(listApp.size());
		final String appName = listApp.get(ran).getName();

		final RecommendTask task = new RecommendTask() {
			@Override
			public void onPostExecute(ArrayList<String> result) {
				final ArrayList<String> recommendList = new ArrayList<String>();
				final int size = result.size();
				for (int i = 0; i < size; i++) {
					String recommendWord = result.get(i);
					for (int j = 0; j < Const.SUGGEST_NG_WORD_LIST.length; j++) {
						recommendWord = recommendWord.replaceAll(
								Const.SUGGEST_NG_WORD_LIST[j], "");
					}
					if (1 < recommendWord.trim().length()
							&& !TextUtils.equals(appName, recommendWord)) {
						recommendList.add(recommendWord.trim());
					}
				}

				final TextView txt1 = (TextView) findViewById(R.id.txt_recommend1);
				final TextView txt2 = (TextView) findViewById(R.id.txt_recommend2);
				final TextView txt3 = (TextView) findViewById(R.id.txt_recommend3);

				txt1.setVisibility(View.GONE);
				txt2.setVisibility(View.GONE);
				txt3.setVisibility(View.GONE);
				findViewById(R.id.txt_recommend_title).setVisibility(
						View.VISIBLE);

				final int recommendSize = recommendList.size();
				if (0 < recommendSize && recommendSize <= 1) {
					txt1.setText(recommendList.get(0));
					txt1.setOnClickListener(MainActivity.this);
					txt1.setVisibility(View.VISIBLE);
				} else if (1 < recommendSize && recommendSize <= 2) {
					txt1.setText(recommendList.get(0));
					txt1.setOnClickListener(MainActivity.this);
					txt2.setText(recommendList.get(1));
					txt2.setOnClickListener(MainActivity.this);
					txt1.setVisibility(View.VISIBLE);
					txt2.setVisibility(View.VISIBLE);
				} else if (2 < recommendSize) {
					txt1.setText(recommendList.get(0));
					txt1.setOnClickListener(MainActivity.this);
					txt2.setText(recommendList.get(1));
					txt2.setOnClickListener(MainActivity.this);
					txt3.setText(recommendList.get(2));
					txt3.setOnClickListener(MainActivity.this);

					txt1.setVisibility(View.VISIBLE);
					txt2.setVisibility(View.VISIBLE);
					txt3.setVisibility(View.VISIBLE);
				} else {
					findViewById(R.id.txt_recommend_title).setVisibility(
							View.GONE);
				}

			}
		};
		task.execute(appName);
	}

	private void startChieListActivity(String searchWord) {
		if (TextUtils.isEmpty(searchWord)) {
			Toast.makeText(getApplicationContext(), "検索ワード入力されていません。",
					Toast.LENGTH_LONG).show();
			return;
		}
		final Intent intent = new Intent(getApplicationContext(),
				ChieListActivity.class);
		intent.putExtra(Const.INTENT_KEY_APPNAME, searchWord);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		if (id == R.id.btn_search) {
			final EditText editText = (EditText) findViewById(R.id.edit_word);
			final String searchWord = editText.getText().toString();
			startChieListActivity(searchWord);
		} else if (id == R.id.btn_app) {
			final Intent intent = new Intent(MainActivity.this,
					AppListActivity.class);
			startActivity(intent);
		} else if (id == R.id.txt_recommend1) {
			final TextView textView = (TextView) findViewById(R.id.txt_recommend1);
			final String searchWord = textView.getText().toString();
			startChieListActivity(searchWord);
		} else if (id == R.id.txt_recommend2) {
			final TextView textView = (TextView) findViewById(R.id.txt_recommend2);
			final String searchWord = textView.getText().toString();
			startChieListActivity(searchWord);
		} else if (id == R.id.txt_recommend3) {
			final TextView textView = (TextView) findViewById(R.id.txt_recommend3);
			final String searchWord = textView.getText().toString();
			startChieListActivity(searchWord);
		}
	}
}
