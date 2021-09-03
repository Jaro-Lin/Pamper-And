package com.nyw.pets.activity.user;

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
import com.nyw.pets.activity.util.GetRegisterUtil;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.net.util.MD5Encoder;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.LoadDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_ok;
    private ClearEditText ct_phone,ct_input_code,ct_inputPassword;
    private ImageView iv_hide;
    private Button btn_sendCode;
    private String phone,code,password;
    private int totime = 60;// 倒计时
    private LoadDialog loadDialogOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }
    private void initView() {
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_phone=findViewById(R.id.ct_phone);
        ct_input_code=findViewById(R.id.ct_input_code);
        ct_inputPassword=findViewById(R.id.ct_inputPassword);
        btn_sendCode=findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        loadDialogOk=new LoadDialog(ChangePasswordActivity.this,true,"正在修改…");



    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_ok:
                //修改密码
                forgotPassword();
                break;
            case R.id.btn_sendCode:
                //发送验证码
                phone=ct_phone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()!=11){
                    Toast.makeText(ChangePasswordActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
                break;
        }
    }

    /**
     * 修改密码
     */
    private void forgotPassword() {
        loadDialogOk.show();
        phone=ct_phone.getText().toString().trim();
        code=ct_input_code.getText().toString().trim();
        password=new MD5Encoder().encode(ct_inputPassword.getText().toString().trim());
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"user_name",phone},
                {"code",code},
                {"user_pwd", password},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sgin = new SignConfig().getSign(ChangePasswordActivity.this, key);

        OkGo.<String>post(ApiTest.USER_PASSWORD_UPDATE).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("user_name",phone)
                .params("code",code)
                .params("user_pwd",password)
                .params("timestamp",time)
                .params("sign",sgin)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        Gson gson=new Gson();
                        GetRegisterUtil getLoginUtil=gson.fromJson(data, GetRegisterUtil.class);
                        int code=getLoginUtil.getCode();
                        if (code==AppConfig.SUCCESS){
                            SaveAPPData saveAPPData=new SaveAPPData();
                            saveAPPData.SaveUserConfig(ChangePasswordActivity.this,phone,ct_inputPassword.getText().toString());
                            finish();
                        }
                        loadDialogOk.cancel();
                        Toast.makeText(ChangePasswordActivity.this,getLoginUtil.getMsg(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        loadDialogOk.cancel();
                        new AppNetUtil(ChangePasswordActivity.this).appInternetError();
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
                {"type", "2"},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sgin = new SignConfig().getSign(ChangePasswordActivity.this, key);

        OkGo.<String>post(ApiTest.SEND_PHONE_CODE).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("user_name",phone)
                .params("type","2")
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
                        Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(ChangePasswordActivity.this).appInternetError();
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
