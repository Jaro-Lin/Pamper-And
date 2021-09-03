package com.nyw.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.ChangePasswordActivity;
import com.nyw.pets.activity.user.ChangePayPasswordActivity;
import com.nyw.pets.activity.user.UserAgreementActivity;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.util.getVistionUtil;

public class SetupActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llt_userAgreement;
    private LinearLayout llt_loginOut,llt_changePassword,llt_appUpdate,llt_changePayPassword;
    private ImageView iv_hide;
    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initView();
    }

    private void initView() {
        llt_loginOut=findViewById(R.id.llt_loginOut);
        llt_loginOut.setOnClickListener(this);
        llt_changePassword=findViewById(R.id.llt_changePassword);
        llt_changePassword.setOnClickListener(this);
        llt_userAgreement=findViewById(R.id.llt_userAgreement);
        llt_userAgreement.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        llt_appUpdate=findViewById(R.id.llt_appUpdate);
        llt_appUpdate.setOnClickListener(this);
        tv_version=findViewById(R.id.tv_version);
        llt_changePayPassword=findViewById(R.id.llt_changePayPassword);
        llt_changePayPassword.setOnClickListener(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version.setText("当前版本："+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case  R.id.llt_loginOut:
                //退出登录
                getLoginOut();
                break;
            case R.id.llt_changePassword:
                //修改密码
                Intent intent = new Intent();
                intent.setClass(SetupActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.llt_userAgreement:
                //用户协议
                Intent userAgreement=new Intent();
                userAgreement.setClass(SetupActivity.this, UserAgreementActivity.class);
                startActivity(userAgreement);
                break;
            case R.id.iv_hide:
                finish();
                break;
            case R.id.llt_appUpdate:
                //版本更新
                new getVistionUtil(SetupActivity.this, ApiTest.VERSION).checkUpdate(true);
                break;
            case R.id.llt_changePayPassword:
                //修改支付密码
                Intent payChange=new Intent();
                payChange.setClass(SetupActivity.this, ChangePayPasswordActivity.class);
                startActivity(payChange);
                break;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    /**
     * 加载用户资料信息
     */
    private void getUserInfo(){
        String url= ApiTest.GET_USER_INFO;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
       String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(SetupActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfsfdfsf",data);
                        Gson gson=new Gson();
                        GetUserInfoUtil getUserInfoUtil=gson.fromJson(data, GetUserInfoUtil.class);
                        if (getUserInfoUtil.getCode()==AppConfig.SUCCESS){

                            llt_loginOut.setVisibility(View.VISIBLE);
                        }else {
                            llt_loginOut.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }
    /**
     * 退出登录
     */
    private void getLoginOut(){
        String url= ApiTest.LOGIN_OUT;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
       String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(SetupActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfsfdfsf",data);
                        Gson gson=new Gson();
                        GetUserInfoUtil getUserInfoUtil=gson.fromJson(data, GetUserInfoUtil.class);
                        if (getUserInfoUtil.getCode()==AppConfig.SUCCESS){
                            llt_loginOut.setVisibility(View.GONE);
                        }
//                        Toast.makeText(SetupActivity.this,getUserInfoUtil.getMsg(),Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        new AppNetUtil(SetupActivity.this).appInternetError();
                    }
                });
    }
}
