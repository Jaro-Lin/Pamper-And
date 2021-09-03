package com.nyw.pets;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.nyw.pets.activity.BaseTabActivity;
import com.nyw.pets.activity.IndexActivity;
import com.nyw.pets.activity.MyAppInfoActivity;
import com.nyw.pets.activity.CurriculumActivity;
import com.nyw.pets.activity.ShopActivity;
import com.nyw.pets.activity.RecordActivity;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends BaseTabActivity {
	private String versionCode;//版本号
	private String versionName;//版本名称
	private String versoinMessage;//版本更新内容
	private String versoinPath;//下载地址
	private String forceUpdate;//是否要强制更新。1是强制更新，0则反
	private MyApplication APP;
	private TabHost tabhost;
	private RadioGroup main_radiogroup;
	//6.0系统版本更新需要添加权限判断
	private static int REQUESTPERMISSION = 110 ;
	private String shopingID,uid;
	@SuppressWarnings("unused")
	private RadioButton tab_map, tab_destination, tab_Realtime_line,
			tab_Personal_center,tab_Realtime_shop;

	//p安卓8定位
	private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
	private NotificationManager notificationManager = null;
	boolean isCreateChannel = false;
	private MyApplication myApplication;


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
		myApplication= (MyApplication) getApplication();
		setContentView(R.layout.layout_main);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// 解决屏幕在切换过程中闪屏问题
		Bundle bundle=getIntent().getExtras();

		try {
			uid=(String)bundle.get("uid");
			Log.i("dfdsfsdfsdfkklloo",uid);
		}catch (Exception e){

		}
		authorizationLocation();
		intitView();


	}
	/**
	 * 初始化所有控件
	 */
	private void intitView() {
		Intent in = getIntent();
		main_radiogroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		tab_map = (RadioButton) findViewById(R.id.tab_map);
		tab_destination = (RadioButton) findViewById(R.id.tab_destination);
//		tab_Realtime_line = (RadioButton) findViewById(R.id.tab_Realtime_line);
		tab_Personal_center = (RadioButton) findViewById(R.id.tab_Personal_center);
//		tab_Realtime_shop=findViewById(R.id.tab_Realtime_shop);
		// 往TabWidget添加Tab
		tabhost = getTabHost();
		tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0")
				.setContent(new Intent(this, IndexActivity.class)));
		tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1")
				.setContent(new Intent(this, RecordActivity.class)));
		tabhost.addTab(tabhost.newTabSpec("tag3").setIndicator("2")
				.setContent(new Intent(this, CurriculumActivity.class)));
//		tabhost.addTab(tabhost.newTabSpec("tag4").setIndicator("3")
//				.setContent(new Intent(this, ShopActivity.class)));
		tabhost.addTab(tabhost.newTabSpec("tag4").setIndicator("3")
				.setContent(new Intent(this, MyAppInfoActivity.class)));
		MyBtnOnclick mMyBtnOnclic = new MyBtnOnclick();
		main_radiogroup.setOnCheckedChangeListener(mMyBtnOnclic);



	}



	/**
	 * 用户授权定位
	 */
	private void authorizationLocation(){
		//这里以ACCESS_COARSE_LOCATION为例
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
				!= PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
//			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//				ToastManager.showShortToast(this, "您已经拒绝过一次");
//			}
			ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
					Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
					Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
					Manifest.permission.CAMERA}, 2);
		}else {
			//6.0系统以下的
//			new LocationUtil(this,this).initLocation();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//可在此继续其他操作。开始定位
		switch (requestCode){
			case 2:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//					new LocationUtil(this, this).initLocation();
				}else {
					// 没有获取到权限，做特殊处理
					Toast.makeText(getApplicationContext(), "获取权限失败，可能部分功能无法正常使用，请手动开启", Toast.LENGTH_SHORT).show();
				}
				break;
		}

	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		if (myApplication.isOpenShop==true){
			myApplication.setOpenShop(false);
			Log.i("kmhgyuinbfgh","iiy9996666");
					tabhost.setCurrentTab(3);
					main_radiogroup.check(R.id.tab_Realtime_shop);

		}

	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

	}
	/**
	 * 用linearlayout作为按钮的监听事件
	 * */
	private class MyBtnOnclick implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.tab_map:
				tabhost.setCurrentTab(0);
				break;
			case R.id.tab_destination:
				tabhost.setCurrentTab(1);
				break;
			case R.id.tab_Realtime_line:
				tabhost.setCurrentTab(2);
				break;
//				case R.id.tab_Realtime_shop:
//					tabhost.setCurrentTab(3);
//					break;
			case R.id.tab_Personal_center:
				tabhost.setCurrentTab(3);
				break;
			default:
				break;

			}

		}
	}

}
