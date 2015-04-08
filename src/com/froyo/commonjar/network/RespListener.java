package com.froyo.commonjar.network;

import org.json.JSONObject;

import com.android.volley.Response;
import com.froyo.commonjar.activity.BaseActivity;

public abstract class RespListener implements Response.Listener<JSONObject> {

	private BaseActivity activity;

	public RespListener(BaseActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onResponse(JSONObject resp) {
		activity.dismissDialog();
		if (resp == null) {
			doFailed();
		} else {
			getResp(resp);
		}
	}

	public abstract void getResp(JSONObject obj);

	public void doFailed() {
		System.out.println("#######：失败了");
	}
}
