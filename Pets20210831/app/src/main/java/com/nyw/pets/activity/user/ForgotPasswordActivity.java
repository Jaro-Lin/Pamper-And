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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.net.util.MD5Encoder;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.LoadDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 忘记密码
 */
public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_ok,btn_return;
    private ClearEditText ct_phone,ct_input_code,ct_inputPassword,ct_input2Password;
    private ImageView iv_hide;
    private Button btn_sendCode;
    private String phone,code,password,again_password;
    private int totime = 60;// 倒计时
    private LoadDialog loadDialogOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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
        btn_return=findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        ct_input2Password=findViewById(R.id.ct_input2Password);
        loadDialogOk=new LoadDialog(ForgotPasswordActivity.this,true,"正在修改…");



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
                    Toast.makeText(ForgotPasswordActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
                break;
            case R.id.btn_return:
                //返回登录
                finish();
                break;
            case R.id.iv_hide:
                //返回登录
                finish();
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
        again_password=new MD5Encoder().encode(ct_input2Password.getText().toString().trim());
        if (!ct_inputPassword.getText().toString().trim().equals(ct_input2Password.getText().toString().trim())){
            ToastManager.showShortToast(ForgotPasswordActivity.this,"你输入的密码不一致，请重新输入确定");
            loadDialogOk.cancel();
            return;
        }

        OkGo.<String>post(Api.USER_ABOUT_USER_FORGET_PASSWORD).tag(this)
                .params("phone",phone)
                .params("code",code)
                .params("password",password)
                .params("again_password",again_password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            msg=  jsonObject.getString("message");
                            code=  jsonObject.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code==AppConfig.SUCCESS){
                            SaveAPPData saveAPPData=new SaveAPPData();
                            saveAPPData.SaveUserConfig(ForgotPasswordActivity.this,phone,ct_inputPassword.getText().toString());
                            finish();
                        }
                        loadDialogOk.cancel();
                        Toast.makeText(ForgotPasswordActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        loadDialogOk.cancel();
                        new AppNetUtil(ForgotPasswordActivity.this).appInternetError();
                    }
                });
    }

    /**
     * 发送验证码
     */
    private void sendCode(){
        phone=ct_phone.getText().toString().trim();

        OkGo.<String>post(Api.SEND_CODE).tag(this)
                .params("username",phone)
                .params("type","2")
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
                        Toast.makeText(ForgotPasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(ForgotPasswordActivity.this).appInternetError();
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
