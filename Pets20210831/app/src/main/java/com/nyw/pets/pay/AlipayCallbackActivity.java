package com.nyw.pets.pay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.SaveAPPData;


/**
 * 支付宝支付回调
 */
public class AlipayCallbackActivity extends BaseActivity implements  View.OnClickListener{
    private TextView tv_return,tv_state,tv_money,tv_payType;
    //支付类型显示，1是支付宝，2是钱包支付
    private String payType="1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_results);
        initView();

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
        tv_payType=findViewById(R.id.tv_payType);



        SharedPreferences sharedPreferences=getSharedPreferences(SaveAPPData.PAY_MONEY,MODE_PRIVATE);
        String money=sharedPreferences.getString(SaveAPPData.MONEY,null);

        try {
            Bundle bundle = getIntent().getExtras();
            String state = bundle.getString("state", null);
            payType = bundle.getString("payType", null);


            tv_money.setText("- " + money);
            if (state.equals("0")) {
                tv_state.setText("支付成功");
                tv_state.setTextColor(getResources().getColor(R.color.black));
            } else {

                tv_state.setText("支付失败");
                tv_state.setTextColor(getResources().getColor(R.color.red));
            }

            if (payType.equals("1")){
                tv_payType.setText("支付宝");
            }else if (payType.equals("2")){
                tv_payType.setText("钱包支付");
            }

        }catch (Exception e){}


    }
    @Override
    public void onClick(View v) {

    }



}
