package jp.takesin.appchiesearch.parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import jp.takesin.appchiesearch.data.ChieData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.text.format.Time;
import android.util.Log;
import android.util.Xml;

/**
 * @author shitakeu
 * 
 */
public class ChieParser {

	@SuppressWarnings("unused")
	private static final String TAG = ChieParser.class.getSimpleName();

	public ArrayList<ChieData> xmlParser(InputStream is) {

		final ArrayList<ChieData> result = new ArrayList<ChieData>();
		ChieData currentMsg = null;
		final XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, null);
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					tag = parser.getName();
					if (tag.equals("Question")) {
						currentMsg = new ChieData();
					} else if (currentMsg != null) {
						if (tag.equals("Content")) {
							currentMsg.mText = parser.nextText();
						} else if (tag.equals("Url")) {
							currentMsg.mLinkUrl = parser.nextText();
						} else if (tag.equals("Condition")) {
							currentMsg.mCondition = parser.nextText();
						} else if (tag.equals("PostedDate")) {
							final Time time = new Time();
							time.parse3339(parser.nextText());
							final Date date = new Date(time.toMillis(false));
							final SimpleDateFormat timeFormatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss"); 
							timeFormatter.setTimeZone(TimeZone.getTimeZone("GMT")); 
							final String diffTimeStr = timeFormatter.format(date); 
							currentMsg.mUpdate = diffTimeStr;
						}
					}
					break;

				case XmlPullParser.END_TAG:
					tag = parser.getName();
					if (tag.equals("Question")) {
						result.add(currentMsg);
						currentMsg = null;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException : " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(TAG, "IOException : " + e.getMessage());
			return null;
		}

		return result;
	}
}
