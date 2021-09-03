package com.nyw.pets.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	/**
	 * 时间戳转北京日期时间
	 * @param dataTime
	 * @return
	 */
	public static String timeStamp(String dataTime){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	    long myTime= Long.parseLong(dataTime);
		long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
	    Date date = new Date(lt);
		String time=formatter.format(date);
		return time;
	}
	public static String timeBle(String dataTime){
		SimpleDateFormat formatter =new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
		long myTime= Long.parseLong(dataTime);
		long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
		Date date = new Date(lt);
		String time=formatter.format(date);
		return time;
	}
	public static String time(String dataTime){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
		long myTime= Long.parseLong(dataTime);
		long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
		Date date = new Date(lt);
		String time=formatter.format(date);
		return time;
	}

	/**
	 * 时间戳转北京日期
	 * @param dataTime
	 * @return
     */
	public static String timeStampDate(String dataTime){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		long myTime= Long.parseLong(dataTime);
		long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
		Date date = new Date(lt);
		String time=formatter.format(date);
		return time;
	}
	public static String timeStampAge(String dataTime){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		long myTime= Long.parseLong(dataTime);
		long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
		Date date = new Date(lt);
		String time=formatter.format(date);
		return time;
	}

}
