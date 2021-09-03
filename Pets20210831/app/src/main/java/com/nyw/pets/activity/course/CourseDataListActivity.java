package com.nyw.pets.activity.course;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.nyw.pets.activity.util.GetCurriculumDataListUtil;
import com.nyw.pets.activity.util.GetCurriculumListUtil;
import com.nyw.pets.activity.util.GetHotVideoDataUtil;
import com.nyw.pets.adapter.CurriculumListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程列表
 */
public class CourseDataListActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private CurriculumListAdapter curriculumListAdapter;
    private List<GetCurriculumListUtil> curriculumList=new ArrayList<>();
    private String type;
    //数据分页
    private int page=1,limit=15;
    //刷新
    private MeiTuanRefreshView refreshview;
    //分类  id
    private String type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_data_list);
        initView();
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        String title="";
        try {
            Bundle bundle = getIntent().getExtras();
             title = bundle.getString("title");
            type = bundle.getString("type");
            type_id = bundle.getString("id");
        }catch (Exception e){}
        tv_title.setText(title);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        //数据
        rcv_data=findViewById(R.id.rcv_data);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_data.setLayoutManager(gridLayoutManager);
//        for (int j=0;j<5;j++){
//            GetCurriculumListUtil getCurriculumListUtil=new GetCurriculumListUtil();
//            getCurriculumListUtil.setTitle("这是热门课程"+j);
//            getCurriculumListUtil.setLearningNumber(j+26+"人已学习");
//            curriculumList.add(getCurriculumListUtil);
//        }
        curriculumListAdapter=new CurriculumListAdapter(CourseDataListActivity.this,curriculumList);
        rcv_data.setAdapter(curriculumListAdapter);

        if (type.equals("hot")){
            getHotVoice();
        }else {
            getVoiceListData();
        }


        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                curriculumList.clear();
                if (type.equals("hot")){
                    getHotVoice();
                }else {
                    getVoiceListData();
                }
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                if (type.equals("hot")){
                    getHotVoice();
                }else {
                    getVoiceListData();
                }
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

    /**
     * 获取所有分类视频数据
     */
    private void getVoiceListData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        OkGo.<String>post(Api.GET_COURSE_COURSE_GET_TYPE_VIDEO).tag(this)
                .params("token",token)
                .params("type_id",type)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsiopptuuwpechdlfsf",data);
                        Gson gson=new Gson();
                        GetCurriculumDataListUtil getCurriculumDataListUtil=   gson.fromJson(data, GetCurriculumDataListUtil.class);
                        for (int i=0;i<getCurriculumDataListUtil.getData().getList().size();i++){
                            GetCurriculumListUtil getCurriculumListUtil=new GetCurriculumListUtil();
                            getCurriculumListUtil.setTitle(getCurriculumDataListUtil.getData().getList().get(i).getTitle());
                            getCurriculumListUtil.setLearningNumber(getCurriculumDataListUtil.getData().getList().get(i).getStudy_number()+"人已学习");
                            getCurriculumListUtil.setId(getCurriculumDataListUtil.getData().getList().get(i).getId()+"");
                            getCurriculumListUtil.setPath(getCurriculumDataListUtil.getData().getList().get(i).getServer());
                            getCurriculumListUtil.setImg(getCurriculumDataListUtil.getData().getList().get(i).getImg()+"");
                            getCurriculumListUtil.setType(type+"");
                            curriculumList.add(getCurriculumListUtil);
                        }
                        curriculumListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseDataListActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取热门视频
     */
    private void getHotVoice() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


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

                            GetCurriculumListUtil getCurriculumListUtil=new GetCurriculumListUtil();
                            getCurriculumListUtil.setTitle(getHotVideoDataUtil.getData().getList().get(i).getTitle());
                            getCurriculumListUtil.setLearningNumber(getHotVideoDataUtil.getData().getList().get(i).getStudy_number()+"人已学习");
                            getCurriculumListUtil.setId(getHotVideoDataUtil.getData().getList().get(i).getId()+"");
                            getCurriculumListUtil.setImg(
                                    getHotVideoDataUtil.getData().getList().get(i).getImg()+"");
                            getCurriculumListUtil.setType(getHotVideoDataUtil.getData().getList().get(i).getType_id()+"");
                            curriculumList.add(getCurriculumListUtil);


                        }
                        curriculumListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseDataListActivity.this,"网络错误");
                    }
                });
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                //关闭
                finish();
                break;

        }
    }
}
