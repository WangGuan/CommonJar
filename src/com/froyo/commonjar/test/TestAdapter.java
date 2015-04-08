package com.froyo.commonjar.test;

import java.util.List;

import android.view.View;
import android.widget.TextView;

import com.froyo.commonjar.activity.BaseActivity;
import com.froyo.commonjar.adapter.SimpleAdapter;

public class TestAdapter extends SimpleAdapter<String> {

	public TestAdapter(List<String> data, BaseActivity activity, int layoutId) {
		super(data, activity, layoutId, ViewHolder.class);
	}

	@Override
	public void doExtra(View convertView, String item, int position) {
		ViewHolder h = (ViewHolder) holder;
		h.tv_name.setText(item);
	}

	public static class ViewHolder{
		TextView tv_name;
	}
}
