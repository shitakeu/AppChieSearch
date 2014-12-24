package jp.takesin.appchiesearch;

import java.util.ArrayList;
import java.util.List;

import jp.takesin.appchiesearch.data.AppData;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class Utiles {

	public static ArrayList<AppData> getAppsData(Context context) {
		final PackageManager packageManager = context.getPackageManager();
		final List<ApplicationInfo> applicationInfo = packageManager
				.getInstalledApplications(PackageManager.GET_META_DATA);
		final ArrayList<AppData> appList = new ArrayList<AppData>();
		for (ApplicationInfo info : applicationInfo) {
			if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) continue;
			final AppData app = new AppData();
			final String name = (String) packageManager
					.getApplicationLabel(info);
			final Drawable icon = packageManager.getApplicationIcon(info);

			app.setName(name);
			app.setIcon(icon);

			appList.add(app);
		}
		return appList;
	}
}
