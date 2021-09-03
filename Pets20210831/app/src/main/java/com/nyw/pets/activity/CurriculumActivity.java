package com.nyw.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.course.CourseDataListActivity;
import com.nyw.pets.activity.course.CourseTypeActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.GetCurriculumDataTypeUtil;
import com.nyw.pets.activity.util.GetCurriculumTypeUtil;
import com.nyw.pets.activity.util.GetHotCurriculumUtil;
import com.nyw.pets.activity.util.GetHotVideoDataUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.adapter.CurriculumHotAdapter;
import com.nyw.pets.adapter.CurriculumTypeAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程
 */
public class CurriculumActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rcv_data;
    private CurriculumTypeAdapter curriculumTypeAdapter;
    private List<GetCurriculumTypeUtil> curriculumList=new ArrayList<>();
    //热门课程
    private RecyclerView rcv_hot_data;
    private CurriculumHotAdapter curriculumHotAdapter;
    private List<GetHotCurriculumUtil>  hotCurriculmList=new ArrayList<>();

    private LinearLayout llt_introduction,llt_base,llt_advanced,llt_professional,llt_hotType
            ,llt_hotCurriculum;
    //课程分类
    private GetCurriculumDataTypeUtil getCurriculumDataTypeUtil;

    private int page=1,limit=15;
    private  String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        initView();
    }

    private void initView() {
        rcv_data=findViewById(R.id.rcv_data);
        llt_introduction=findViewById(R.id.llt_introduction);
        llt_introduction.setOnClickListener(this);
        llt_base=findViewById(R.id.llt_base);
        llt_base.setOnClickListener(this);
        llt_advanced=findViewById(R.id.llt_advanced);
        llt_advanced.setOnClickListener(this);
        llt_professional=findViewById(R.id.llt_professional);
        llt_professional.setOnClickListener(this);
        llt_hotType=findViewById(R.id.llt_hotType);
        llt_hotType.setOnClickListener(this);
        llt_hotCurriculum=findViewById(R.id.llt_hotCurriculum);
        llt_hotCurriculum.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_data.setLayoutManager(gridLayoutManager);
//        for (int i=0;i<8;i++){
//            GetCurriculumTypeUtil getCurriculumTypeUtil=new GetCurriculumTypeUtil();
//            getCurriculumTypeUtil.setTypeName("分类测试"+i);
//            curriculumList.add(getCurriculumTypeUtil);
//        }
        curriculumTypeAdapter=new CurriculumTypeAdapter(CurriculumActivity.this,curriculumList);
        rcv_data.setAdapter(curriculumTypeAdapter);

        //热门课程
        rcv_hot_data=findViewById(R.id.rcv_hot_data);
        LinearLayoutManager layoutManager=  new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_hot_data.setLayoutManager(layoutManager);

//        for (int j=0;j<5;j++){
//            GetHotCurriculumUtil getHotCurriculumUtil=new GetHotCurriculumUtil();
//            getHotCurriculumUtil.setTitle("这是热门课程"+j);
//            getHotCurriculumUtil.setLearningNumber(j+26+"人已学习");
//            hotCurriculmList.add(getHotCurriculumUtil);
//        }
        curriculumHotAdapter=new CurriculumHotAdapter(CurriculumActivity.this,hotCurriculmList);
        rcv_hot_data.setAdapter(curriculumHotAdapter);



    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        hotCurriculmList.clear();
        curriculumList.clear();
        getVoiceType();
        getHotVoice();
    }
    /**
     * 获取用户信息
     */
    private void getUserInfo(){

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(CurriculumActivity.this, LoginActivity.class);
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
                            //登录后的正常获取到数据
                            Gson gson=new Gson();
                            GetUserInfoUtil getUserInfoUtil=  gson.fromJson(data, GetUserInfoUtil.class);

                        }else {
                            //未登录的
                            Intent login=new Intent();
                            login.setClass(CurriculumActivity.this, LoginActivity.class);
                            startActivity(login);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CurriculumActivity.this,"网络错误");
                    }
                });
    }
    /**
     * 获取视频分类
     */
    private void getVoiceType() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, MODE_PRIVATE);
         token= getUser.getString(SaveAPPData.TOKEN,null);
        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(CurriculumActivity.this, LoginActivity.class);
            startActivity(login);
            return;
        }
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.GET_COURSE_GET_TYPE).tag(this)
                .params("token",token)
                .params("type","rec")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                        Log.i("sdfjsiopwerptuuwpsf",data);
                        Gson gson=new Gson();
                         getCurriculumDataTypeUtil=   gson.fromJson(data, GetCurriculumDataTypeUtil.class);
                         for (int i=0;i<getCurriculumDataTypeUtil.getData().size();i++){
                             if (i<10) {
                                 GetCurriculumTypeUtil getCurriculumTypeUtil = new GetCurriculumTypeUtil();
                                 getCurriculumTypeUtil.setTypeName(getCurriculumDataTypeUtil.getData().get(i).getType_title());
                                 getCurriculumTypeUtil.setId(getCurriculumDataTypeUtil.getData().get(i).getId() + "");
                                 getCurriculumTypeUtil.setType("rec");
                                 curriculumList.add(getCurriculumTypeUtil);
                             }
                         }
                        curriculumTypeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CurriculumActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取热门视频
     */
    private void getHotVoice() {


        OkGo.<String>post(Api.GET_COURSE_GET_POPULAR).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsiopptuuwpechdlfsf",data);
                        Gson gson=new Gson();
                        GetHotVideoDataUtil getHotVideoDataUtil=   gson.fromJson(data, GetHotVideoDataUtil.class);
                        for (int i=0;i<getHotVideoDataUtil.getData().getList().size();i++){

                            if (i<10) {
                                GetHotCurriculumUtil getHotCurriculumUtil = new GetHotCurriculumUtil();
                                getHotCurriculumUtil.setTitle(getHotVideoDataUtil.getData().getList().get(i).getTitle());
                                getHotCurriculumUtil.setLearningNumber(getHotVideoDataUtil.getData().getList().get(i).getStudy_number() + " 人已学习");

                                getHotCurriculumUtil.setId(getHotVideoDataUtil.getData().getList().get(i).getId() + "");
                                getHotCurriculumUtil.setImg(getHotVideoDataUtil.getData().getList().get(i).getServer() +
                                        getHotVideoDataUtil.getData().getList().get(i).getImg());

                                getHotCurriculumUtil.setType(getHotVideoDataUtil.getData().getList().get(i).getType_id() + "");

                                hotCurriculmList.add(getHotCurriculumUtil);
                            }


                        }
                        curriculumHotAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CurriculumActivity.this,"网络错误");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.llt_introduction:
                //打开入门
                Intent introduction=new Intent();
                introduction.setClass(CurriculumActivity.this, CourseDataListActivity.class);
                introduction.putExtra("title","入门");
                introduction.putExtra("type","1");
                startActivity(introduction);
                break;
            case R.id.llt_base:
                //基础
                Intent base=new Intent();
                base.setClass(CurriculumActivity.this, CourseDataListActivity.class);
                base.putExtra("title","基础");
                base.putExtra("type","2");
                startActivity(base);
                break;
            case R.id.llt_advanced:
                //进阶
                Intent advanced=new Intent();
                advanced.setClass(CurriculumActivity.this, CourseDataListActivity.class);
                advanced.putExtra("title","进阶");
                advanced.putExtra("type","3");
                startActivity(advanced);
                break;
            case R.id.llt_professional:
                //专业
                Intent professional=new Intent();
                professional.setClass(CurriculumActivity.this, CourseDataListActivity.class);
                professional.putExtra("title","专业");
                professional.putExtra("type","4");
                startActivity(professional);
                break;
            case R.id.llt_hotType:
                //热门课程分类，全部分类
                Intent all=new Intent();
                all.setClass(CurriculumActivity.this, CourseTypeActivity.class);
                startActivity(all);
                break;
            case R.id.llt_hotCurriculum:
                //热门课程，查看全部
                Intent hotCurriculum=new Intent();
                hotCurriculum.setClass(CurriculumActivity.this, CourseDataListActivity.class);
                hotCurriculum.putExtra("title","热门课程");
                hotCurriculum.putExtra("type","hot");
                startActivity(hotCurriculum);
                break;
        }
    }
}
