package com.nyw.pets.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
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
import com.nyw.pets.activity.util.GetLoginUtil;
import com.nyw.pets.activity.util.GetWechatLoginUtil;
import com.nyw.pets.activity.util.LoginWenxinQQSetData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.net.util.MD5Encoder;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.util.getVistionUtil;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.LoadDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_login,btn_registered,btn_sendCode;
    private ClearEditText ct_phone_login,ct_inputPassword;
    private String phone,password;
    private TextView tv_forgotPassword,tv_switch;
    private ImageView iv_hide;
    private LoadDialog loadDialogLogin;
    private ImageView iv_wenxinLogin,iv_qq_login;
    private  String uid,name,gender,iconurl;
    private   boolean isWitch=true;
    private int totime = 60;// 倒计时
    //QQ登录
    private    UMShareAPI umShareAPI;
    private boolean isLoginType=true;//默认微信登录
    private  int mySex=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        umShareAPI=UMShareAPI.get(this);
        initView();

    }

    private void initView() {
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        ct_phone_login=findViewById(R.id.ct_phone_login);
        ct_inputPassword=findViewById(R.id.ct_inputPassword);
        tv_forgotPassword=findViewById(R.id.tv_forgotPassword);
        tv_forgotPassword.setOnClickListener(this);
        btn_registered=findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_wenxinLogin=findViewById(R.id.iv_wenxinLogin);
        iv_wenxinLogin.setOnClickListener(this);
        tv_switch=findViewById(R.id.tv_switch);
        tv_switch.setOnClickListener(this);
        iv_qq_login=findViewById(R.id.iv_qq_login);
        iv_qq_login.setOnClickListener(this);
        btn_sendCode=findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        loadDialogLogin=new LoadDialog(LoginActivity.this,true,"正在登录…");
        new getVistionUtil(LoginActivity.this, Api.GET_OTHER_APP_VERSION).checkUpdate(false);

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_login:
                phone=ct_phone_login.getText().toString().trim();
                password=new MD5Encoder().encode(ct_inputPassword.getText().toString().trim());

            //登录
                if (isWitch==true){
                    //手机号码登录
                    login();
                }else {
                    //验证码登录
                    loginCode();
                }

            break;
            case R.id.btn_registered:
                //注册
                Intent register=new Intent();
                register.setClass(LoginActivity.this, RegisteredActivity.class);
                startActivity(register);
                break;
            case R.id.tv_switch:
                //开关，切换登录方式
                if (isWitch){
                    ct_inputPassword.setHint("请输入手机验证码");
                    ct_inputPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ct_inputPassword.setText("");
                    btn_sendCode.setVisibility(View.VISIBLE);
                    isWitch=false;
                }else {
                    ct_inputPassword.setHint("请输入密码");
                    ct_inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ct_inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isWitch=true;
                    ct_inputPassword.setText("");
                    btn_sendCode.setVisibility(View.GONE);
                }

                break;
            case R.id.tv_forgotPassword:
                //忘记密码
                Intent forgot=new Intent();
                forgot.setClass(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgot);
                break;
            case R.id.iv_hide:
                //关闭
                finish();
                break;
            case R.id.iv_wenxinLogin:
                //微信登录
                isLoginType=true;
                UMShareAPI mShareAPI = UMShareAPI.get( LoginActivity.this );
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
//                Intent intent=new Intent();
//                intent.setClass(LoginActivity.this,BindPhoneActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_qq_login:
                //QQ登录
                isLoginType=false;
                UMShareAPI mShareQQAPI = UMShareAPI.get( LoginActivity.this );
                mShareQQAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.btn_sendCode:
                //发送验证码
                phone=ct_phone_login.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()!=11){
                    Toast.makeText(LoginActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode,resultCode,data);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //http://dev.umeng.com/social/android/login-page友盟官方文档
            //友盟统一封装处理后的字段uid、name、gender、iconurl
            // （用户唯一标识、用户昵称、用户性别、用户头像）详情查看文档

            uid=data.get("uid");
            name=data.get("name");
            gender=data.get("gender");
            iconurl=data.get("iconurl");
//			Toast.makeText(LoginActivity.this,name,Toast.LENGTH_LONG).show();
            Log.d("name",name+gender);
            if (isLoginType==true){
                //微信
                authorizationLogin("wechat","uuid",Api.GET_USER_OAUTH_LOGIN);
            }else {
                //QQ
                authorizationLogin("qq","openid",Api.GET_USER_OAUTH_LOGIN);
            }


        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            loadDialogLogin.dismiss();
            Toast.makeText( getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            loadDialogLogin.dismiss();
            Toast.makeText( getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 微信或QQ登录
     */
    private void  authorizationLogin(String type,String uuidName,String url){


        if (gender.equals("男")){
            mySex=1;
        }else {
            mySex=0;
        }

        Log.i("sfdjsifsfmsf",uuidName);
        Log.i("sfdjsifsfmsf",uuidName+"   "+uid);
        Log.i("sfdjsifsfmsf","nickname  "+name);
        Log.i("sfdjsifsfmsf","sex   "+mySex+"");
        Log.i("sfdjsifsfmsf","avatar  "+iconurl);
        LoginWenxinQQSetData loginWenxinQQSetData=new LoginWenxinQQSetData();
        loginWenxinQQSetData.setDevice_type("android");
        loginWenxinQQSetData.setOpen_id(uid);
        loginWenxinQQSetData.setType(type);

        String sendData= new Gson().toJson(loginWenxinQQSetData);

        Log.i("ldfmsifsmfsfsrtyty",sendData);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendData);



        OkGo.<String>post(url).tag(this)
                .params(uuidName,uid)
                .upRequestBody(body)
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
                            if (code==1) {



                                    //登录成功
                                    GetWechatLoginUtil getWechatLoginUtil = gson.fromJson(data, GetWechatLoginUtil.class);

                                    if (getWechatLoginUtil.getData()!=null){
                                        if (getWechatLoginUtil.getData().getIs_reg()==0){
                                            //未注册
                                            Intent bind = new Intent();
                                            bind.setClass(LoginActivity.this, BindPhoneActivity.class);
                                            bind.putExtra("uuid", uid);
                                            bind.putExtra("loginType", isLoginType);

                                            bind.putExtra("uuidName", uuidName);
                                            bind.putExtra("uid", uid);
                                            bind.putExtra("name", name);
                                            bind.putExtra("mySex", mySex+"");
                                            bind.putExtra("iconurl", iconurl);
                                            bind.putExtra("url", url);
                                            bind.putExtra("type",type);

                                            startActivity(bind);
                                            finish();
                                        }else {
                                            //登录
                                            SaveAPPData saveAPPData = new SaveAPPData();
                                            saveAPPData.SaveUserConfig(LoginActivity.this,phone,ct_inputPassword.getText().toString());

                                            saveAPPData.SaveUserID(LoginActivity.this, String.valueOf(""),
                                            getWechatLoginUtil.getData().getToken());
                                            setResult(AppConfig.LOGIN_CODE);
                                            ToastManager.showShortToast(LoginActivity.this, msg);
                                            finish();
                                        }
                                    }

                                if (getWechatLoginUtil.getData().getIs_reg()!=0) {
                                    ToastManager.showShortToast(LoginActivity.this, msg);
                                }

                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDialogLogin.cancel();
                        new AppNetUtil(LoginActivity.this).appInternetError();
                        Log.e("datadata",response.getException().getMessage());
                    }
                });

    }
    /**
     * 登录
     */
    private void login() {
        loadDialogLogin.show();

        OkGo.<String>post(Api.USER_LOGIN_PHONE).tag(this)
                .params("phone",phone)
                .params("password",password)
                .params("login_phone","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                        Log.i("datadata",data);
                        Gson gson=new Gson();
                        GetLoginUtil getLoginUtil=gson.fromJson(data, GetLoginUtil.class);
                        int code=getLoginUtil.getCode();
                        if (code==AppConfig.SUCCESS){
                            SaveAPPData saveAPPData=new SaveAPPData();
                            saveAPPData.SaveUserConfig(LoginActivity.this,phone,password);
                            saveAPPData.SaveUserID(LoginActivity.this,String.valueOf(""),
                                    getLoginUtil.getData().getToken());
                            setResult(AppConfig.LOGIN_CODE);
                            finish();
                        }
                        loadDialogLogin.cancel();
                        Toast.makeText(LoginActivity.this,getLoginUtil.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDialogLogin.cancel();
                        new AppNetUtil(LoginActivity.this).appInternetError();
                        Log.e("datadata",response.getException().getMessage());
                    }
                });
    }

    /**
     * 手机验证码登录
     */
    private void loginCode() {
        loadDialogLogin.show();

        OkGo.<String>post(Api.USER_LOGIN_CODE).tag(this)
                .params("phone",phone)
                .params("code",ct_inputPassword.getText().toString())
                .params("login_phone","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        Gson gson=new Gson();
                        GetLoginUtil getLoginUtil=gson.fromJson(data, GetLoginUtil.class);
                        int code=getLoginUtil.getCode();
                        if (code==AppConfig.SUCCESS){
                            SaveAPPData saveAPPData=new SaveAPPData();
//                            saveAPPData.SaveUserConfig(LoginActivity.this,phone,password);
                            saveAPPData.SaveUserID(LoginActivity.this,String.valueOf("112"),
                                    getLoginUtil.getData().getToken());
                            setResult(AppConfig.LOGIN_CODE);
                            finish();
                        }
                        loadDialogLogin.cancel();
                        Toast.makeText(LoginActivity.this,getLoginUtil.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDialogLogin.cancel();
                        new AppNetUtil(LoginActivity.this).appInternetError();
                        Log.e("datadata",response.getException().getMessage());
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
     * 发送验证码
     */
    private void sendCode(){
        phone=ct_phone_login.getText().toString().trim();

        OkGo.<String>post(Api.SEND_CODE).tag(this)
                .params("username",phone)
                .params("type","1")
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
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(LoginActivity.this).appInternetError();
                    }
                });
    }

}
