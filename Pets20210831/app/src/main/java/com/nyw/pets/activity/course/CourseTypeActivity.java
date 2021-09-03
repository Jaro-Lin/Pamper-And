package com.nyw.pets.activity.course;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.CourseTypeUtil;
import com.nyw.pets.activity.util.GetAllCurriculumDataTypeUtil;
import com.nyw.pets.activity.util.GetCurriculumDataTypeUtil;
import com.nyw.pets.adapter.CourseTypeAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程分类
 */
public class CourseTypeActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rcv_data;
    private CourseTypeAdapter courseTypeAdapter;
    private List<CourseTypeUtil> courseList=new ArrayList<>();
    //课程分类
    private GetCurriculumDataTypeUtil getCurriculumDataTypeUtil;
    //搜索
    private ClearEditText ct_msg;
    private    int limit=15,page=1;
    private ImageView iv_hide;
    //刷新
    private MeiTuanRefreshView refreshview;
    private TextView tv_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);
        initView();
    }

    private void initView() {
        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
        courseTypeAdapter=new CourseTypeAdapter(CourseTypeActivity.this,courseList);
        rcv_data.setAdapter(courseTypeAdapter);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_msg=findViewById(R.id.ct_msg);
        refreshview=findViewById(R.id.refreshview);
        tv_search=findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        ct_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                courseList.clear();
//                searchVoiceType();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getVoiceType();

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                courseList.clear();
                getVoiceType();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getVoiceType();
            }
        });

    }
    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    refreshview.stopRefresh(true);
                    break;
                case 1:
                    refreshview.stopLoadMore(true);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.tv_search:
                //搜索
                courseList.clear();
                searchVoiceType();
                break;
        }
    }

    /**
     * 搜索课程分类
     */
    private void searchVoiceType() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        OkGo.<String>post(Api.SEARCH_COURSE_TYPE).tag(this)
                .params("token",token)
                .params("content",ct_msg.getText().toString())
                .params("page","1")
                .params("limit","1000")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsiopptuuwpechdlfsf",data);
                        Gson gson=new Gson();
                        GetAllCurriculumDataTypeUtil getAllCurriculumDataTypeUtil=   gson.fromJson(data, GetAllCurriculumDataTypeUtil.class);
                        if (getAllCurriculumDataTypeUtil.getData()!=null) {
                            for (int i = 0; i < getAllCurriculumDataTypeUtil.getData().getList().size(); i++) {
                                CourseTypeUtil getCurriculumTypeUtil = new CourseTypeUtil();
                                getCurriculumTypeUtil.setName(getAllCurriculumDataTypeUtil.getData().getList().get(i).getType_title());
                                getCurriculumTypeUtil.setId(getAllCurriculumDataTypeUtil.getData().getList().get(i).getId() + "");
                                getCurriculumTypeUtil.setType("rec");
                                courseList.add(getCurriculumTypeUtil);
                            }
                        }
                        courseTypeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseTypeActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取课程分类
     */
    private void getVoiceType() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.GET_COURSE_GET_TYPE).tag(this)
                .params("token",token)
                .params("type","rec")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsiopptuuwpechdlfsf",data);
                        Gson gson=new Gson();
                        getCurriculumDataTypeUtil=   gson.fromJson(data, GetCurriculumDataTypeUtil.class);
                        for (int i=0;i<getCurriculumDataTypeUtil.getData().size();i++){
                            CourseTypeUtil getCurriculumTypeUtil=new CourseTypeUtil();
                            getCurriculumTypeUtil.setName(getCurriculumDataTypeUtil.getData().get(i).getType_title());
                            getCurriculumTypeUtil.setId(getCurriculumDataTypeUtil.getData().get(i).getId()+"");
                            getCurriculumTypeUtil.setType("rec");
                            courseList.add(getCurriculumTypeUtil);
                        }
                        courseTypeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseTypeActivity.this,"网络错误");
                    }
                });
    }

}
