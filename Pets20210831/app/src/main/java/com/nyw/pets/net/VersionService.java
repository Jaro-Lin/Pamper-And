package com.nyw.pets.net;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import com.nyw.pets.MainActivity;
import com.nyw.pets.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class VersionService extends Service {
	// notification 名字
	private String notify_name = "正在下载...";
	public static final String INTENT_URL = "url";
	private Context context = this;
	private static final int NOTIFY_ID = 0;
	private NotificationManager mNotificationManager;
	private Notification notification;
	/* 下载包安装路径 */
	private static final String savePath = Function_Utility.getUpgradePath();
	private  final String saveFileName = savePath + "hezuobuluo.apk";
	private String apkUrl;
	private int progress;
	boolean canceled;
	private Thread downLoadThread;



	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("DownloadService", "intent=" + intent.toString()
				+ " ;           flags= " + flags + " ;    startId" + startId);
		if (intent.hasExtra(VersionService.INTENT_URL)) {
			apkUrl = (String) intent.getExtras().get(VersionService.INTENT_URL);
		}
		progress = 0;
		setUpNotification();
		new Thread() {
			public void run() {
				// 开始下载
				startDownload();
			};
		}.start();

		return startId;

	};

	private void startDownload() {
		canceled = false;
		downloadApk();
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				// 下载完毕
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				// 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 1:
				int rate = msg.arg1;
				if (rate < 100) {
					RemoteViews contentview = notification.contentView;
					contentview.setTextViewText(R.id.tv_progress, rate + "%");
					contentview.setProgressBar(R.id.progressbar, 100, rate,
							false);
				} else {
					// 下载完毕后清除通知
					notification.flags = Notification.FLAG_AUTO_CANCEL;
					stopSelf();// 停掉服务自身
				}
				PendingIntent contentIntent2 = PendingIntent.getActivity(
						getApplicationContext(), 0, new Intent(),
						PendingIntent.FLAG_UPDATE_CURRENT);
				notification.contentIntent = contentIntent2;
				mNotificationManager.notify(NOTIFY_ID, notification);

				break;
			case 3:
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				RemoteViews contentView = new RemoteViews(getPackageName(),
						R.layout.update_download_notification_layout);
				contentView.setTextViewText(R.id.name, "下载失败");
				// 指定个性化视图
				notification.contentView = contentView;
//				Intent intent = new Intent(getApplicationContext(),
//						FacialMaskActivity.class);
//				PendingIntent contentIntent = PendingIntent.getActivity(
//						getApplicationContext(), 0, intent,
//						PendingIntent.FLAG_UPDATE_CURRENT);
//
//				// 指定内容意图
//				notification.contentIntent = contentIntent;
				mNotificationManager.cancel(NOTIFY_ID);
				mNotificationManager.notify(NOTIFY_ID, notification);

				stopSelf();// 停掉服务自身
				break;

			}
		}
	};

	/**
	 * 安装apk
	 * 
	 * @param
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
//		//开始显示新通知
//		Notification.Builder builder1 = new Notification.Builder(getApplicationContext());
//		builder1.setSmallIcon(R.mipmap.ic_launcher); //设置图标
////					builder1.setTicker("显示第二个通知");
//		builder1.setContentTitle(getResources().getString(R.string.app_name)); //设置标题
//		builder1.setContentText("下载完成"); //消息内容
//		builder1.setWhen(System.currentTimeMillis()); //发送时间
//		builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
//		builder1.setAutoCancel(true);//打开程序后图标消失
////		Intent intent =new Intent (getApplicationContext(),FacialMaskActivity.class);
////		PendingIntent pendingIntent =PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
////		builder1.setContentIntent(pendingIntent);
//		Notification notification1 = builder1.build();
//		mNotificationManager.notify(1, notification1); // 通过通知管理器发送通知
		if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
			Uri apkUri = FileProvider.getUriForFile(this, getPackageName(), apkfile);//在AndroidManifest中的android:authorities值
			Intent install = new Intent(Intent.ACTION_VIEW);
			install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			install.setDataAndType(apkUri, "application/vnd.android.package-archive");
			startActivity(install);

		} else{
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
					"application/vnd.android.package-archive");
			startActivity(i);
		}
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//				"application/vnd.android.package-archive");
//		mContext.startActivity(i);
	}

	private int lastRate = 0;
	private InputStream is = null;
	private FileOutputStream fos = null;

	/**
	 * 下载apk
	 * 
	 * @param
	 */
	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	private Runnable mdownApkRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
					}
					if (numread <= 0) {
						mHandler.sendEmptyMessage(0);// 下载完成通知安装
						// 下载完了，cancelled也要设置
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// 点击取消就停止下载.
//				Log.i("DownloadService----------canceled", canceled + "");
				fos.close();
				is.close();
			} catch (Exception e) {

				Message msg = mHandler.obtainMessage();
				msg.what = 3;
				mHandler.sendMessage(msg);
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					is.close();
					if (is != null) {
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	};

	/**
	 * 创建通知
	 */
	private void setUpNotification() {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
        	//安卓9.0发送通知
			Notification.Builder builder = null;
			RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.update_download_notification_layout);
			view_custom.setTextViewText(R.id.name, notify_name + getString(R.string.app_name));
			builder = new Notification.Builder(this, "chatChannelId");
			//setSmallIcon 必须添加否则不能在通知栏显示（Android 8.0）
			builder.setSmallIcon(R.mipmap.ic_logo)
					.setBadgeIconType(3)
					.setNumber(1)
					.setAutoCancel(true);
			Intent resultIntent = new Intent(this, MainActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(resultPendingIntent);
			notification = builder.setContent(view_custom).build();
			//setChannelId 必须添加否则不能在通知栏显示（Android 8.0）
			builder.setChannelId(getPackageName());
			//createNotificationChannel 和 NotificationChannel必须添加，否则  Android9.0  不显示
			NotificationChannel channel = new NotificationChannel(
					getApplication().getPackageName(),
					"通知栏消息",
					NotificationManager.IMPORTANCE_DEFAULT

			);
			mNotificationManager.createNotificationChannel(channel);


			notification = builder.setContent(view_custom).build();
//			mNotificationManager.notify((int) System.currentTimeMillis(), notification);
			mNotificationManager.notify(NOTIFY_ID, notification);
		}else {
			//先设定RemoteViews
			RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.update_download_notification_layout);
			view_custom.setTextViewText(R.id.name, notify_name + getString(R.string.app_name));
			Notification.Builder builder1 = new Notification.Builder(getApplicationContext());
			builder1.setSmallIcon(R.mipmap.ic_logo); //设置图标
			builder1.setWhen(System.currentTimeMillis()); //发送时间
			notification = builder1.setContent(view_custom).build();
			mNotificationManager.notify(NOTIFY_ID, notification); // 通过通知管理器发送通知
		}
	}




}
