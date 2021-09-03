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
import com.nyw.pets.activity.util.GetWechatLoginUtil;
import com.nyw.pets.activity.util.RegSetData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 邦定手机号码
 */
public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_sendCode;
    private ClearEditText ct_inputPhone;
    private int totime = 60;// 倒计时
    private ClearEditText ct_inputCode;
    private Button btn_ok;
    private String uid;
    private boolean isLoginType;//默认1是微信登录的
    //第三方登录信息
    private String uuidName="uuid";//参数名
    private String name;
    private String mySex,gender;
    private String iconurl;
    private String loginUrl;
    private String myUid;
    //登录类型  qq   wechat
    private String loginType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        initView();
    }
    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        try {
            Bundle bundle = getIntent().getExtras();
            loginType =bundle.getString("type",null);
             uid = bundle.getString("uuid", null);
            isLoginType = bundle.getBoolean("loginType");

            uuidName=bundle.getString("uuidName",null);
            myUid= bundle.getString("uid",null);
            name=bundle.getString("name",null);
            mySex=bundle.getString("mySex",null);
            iconurl=bundle.getString("iconurl",null);
            loginUrl= bundle.getString("url",null);





        }catch (Exception e){}
        iv_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_sendCode=findViewById(R.id.btn_sendCode);
        ct_inputPhone=findViewById(R.id.ct_inputPhone);
        btn_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=ct_inputPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()!=11){
                    Toast.makeText(BindPhoneActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
            }
        });
        ct_inputCode=findViewById(R.id.ct_inputCode);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }
    /**
     * 发送验证码
     */
    private void sendCode() {
        String phone=ct_inputPhone.getText().toString().trim();

        OkGo.<String>post(Api.SEND_CODE).tag(this)
                .params("username",phone)
                .params("type","4")
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
                        Toast.makeText(BindPhoneActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(BindPhoneActivity.this).appInternetError();
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

    /**
     * 邦定手机
     */
    private void bindPhone() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
//        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
//        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        String phone=ct_inputPhone.getText().toString().trim();
        String code=ct_inputCode.getText().toString().trim();
        String url=Api.GET_USER_OAUTH_REG;
        if (isLoginType==true){
             url=Api.GET_USER_OAUTH_REG;
            uuidName="uuid";
        }else {
             url=Api.GET_USER_OAUTH_REG;
            uuidName="openid";
        }



        RegSetData regSetData=new RegSetData();
        regSetData.setAvatar(iconurl);
        regSetData.setDevice_type("android");
        regSetData.setNickname(name);
        regSetData.setOpen_id(myUid);
        regSetData.setPhone(ct_inputPhone.getText().toString());
        regSetData.setSex(mySex);
        regSetData.setType(loginType);
        regSetData.setVerify_code(ct_inputCode.getText().toString());

        String sendData= new Gson().toJson(regSetData);

        Log.i("ldfmsifsmfsfsrtyty",sendData+"登录类型是    "+loginType+"    性别"+mySex);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendData);

        OkGo.<String>post(url).tag(this)
                .upRequestBody(body)
                .params("code",code)
                .params(uuidName,uid)
                .params("phone",phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.d("datammmm", data);
                        int code=1;
                        String msg=null;
                        JSONObject jsonObject=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=jsonObject.getInt("code");
                            msg=jsonObject.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Gson gson=new Gson();
                        if (code==AppConfig.SUCCESS){
//                            authorizationLogin(uuidName,loginUrl);
                            //邦定成功关闭界面显示
                            GetWechatLoginUtil getWechatLoginUtil=gson.fromJson(data, GetWechatLoginUtil.class);
                            SaveAPPData saveAPPData=new SaveAPPData();
//                                saveAPPData.SaveUserConfig(LoginActivity.this,phone,password);
                                saveAPPData.SaveUserID(BindPhoneActivity.this,String.valueOf(""),
                                        getWechatLoginUtil.getData().getToken());
                            setResult(AppConfig.LOGIN_CODE);
                            ToastManager.showShortToast(BindPhoneActivity.this,msg);
                            finish();
                        }
                        Toast.makeText(BindPhoneActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sfjsidfjsifjsfdf",response.getRawResponse().message());
                        Log.i("sfjsidfjsifjsfdf",response.getException().getMessage());
                        Toast.makeText(BindPhoneActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 第三方登录
     * @param uuidName
     * @param url
     */
    private void  authorizationLogin(String uuidName,String url){



        Log.i("sfdjsifsfmsf",uuidName);
        Log.i("sfdjsifsfmsf",uuidName+"   "+uid);
        Log.i("sfdjsifsfmsf","nickname  "+name);
        Log.i("sfdjsifsfmsf","sex   "+mySex+"");
        Log.i("sfdjsifsfmsf","avatar  "+iconurl);

        OkGo.<String>post(url).tag(this)
                .params(uuidName,uid)
                .params("nickname",name)
                .params("sex",mySex+"")
                .params("avatar",iconurl)
                .params("login_phone","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        Gson gson=new Gson();
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            if (code==1){
                                //登录成功
                                GetWechatLoginUtil getWechatLoginUtil=gson.fromJson(data, GetWechatLoginUtil.class);
                                SaveAPPData saveAPPData=new SaveAPPData();
//                                saveAPPData.SaveUserConfig(LoginActivity.this,phone,password);
//                                saveAPPData.SaveUserID(BindPhoneActivity.this,String.valueOf(""),
//                                        getWechatLoginUtil.getData().getToken());
                                setResult(AppConfig.LOGIN_CODE);
                                ToastManager.showShortToast(BindPhoneActivity.this,msg);
                                finish();
                            }else {
                                ToastManager.showShortToast(BindPhoneActivity.this,msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        new AppNetUtil(BindPhoneActivity.this).appInternetError();
                        Log.e("datadata",response.getException().getMessage());
                    }
                });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_ok:
                //完成
                String phone=ct_inputPhone.getText().toString().trim();
                String code=ct_inputCode.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()!=11){
                    Toast.makeText(BindPhoneActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(code)){
                    Toast.makeText(BindPhoneActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                    bindPhone();



                break;
            case R.id.btn_sendCode:
                //发送验证码吗
                sendCode();
                break;
        }
    }
}
