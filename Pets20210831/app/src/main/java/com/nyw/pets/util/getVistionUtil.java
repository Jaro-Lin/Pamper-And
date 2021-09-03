package com.nyw.pets.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.net.VersionService;
import com.nyw.pets.view.MyVersionDialog;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class getVistionUtil {
    private String versionCode;//版本号
    private String versionName;//版本名称
    private String versoinMessage;//版本更新内容
    private String versoinPath;//下载地址
    private String forceUpdate;//是否要强制更新。1是强制更新，0则反
    private Activity activity;
    private Context context;
    private String updateUrl=null;
    //6.0系统版本更新需要添加权限判断
    private static int REQUESTPERMISSION = 110 ;
    private GetAppVersionUtil getAppVersionUtil;
    MyApplication myApplication;
    public  boolean isUpdate=false;

    public getVistionUtil(Activity activity, String url){
        this.activity=activity;
        this.updateUrl=url;
        myApplication= (MyApplication) activity.getApplication();
    }
    public getVistionUtil(Context context, String url){
        this.context=context;
        this.updateUrl=url;
        myApplication= (MyApplication) ((Activity)context).getApplication();

    }
        /**
         * 检测软件是否需要更新
         */
        public void checkUpdate(final boolean isShow) {
            String url= Api.GET_OTHER_APP_VERSION;
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
            String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
            String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
            Log.i("sfsdfsfdfsf",uid+"   "+token);
//            String time = new TimeStampUtil().getTimeStamp();
//            String[][] str = {{"app_key", AppConfig.APP_KEY},
//                    {"master_secret",AppConfig.MASTER_SECRET},
//                    {"uid",uid},
//                    {"token", token},
//                    {"type", "android"},
//                    {"timestamp", time}};
//            String key = new InitialSort().getKey(str);
//            String sign = new SignConfig().getSign(activity, key);
            VersionData versionData=new VersionData();
            versionData.setVersion("new");
            Gson gson=new Gson();
            String version=gson.toJson(versionData);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), version);
            OkGo.<String>post(url).tag(this)
                    .upRequestBody(body)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String data=response.body();
                            Log.i("sfsdfsfdDSFSDFSFfsf",data);
                            Gson gson=new Gson();
                            getAppVersionUtil= gson.fromJson(data, GetAppVersionUtil.class);
                            versionCode=String.valueOf(( toVistion(String.valueOf(getAppVersionUtil.getData().getData().getVersion()))));
                            versionName = getAppVersionUtil.getData().getData().getVersion_name();
                            versoinMessage = getAppVersionUtil.getData().getData().getDesc();
                            versoinPath= myApplication.getImgFilePathUrl()+getAppVersionUtil.getData().getData().getUrl();
                            forceUpdate=String.valueOf(getAppVersionUtil.getData().getData().getForce());
                             isUpdate();
                            upadpdAPK(isShow);

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });

    }

    /**
     * 版本号计算
     * @param data
     * @return
     */
    private int toVistion(String data){
            //默认100版本号
        int vistion=100;
        if (data!=null) {
            vistion = Integer.parseInt(data.replace(".", ""));
        }
        return vistion;
    }
    private boolean isupdateVersion = false;

    public boolean isIsupdateVersion() {
        return isupdateVersion;
    }

    public void setIsupdateVersion(boolean isupdateVersion) {
        this.isupdateVersion = isupdateVersion;
    }
    /**
     * 与本地版本号比较是否需要更新
     */
    public boolean isUpdate() {
        double serverVersion = Double.parseDouble(versionCode);
        double localversion = 1;
        try {
            if (activity!=null) {
                localversion = activity.getPackageManager()
                        .getPackageInfo(activity.getPackageName(), 0).versionCode;
            }else {
                localversion = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0).versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (serverVersion > localversion) {
            myApplication.setUpdateApp(true);
            return true;
        } else {
            myApplication.setUpdateApp(false);
            return false;
        }

    }


    /**
     *检测是否执行更新版本
     */
    private void upadpdAPK(boolean isShow){
        try {
            if (isUpdate()==true) {
                MyVersionDialog dialog = new MyVersionDialog(
                        activity);
                dialog.setMessage(versionName,versionCode,versoinMessage,forceUpdate);
                dialog.setDialogCallback(dialogcallback);
                dialog.show();
//				ToastManager.showShortToast(FacialMainActivity.this,"999999666");
            } else {
                if (isShow==true) {
                    Toast.makeText(activity, "已经是最新版本",
                            Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){

        }

    }
    /**
     * 下载更新版本
     *
     * @param
     * @throws Exception
     */
    public void download(String url) throws Exception {
        boolean isContext;
        if (activity!=null){
            isContext=true;
        }else {
            isContext=false;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            Intent service = new Intent(isContext?activity:context, VersionService.class);
            service.putExtra(VersionService.INTENT_URL, url);
            if(ContextCompat.checkSelfPermission(isContext?activity:context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //adnroid 6.0申请权限才可以下载APP
                if (activity!=null){
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);

                }else {
                    ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
                }
            }else{
                if (activity!=null){
                    activity.startService(service);
                }else {
                    context.startService(service);
                }
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        }
    }
    /**
     * 设置mydialog更新对话框　需要处理的事情
     */
    MyVersionDialog.Dialogcallback dialogcallback = new MyVersionDialog.Dialogcallback() {
        @Override
        public void dialogdo(String string) {
            // txt_updata_message.setText(string);
        }

        @Override
        public void updata() {
            try {
                download(versoinPath);
//                download("http://rongtaijt.com/bipp_backup/download/app.apk");
                if (activity!=null) {
                    Toast.makeText(activity, R.string.downloadMyApp, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, R.string.downloadMyApp, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void exit() {
            //强制更新，如果用户不更新就直接退出程序
            if (activity!=null) {
                activity.finish();
                Toast.makeText(activity, R.string.updateExit, Toast.LENGTH_LONG).show();
            }else {
                ((Activity)context).finish();
                Toast.makeText(context, R.string.updateExit, Toast.LENGTH_LONG).show();
            }
        }

    };
}
