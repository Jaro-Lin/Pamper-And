package com.nyw.pets.activity.shop;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetJsonDataUtil;
import com.nyw.pets.activity.util.JsonBean;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增加地址
 */
public class NewAdressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private TextView tv_del,tv_adress;
    private Button btn_ok;
    private LinearLayout llt_adress;

    //选择城市
    private List<JsonBean> optionsCityItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2CityItems = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3CityItems = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    //显示收货地址信息
    private String adress,id,phone,name;
    private TextView ct_name,tv_detailedAdress,tv_title;
    private ClearEditText ct_phone;
    private String province,city,area,detailed;
    //是否为默认地址,默认是
    private boolean is_default=true;
    //开关
    private Switch sw_on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_adress);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_del=findViewById(R.id.tv_del);
        tv_del.setOnClickListener(this);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        llt_adress=findViewById(R.id.llt_adress);
        llt_adress.setOnClickListener(this);
        tv_adress=findViewById(R.id.tv_adress);
        ct_name=findViewById(R.id.ct_name);
        ct_phone=findViewById(R.id.ct_phone);
        tv_detailedAdress=findViewById(R.id.tv_detailedAdress);
        sw_on_off=findViewById(R.id.sw_on_off);
        tv_title=findViewById(R.id.tv_title);

        try{

           Bundle bundle= getIntent().getExtras();
            name=bundle.getString("name",null);
            phone=bundle.getString("phone",null);
            id=bundle.getString("id",null);
            adress=bundle.getString("adress",null);

            province=bundle.getString("province",null);
            city=bundle.getString("city",null);
            area=bundle.getString("area",null);
            detailed=bundle.getString("detailed",null);
            is_default=bundle.getBoolean("is_default");

            if (TextUtils.isEmpty(name)){
                tv_del.setVisibility(View.INVISIBLE);
                return;
            }
            tv_title.setText("编辑收货地址");
            tv_del.setVisibility(View.VISIBLE);

            if (is_default==true){
                //是默认收货地址
                sw_on_off.setChecked(true);

            }else {
                //不是默认
                sw_on_off.setChecked(false);
            }



            ct_name.setText(name);
            ct_phone.setText(phone);
            tv_adress.setText(province+city+area);
            tv_detailedAdress.setText(detailed);


        }catch (Exception e){}



    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.tv_del:
                //删除
                delAdressData();
                break;
            case R.id.btn_ok:
                //保存
                if (TextUtils.isEmpty(name)){
                    addAdressData();
                }else {
                    updateAdressData();
                }


                break;
            case R.id.llt_adress:
                //选择收货地址
                showPickerView();
                break;
        }
    }

    /**
     * 更新收货地址
     */
    private void updateAdressData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String isDefault;
        if (sw_on_off.isChecked()==true){
            isDefault="1";
        }else {
            isDefault="0";
        }

        phone=ct_phone.getText().toString();
        name=ct_name.getText().toString();
        detailed=tv_detailedAdress.getText().toString();

        if (TextUtils.isEmpty(name)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入姓名");
            return;

        }else if (TextUtils.isEmpty(phone)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入手机号码");
            return;
        }else if (TextUtils.isEmpty(province)){
            ToastManager.showShortToast(NewAdressActivity.this,"请选择地区");
            return;
        }else if (TextUtils.isEmpty(detailed)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入详情地址");
            return;
        }

        String url= Api.UPFATE_USER_ADRESS_INFO;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("address_id",id)
                .params("phone",phone)
                .params("username",name)
                .params("province",province)
                .params("city",city)
                .params("area",area)
                .params("detailed",detailed)
                .params("default",isDefault)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String msg=jsonObject.getString("message");
                            ToastManager.showShortToast(NewAdressActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(NewAdressActivity.this,"网络错误");
                    }
                });

    }

    /**
     * 添加收货地址
     */
    private void addAdressData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String isDefault;
        if (sw_on_off.isChecked()==true){
            isDefault="1";
        }else {
            isDefault="0";
        }

        phone=ct_phone.getText().toString();
        name=ct_name.getText().toString();
        detailed=tv_detailedAdress.getText().toString();

        if (TextUtils.isEmpty(name)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入姓名");
            return;

        }else if (TextUtils.isEmpty(phone)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入手机号码");
            return;
        }else if (TextUtils.isEmpty(province)){
            ToastManager.showShortToast(NewAdressActivity.this,"请选择地区");
            return;
        }else if (TextUtils.isEmpty(detailed)){
            ToastManager.showShortToast(NewAdressActivity.this,"请输入详情地址");
            return;
        }

        String url= Api.ADD_USER_ADRESS_INFO;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("phone",phone)
                .params("username",name)
                .params("province",province)
                .params("city",city)
                .params("area",area)
                .params("detailed",detailed)
                .params("default",isDefault)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String msg=jsonObject.getString("message");
                            ToastManager.showShortToast(NewAdressActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(NewAdressActivity.this,"网络错误");
                    }
                });

}

    /**
     * 删除收货地址
     */
    private void delAdressData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.DEL_USER_ADRESS_INFO;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("address_id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String msg=jsonObject.getString("message");
                            ToastManager.showShortToast(NewAdressActivity.this,msg);

                            if (code== AppConfig.SUCCESS){
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(NewAdressActivity.this,"网络错误");
                    }
                });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
                province=opt1tx;
                city= opt2tx;
                area=opt3tx;
                String tx = opt1tx + opt2tx + opt3tx;
//                Toast.makeText(RegisteredActivity.this, tx, Toast.LENGTH_SHORT).show();
                tv_adress.setText(tx);
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

}
