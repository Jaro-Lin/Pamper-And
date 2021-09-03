package com.nyw.pets.net.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知工具类
 * Created by Administrator on 2016/12/10.
 */

public class NotificationUtil {
    private NotificationManager mNotificationManager=null;
    private Map<Integer,Notification> mNotification=null;
    public NotificationUtil(Context context){
        //获得通知系统服务
        mNotificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    //创建通知集合
        mNotification=new HashMap<Integer,Notification>();
    }
//    public void showNotification(FileInfo fileInfo){
//        if (mNotification.containsKey(fileInfo.getId()));
//    }
}
