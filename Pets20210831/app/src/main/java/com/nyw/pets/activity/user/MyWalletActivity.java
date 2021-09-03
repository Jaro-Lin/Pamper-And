package com.nyw.pets.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.view.LoadDialog;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_walletDetails,btn_withdrawal;
    private  GetUserInfoUtil getUserInfoUtil;
    private LoadDialog loadDiaShow;
    private TextView tv_money;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        initView();

    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_walletDetails=findViewById(R.id.btn_walletDetails);
        btn_walletDetails.setOnClickListener(this);
        btn_withdrawal=findViewById(R.id.btn_withdrawal);
        btn_withdrawal.setOnClickListener(this);
        tv_money=findViewById(R.id.tv_money);

        loadDiaShow=new LoadDialog(MyWalletActivity.this,true,"正在加载…");
        getUserInfo();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_walletDetails:
                //账单明细
                Intent walletDetails=new Intent();
                walletDetails.setClass(MyWalletActivity.this, WalletListActivity.class);
                startActivity(walletDetails);
                break;
            case R.id.btn_withdrawal:
                //提现
                Intent withdrawal=new Intent();
                withdrawal.setClass(MyWalletActivity.this,WithdrawalActivity.class);
                withdrawal.putExtra("money",money);
                startActivity(withdrawal);
                break;
        }
    }
    /**获取用户信息
     *
     */
    private void getUserInfo() {
        loadDiaShow.show();
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
        String sign = new SignConfig().getSign(MyWalletActivity.this, key);
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
                        if (getUserInfoUtil.getCode()== AppConfig.SUCCESS) {
                            //显示用户数据
//                            money=new GetPriceUtil().getPrice(getUserInfoUtil.getData().getInfo().getWallet()+"");
//                            tv_money.setText(new GetPriceUtil().getPrice(getUserInfoUtil.getData().getInfo().getWallet()+""));
                        }
                        loadDiaShow.cancel();
//                        Toast.makeText(UserInfoActivity.this, getUserInfoUtil.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDiaShow.cancel();
                        new AppNetUtil(MyWalletActivity.this).appInternetError();
                    }
                });
    }
}
