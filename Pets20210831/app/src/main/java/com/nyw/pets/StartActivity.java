package com.nyw.pets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.config.Api;
import com.nyw.pets.util.GetAppVersionUtil;
import com.nyw.pets.util.GetImgFilePathUtil;


/**
 * 软件启动界面
 * */
@SuppressWarnings("unused")
public class StartActivity extends Activity {
	private long firstClick;
	private GetAppVersionUtil getAppVersionUtil;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_my01);
//		new getVistionUtil(StartActivity.this, Api.GET_OTHER_APP_VERSION).checkUpdate(false);
		getImagePathUrl();
		LinearLayout mLinear = (LinearLayout) findViewById(R.id.Fragment01Linear);
		mLinear.setBackgroundResource(R.mipmap.start_img);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = hand.obtainMessage();
				hand.sendMessage(msg);
			}

		}.start();
	}

	/**
	 * 获取图片、文件等七牛服务器域名
	 */
	private void getImagePathUrl() {
		String url= Api.GET_IMG_FILE_PATH_SERVICE_URL;
		OkGo.<String>post(url).tag(this)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						String data=response.body();
						Log.i("sfdjsdfisxvcxvfdf",data);
						Gson gson=new Gson();
						GetImgFilePathUtil getImgFilePathUtil=gson.fromJson(data, GetImgFilePathUtil.class);
						MyApplication application = (MyApplication)getApplication();
						application.setImgFilePathUrl(getImgFilePathUtil.getData().getHost());
					}
				});
	}


	@SuppressLint("HandlerLeak")
    Handler hand = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
//			if (isFristRun()) {
//				Intent intent = new Intent(StartActivity.this,
//						MyFragmentActivity.class);
//				startActivity(intent);
//			} else {
//				Intent intent = new Intent(StartActivity.this,
//						MainActivity.class);
//				startActivity(intent);
//			}
			Intent intent = new Intent(StartActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		};
	};

	private boolean isFristRun() {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if (!isFirstRun) {
			return false;
		} else {
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}
		return true;
	}


}
