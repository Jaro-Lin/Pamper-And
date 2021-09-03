package com.nyw.pets.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.nyw.pets.activity.util.CardBean;
import com.nyw.pets.activity.util.InjectionData;
import com.nyw.pets.activity.util.InjectionPetsData;
import com.nyw.pets.activity.util.SendPetsInitData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 宠物调查
 */
public class PetResearchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private CheckBox cb_allSelect,cb_rabiesVaccineSelect,cb_dewormingSelect,cb_takeBathSelect;
    private LinearLayout llt_vaccine_time,llt_vaccinesNumber,llt_rabiesVaccineSelect,llt_rabiesVaccineSelectNumber
            ,llt_dewormingSelect,llt_takeBathSelect,llt_recently;
    private TextView tv_vaccine_time,tv_vaccinesNumber,tv_rabiesVaccineSelectTime,tv_dewormingSelect,tv_takeBathSelectTime,tv_rabiesVaccineSelectNumber,
            tv_recently;

    //驱虫间隔设置
    private Spinner sp_dewormingInterval,sp_bathInterval;
    private List<String> dewormingIntervalList;
    private ArrayAdapter<String> dewormingIntervalAdapter;
    private String dewormingInterval="0";

    //洗澡间隔设置
    private List<String> bathIntervalList;
    private ArrayAdapter<String> bathIntervalAdapter;
    private String bathInterval="0";

    //选择年月日
    private TimePickerView pvTime;
    private String brithday;
    private int clickNumber=0;

    //选择次数
    private OptionsPickerView pvWeightPickerView;
    private ArrayList<CardBean> cardWeightItem = new ArrayList<>();
    //注射狂犬疫苗 总共打了几针疫苗
    private String rabiesVaccineSelectNumber="1";
    //三联疫苗 总共打了几针疫苗
    private String vaccinesNumber="1";
    //上次注射三联疫苗时间
    private String vaccine_time;
    //注射狂犬疫苗 时间
    private String rabiesVaccineSelectTime;
    //上次驱虫时间
    private String dewormingSelect;
    //上次洗澡时间
    private String takeBathSelectTime;
    //最近打疫苗时间
    private String  recentlyTime;
    //提交初始化宠物
    private Button btn_initPets;
    //宠物 id
    private String pets_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_research);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        cb_allSelect=findViewById(R.id.cb_allSelect);
        llt_vaccine_time=findViewById(R.id.llt_vaccine_time);
        llt_vaccinesNumber=findViewById(R.id.llt_vaccinesNumber);
        tv_vaccine_time=findViewById(R.id.tv_vaccine_time);
        tv_vaccinesNumber=findViewById(R.id.tv_vaccinesNumber);
        cb_rabiesVaccineSelect=findViewById(R.id.cb_rabiesVaccineSelect);
        llt_rabiesVaccineSelect=findViewById(R.id.llt_rabiesVaccineSelect);
        tv_rabiesVaccineSelectTime=findViewById(R.id.tv_rabiesVaccineSelectTime);
        llt_rabiesVaccineSelectNumber=findViewById(R.id.llt_rabiesVaccineSelectNumber);
        cb_dewormingSelect=findViewById(R.id.cb_dewormingSelect);
        llt_dewormingSelect=findViewById(R.id.llt_dewormingSelect);
        tv_dewormingSelect=findViewById(R.id.tv_dewormingSelect);
        cb_takeBathSelect=findViewById(R.id.cb_takeBathSelect);
        llt_takeBathSelect=findViewById(R.id.llt_takeBathSelect);
        tv_takeBathSelectTime=findViewById(R.id.tv_takeBathSelectTime);
        sp_dewormingInterval=findViewById(R.id.sp_dewormingInterval);
        sp_bathInterval=findViewById(R.id.sp_bathInterval);
        tv_rabiesVaccineSelectNumber=findViewById(R.id.tv_rabiesVaccineSelectNumber);
        llt_vaccine_time.setOnClickListener(this);
        llt_rabiesVaccineSelect.setOnClickListener(this);
        llt_takeBathSelect.setOnClickListener(this);
        llt_takeBathSelect.setOnClickListener(this);
        llt_dewormingSelect.setOnClickListener(this);
        llt_vaccinesNumber.setOnClickListener(this);
        llt_rabiesVaccineSelectNumber.setOnClickListener(this);
        btn_initPets=findViewById(R.id.btn_initPets);
        btn_initPets.setOnClickListener(this);
        llt_recently=findViewById(R.id.llt_recently);
        llt_recently.setOnClickListener(this);
        tv_recently=findViewById(R.id.tv_recently);



        cb_allSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最近是否有注射三联疫苗或者六联疫苗？
                if (cb_allSelect.isChecked()==true){
                    llt_vaccine_time.setVisibility(View.VISIBLE);
                    llt_vaccinesNumber.setVisibility(View.VISIBLE);
                }else {
                    llt_vaccine_time.setVisibility(View.GONE);
                    llt_vaccinesNumber.setVisibility(View.GONE);
                }
            }
        });

        cb_rabiesVaccineSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最近是否有注射狂犬疫苗
                if (cb_rabiesVaccineSelect.isChecked()==true){
                    llt_rabiesVaccineSelect.setVisibility(View.VISIBLE);
                    llt_rabiesVaccineSelectNumber.setVisibility(View.VISIBLE);
                }else {
                    llt_rabiesVaccineSelect.setVisibility(View.GONE);
                    llt_rabiesVaccineSelectNumber.setVisibility(View.GONE);
                }
            }
        });

        cb_dewormingSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最近是否有驱虫
                if (cb_dewormingSelect.isChecked()==true){
                    llt_dewormingSelect.setVisibility(View.VISIBLE);
                }else {
                    llt_dewormingSelect.setVisibility(View.GONE);
                }

            }
        });
        cb_takeBathSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最近是否有洗澡
                if (cb_takeBathSelect.isChecked()==true){
                    llt_takeBathSelect.setVisibility(View.VISIBLE);
                }else {
                    llt_takeBathSelect.setVisibility(View.GONE);
                }
            }
        });

        //驱虫间隔设置
        sp_dewormingInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(RequestAfterSalesActivity.this,sp_afterSalesType.getSelectedItem().toString());
                dewormingInterval=sp_dewormingInterval.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dewormingIntervalList = new ArrayList<String>();
        for (int i=0;i<=99;i++){
            dewormingIntervalList.add(  i+"");
        }
        bathIntervalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dewormingIntervalList);

        //设置样式
        bathIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_dewormingInterval.setAdapter(bathIntervalAdapter);




        //洗澡间隔设置
        sp_bathInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(RequestAfterSalesActivity.this,sp_afterSalesType.getSelectedItem().toString());
                bathInterval=sp_bathInterval.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bathIntervalList = new ArrayList<String>();
        for (int i=0;i<=99;i++){
            bathIntervalList.add(  i+"");
        }
        dewormingIntervalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bathIntervalList);

        //设置样式
        dewormingIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_bathInterval.setAdapter(dewormingIntervalAdapter);

        //选择出生日期
        initTimePicker();
        //选择次数
        initWeightPicker();

        try{
            //获取宠物 id
          Bundle bundle=  getIntent().getExtras();
          pets_id= bundle.getString("pets_id",null);
        }catch (Exception e){}

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.llt_vaccine_time:
                //选择上次注射疫苗时间，六联疫苗
                pvTime.show();
                clickNumber=0;
                break;
            case R.id.llt_rabiesVaccineSelect:
                //上次注射疫苗时间
                pvTime.show();
                clickNumber=1;
                break;
            case R.id.llt_dewormingSelect:
                //上次驱虫时间
                pvTime.show();
                clickNumber=2;
                break;
            case R.id.llt_takeBathSelect:
                //上次洗澡时间
                pvTime.show();
                clickNumber=3;
                break;
            case R.id.llt_recently:
                //最近一次打疫苗的时间
                pvTime.show();
                clickNumber=4;
                break;
            case R.id.llt_vaccinesNumber:
                //三联疫苗 总共打了几针疫苗
                pvWeightPickerView.show();
                clickNumber=0;
                break;
            case R.id.llt_rabiesVaccineSelectNumber:
                //注射狂犬疫苗 总共打了几针疫苗
                pvWeightPickerView.show();
                clickNumber=1;
                break;
            case R.id.btn_initPets:
                //提交
                sendInitPetsData();
                break;

        }
    }

    /**
     * 提交初始化宠物数据
     */
    private void sendInitPetsData() {
        String url= Api.GET_USER_SCHEDULE_SCHEDULE_INIT;

        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
//        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);

        String initData="";
        SendPetsInitData sendPetsInitData=new SendPetsInitData();
//        InjectionPetsData injectionPetsData=new InjectionPetsData();

//        InjectionData injectionData=new InjectionData();
//        injectionData.setVaccin_count(vaccinesNumber);
//        injectionData.setVaccin_time(vaccine_time);
//        injectionData.setVaccin_type("0");
//        injectionPetsData.setOne(injectionData);
//
//        InjectionData injectionData1=new InjectionData();
//        injectionData1.setVaccin_count(rabiesVaccineSelectNumber);
//        injectionData1.setVaccin_time(rabiesVaccineSelectTime);
//        injectionData1.setVaccin_type("1");
//        injectionPetsData.setTwo(injectionData1);
//
//        sendPetsInitData.setVaccin(injectionPetsData);

        //三联/六联疫苗打了几次
        sendPetsInitData.setVaccin_0_count(vaccinesNumber);
        //狂犬疫苗打了几次
        sendPetsInitData.setVaccin_1_count(rabiesVaccineSelectNumber);

        //上一次打三联/六联疫时间
        sendPetsInitData.setLast_vaccin_0(vaccine_time);
        //上一次打狂犬疫苗时间
        sendPetsInitData.setLast_vaccin_1(rabiesVaccineSelectTime);
        //每隔几天洗澡
        sendPetsInitData.setBath_inter(bathInterval);

        //上次洗澡是什么时候
        sendPetsInitData.setLast_bath(takeBathSelectTime);
        //上次驱虫是什么时候
        sendPetsInitData.setLast_expelling(dewormingSelect);
        sendPetsInitData.setExpelling_inter(dewormingInterval);

        //要初始化的宠物id
        sendPetsInitData.setMypets_id(pets_id);
        //用户token
        sendPetsInitData.setToken(token);
        //最近一次打疫苗的时间
//        sendPetsInitData.setVaccin_time(recentlyTime);
        //最近打疫苗什么疫苗 0为
//        sendPetsInitData.setVaccin_type("0");

        Gson gson=new Gson();
        initData= gson.toJson(sendPetsInitData);

        Log.i("sdfjsifjsdfingdghhf",initData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), initData);

        OkGo.<String>post(url).tag(this)
               .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                        Log.i("sfjdjsifsjfjsdtyyd",data);
                        JSONObject jsonObject=null;
                        int code=2;
                        String msg;
                        try {
                            jsonObject=new JSONObject(data);
                            code= jsonObject.getInt("code");
                           msg= jsonObject.getString("message");
                           if (code== AppConfig.SUCCESS){
                               //初始化成功
                               finish();
                           }
                           ToastManager.showShortToast(PetResearchActivity.this,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sdkfsifsndfsfksfst",response.getRawResponse().message());
                        Log.i("sdkfsifsndfsfksfst",response.getException().getMessage());
                        ToastManager.showShortToast(PetResearchActivity.this,"网络错误");
                    }
                });
    }

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
        pvTime = new TimePickerBuilder(PetResearchActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                String time=getTime(date);

                if (clickNumber==0){
                    //上次注射三联疫苗时间
                    tv_vaccine_time.setText(getTime(date));
                    vaccine_time=getTime(date);
//                    vaccine_time=time.replace("-","");
                }else if (clickNumber==1){
                    //注射狂犬疫苗 时间
                    tv_rabiesVaccineSelectTime.setText(getTime(date));
                    rabiesVaccineSelectTime=getTime(date);
//                    rabiesVaccineSelectTime=time.replace("-","");
                }else if (clickNumber==2){
                    //上次驱虫时间
                     tv_dewormingSelect.setText(getTime(date));
                    dewormingSelect=getTime(date);
//                    dewormingSelect=time.replace("-","");
                }else if (clickNumber==3){
                    //上次洗澡时间
                    tv_takeBathSelectTime.setText(getTime(date));
                    takeBathSelectTime=getTime(date);
//                    takeBathSelectTime=time.replace("-","");
                }else if (clickNumber==4){
                    //最近打疫苗时间
                    tv_recently.setText(getTime(date));
                    recentlyTime=getTime(date);
                }


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
     * 体重
     */
    private void initWeightPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvWeightPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardWeightItem.get(options1).getPickerViewText();
                if (clickNumber==0){
                    //三联疫苗 总共打了几针疫苗
                    tv_vaccinesNumber.setText(tx+" 针");
                    vaccinesNumber= tx;
                }else if (clickNumber==1){
                    //注射狂犬疫苗 总共打了几针疫苗
                    tv_rabiesVaccineSelectNumber.setText(tx+" 针");
                    rabiesVaccineSelectNumber= tx;
                }


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
                                pvWeightPickerView.returnData();
                                pvWeightPickerView.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvWeightPickerView.dismiss();
                            }
                        });
                        getWeightData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvWeightPickerView.setPicker(cardWeightItem);//添加数据


    }
    private void getWeightData() {

        for (int i=0;i<1000;i++){
            cardWeightItem.add(new CardBean(0,""+(i+1)));
        }

        for (int i = 0; i < 2; i++) {
            cardWeightItem.get(i).setCardNo(cardWeightItem.get(i).getCardNo());
        }

    }


}
