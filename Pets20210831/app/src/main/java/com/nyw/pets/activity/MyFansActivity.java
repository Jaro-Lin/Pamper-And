package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.GetMyFansDataUtil;
import com.nyw.pets.activity.util.GetMyFansUtil;
import com.nyw.pets.adapter.MyFansAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的粉丝
 */
public class MyFansActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private MyFansAdapter myFansAdapter;
    private List<GetMyFansUtil> myFansUtilList = new ArrayList<>();
    private    int page=1,limit=15;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);

        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<10;i++){
//            GetMyFansUtil getMyFansUtil=new GetMyFansUtil();
//            getMyFansUtil.setId(i+"");
//            getMyFansUtil.setUserImg(i+"");
//            getMyFansUtil.setTitle("账户001"+i);
//            getMyFansUtil.setMsg("简介，即简明扼要的介绍。是当..."+i);
//            myFansUtilList.add(getMyFansUtil);
//        }
        myFansAdapter=new MyFansAdapter(MyFansActivity.this,myFansUtilList);
        rcv_data.setAdapter(myFansAdapter);
        myFansAdapter.setUpdateFansData(new MyFansAdapter.UpdateFansData() {
            @Override
            public void updateFans() {
                //关注与取消关注
                myFansUtilList.clear();
                getData();
                myFansAdapter.notifyDataSetChanged();
            }
        });



        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                myFansUtilList.clear();
                getData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getData();
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
    protected void onResume() {
        super.onResume();
        myFansUtilList.clear();
        getData();
    }

    /**
     * 获取我的粉丝列表
     */
    private void getData() {
        String url= Api.GET_USER_PERSONAL_FANS;
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(MyFansActivity.this, LoginActivity.class);
            startActivity(login);
        }

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
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

                            if (code==AppConfig.SUCCESS){
                                Gson gson=new Gson();
                                GetMyFansDataUtil getMyFansDataUtil=gson.fromJson(data, GetMyFansDataUtil.class);
                                for (int i=0;i<getMyFansDataUtil.getData().getList().size();i++){


                                    GetMyFansUtil getMyFansUtil=new GetMyFansUtil();
                                    getMyFansUtil.setId(getMyFansDataUtil.getData().getList().get(i).getUid());
                                    String avatar=   getMyFansDataUtil.getData().getList().get(i).getAvatar();
                                    if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                                        getMyFansUtil.setUserImg(getMyFansDataUtil.getData().getList().get(i).getAvatar());
                                    }else {
                                        getMyFansUtil.setUserImg(getMyFansDataUtil.getData().getList().get(i).getServer() +
                                                getMyFansDataUtil.getData().getList().get(i).getAvatar());
                                    }
                                    getMyFansUtil.setTitle(getMyFansDataUtil.getData().getList().get(i).getNickname());
                                    getMyFansUtil.setMsg(getMyFansDataUtil.getData().getList().get(i).getSpe());
                                    getMyFansUtil.setIsFocusOn(getMyFansDataUtil.getData().getList().get(i).getIf_follow()+"");
                                    myFansUtilList.add(getMyFansUtil);
                                }
                            }
                            myFansAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyFansActivity.this,"网络错误");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
        }
    }
}
