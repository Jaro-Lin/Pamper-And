package com.nyw.pets.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.CardBean;
import com.nyw.pets.activity.util.GetJsonDataUtil;
import com.nyw.pets.activity.util.JsonBean;
import com.nyw.pets.activity.util.ProvinceBean;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.net.util.MD5Encoder;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.LoadDialog;
import com.nyw.pets.view.RegisterSuccessDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 注册
 */
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_registered;
    private ClearEditText ct_phone,ct_input_code,ct_inputPassword;
    private ImageView iv_hide;
    private Button btn_sendCode;
    private String phone,code,password;
    private int totime = 60;// 倒计时
    private LoadDialog loadDialogRegister;
    private CheckBox cb_agreement;
    private TextView tv_readUse;
    private LinearLayout llt_sex,llt_my_create_data,llt_my_city;
    private TextView tv_create_data,tv_my_city,tv_sex;

    private OptionsPickerView pvOptions, pvCustomOptions, pvNoLinkOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();
    private ArrayList<String> computer = new ArrayList<>();

    private TimePickerView pvTime;
    //选择城市
    private List<JsonBean> optionsCityItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2CityItems = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3CityItems = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    //选择性别
    private TimePickerView  pvCustomTime;
    private ClearEditText ct_user_name,ct_input2Password;
    private int sexInt=1;//默认男

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();

    }

    private void initView() {
        btn_registered=findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_phone=findViewById(R.id.ct_phone);
        ct_input_code=findViewById(R.id.ct_input_code);
        ct_inputPassword=findViewById(R.id.ct_inputPassword);
        btn_sendCode=findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        cb_agreement=findViewById(R.id.cb_agreement);
        tv_readUse=findViewById(R.id.tv_readUse);
        tv_readUse.setOnClickListener(this);
        llt_sex=findViewById(R.id.llt_sex);
        llt_sex.setOnClickListener(this);
        llt_my_create_data=findViewById(R.id.llt_my_create_data);
        llt_my_create_data.setOnClickListener(this);
        tv_create_data=findViewById(R.id.tv_create_data);
        llt_my_city=findViewById(R.id.llt_my_city);
        llt_my_city.setOnClickListener(this);
        tv_my_city=findViewById(R.id.tv_my_city);
        tv_sex=findViewById(R.id.tv_sex);
        ct_user_name=findViewById(R.id.ct_user_name);
        ct_input2Password=findViewById(R.id.ct_input2Password);

        loadDialogRegister=new LoadDialog(RegisteredActivity.this,true,"正在注册…");
        initTimePicker();
        initCustomOptionPicker();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
                    Toast.makeText(RegisteredActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(sendcode).start();//倒计时60秒
                    sendCode();
                }
                break;
            case R.id.btn_registered:
                //注册

                register();
//                if (cb_agreement.isChecked()==true) {
//                    register();
//                }else {
//                    Toast.makeText(RegisteredActivity.this,"请认真阅读并同意用户协议",Toast.LENGTH_LONG).show();
//                }
                break;
            case R.id.tv_readUse:
                //用户协议
                cb_agreement.setChecked(true);
                Intent intent=new Intent();
                intent.setClass(RegisteredActivity.this,UserAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.llt_sex:
                //选择性别
                pvCustomOptions.show();
                break;
            case R.id.llt_my_create_data:
                //选择出生日期
                pvTime.show();
                break;
            case R.id.llt_my_city:
                //选择城市
                showPickerView();
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 注册
     */
    private void register(){
        loadDialogRegister.show();
        phone=ct_phone.getText().toString().trim();
        code=ct_input_code.getText().toString().trim();
        password=new MD5Encoder().encode(ct_inputPassword.getText().toString().trim());
        String input2Password=new MD5Encoder().encode(ct_input2Password.getText().toString().trim());
        if (!ct_inputPassword.getText().toString().trim().equals(ct_input2Password.getText().toString().trim())){
            ToastManager.showShortToast(RegisteredActivity.this,"你输入的密码不一致，请重新输入确定");
            loadDialogRegister.cancel();
            return;
        }
        String sex=tv_sex.getText().toString();
        String user_name=ct_user_name.getText().toString();
        String create_data=null;
        create_data= tv_create_data.getText().toString().toString();
        create_data=create_data.replace("-","");
        String city=tv_my_city.getText().toString();


        OkGo.<String>post(Api.GET_USER_LOGIN_REGISTER).tag(this)
                .params("nickname", user_name)
                .params("phone",phone)
                .params("code",code)
                .params("password",password)
                .params("password_again",input2Password)
                .params("sex",sexInt)
                .params("birthday",create_data)
                .params("login_phone","android")
                .params("city",city)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadata",data);
                        loadDialogRegister.cancel();
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
                        Toast.makeText(RegisteredActivity.this,msg,Toast.LENGTH_SHORT).show();
                        if (code==AppConfig.SUCCESS){
                            new RegisterSuccessDialog(RegisteredActivity.this).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        loadDialogRegister.cancel();
                        new AppNetUtil(RegisteredActivity.this).appInternetError();
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
                        Toast.makeText(RegisteredActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
                        new AppNetUtil(RegisteredActivity.this).appInternetError();
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
     * 选择出生日期
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 9, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(RegisteredActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tv_create_data.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                                /*pvTime.dismiss();*/
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*pvTime.dismiss();*/
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }

    /**
     * 获取时间
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 选择城市
     */
    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = optionsCityItems.size() > 0 ?
                        optionsCityItems.get(options1).getPickerViewText() : "";

                String opt2tx = options2CityItems.size() > 0
                        && options2CityItems.get(options1).size() > 0 ?
                        options2CityItems.get(options1).get(options2) : "";

                String opt3tx = options2CityItems.size() > 0
                        && options3CityItems.get(options1).size() > 0
                        && options3CityItems.get(options1).get(options2).size() > 0 ?
                        options3CityItems.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
//                Toast.makeText(RegisteredActivity.this, tx, Toast.LENGTH_SHORT).show();
                tv_my_city.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(optionsCityItems, options2CityItems, options3CityItems);//三级选择器
        pvOptions.show();
    }

    /**
     * 解析城市数据
     */
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        optionsCityItems = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2CityItems.add(cityList);

            /**
             * 添加地区数据
             */
            options3CityItems.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

//                        Toast.makeText(RegisteredActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        Log.i(".sdfmskfsfsf","Begin Parse Data");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(RegisteredActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    Log.i(".sdfmskfsfsf","Parse Succeed");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Log.i(".sdfmskfsfsf","Parse Failed");
//                    Toast.makeText(RegisteredActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 性别
     */
    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                if (tx.equals("男")){
                    sexInt=1;
                }else {
                    sexInt=0;
                }
                tv_sex.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_sex, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                        getCardData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(cardItem);//添加数据


    }
    private void getCardData() {

        cardItem.add(new CardBean(0,"男"));
        cardItem.add(new CardBean(1,"女"));

        for (int i = 0; i < 2; i++) {
            cardItem.get(i).setCardNo(cardItem.get(i).getCardNo());
        }

    }

}
