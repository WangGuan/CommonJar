package com.froyo.commonjar.activity;

import java.io.Serializable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.froyo.commonjar.R;
import com.froyo.commonjar.utils.AppUtils;
import com.lidroid.xutils.ViewUtils;

public abstract class BaseActivity extends FragmentActivity {

	protected BaseActivity activity = this;
	
	private ProgressDialog loadingDialog;

	protected abstract int setLayoutResID();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setLayoutResID());
		ViewUtils.inject(activity);
		doBusiness();
	}

	public void toast(Object obj) {
		toast(obj, Toast.LENGTH_SHORT);
	}

	/**
	 * 
	 * @Des: 重写此方法，实现业务
	 * @param
	 * @return void
	 */
	public void doBusiness() {

	}

	public void toast(Object obj, int dur) {
		Toast toast = new Toast(activity);
		View layout = makeView(R.layout.view_toast);
		LayoutParams lp = new LayoutParams(AppUtils.getWidth(activity),
				AppUtils.getHeight(activity));
		layout.setLayoutParams(lp);
		toast.setView(layout);
		toast.setDuration(dur);
		TextView toastText = (TextView) layout.findViewById(R.id.tv_toast);
		if (obj != null) {
			toastText.setText(obj.toString());
			toast.show();
		}
	}

	public void skip(Class<? extends Activity> cls) {
		startActivity(new Intent(this, cls));
	}

	public void skip(String action) {
		startActivity(new Intent(action));
	}

	public void skip(String action, Serializable... serializ) {
		Intent intent = new Intent(action);
		Bundle extras = new Bundle();
		for (int i = 0; i < serializ.length; i++) {
			Serializable s = serializ[i];
			// 放对象的规则，以顺序为键
			extras.putSerializable(i + "", s);
		}
		intent.putExtras(extras);
		startActivity(intent);
	}

	public void skip(Class<? extends Activity> cls, Serializable... serializ) {
		Intent intent = new Intent(this, cls);
		Bundle extras = new Bundle();
		for (int i = 0; i < serializ.length; i++) {
			Serializable s = serializ[i];
			// 放对象的规则，以顺序为键
			extras.putSerializable(i + "", s);
		}
		intent.putExtras(extras);
		startActivity(intent);
	}

	public Serializable getVo(String key) {
		Intent myIntent = getIntent();
		Bundle bundle = myIntent.getExtras();
		if (bundle == null) {
			return "";
		}
		Serializable vo = bundle.getSerializable(key);
		return vo;
	}

	public View makeView(int resId) {
		LayoutInflater inflater = LayoutInflater.from(this);
		return inflater.inflate(resId, null);
	}

	public String getTextRes(int id) {
		return getResources().getString(id);
	}

	public Drawable getDrawableRes(int id) {
		return getResources().getDrawable(id);
	}

	public int getColorRes(int id) {
		return getResources().getColor(id);
	}

	public BaseActivity getActivity() {
		return this;
	}

	public void showDialog() {
		if (activity == null) {
			return;
		}
		if (loadingDialog == null) {
			loadingDialog = new ProgressDialog(activity);
			loadingDialog.setCanceledOnTouchOutside(false);
		}
		loadingDialog.setMessage("加载中...");
		loadingDialog.show();
	}

	public void showDialog(String msg) {
		if (loadingDialog == null) {
			loadingDialog = new ProgressDialog(activity);
			loadingDialog.setCanceledOnTouchOutside(false);
		}
		if (msg.length() < 1) {
			showDialog();
		} else {
			loadingDialog.setMessage(msg);
			loadingDialog.show();
		}
	}

	public void dismissDialog() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
	}
}
