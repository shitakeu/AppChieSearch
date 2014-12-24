package jp.takesin.appchiesearch;

import java.util.ArrayList;

import jp.takesin.appchiesearch.data.ChieData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChieAdapter extends ArrayAdapter<ChieData> {

	private static final String TAG = ChieAdapter.class.getSimpleName();

	public ChieAdapter(Context context, int resourceId,
			ArrayList<ChieData> chieDataList) {
		super(context, resourceId, chieDataList);
		mContext = context;
		mChieDataList = chieDataList;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private ArrayList<ChieData> mChieDataList;
	private LayoutInflater mInflater;
	private Context mContext;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row, parent, false);
			final TextView content = (TextView) convertView.findViewById(R.id.txt_title);
			final TextView condition = (TextView) convertView.findViewById(R.id.txt_condition);
			final TextView update = (TextView) convertView.findViewById(R.id.txt_update);

			holder = new ViewHolder();
			holder.content = content; 
			holder.condition = condition; 
			holder.update = update; 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();  
		}


		
		final ChieData data = mChieDataList.get(position);
		holder.content.setText(data.mText);
		holder.condition.setText(data.mCondition);
		String condition = "";
		if(TextUtils.equals(data.mCondition, "solved") ){
			condition = "解決済み";
			holder.condition.setTextColor(Color.RED);
		}else if(TextUtils.equals(data.mCondition, "open")){
			condition = "回答受付中";
			holder.condition.setTextColor(Color.BLUE);
		}else if(TextUtils.equals(data.mCondition, "open")){
			condition = "投票受付中";
			holder.condition.setTextColor(Color.GREEN);
		}
		holder.condition.setText(condition);
		
		
		holder.update.setText(data.mUpdate);
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Uri uri = Uri.parse(data.mLinkUrl);
				final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		TextView content;
		TextView condition;
		TextView update;
	}

}
