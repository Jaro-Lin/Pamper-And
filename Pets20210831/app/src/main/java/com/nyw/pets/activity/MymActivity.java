package com.nyw.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.CustomerServiceActivity;
import com.nyw.pets.activity.user.InviteFriendsActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.user.MyWalletActivity;
import com.nyw.pets.activity.user.UserInfoActivity;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.view.LoadDialog;
import com.nyw.pets.view.RoundImageView;

/**
 * 我的
 */
public class MymActivity extends BaseActivity implements View.OnClickListener {
    private RoundImageView rv_myPhoto;
    private String uid,token;
    private   GetUserInfoUtil getUserInfoUtil;
    private boolean isLogin=false;
    private TextView tv_name;
    private LoadDialog loadDiaShow;
    private LinearLayout llt_wallet,llt_inviteFriends,llt_setup,llt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();

    }

    private void initView() {
        rv_myPhoto=findViewById(R.id.rv_myPhoto);
        rv_myPhoto.setOnClickListener(this);
        tv_name=findViewById(R.id.tv_name);
        llt_wallet=findViewById(R.id.llt_wallet);
        llt_wallet.setOnClickListener(this);
        llt_inviteFriends=findViewById(R.id.llt_inviteFriends);
        llt_inviteFriends.setOnClickListener(this);
        llt_setup=findViewById(R.id.llt_setup);
        llt_setup.setOnClickListener(this);
        llt_phone=findViewById(R.id.llt_phone);
        llt_phone.setOnClickListener(this);



        loadDiaShow=new LoadDialog(MymActivity.this,true,"正在加载…");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.LOGIN_CODE && resultCode == AppConfig.LOGIN_CODE) {
            //登录成功，加载 用户资料
            getUserInfo();
        }
    }

    /**
     * 加载用户资料信息
     */
    private void getUserInfo(){
        loadDiaShow.show();
        String url= ApiTest.GET_USER_INFO;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(MymActivity.this, key);
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
                         getUserInfoUtil=gson.fromJson(data, GetUserInfoUtil.class);
                         if (getUserInfoUtil.getCode()==AppConfig.SUCCESS){
                             isLogin=true;
                             //显示头像和用户名称
                             setUserInfo();
                         }else {
                             tv_name.setText("未登录");
                             isLogin=false;
                             rv_myPhoto.setImageResource(R.mipmap.user_app_default);
                         }
                        loadDiaShow.cancel();


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDiaShow.cancel();
                    }
                });

    }

    /**
     * 显示用户信息
     */
    private void setUserInfo() {
//        tv_name.setText(getUserInfoUtil.getData().getInfo().getNick());
//        Glide.with(MymActivity.this).load(getUserInfoUtil.getData().getInfo().getMore().getPortrait())
//                .error(R.mipmap.user_app_default)
//                .placeholder(R.mipmap.user_app_default).into(rv_myPhoto);

    }



    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    public void onClick(View view) {
       int id= view.getId();
        switch (id){
            case R.id.rv_myPhoto:

                if (isLogin==false) {
                    //登录
                    Intent intent = new Intent();
                    intent.setClass(MymActivity.this, LoginActivity.class);
                    startActivityForResult(intent, AppConfig.LOGIN_CODE);
                }else {
                    //查看用户信息
                    Intent intent = new Intent();
                    intent.setClass(MymActivity.this, UserInfoActivity.class);
                    startActivityForResult(intent, AppConfig.HEAD_CODE);
                }
                break;

            case R.id.llt_wallet:
                //我的钱包
                if (isLogin==false) {
                    Intent intent = new Intent();
                    intent.setClass(MymActivity.this, LoginActivity.class);
                    startActivityForResult(intent, AppConfig.LOGIN_CODE);
                }else {
                    Intent wallet = new Intent();
                    wallet.setClass(MymActivity.this, MyWalletActivity.class);
                    startActivity(wallet);
                }
                break;

            case R.id.llt_inviteFriends:
                //邀请好友
                Intent invite=new Intent();
                invite.setClass(MymActivity.this, InviteFriendsActivity.class);
                startActivity(invite);
                break;
            case R.id.llt_setup:
                //设置
                Intent setup=new Intent();
                setup.setClass(MymActivity.this, SetupActivity.class);
                startActivity(setup);
                break;
            case R.id.llt_phone:
                //联系电话
                Intent intent=new Intent();
                intent.setClass(MymActivity.this, CustomerServiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
