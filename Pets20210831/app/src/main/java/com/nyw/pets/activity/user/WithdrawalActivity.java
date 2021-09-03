package com.nyw.pets.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.interfaces.PayMoneyInterface;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.net.util.MD5Encoder;
import com.nyw.pets.pay.PayMoneyDialogUtil;
import com.nyw.pets.util.GetPaySignUtil;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提现
 */
public class WithdrawalActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private TextView tv_money,ct_inputName,ct_inputPay,ct_inputMoney;
    private Button btn_ok;
    private String money,myMoney,payID,no_name;
    private PayMoneyDialogUtil payUtil;
    private String payment_pwd, payid,randomstr,paysign,paytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        initView();
    }
    private void initView() {
        payUtil=new PayMoneyDialogUtil(WithdrawalActivity.this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_money=findViewById(R.id.tv_money);
        ct_inputName=findViewById(R.id.ct_inputName);
        ct_inputPay=findViewById(R.id.ct_inputPay);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        ct_inputMoney=findViewById(R.id.ct_inputMoney);
        try{
            Bundle bundle=getIntent().getExtras();
            money=bundle.getString("money");
            tv_money.setText("当前可提现额度： "+money);
        }catch (Exception e){}

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_ok:
                //提现
                no_name=ct_inputName.getText().toString();
                payID=ct_inputPay.getText().toString();
                myMoney=ct_inputMoney.getText().toString();
                if (TextUtils.isEmpty(no_name)||TextUtils.isEmpty(payID)||TextUtils.isEmpty(myMoney)){
                    ToastManager.showLongToast(WithdrawalActivity.this,"请你输入真实姓名、支付宝账号以及提现金额");
                    return;
                }else if (Double.parseDouble(myMoney)>Double.parseDouble(money)){
                    ToastManager.showLongToast(WithdrawalActivity.this,"可提现金额不足，请您稍后在试");
                    return;
                }
                payUtil.payDialog(1,"");
                payUtil.setCheckPayPasswordListener(new PayMoneyInterface() {
                    @Override
                    public void inputPasswordResult(String payPassword) {
                        //输入密码完成提交服务器
                        payment_pwd=new MD5Encoder().encode(payPassword);
                        sendWithdrawal();


                    }

                });

                break;
        }
    }

    /**
     * 提现
     */
    private void sendWithdrawal() {

        String url= ApiTest.SEND_MONEY_WITHDRAWAL;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();

        float  money=Float.parseFloat(myMoney)*100;
        String wallet=String.valueOf(money);

        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"no_name", no_name},
                {"money", wallet},
                {"no", payID},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(WithdrawalActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("no_name",no_name)
                .params("money",wallet)
                .params("no",payID)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfkklghgsfdfsf",data);
                        Gson gson=new Gson();
                        GetPaySignUtil getPaySignUtil=gson.fromJson(data, GetPaySignUtil.class);
                        if (getPaySignUtil.getCode()== AppConfig.SUCCESS) {
                            //提交到服务器成功,拿到签名信息，接下来执行钱包支付
                            payid=getPaySignUtil.getData().getPayid();
                            paysign=getPaySignUtil.getData().getPaysign();
                            randomstr=getPaySignUtil.getData().getRandomstr();
                            paytime=getPaySignUtil.getData().getPaytime()+"";
                            sendPay();

                        }
//                        Toast.makeText(WithdrawalActivity.this, getPaySignUtil.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        new AppNetUtil(WithdrawalActivity.this).appInternetError();
                    }
                });
    }

    /**
     * 发送请求支付
     */
    private void sendPay() {
        String url= ApiTest.MONEY_PAY_PWD_CONFIRM;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        Log.i("sfsdfkklghgsfdfsf",paysign);
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"payid", payid},
                {"randomstr", randomstr},
                {"paysign", paysign},
                {"paytime", paytime},
                {"paypwd", payment_pwd},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(WithdrawalActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("payid",payid)
                .params("randomstr",randomstr)
                .params("paysign",paysign)
                .params("paytime",paytime)
                .params("paypwd",payment_pwd)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("jljlghgsfdfsf",data);
                        JSONObject jsonObject=null;
                        int  code=11111;
                        String msg=null;
                        try {
                             jsonObject=new JSONObject(data);
                             code=jsonObject.getInt("code");
                            msg=jsonObject.getString("msg");
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(WithdrawalActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        new AppNetUtil(WithdrawalActivity.this).appInternetError();
                    }
                });
    }
}
