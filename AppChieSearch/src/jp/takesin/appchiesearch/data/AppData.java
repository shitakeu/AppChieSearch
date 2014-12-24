package jp.takesin.appchiesearch.data;

import android.graphics.drawable.Drawable;

public class AppData {
	private String mName;
	private Drawable mIcon;

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public Drawable getIcon() {
		return mIcon;
	}

	public void setIcon(Drawable icon) {
		this.mIcon = icon;
	}
}
