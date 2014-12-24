package jp.takesin.appchiesearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.takesin.appchiesearch.data.ChieData;
import jp.takesin.appchiesearch.parser.ChieParser;

import android.os.AsyncTask;

public class RecommendTask extends AsyncTask<String, Void, ArrayList<ChieData>> {
	

	private static final String TAG = RecommendTask.class.getSimpleName();
	
	public RecommendTask(){
	}
	
	@Override
	protected ArrayList<ChieData> doInBackground(String... params){
		return requestChie(params[0]);
	}
	
	private ArrayList<ChieData> requestChie(String query){
		
		final StringBuffer sb = new StringBuffer();
		sb.append(Const.CHIE_API);
		sb.append("&query=");
		try {
			sb.append(URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			sb.append(query);
			e1.printStackTrace();
		}
		
		ArrayList<ChieData> data = null;
		URL url;
		InputStream in;
		URLConnection conn;
		try {
			url = new URL( sb.toString() );
			conn = url.openConnection();
			conn.setReadTimeout(Const.API_TIMEOUT);
			conn.setConnectTimeout(Const.API_TIMEOUT);
			conn.connect();
			in = conn.getInputStream();
			
			final ChieParser parser = new ChieParser();
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
