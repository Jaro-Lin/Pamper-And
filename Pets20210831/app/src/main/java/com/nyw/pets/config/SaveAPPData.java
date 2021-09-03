package com.nyw.pets.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SaveAPPData {
	//用户id和token标志
	public  static String USER_ID_Token = "user_id_token";
	//账号标志 
	public  static String USER_ACCOUNT = "account";
	//头像标志
	public  static String PHOTO = "photo";
	//头像路径
	public  static String PATH = "path";
	//账号ID
	public  static String USER_ID = "userID";
	//用户token
	public  static String TOKEN = "token";
	//用户名
	public  static String NAME = "name";
	//密码
	public  static String PASSWORD = "password";
	//保存是否要启动APP红包领取界面标志位
	public  static String WELCOME = "welcome";
	//保存是否要启动APP红包领取界面
	public  static String WELCOME_CODE = "welcome_code";
	// 启动红包领取界面
	public  static String WELCOME_START = "1";
	// 关闭纪领取界面
	public  static String WELCOME_STOP = "0";
	//保存水度与油度值，然后取出计算差距
	public  static String WATER_AND_OIL = "WATER_AND_OIL";
	//水度
	public  static String WATER = "WATER";
	//油度
	public  static String OIL = "OIL";
	//MAC 地址
	public static String DEVICE_MAC="device_mac";
	//保存mac 地址标志
	public static String SAVE_DEVICE_MAC="save_device_mac";
	//调节面膜福度大小值
	public static  String 	USER_LEVEL="user_level";
	//面膜福度值
	public static String LEVEL="level";
	//退款原因
	public static String REASON = "reason";
	//城市定位
	public static  String MAP_LOCATION_CONFIG="MAP_LOCATION_CONFIG";
	//定位城市
	public static  String ADRESS_LOCATION_CITY="ADRESS_LOCATION_CITY";
	//本地音乐与在线音乐切换
	public static String ON_LINE_AND_LOCAL_MUSIC="ON_LINE_AND_LOCAL_MUSIC";
	public static String ON_LINE_AND_LOCAL_MUSIC_PLAY="ON_LINE_AND_LOCAL_MUSIC_PLAY";
	//保存支付金额
	public static String PAY_MONEY="PAY_MONEY";
	public static String MONEY="MONEY";
	//出租或租用打开方式
	public static String LEASE_AND_RENT_TITLE="LEASE_AND_RENT_TITLE";
	public static String LEASE_AND_RENT="LEASE_AND_RENT";
	//保存支付状态
	public static String PAY_STATE_TITLE="PAY_STATE_TITLE";
	public static String PAY_STATE="PAY_STATE";


	/**
	 * 保存支付状态
	 * @param context
	 * @param state
	 */
	public static void SavePayState(Context context, String state) {
		SharedPreferences sp = context.getSharedPreferences(PAY_STATE_TITLE,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(PAY_STATE, state);
		et.commit();// 提交
	}

	/**
	 * 保存出租或租用打开方式
	 * @param context
	 * @param type
	 */
	public static void SaveLeaseAndRent(Context context, String type) {
		SharedPreferences sp = context.getSharedPreferences(LEASE_AND_RENT_TITLE,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(LEASE_AND_RENT, type);
		et.commit();// 提交
	}

	/**
	 * 保存支付金额，用于支付回调后显示实际金额
	 * @param context
	 * @param money
	 */
	public static void SavePayMoney(Context context, String money) {
		SharedPreferences sp = context.getSharedPreferences(PAY_MONEY,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(MONEY, money);
		et.commit();// 提交
	}
	/**
	 * 保存用户名和密码到SD卡
	 * 
	 * @param context
	 *            获取SD卡路途
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 * @return true是保存成功，flase时保存失败
	 */
	public static boolean SaveUserInfo(Context context, String name,
									   String password) {
		try {
			// 方法一 路途写死了，不好使用
			// File file = new File("/data/data/Info.txt");
			// FileOutputStream fos = new FileOutputStream(file);
			// 方法二 任何程序都可以访问读取或更改，安全性差
			// File file = new File(context.getCacheDir(), "Info.txt");
			// FileOutputStream fos = new FileOutputStream(file);
			// 方法三，参数 文件名+访问权限
			// 判断SD卡是否存在，SD存在保存到SD卡，如果SD卡不存在 就保存 到手机内存
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				// Environment.getExternalStorageDirectory()自动获取SD卡位置
				File file = new File(Environment.getExternalStorageDirectory(),
						"Info.txt");
				FileOutputStream fos = new FileOutputStream(file);
				fos.write((name + "##" + password).getBytes());
				fos.close();
			} else {
				FileOutputStream fos = context.openFileOutput("Info.txt",
						Context.MODE_PRIVATE);
				fos.write((name + "##" + password).getBytes());
				fos.close();
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取用户名和密码进行登录
	 * @param context 是获取用户名和密码存储路途
	 * @return  如果SD卡存在就保存数据到SD卡，否则保存到手机内存卡,返回null表示当前没有用户或密码
     */
	public static Map<String, String> GetSaveUserInfo(Context context) {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				File file = new File(Environment.getExternalStorageDirectory(),
						"Info.txt");
				FileInputStream fos;
				fos = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fos));
				String str = br.readLine();
				String[] info = str.split("##");
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", info[0]);
				map.put("password", info[1]);

				return map;
			} else {
				File file = new File(context.getCacheDir(), "Info.txt");
				FileInputStream fos;
				fos = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fos));
				String str = br.readLine();
				String[] info = str.split("##");
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", info[0]);
				map.put("password", info[1]);

				return map;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 以上保存在BUG，使用官方API实现保存用户名和密码如下：
	/**
	 * 使用官方API SharedPreferences存储数据方式 实现保存用户名和密码
	 * 
	 * @param context
	 * @param name
	 * @param password
	 * @param
	 * @param
	 */
	public static void SaveUserConfig(Context context, String name,
									  String password) {
		SharedPreferences sp = context.getSharedPreferences(USER_ACCOUNT,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(NAME, name);
		et.putString(PASSWORD, password);
		et.commit();// 提交
	}

	/**
	 * 保存用户 ID、用户token
	 * 
	 * @param context
	 * @param userID
	 * @param token
	 */
	public static void SaveUserID(Context context, String userID, String token) {
		SharedPreferences sp = context.getSharedPreferences(USER_ID_Token,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(USER_ID, userID);
		et.putString(TOKEN, token);
		et.commit();// 提交
	}

	/**
	 * 保存本地音乐与在线播放轻音乐选择，默认播放在线轻音乐
	 * @param context
	 * @param play  当为1时为在线音乐，则为本地音乐
	 */
	public static void localAndOnLine(Context context, String play) {
		SharedPreferences sp = context.getSharedPreferences(ON_LINE_AND_LOCAL_MUSIC,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(ON_LINE_AND_LOCAL_MUSIC_PLAY, play);
		et.commit();// 提交
	}
	/**
	 * 保存用户头像地址
	 * 
	 * @param context
	 * @param path
	 */
	public static void SavePhoto(Context context, String path) {
		SharedPreferences sp = context.getSharedPreferences(PHOTO,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(PATH, path);
		et.commit();// 提交
	}

	/**
	 * 保存城市定位信息
	 * @param context
	 * @param adress
	 */
	public static void AdressLocation(Context context, String adress) {
		SharedPreferences sp = context.getSharedPreferences(MAP_LOCATION_CONFIG,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(ADRESS_LOCATION_CITY, adress);
		et.commit();// 提交
	}



	/**
	 * 保存启动界面红包页面状态
	 * 
	 * @param context
	 * @param welcomeCode
	 */
	public static void SaveWelcomeCode(Context context, String welcomeCode) {
		SharedPreferences sp = context.getSharedPreferences(WELCOME,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(WELCOME_CODE, welcomeCode);
		et.commit();// 提交
	}
	/**
	 * 保存水度与油度值，然后取出计算使用前与使用后的差距，以方便提示用户水份提升多少
	 * @param context
	 * @param water
	 * @param oil
	 */
	public static void saveWaterAndOilData(Context context, String water, String oil) {
		SharedPreferences sp = context.getSharedPreferences(WATER_AND_OIL,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(WATER, water);
		et.putString(OIL, oil);
		et.commit();// 提交
	}
	/**
	 * 保存用户ID，还有当前用户面膜工作福度等级
	 * @param context
	 * @param userID
	 * @param level
	 */
	public static void SaveLevelID(Context context, String userID, String level) {
		SharedPreferences sp = context.getSharedPreferences(USER_LEVEL,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(USER_ID, userID);
		et.putString(LEVEL, level);
		et.commit();// 提交
	}
	/**
	 * 保存mac地址
	 * @param context
	 * @param mac
	 */
	public static void SaveMac(Context context, String mac) {
		SharedPreferences sp = context.getSharedPreferences(SAVE_DEVICE_MAC,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(DEVICE_MAC, mac);
		et.commit();// 提交
	}
	/**
	 * 清空SharedPreferences存储数据
	 */
	public static void remoreuser(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(USER_ID_Token,
				Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}
	/*
	退款原因
	 */
	public static void spReason(Context context, String reason) {
		SharedPreferences sp = context.getSharedPreferences(REASON,
				Context.MODE_PRIVATE);
		Editor et = sp.edit();// 得到一个编辑器
		et.putString(REASON, reason);
		et.commit();// 提交
	}
}
