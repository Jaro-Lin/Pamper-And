package com.nyw.pets.activity;

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
import com.nyw.pets.activity.util.GetDynamicDataUtil;
import com.nyw.pets.adapter.RecommendAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.fragment.util.RecommendUtil;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索动态
 */
public class SearchDynamicActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText ct_msg;
    private ImageView iv_hide;
    //分页
    private int page=1,limit=100;
    private RecyclerView rcv_data;
    //数据
    private RecommendAdapter recommendAdapter;
    private List<RecommendUtil> recommendList = new ArrayList<>();
    private TextView tv_search;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dynamic);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_msg=findViewById(R.id.ct_msg);
        tv_search=findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        ct_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                getSearchInfo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rcv_data=findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_data.setLayoutManager(linearLayoutManager);
        recommendAdapter=new RecommendAdapter(this,recommendList);
        rcv_data.setAdapter(recommendAdapter);
        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                recommendList.clear();
                getSearchInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getSearchInfo();
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
                getSearchInfo();
                break;
        }
    }

    /**
     * 搜索动态信息
     */
    private void getSearchInfo() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        OkGo.<String>post(Api.GET_POST_ABOUT__SEARCH_POST).tag(this)
                .params("token",token)
                .params("content",ct_msg.getText().toString())
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("slfdjsidfsfmxvxcvxxvb",data);
                        Gson gson=new Gson();
                        GetDynamicDataUtil getDynamicDataUtil=gson.fromJson(data, GetDynamicDataUtil.class);


                        if ( getDynamicDataUtil.getData().getList()!=null) {
                            for (int i = 0; i < getDynamicDataUtil.getData().getList().size(); i++) {
                                RecommendUtil recommendUtil = new RecommendUtil();
                                recommendUtil.setUid(getDynamicDataUtil.getData().getList().get(i).getUid());
                                recommendUtil.setPost_id(getDynamicDataUtil.getData().getList().get(i).getPost_id());
                                recommendUtil.setName(getDynamicDataUtil.getData().getList().get(i).getNickname());
                                recommendUtil.setTime(getDynamicDataUtil.getData().getList().get(i).getPost_time());
                                recommendUtil.setMsg(getDynamicDataUtil.getData().getList().get(i).getContent());
                                recommendUtil.setComment(getDynamicDataUtil.getData().getList().get(i).getComment() + "");
                                recommendUtil.setThumbs(getDynamicDataUtil.getData().getList().get(i).getGood() + "");
                                recommendUtil.setCollection(getDynamicDataUtil.getData().getList().get(i).getCollection() + "");

                                recommendUtil.setIf_collection(getDynamicDataUtil.getData().getList().get(i).getIf_collection() + "");
                                recommendUtil.setIf_good(getDynamicDataUtil.getData().getList().get(i).getIf_good() + "");

                                recommendUtil.setServer(getDynamicDataUtil.getData().getList().get(i).getServer());
                                recommendUtil.setImg(getDynamicDataUtil.getData().getList().get(i).getAvatar());

                                recommendUtil.setComment_id( getDynamicDataUtil.getData().getList().get(i).getComment_id());


                                ArrayList<MyMedia> mediaList10 = new ArrayList<>();

                                if (getDynamicDataUtil.getData().getList().get(i).getImages().size() >= 1
                                        && !getDynamicDataUtil.getData().getList().get(i).getImages().equals("[]")
                                        && getDynamicDataUtil.getData().getList().get(i).getImages() != null) {
                                    for (int j = 0; j < getDynamicDataUtil.getData().getList().get(i).getImages().size(); j++) {
                                        MyMedia myMedia = new MyMedia();

                                        myMedia.setImageUrl(getDynamicDataUtil.getData().getList().get(i).getServer()
                                                + getDynamicDataUtil.getData().getList().get(i).getImages().get(j).getAddress());
                                        mediaList10.add(myMedia);
                                    }
                                    recommendUtil.setImg(true);

                                } else {
                                    recommendUtil.setImg(false);
                                }

                                if (getDynamicDataUtil.getData().getList().get(i).getVideo().size() >= 1
                                        && !getDynamicDataUtil.getData().getList().get(i).getVideo().equals("[]")
                                        && getDynamicDataUtil.getData().getList().get(i).getVideo() != null) {
                                    for (int j = 0; j < getDynamicDataUtil.getData().getList().get(i).getVideo().size(); j++) {
                                        MyMedia myMedia = new MyMedia();

                                        myMedia.setVideoUrl(getDynamicDataUtil.getData().getList().get(i).getServer()
                                                + getDynamicDataUtil.getData().getList().get(i).getVideo().get(j).getAddress());
                                        mediaList10.add(myMedia);
                                    }
                                    recommendUtil.setVideo(true);
                                } else {
                                    recommendUtil.setVideo(false);
                                }


                                recommendUtil.setMediaList(mediaList10);
                                recommendList.add(recommendUtil);


                            }
                        }
                        recommendAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(SearchDynamicActivity.this,"网络错误");
                    }
                });
    }

}
