package com.nyw.pets.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.LoadDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 修改支付密码
 */
public class ChangePayPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_ok;
    private ClearEditText ct_phone,ct_input_code,ct_inputPassword;
    private Button btn_sendCode;
    private String phone,code,password;
    private int totime = 60;// 倒计时
    private LoadDialog loadDialogOk;
    private PayMoneyDialogUtil payUtil;
    private String payment_pwd, payid,randomstr,paysign,paytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pay_password);
        initView();

    }

    private void initView() {
        payUtil=new PayMoneyDialogUtil(ChangePayPasswordActivity.this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        ct_phone=findViewById(R.id.ct_phone);
        ct_input_code=findViewById(R.id.ct_input_code);
        ct_inputPassword=findViewById(R.id.ct_inputPassword);
        btn_sendCode=findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        loadDialogOk=new LoadDialog(ChangePayPasswordActivity.this,true,"正在修改…");
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_sendCode:
                //发送验证码
                phone=ct_phone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()!=11){
                    Toast.makeText(ChangePayPasswordActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
                break;
            case R.id.btn_ok:
                //修改支付密码
                payUtil.payDialog(1,"");
                payUtil.setCheckPayPasswordListener(new PayMoneyInterface() {
                    @Override
                    public void inputPasswordResult(String payPassword) {
                        //输入密码完成提交服务器
                        payment_pwd=new MD5Encoder().encode(payPassword);
                        changePayPassword();


                    }

                });

                break;
        }
    }
    /**
     * 修改支付密码
     */
    private void changePayPassword() {
        loadDialogOk.show();
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        phone=ct_phone.getText().toString().trim();
        code=ct_input_code.getText().toString().trim();
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token",token},
                {"code",code},
                {"user_name",phone},
                {"paypwd", payment_pwd},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sgin = new SignConfig().getSign(ChangePayPasswordActivity.this, key);
        Log.i("sdfsfsfsf", ApiTest.USER_PAYPWD_RESET);

        OkGo.<String>post(ApiTest.USER_PAYPWD_RESET).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("user_name",phone)
                .params("code",code)
                .params("paypwd",payment_pwd)
                .params("timestamp",time)
                .params("sign",sgin)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        Gson gson=new Gson();
                        loadDialogOk.cancel();
                        JSONObject obj=null;
                        int  code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("msg")+"");
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Toast.makeText(ChangePayPasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        loadDialogOk.cancel();
                        new AppNetUtil(ChangePayPasswordActivity.this).appInternetError();
                    }
                });
    }
    /**
     * 发送验证码
     */
    private void sendCode(){
        phone=ct_phone.getText().toString().trim();
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"user_name",phone},
                {"type", "3"},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sgin = new SignConfig().getSign(ChangePayPasswordActivity.this, key);

        OkGo.<String>post(ApiTest.SEND_PHONE_CODE).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("user_name",phone)
                .params("type","3")
                .params("timestamp",time)
                .params("sign",sgin)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        JSONObject obj=null;
                        String code=null;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code")+"");
                            message=(obj.getString("msg")+"");
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Toast.makeText(ChangePayPasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(ChangePayPasswordActivity.this).appInternetError();
                    }
                });
    }
    /**
     * 发送验证码按钮实现倒计时线程
     */
    private Thread sendcode = new Thread() {
        public void run() {
            try {

                Thread.sleep(1000);
                totime--;
                Message message = mhandler.obtainMessage();
                message.arg1 = totime;
                mhandler.sendMessage(message);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        };
    };
    /**
     * 更新发送验证码UI显示
     */
    Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            btn_sendCode.setText(msg.arg1 + " 秒重新获取");
            if (msg.arg1 > 0) {
                new Thread(sendcode).start();
                btn_sendCode.setClickable(false);
            } else {
                btn_sendCode.setText("获取验证码");
                totime = 60;
                btn_sendCode.setClickable(true);
            }

        };
    };
}
