package com.nyw.pets.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.AddPetsActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.CardBean;
import com.nyw.pets.activity.util.GetBasePetsDataUtil;
import com.nyw.pets.activity.util.GetDefaultInfoUtil;
import com.nyw.pets.activity.util.GetPetsBaseInitData;
import com.nyw.pets.activity.util.GetPetsData;
import com.nyw.pets.activity.util.GetPetsListDataUtil;
import com.nyw.pets.activity.util.GetSomeMonthPetsDataUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.activity.util.SendBathData;
import com.nyw.pets.activity.util.SendRecordPetsData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.DisplayUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.GetPetsDialog;
import com.nyw.pets.view.Pets_initDialog;
import com.nyw.pets.view.ShopPetsMenu;
import com.nyw.pets.view.UpdatePetsMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ??????
 */
public class RecordActivity extends BaseActivity implements View.OnClickListener ,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener,
        DialogInterface.OnClickListener{
    private CalendarView mCalendarView;
    private  Switch sw_repellent,sw_vaccine,switch_bathing,sw_rabies_vaccine;
    private Pets_initDialog pets_initDialog;
    //??????id
    private String pets_id;
    //??????Pid
    private String pid;
    //???????????????????????????
    private GetPetsDialog getPetsDialog;
    private TextView tv_name;
    private ImageView iv_name;
    //????????????
    private TextView tv_timeData;
    private ImageView iv_timeData;
    //?????????????????? ?????? 2020-05
    private String myMonth;
    //???????????? ??????
    private GetPetsListDataUtil getPetsListDataUtil;
    //??????????????????
    private  GetBasePetsDataUtil getBasePetsDataUtil;
    //??????
    private TextView tv_weight,tv_feed,tv_healthy,tv_extra_meal,tv_null;
    //??????????????????
    private String today;
    private LinearLayout llt_pets,llt_no_pets,llt_weight;
    private Button btn_add;
    //????????????????????????
    private boolean isWriteData=false;
    //??????????????????
    private MyApplication myApplication;
    //??????????????????
    private LinearLayout llt_no_init;
    private Button btn_init;
    private ImageView iv_explanation1,iv_explanation2,iv_explanation3,iv_explanation4,
            iv_explanation5,iv_explanation6,iv_explanation7,iv_explanation8,iv_explanation9,iv_setup;

    private String meal_add,feed,shower,vaccin,expelling;
    private String myTime="";
    //????????????
    //????????????
    private UpdatePetsMenu updatePetsMenu;
    //????????????
    private OptionsPickerView mOptionsPickerView;
    private ArrayList<CardBean> numberItem = new ArrayList<>();
    //??????,??????  ?????????
    private String healthy="??????";
    //????????????????????????
    private OptionsPickerView  pvHealthyOptions;
    private ArrayList<CardBean> cardHealthyItem = new ArrayList<>();
    //??????????????? ????????????
    private  int recordTime=1;
    //????????????ID
    private String varieties_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        myApplication= (MyApplication) getApplication();
        getPetsDialog=new GetPetsDialog(RecordActivity.this);
        initView();
    }

    private void initView() {
        tv_name=findViewById(R.id.tv_name);
        tv_name.setOnClickListener(this);
        iv_name=findViewById(R.id.iv_name);
        iv_name.setOnClickListener(this);
        tv_timeData=findViewById(R.id.tv_timeData);
        tv_timeData.setOnClickListener(this);
        iv_timeData=findViewById(R.id.iv_timeData);
        iv_timeData.setOnClickListener(this);
        tv_weight=findViewById(R.id.tv_weight);
        tv_feed=findViewById(R.id.tv_feed);
        tv_healthy=findViewById(R.id.tv_healthy);
        tv_healthy.setOnClickListener(this);
        tv_extra_meal=findViewById(R.id.tv_extra_meal);
        llt_pets=findViewById(R.id.llt_pets);
        llt_pets.setOnClickListener(this);
        btn_add=findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        llt_no_pets=findViewById(R.id.llt_no_pets);
        sw_rabies_vaccine=findViewById(R.id.sw_rabies_vaccine);
        llt_weight=findViewById(R.id.llt_weight);
        tv_null=findViewById(R.id.tv_null);
        llt_no_init=findViewById(R.id.llt_no_init);
        btn_init=findViewById(R.id.btn_init);
        btn_init.setOnClickListener(this);

        iv_explanation1=findViewById(R.id.iv_explanation1);
        iv_explanation1.setOnClickListener(this);
        iv_explanation3=findViewById(R.id.iv_explanation3);
        iv_explanation3.setOnClickListener(this);
        iv_explanation4=findViewById(R.id.iv_explanation4);
        iv_explanation4.setOnClickListener(this);
        iv_explanation5=findViewById(R.id.iv_explanation5);
        iv_explanation5.setOnClickListener(this);
        iv_explanation6=findViewById(R.id.iv_explanation6);
        iv_explanation6.setOnClickListener(this);
        iv_explanation7=findViewById(R.id.iv_explanation7);
        iv_explanation7.setOnClickListener(this);
        iv_explanation8=findViewById(R.id.iv_explanation8);
        iv_explanation8.setOnClickListener(this);
        iv_explanation9=findViewById(R.id.iv_explanation9);
        iv_explanation9.setOnClickListener(this);
        iv_setup=findViewById(R.id.iv_setup);
        iv_setup.setOnClickListener(this);



        mCalendarView=findViewById(R.id.calendarView);


        //??????
          sw_repellent = (Switch) findViewById(R.id.sw_repellent);

        sw_repellent.setChecked(false);

        sw_repellent.setSwitchTextAppearance(RecordActivity.this,R.color.my_index_bg);
        sw_repellent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWriteData=true;
                if (sw_repellent.isChecked()==true) {
//                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sw_repellent.setSwitchTextAppearance(RecordActivity.this, R.style.s_true);
                    sendPetsData("expelling", "1");
//                    sw_repellent.setClickable(false);

                } else {
                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sendPetsData("expelling", "0");
                    sw_repellent.setSwitchTextAppearance(RecordActivity.this, R.color.my_index_bg);

                }
            }
        });



        //??????
        sw_vaccine = (Switch) findViewById(R.id.sw_vaccine);

        sw_vaccine.setChecked(false);

        sw_vaccine.setSwitchTextAppearance(RecordActivity.this,R.color.my_index_bg);

        sw_vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_vaccine.isChecked()==true) {
//                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sw_vaccine.setSwitchTextAppearance(RecordActivity.this, R.style.s_true);
                    sendPetsData("vaccin_0", "1");
//                    sw_vaccine.setClickable(false);

                } else {
                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sendPetsData("vaccin_0", "0");
                    sw_vaccine.setSwitchTextAppearance(RecordActivity.this, R.color.my_index_bg);

                }
            }
        });



        //??????

        switch_bathing = (Switch) findViewById(R.id.switch_bathing);

        switch_bathing.setChecked(false);

        switch_bathing.setSwitchTextAppearance(RecordActivity.this,R.color.my_index_bg);
        switch_bathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_bathing.isChecked()==true) {
//                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    switch_bathing.setSwitchTextAppearance(RecordActivity.this, R.style.s_true);
                    sendPetsData("bath", "1");
//                    switch_bathing.setClickable(false);

                } else {
                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sendPetsData("bath", "0");
                    switch_bathing.setSwitchTextAppearance(RecordActivity.this, R.color.my_index_bg);


                }
            }
        });

        //????????????
        sw_rabies_vaccine.setChecked(false);

        sw_rabies_vaccine.setSwitchTextAppearance(RecordActivity.this,R.color.my_index_bg);
        sw_rabies_vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_rabies_vaccine.isChecked()==true) {
//                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sw_rabies_vaccine.setSwitchTextAppearance(RecordActivity.this, R.style.s_true);
                    sendPetsData("vaccin_1", "1");
//                    sw_rabies_vaccine.setClickable(false);

                } else {
                    ToastManager.showShortToast(RecordActivity.this, "??????");
                    sendPetsData("vaccin_1", "0");
                    sw_rabies_vaccine.setSwitchTextAppearance(RecordActivity.this, R.color.my_index_bg);


                }
            }
        });


        //????????????????????????
        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();
        if (String.valueOf(mCalendarView.getCurMonth()).length()==1){
            if (String.valueOf(mCalendarView.getCurDay()).length()==1){
                myMonth=mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth();
                tv_timeData.setText(mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth()+"-"+"0"+mCalendarView.getCurDay());
                today=mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth()+"-"+"0"+mCalendarView.getCurDay();
            }else {
                myMonth=mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth();
                tv_timeData.setText(mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay());
                today=mCalendarView.getCurYear()+"-"+"0"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay();
            }

        }else {
            if (String.valueOf(mCalendarView.getCurDay()).length()==1){
                myMonth=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth();
                tv_timeData.setText(mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+"0"+mCalendarView.getCurDay());
                today=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+"0"+mCalendarView.getCurDay();
                myMonth=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth();
            }else {
                myMonth=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth();
                tv_timeData.setText(mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay());
                today=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay();
                myMonth=mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth();
            }

        }


        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);
        //???????????????????????????????????????????????????????????????
        mCalendarView.setOnCalendarInterceptListener(this);

        mCalendarView.setOnViewChangeListener(this);


