package com.nyw.pets.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends BaseActivity implements  View.OnClickListener , IWXAPIEventHandler {
    private IWXAPI api;
    private TextView tv_return,tv_state,tv_money;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_pay_results);
        initView();
        api = WXAPIFactory.createWXAPI(this, AppConfig.WENIN_APP_ID);
        api.registerApp(AppConfig.WENIN_APP_ID);
        api.handleIntent(getIntent(), this);

    }
    private void initView(){
        tv_return=findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_state=findViewById(R.id.tv_state);
        tv_money=findViewById(R.id.tv_money);
    }
    @Override
    public void onClick(View v) {

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        api.registerApp(AppConfig.WENIN_APP_ID);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //支付回调
        Log.d("payData","onPayFinish,errCode=");

        SharedPreferences sharedPreferences=getSharedPreferences(SaveAPPData.PAY_MONEY,MODE_PRIVATE);
        String money=sharedPreferences.getString(SaveAPPData.MONEY,null);

        if(money.indexOf(".") == -1)//如果没有小数点
        {
            money += ".00";//直接在后面补点并且加两个00
        }else {
            String [] array= money.split("\\.");
            if ( array[1].length()==1){
                money += "0";//直接在后面补点并且加两个00
            }
        }

        tv_money.setText("- "+money);
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            Log.d("payData","onPayFinish,errCode="+baseResp.errCode);
            int code = baseResp.errCode;
            SaveAPPData saveAPPData=new SaveAPPData();
            Log.i("payData",code+"");
            if (code == 0){

                saveAPPData.SavePayState(WXPayEntryActivity.this,"1");
                //支付成功
                Log.i("payData","支付成功");
                tv_state.setText("支付成功");
                tv_state.setTextColor(getResources().getColor(R.color.black));
            }else if (code == -1){
                saveAPPData.SavePayState(WXPayEntryActivity.this,"2");
                Log.i("payData","支付错误");
                ToastManager.showShortToast(WXPayEntryActivity.this,baseResp.errCode+"");
                tv_state.setText("支付失败  "+baseResp.errCode);
                tv_state.setTextColor(getResources().getColor(R.color.red));
            }else if (code == -2){
                saveAPPData.SavePayState(WXPayEntryActivity.this,"3");
                Log.i("payData","取消支付");
                tv_state.setText(" 取消支付");
            }
        }
    }


}
