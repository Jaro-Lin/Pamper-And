package com.nyw.pets.net.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class AppNetUtil {
    private Activity activity;
    private Context context;
    private int type=0;
    public AppNetUtil(Activity activity){
        this.activity=activity;
        type=1;
    }
    public AppNetUtil(Context context){
        this.context=context;
        type=2;
    }

    public void appInternetError(){
        if (type==1) {
            Toast.makeText(activity, "网络错误，请检查手机网络连接状态", Toast.LENGTH_SHORT).show();
        }else if (type==2) {
            Toast.makeText(context, "网络错误，请检查手机网络连接状态", Toast.LENGTH_SHORT).show();
        }
    }

}