//        tv_timeData.setText(mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay());







        //?????????????????????
        pets_initDialog=new Pets_initDialog(this);
        pets_initDialog.setDialogCallback(new Pets_initDialog.Dialogcallback() {
            @Override
            public void petsInitData(String string) {
                //?????????????????????????????????
                Intent intent=new Intent();
                intent.setClass(RecordActivity.this,PetResearchActivity.class);
                intent.putExtra("pets_id",pets_id);
                startActivity(intent);
            }

            @Override
            public void noPetsInitData(String string) {
                //??????????????????
                llt_no_init.setVisibility(View.VISIBLE);
                llt_pets.setVisibility(View.GONE);
            }
        });
        //????????????
        getPetsDialog.setDialogCallback(new GetPetsDialog.Dialogcallback() {
            @Override
            public void selectPets(GetPetsListDataUtil getPetsListDataUtil, int selectNumer) {
                //????????????????????????
                myApplication.setIs_init(getPetsListDataUtil.getData().get(selectNumer).getIs_init()+"");
                myApplication.setPets_id(getPetsListDataUtil.getData().get(selectNumer).getId()+"");

                tv_name.setText(getPetsListDataUtil.getData().get(selectNumer).getNickname());
                pets_id=getPetsListDataUtil.getData().get(selectNumer).getId()+"";
                tv_healthy.setText(getPetsListDataUtil.getData().get(selectNumer).getHealthy());
                tv_weight.setText(getPetsListDataUtil.getData().get(selectNumer).getWeight()+" kg");
                varieties_id=getPetsListDataUtil.getData().get(selectNumer).getVarieties_id()+"";
                getDefaultPetsInfo(getPetsListDataUtil.getData().get(selectNumer).getId()+"");
                //???????????????????????????????????????????????????????????????????????????????????????
                if (getPetsListDataUtil.getData().get(selectNumer).getIs_init()==0){
                    pets_initDialog.show();
                }else {
                    llt_no_init.setVisibility(View.GONE);
                    llt_pets.setVisibility(View.VISIBLE);
                    getPetsData();
                }


            }
        });

        initNumberPicker();
        initHealthyCustomOptionPicker();

    }

    /**
     * ??????????????????
     */
    private void calendarInitData(  GetSomeMonthPetsDataUtil getSomeMonthPetsDataUtil) {

        Map<String, Calendar> map = new HashMap<>();
        for (int y = 1997; y < 2082; y++) {
            for (int m = 1; m <= 12; m++) {
                //????????????

                //????????????
                String predictionData;
                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getBath().size();i++){
                    predictionData=  getSomeMonthPetsDataUtil.getData().getBath().get(i);
                    String [] timeData=predictionData.split("-");
                   int time=Integer.parseInt( timeData[2].toString());
                        map.put(getSchemeCalendar(y, m, time, 0xFF40db25, "??????").toString(),
                                getSchemeCalendar(y, m, time, 0xFF40db25, "??????"));
                }

                //????????????
                String expellingData;
                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getExpelling().size();i++){
                    expellingData=  getSomeMonthPetsDataUtil.getData().getExpelling().get(i);
                    String [] timeData=expellingData.split("-");
                    int time=Integer.parseInt( timeData[2].toString());
                    map.put(getSchemeCalendar(y, m, time, 0xFFe69138, "??????").toString(),
                            getSchemeCalendar(y, m, time, 0xFFe69138, "??????"));
                }

                //??????or??????????????????
                String vaccin_0_Data;
                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getVaccin_0().size();i++){
                    vaccin_0_Data=  getSomeMonthPetsDataUtil.getData().getVaccin_0().get(i);
                    String [] timeData=vaccin_0_Data.split("-");
                    int time=Integer.parseInt( timeData[2].toString());
                    map.put(getSchemeCalendar(y, m, time, 0xFFaacc44, "??????or????????????").toString(),
                            getSchemeCalendar(y, m, time, 0xFFaacc44, "??????or????????????"));
                }

                //??????????????????
                String vaccin_1_Data;
                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getVaccin_1().size();i++){
                    vaccin_1_Data=  getSomeMonthPetsDataUtil.getData().getVaccin_1().get(i);
                    String [] timeData=vaccin_1_Data.split("-");
                    int time=Integer.parseInt( timeData[2].toString());
                    map.put(getSchemeCalendar(y, m, time, 0xFFFF0000, "????????????").toString(),
                            getSchemeCalendar(y, m, time, 0xFFFF0000, "????????????"));
                }

