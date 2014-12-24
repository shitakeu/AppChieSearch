package jp.takesin.appchiesearch;

public class Const {

	private static final String APP_ID = "dj0zaiZpPXZUbkRSb0hHN3BjYSZkPVlXazlVVFJ6VVZCbk16UW1jR285TUEtLSZzPWNvbnN1bWVyc2VjcmV0Jng9M2M-";

	public static final String CHIE_API = "http://chiebukuro.yahooapis.jp/Chiebukuro/V1/questionSearch?condition=solved&categoryid=2080159598&results=100&appid="
			+ APP_ID;
	public static final int API_TIMEOUT = 30000;

	public static final String INTENT_KEY_APPNAME = "appname";

	// Google Suggest　API
	public static final String SUGGEST_API = "http://www.google.com/complete/search?hl=jp&output=toolbar&q=";

	public static final String SUGGEST_PRF = "アンドロイド　アプリ ";

	public static final String SUGGEST_NG_WORD_LIST[] = { "アンドロイド", "アプリ", "ダウンロード",
			"iOS", "iPhone", "iPad"
	};
}
