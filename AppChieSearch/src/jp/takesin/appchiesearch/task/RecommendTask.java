package jp.takesin.appchiesearch.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.takesin.appchiesearch.Const;
import jp.takesin.appchiesearch.parser.GoogleSuggestParser;

import android.os.AsyncTask;

public class RecommendTask extends AsyncTask<String, Void, ArrayList<String>> {

	private static final String TAG = RecommendTask.class.getSimpleName();

	public RecommendTask() {
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		return requestSuggest(params[0]);
	}

	private ArrayList<String> requestSuggest(String query) {

		final StringBuffer sb = new StringBuffer();
		sb.append(Const.SUGGEST_API);
		try {
			sb.append(URLEncoder.encode(query, "UTF-8"));
			// sb.append(URLEncoder.encode(Const.SUGGEST_PRF.concat("パズドラ"),
			// "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			sb.append(query);
			e1.printStackTrace();
		}

		ArrayList<String> data = null;
		URL url;
		InputStream in;
		URLConnection conn;
		try {
			url = new URL(sb.toString());
			conn = url.openConnection();
			conn.setReadTimeout(Const.API_TIMEOUT);
			conn.setConnectTimeout(Const.API_TIMEOUT);
			conn.connect();
			in = conn.getInputStream();

			final GoogleSuggestParser parser = new GoogleSuggestParser();
			data = parser.xmlParser(in);
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