//                map.put(getSchemeCalendar(y, m, 1, 0xFF40db25, "???").toString(),
//                        getSchemeCalendar(y, m, 1, 0xFF40db25, "???"));
//                map.put(getSchemeCalendar(y, m, 2, 0xFFe69138, "???").toString(),
//                        getSchemeCalendar(y, m, 2, 0xFFe69138, "???"));
//                map.put(getSchemeCalendar(y, m, 3, 0xFFdf1356, "???").toString(),
//                        getSchemeCalendar(y, m, 3, 0xFFdf1356, "???"));
//                map.put(getSchemeCalendar(y, m, 4, 0xFFaacc44, "???").toString(),
//                        getSchemeCalendar(y, m, 4, 0xFFaacc44, "???"));
            }
        }


        //28560 ???????????????????????????UI??????????????????????????????API??????
        mCalendarView.setSchemeDate(map);








    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tv_name:
                //????????????
            case R.id.iv_name:
                //????????????
                if (getPetsListDataUtil.getData().size()<=0){
                    ToastManager.showShortToast(RecordActivity.this,"?????????????????????????????????");
                    return;
                }
                getPetsDialog=null;
                getPetsDialog=new GetPetsDialog(RecordActivity.this);
                getPetsDialog.show();

                getPetsDialog.setDialogCallback(new GetPetsDialog.Dialogcallback() {
                    @Override
                    public void selectPets(GetPetsListDataUtil getPetsListDataUtil, int selectNumer) {
                        //????????????????????????
                        tv_name.setText(getPetsListDataUtil.getData().get(selectNumer).getNickname());
                        pets_id=getPetsListDataUtil.getData().get(selectNumer).getId()+"";
                        tv_healthy.setText(getPetsListDataUtil.getData().get(selectNumer).getHealthy());
                        tv_weight.setText(getPetsListDataUtil.getData().get(selectNumer).getWeight()+" kg");
                        getDefaultPetsInfo(getPetsListDataUtil.getData().get(selectNumer).getId()+"");
                        myApplication.setPets_id(pets_id);
                        varieties_id=getPetsListDataUtil.getData().get(selectNumer).getVarieties_id()+"";
                        myApplication.setIs_init(getPetsListDataUtil.getData().get(selectNumer).getIs_init()+"");
                        //???????????????????????????????????????????????????????????????????????????????????????
                        if (getPetsListDataUtil.getData().get(selectNumer).getIs_init()==0){
                            pets_initDialog.show();
                        }else {
                            getPetsData();
                        }


                    }
                });
                break;
            case R.id.tv_timeData:
                //????????????

                break;
            case R.id.iv_timeData:
                //????????????
                break;
            case R.id.btn_add:
                // ????????????
                Intent intent=new Intent();
                intent.setClass(RecordActivity.this, AddPetsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_init:
                //???????????????
                pets_initDialog.show();
                break;
            case R.id.iv_explanation1:
                //??????

                Intent explanation1=new Intent();
                explanation1.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation1.putExtra("title","??????");
                explanation1.putExtra("msg",meal_add);
                explanation1.putExtra("url",Api.GET_PETS_TYPE_MEAL_ADD_ID+pets_id);
                startActivity(explanation1);
                break;
            case R.id.iv_explanation3:
                //????????????
                Intent explanation3=new Intent();
                explanation3.setClass(RecordActivity.this,GetSickTypeActivity.class);
                explanation3.putExtra("title","????????????");
                explanation3.putExtra("msg",pets_id);
                explanation3.putExtra("url",Api.GET_PETS_MALADY_SHOW_ID);
                startActivity(explanation3);
                break;
            case R.id.iv_explanation4:
                //??????
                Intent explanation4=new Intent();
                explanation4.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation4.putExtra("title","??????");
                explanation4.putExtra("msg",meal_add);
                startActivity(explanation4);
                break;
            case R.id.iv_explanation5:
                //??????    //meal_add,feed,shower,vaccin,expelling;
                Intent explanation5=new Intent();
                explanation5.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation5.putExtra("title","??????");
                explanation5.putExtra("msg",expelling);
                //????????????id
                explanation5.putExtra("url",Api.GET_PETS_EXPELLING_ID+varieties_id);
                startActivity(explanation5);
                break;
            case R.id.iv_explanation6:
                //??????
                Intent explanation6=new Intent();
                explanation6.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation6.putExtra("title","??????");
                explanation6.putExtra("msg",vaccin);
                explanation6.putExtra("url",Api.GET_PETS_VACCIN_ID+varieties_id);

                startActivity(explanation6);
                break;
            case R.id.iv_explanation7:
                //????????????
                Intent explanation7=new Intent();
                explanation7.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation7.putExtra("title","????????????");
                explanation7.putExtra("msg",vaccin);
                explanation7.putExtra("url",Api.GET_PETS_VACCIN_ID+varieties_id);
                startActivity(explanation7);
                break;
            case R.id.iv_explanation8:
                //??????
                Intent explanation8=new Intent();
                explanation8.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation8.putExtra("title","??????");
                explanation8.putExtra("msg",shower);
                explanation8.putExtra("url",Api.GET_PETS_SHOWER_ID+varieties_id);
                startActivity(explanation8);
                break;
            case R.id.iv_explanation9:
                //??????
                Intent explanation9=new Intent();
                explanation9.setClass(RecordActivity.this,ExplanationActivity.class);
                explanation9.putExtra("title","??????");
                explanation9.putExtra("msg",feed);
                explanation9.putExtra("url",Api.GET_PETS_FEED_ID+varieties_id);
                startActivity(explanation9);
                break;
            case R.id.iv_setup:
                //????????????
                selectMenu();
                break;
            case R.id.tv_healthy:
                //??????????????????
                pvHealthyOptions.show();
                break;

        }
    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//???????????????????????????????????????????????????
        calendar.setScheme(text);
        return calendar;
    }
    private static String getCalendarText(Calendar calendar) {
        return String.format("??????%s \n ??????%s \n ???????????????%s \n ???????????????%s \n ?????????%s \n ???????????????%s",
                calendar.getYear() + "???" +calendar.getMonth() + "???" + calendar.getDay() + "???",
                calendar.getYear() + "???" +calendar.getLunarCalendar().getMonth() + "???" + calendar.getLunarCalendar().getDay() + "???",
                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "???" : calendar.getGregorianFestival(),
                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "???" : calendar.getTraditionFestival(),
                TextUtils.isEmpty(calendar.getSolarTerm()) ? "???" : calendar.getSolarTerm(),
                calendar.getLeapMonth() == 0 ? "???" : String.format("???%s???", calendar.getLeapMonth()));
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    /**
     *????????????????????????????????????????????????????????????????????????
     * @param calendar
     * @return  ??????????????????????????????????????????MonthView???WeekView????????????API?????????
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        Log.e("onCalendarIntercept", calendar.toString());
        int day = calendar.getDay();
//        return day == 1 || day == 3 || day == 6 || day == 11 || day == 12 || day == 15 || day == 20 || day == 26;
        return day==100;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this, calendar.toString() + "??????????????????", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Toast.makeText(this, String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        //?????????????????????
        Toast.makeText(this, "?????????????????????\n" + getCalendarText(calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        int mYear = calendar.getYear();
        tv_timeData.setText(calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay());
        if (String.valueOf(calendar.getMonth()).length()==1){
            myMonth=calendar.getYear()+"-"+"0"+calendar.getMonth();
        }else {
            myMonth=calendar.getYear()+"-"+calendar.getMonth();
        }

         int year =calendar.getYear();
         int month = calendar.getMonth();
         int day=calendar.getDay();
        if (String.valueOf(month).length()==1){
            if (String.valueOf(day).length()==1){
                myMonth=year+"-"+"0"+month;
                tv_timeData.setText(year+"-"+"0"+month+"-"+"0"+day);
                today=year+"-"+"0"+month+"-"+"0"+day;
            }else {
                myMonth=year+"-"+"0"+month;
                tv_timeData.setText(year+"-"+"0"+month+"-"+day);
                today=year+"-"+"0"+month+"-"+day;
            }

        }else {
            if (String.valueOf(day).length()==1){
                myMonth=year+"-"+month;
                tv_timeData.setText(year+"-"+month+"-"+"0"+day);
                today=year+"-"+month+"-"+"0"+day;
                myMonth=year+"-"+month;
            }else {
                myMonth=year+"-"+month;
                tv_timeData.setText(year+"-"+month+"-"+day);
                today=year+"-"+month+"-"+day;
                myMonth=year+"-"+month;
            }

        }

        getPetsData();
        if (isClick) {
//            Toast.makeText(this, getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMonthChange(int my_year, int my_month) {
        Calendar calendar = mCalendarView.getSelectedCalendar();
        int  mYear = calendar.getYear();
        tv_timeData.setText(calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay());

        if (String.valueOf(calendar.getMonth()).length()==1){
            myMonth=calendar.getYear()+"-"+"0"+calendar.getMonth();
        }else {
            myMonth=calendar.getYear()+"-"+calendar.getMonth();
        }

        int year =calendar.getYear();
        int  month = calendar.getMonth();
        int day=calendar.getDay();
        if (String.valueOf(month).length()==1){
            if (String.valueOf(day).length()==1){
                myMonth=year+"-"+"0"+month;
                tv_timeData.setText(year+"-"+"0"+month+"-"+"0"+day);
                today=year+"-"+"0"+month+"-"+"0"+day;
            }else {
                myMonth=year+"-"+"0"+month;
                tv_timeData.setText(year+"-"+"0"+month+"-"+day);
                today=year+"-"+"0"+month+"-"+day;
            }

        }else {
            if (String.valueOf(day).length()==1){
                myMonth=year+"-"+month;
                tv_timeData.setText(year+"-"+month+"-"+"0"+day);
                today=year+"-"+month+"-"+"0"+day;
                myMonth=year+"-"+month;
            }else {
                myMonth=year+"-"+month;
                tv_timeData.setText(year+"-"+month+"-"+day);
                today=year+"-"+month+"-"+day;
                myMonth=year+"-"+month;
            }

        }

        getPetsData();
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        Log.e("onViewChange", "  ---  " + (isMonthView ? "?????????" : "?????????"));
    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        for (Calendar calendar : weekCalendars) {
            Log.e("onWeekChange", calendar.toString());
        }
    }

    @Override
    public void onYearChange(int year) {
        Log.e("onYearChange", " ???????????? " + year);
    }

    @Override
    public void onYearViewChange(boolean isClose) {
        Log.e("onYearViewChange", "????????? -- " + (isClose ? "??????" : "??????"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserInfo();
        getPetsData();
        getPetsInfo();



    }

    /**
     * ???????????????????????????????????????
     */
    private void getBaseInit() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        GetPetsBaseInitData getPetsBaseInitData=new GetPetsBaseInitData();
        getPetsBaseInitData.setId(pets_id);
        Gson gson=new Gson();
        String recordPetsInitData=gson.toJson(getPetsBaseInitData);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordPetsInitData);

        String url=Api.GET_PETS_PETS_TYPE_GET_TYPE;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsweqifsjdfgvfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            if(code==AppConfig.SUCCESS){
                                Gson gson=new Gson();
                                 getBasePetsDataUtil= gson.fromJson(data, GetBasePetsDataUtil.class);


                                meal_add=getBasePetsDataUtil.getData().getData().getMeal_add();
                                feed=getBasePetsDataUtil.getData().getData().getFeed();
                                shower=getBasePetsDataUtil.getData().getData().getShower();
                                vaccin=getBasePetsDataUtil.getData().getData().getVaccin();
                                expelling=getBasePetsDataUtil.getData().getData().getExpelling();

//                                 if (!getBasePetsDataUtil.getData().equals("null")) {
//                                     tv_weight.setText(getBasePetsDataUtil.getData().getData().getWeight() + "");
//                                     tv_feed.setText(getBasePetsDataUtil.getData().getData().getFeed() + "");
//                                     tv_extra_meal.setText(getBasePetsDataUtil.getData().getData().getMeal_add() + "");
//
//                                 }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){


                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });

    }

    /**
     * ???????????????????????????
     */
    private void getPetsData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(pets_id)){
//            ToastManager.showShortToast(RecordActivity.this,"???????????????????????????");
            return;
        }

        GetPetsData getPetsData=new GetPetsData();
        getPetsData.setToken(token);
        getPetsData.setMypets_id(pets_id);
        Gson gson=new Gson();
        String recordPetsInitData=gson.toJson(getPetsData);

        Log.i("safjsafisfjsfsf",recordPetsInitData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordPetsInitData);


        Log.i("dsfsifsifsmfsfbvv",token);
        Log.i("dsfsifsifsmfsfbvv",myMonth);
        Log.i("dsfsifsifsmfsfbvv",pets_id);
        OkGo.<String>post(Api.GET_USER_SCHEDULE_SOME_MONTH).tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjserfsiawfsgvfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            if(code==AppConfig.SUCCESS){
                                Gson gson=new Gson();
                                GetSomeMonthPetsDataUtil getSomeMonthPetsDataUtil= gson.fromJson(data, GetSomeMonthPetsDataUtil.class);
                                //????????????????????????
                                String bath;
                                if (getSomeMonthPetsDataUtil.getData()==null){
                                    return;
                                }
                                if (getSomeMonthPetsDataUtil.getData().getScedule()==null){
                                    return;
                                }

                               int bathSize=getSomeMonthPetsDataUtil.getData().getScedule().getBath().size();
                                String [] bathArray=new String[bathSize];
                                Log.i("dsfWERYFsifsifsmfsfbvv",today);
                                switch_bathing.setChecked(false);
                                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getScedule().getBath().size();i++){
                                    bath=  getSomeMonthPetsDataUtil.getData().getScedule().getBath().get(i).toString();
//                                    Log.i("dsfWERYFsifsifsmfsfbvv","bath   "+bath);
                                    if (bath.equals(today)){

                                        switch_bathing.setChecked(true);
//                                        switch_bathing.setClickable(false);
                                    }else {
//                                        switch_bathing.setChecked(false);
//                                        switch_bathing.setClickable(true);
                                    }
                                }

                                //???????????????????????????
                                String vaccine1,vaccine2;
                                sw_vaccine.setChecked(false);
                                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getScedule().getVaccin_0().size();i++){
                                    vaccine1=  getSomeMonthPetsDataUtil.getData().getScedule().getVaccin_0().get(i).toString();
                                    if (vaccine1.equals(today)){
                                        sw_vaccine.setChecked(true);
//                                        sw_vaccine.setClickable(false);
                                    }else {

//                                        sw_vaccine.setClickable(true);
                                    }
                                }
                                //??????????????????????????????????????????
                                sw_rabies_vaccine.setChecked(false);
                                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getScedule().getVaccin_1().size();i++){
                                    vaccine2=  getSomeMonthPetsDataUtil.getData().getScedule().getVaccin_1().get(i).toString();
                                    if (vaccine2.equals(today)){
                                        sw_rabies_vaccine.setChecked(true);
//                                        sw_rabies_vaccine.setClickable(false);
                                    }else {

//                                        sw_rabies_vaccine.setClickable(true);
                                    }
                                }
                                //??????????????????
                                String repellent;
                                sw_repellent.setChecked(false);
                                for (int i=0;i<getSomeMonthPetsDataUtil.getData().getScedule().getExpelling().size();i++){
                                    repellent=  getSomeMonthPetsDataUtil.getData().getScedule().getExpelling().get(i).toString();
                                    if (repellent.equals(today)){
                                        sw_repellent.setChecked(true);
//                                        sw_repellent.setClickable(false);
                                    }else {

//                                        sw_repellent.setClickable(true);
                                    }
                                }


                                //?????????????????????
                                calendarInitData(getSomeMonthPetsDataUtil);



                                getBaseInit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){


                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sfjsifjsfisfsfsf",response.message()+"    "+response.code()+"    "+response.getException().getMessage());
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });

    }

    /**
     * ????????????
     * type 0????????? 1????????? 2?????????
     * value  ??????????????? 0????????????????????????????????? 1??????????????? ????????????????????????true
     */
    private void sendPetsData(String type,String value) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(pets_id)){
//            ToastManager.showShortToast(RecordActivity.this,"???????????????????????????");
            return;
        }
        SendRecordPetsData sendRecordPetsData=new SendRecordPetsData();
        sendRecordPetsData.setMypets_id(pets_id);
        sendRecordPetsData.setToken(token);
        sendRecordPetsData.setType(type);
        sendRecordPetsData.setValue(value);
        sendRecordPetsData.setDay(tv_timeData.getText().toString());
        Gson gson=new Gson() ;
        String recordPetsData=gson.toJson(sendRecordPetsData);
        Log.i("sfjsifnsidfsnfsf",recordPetsData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordPetsData);

        Log.i("dsfsifsifsmfsfbvv",token);
        Log.i("dsfsifsifsmfsfbvv",myMonth);
        Log.i("dsfsifsifsmfsfbvv",pets_id);
        OkGo.<String>post(Api.SEND_USER_SCHEDULE_WRITE).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsifsjdfgvfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            if(code==AppConfig.SUCCESS){

                            }
                            ToastManager.showShortToast(RecordActivity.this,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){


                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sfjsifjsfisfsfsf",response.message()+"    "+response.code()+"    "+response.getException().getMessage());
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });

    }

    /**
     * ??????????????????
     */
    private void getUserInfo(){

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)){
            //??????????????????
            Intent login=new Intent();
            login.setClass(RecordActivity.this, LoginActivity.class);
            startActivity(login);
        }
        OkGo.<String>post(Api.GET_USER_INFO).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsifsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){
                            //?????????????????????????????????
                            Gson gson=new Gson();
                            GetUserInfoUtil getUserInfoUtil=  gson.fromJson(data, GetUserInfoUtil.class);

                        }else {
                            //????????????
                            Intent login=new Intent();
                            login.setClass(RecordActivity.this, LoginActivity.class);
                            startActivity(login);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });
    }
    /**
     * ??????????????????
     */
    private void getPetsInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(Api.GET_PETS_INFO).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsiewfsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS) {
                            Gson gson = new Gson();
                            getPetsListDataUtil = gson.fromJson(data, GetPetsListDataUtil.class);


                            if (getPetsListDataUtil.getData().size() > 0) {
                                //?????????
                                varieties_id=getPetsListDataUtil.getData().get(0).getVarieties_id()+"";
//                                //????????????
//                                llt_pets.setVisibility(View.VISIBLE);
//                                llt_no_pets.setVisibility(View.GONE);
//                                getPetsDialog=null;
//                                getPetsDialog=new GetPetsDialog(RecordActivity.this);
//                                getPetsDialog.show();
//
//
//                                getPetsDialog.setDialogCallback(new GetPetsDialog.Dialogcallback() {
//                                    @Override
//                                    public void selectPets(GetPetsListDataUtil getPetsListDataUtil, int selectNumer) {
//                                        //????????????????????????
//                                        tv_name.setText(getPetsListDataUtil.getData().get(selectNumer).getNickname());
//                                        pets_id=getPetsListDataUtil.getData().get(selectNumer).getId()+"";
//                                        tv_healthy.setText(getPetsListDataUtil.getData().get(selectNumer).getHealthy());
//                                        tv_weight.setText(getPetsListDataUtil.getData().get(selectNumer).getWeight()+" kg");
//
//                                        getDefaultPetsInfo(getPetsListDataUtil.getData().get(selectNumer).getId()+"");
//
//                                        //???????????????????????????????????????????????????????????????????????????????????????
//                                        if (getPetsListDataUtil.getData().get(selectNumer).getIs_init()==0){
//                                            pets_initDialog.show();
//                                        }else {
//                                            getPetsData();
//                                        }
//
//
//                                    }
//                                });


                                getDefaultPetsInfo(myApplication.getPets_id()+"");
                                pets_id=myApplication.getPets_id()+"";




                            } else {
                                //?????????
                                llt_pets.setVisibility(View.GONE);
                                llt_no_pets.setVisibility(View.VISIBLE);
                                ToastManager.showShortToast(RecordActivity.this,"???????????????????????????????????????????????????");

                            }

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });
    }

    /**
     * ?????????????????????????????????????????????
     */
    private void getDefaultPetsInfo(String mypets_id){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(Api.GET_PETS_ABOUT_GET_DEFAULT).tag(this)
                .params("token",token)
                .params("mypets_id",mypets_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsidferqssffsjfsf",data);
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
                        if (code== AppConfig.SUCCESS){
                            //????????????
                            Gson gson=new Gson();
                            GetDefaultInfoUtil getDefaultInfoUtil=  gson.fromJson(data, GetDefaultInfoUtil.class);
                            if (getDefaultInfoUtil.getData()==null){
                                return;
                            }

                            //???????????????????????????????????????????????????????????????????????????????????????
                            if (getDefaultInfoUtil.getData().getIs_init()==0){
                                llt_pets.setVisibility(View.GONE);
                                llt_no_pets.setVisibility(View.GONE);
                                llt_no_init.setVisibility(View.VISIBLE);

                                pets_initDialog.show();
                            }else {

                                llt_no_pets.setVisibility(View.GONE);
                                llt_no_init.setVisibility(View.GONE);
                                llt_pets.setVisibility(View.VISIBLE);
                                getPetsData();
                            }

                            tv_name.setText(getDefaultInfoUtil.getData().getNickname());
                            pets_id=getDefaultInfoUtil.getData().getId()+"";
                            tv_healthy.setText(getDefaultInfoUtil.getData().getHealthy());
                            tv_weight.setText(getDefaultInfoUtil.getData().getWeight()+" kg");
                            tv_feed.setText(getDefaultInfoUtil.getData().getBody_status().getFeed().getFeed()+" kg");
                            String petsMsg="";
                            pid=getDefaultInfoUtil.getData().getPid();

                            if (getDefaultInfoUtil.getData().getDo_some().getBath()==0){
                                //????????????
                                petsMsg=petsMsg+" ??????";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getExpelling()==0){
                                //????????????
                                petsMsg=petsMsg+" ??????";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getVaccin_1()==0){
                                //??????????????????
                                petsMsg=petsMsg+" ????????????";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getVaccin_0()==0){
                                //????????????or????????????
                                petsMsg=petsMsg+" ?????????????????????";
                            }

                            if (getDefaultInfoUtil.getData().getBody_status().getWeight()==null){
                                //??????
                                tv_null.setVisibility(View.VISIBLE);
                                llt_weight.setVisibility(View.GONE);
                            }else {
                                //??????
                                tv_null.setVisibility(View.GONE);
                                llt_weight.setVisibility(View.VISIBLE);
                            }




                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });
    }


    /**
     * ????????????
     */
    private void selectMenu() {
        if(updatePetsMenu==null) {
            //????????????????????????
            OnClickLintener paramOnClickListener = new OnClickLintener();
            //??????????????????????????????????????????????????????
            updatePetsMenu = new UpdatePetsMenu(this,paramOnClickListener,
                    DisplayUtil.dip2px(this, 130), DisplayUtil.dip2px(this, 145));
            //???????????????????????????????????????????????????????????????
            updatePetsMenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        updatePetsMenu.dismiss();
                    }
                }
            });
        }
        //????????????????????????
        updatePetsMenu.setFocusable(true);
        //??????????????????x???y????????????????????????????????????
        updatePetsMenu.showAsDropDown(iv_setup, 0, 0);
        //??????????????????????????????
        updatePetsMenu.update();
    }
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_switch:
                    //????????????
                    updatePetsMenu.dismiss();
                    //????????????
                    if (getPetsListDataUtil.getData().size()<=0){
                        ToastManager.showShortToast(RecordActivity.this,"?????????????????????????????????");
                        return;
                    }
                    getPetsDialog=null;
                    getPetsDialog=new GetPetsDialog(RecordActivity.this);
                    getPetsDialog.show();

                    getPetsDialog.setDialogCallback(new GetPetsDialog.Dialogcallback() {
                        @Override
                        public void selectPets(GetPetsListDataUtil getPetsListDataUtil, int selectNumer) {
                            //????????????????????????
                            tv_name.setText(getPetsListDataUtil.getData().get(selectNumer).getNickname());
                            pets_id=getPetsListDataUtil.getData().get(selectNumer).getId()+"";
                            tv_healthy.setText(getPetsListDataUtil.getData().get(selectNumer).getHealthy());
                            tv_weight.setText(getPetsListDataUtil.getData().get(selectNumer).getWeight()+" kg");
                            getDefaultPetsInfo(getPetsListDataUtil.getData().get(selectNumer).getId()+"");
                            myApplication.setPets_id(pets_id);
                            varieties_id=getPetsListDataUtil.getData().get(selectNumer).getVarieties_id()+"";
                            myApplication.setIs_init(getPetsListDataUtil.getData().get(selectNumer).getIs_init()+"");
                            //???????????????????????????????????????????????????????????????????????????????????????
                            if (getPetsListDataUtil.getData().get(selectNumer).getIs_init()==0){
                                pets_initDialog.show();
                            }else {
                                getPetsData();
                            }


                        }
                    });
                    break;
                case R.id.layout_bath:
                    //????????????
                    updatePetsMenu.dismiss();
                    mOptionsPickerView.show();
                    recordTime=1;
                    break;
                case R.id.layout_deworming:
                    //????????????
                    updatePetsMenu.dismiss();
                    mOptionsPickerView.show();
                    recordTime=2;
                    break;
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private void initNumberPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        mOptionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
                String tx = numberItem.get(options1).getPickerViewText();
                tv_weight.setText(tx);
                bath(1,tx);

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
                                mOptionsPickerView.returnData();
                                mOptionsPickerView.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsPickerView.dismiss();
                            }
                        });
                        getWeightData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        mOptionsPickerView.setPicker(numberItem);//????????????


    }
    private void getWeightData() {

        for (int i=0;i<1000;i++){
            numberItem.add(new CardBean(0,""+(i+1)));
        }

        for (int i = 0; i < 2; i++) {
            numberItem.get(i).setCardNo(numberItem.get(i).getCardNo());
        }

    }


    /**
     * ????????????????????????
     */
    private void initHealthyCustomOptionPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        pvHealthyOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
                String tx = cardHealthyItem.get(options1).getPickerViewText();
                tv_healthy.setText(tx);
                editPetsData(Api.GET_PETS_UPDATA_PETS);

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
                                pvHealthyOptions.returnData();
                                pvHealthyOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvHealthyOptions.dismiss();
                            }
                        });
                        getHealthyCardData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvHealthyOptions.setPicker(cardHealthyItem);//????????????


    }
    private void getHealthyCardData() {

        cardHealthyItem.add(new CardBean(0,"??????"));
        cardHealthyItem.add(new CardBean(1,"??????"));

        for (int i = 0; i < 2; i++) {
            cardHealthyItem.get(i).setCardNo(cardHealthyItem.get(i).getCardNo());
        }

    }

    /**
     * ????????????
     */
    private void bath(int type ,String data){

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        SendBathData   sendBathData =new SendBathData();

        sendBathData.setToken(token);
        sendBathData.setMypets_id(pets_id);
        if (type==1) {
            sendBathData.setBath_inter(data);
        }else {
            sendBathData.setExpelling_inter(data);
        }

        Gson gson=new Gson() ;
        String recordPetsData=gson.toJson(sendBathData);
        Log.i("sfjsifnsidfsnfsf",recordPetsData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordPetsData);
        OkGo.<String>post(Api.GET_USER_SCHEDULE_V2_EDIT_RULE).tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfksfsfiskinvfnff",data);
                        JSONObject jsonObject=null;
                        String msg;
                        int code=1;

                        try {
                             jsonObject=new JSONObject(data);
                            msg=jsonObject.getString("message");
                            ToastManager.showShortToast(RecordActivity.this,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void editPetsData(String url){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        healthy=tv_healthy.getText().toString();
        if (TextUtils.isEmpty(healthy)){
            healthy="??????";
        }

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("healthy",healthy)
                .params("pid",pid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sfsjfisjfsifsivnvv",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            ToastManager.showShortToast(RecordActivity.this,msg);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(RecordActivity.this,"????????????");
                    }
                });
    }
}
