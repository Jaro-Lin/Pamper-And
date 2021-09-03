package com.nyw.pets.activity.shop;

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
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetAfterSalesDataUtil;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.GetAfterSalesUtil;
import com.nyw.pets.adapter.AfterSalesAdapter;
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
 * 我的售后
 */
public class AfterSalesActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private AfterSalesAdapter afterSalesAdapter;
    private List<GetAfterSalesUtil> afterSalesUtils=new ArrayList<>();
    private  int page=1,limit=15;
    //刷新
    private MeiTuanRefreshView refreshview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);

        rcv_data=findViewById(R.id.rcv_data);
        refreshview=findViewById(R.id.refreshview);

        rcv_data.setLayoutManager(new LinearLayoutManager(this));

//        for (int i=0;i<10;i++){
//            GetAfterSalesUtil getAfterSalesUtil=new GetAfterSalesUtil();
//            getAfterSalesUtil.setId(i+"");
//            getAfterSalesUtil.setShopImg("");
//            getAfterSalesUtil.setTitle("狗狗食物超级好吃的零食营养的食物优惠"+i);
//            getAfterSalesUtil.setSpecifications("规格:1000 g");
//            getAfterSalesUtil.setState("已退款");
//            getAfterSalesUtil.setStateType("申请退款");
//            getAfterSalesUtil.setPrice("￥ 19.9");
//
//            afterSalesUtils.add(getAfterSalesUtil);
//        }
        afterSalesAdapter=new AfterSalesAdapter(AfterSalesActivity.this,afterSalesUtils);
        rcv_data.setAdapter(afterSalesAdapter);

        afterSalesAdapter.setUpdateAfterSalesInfo(new AfterSalesAdapter.UpdateAfterSalesInfo() {
            @Override
            public void updateAfterSalesInfo() {
                //刷新售后数据
                afterSalesUtils.clear();
                getData();
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
                afterSalesUtils.clear();
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
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        afterSalesUtils.clear();
        getData();
    }

    /**
     * 获取我的售后商品
     */
    private void getData() {
        SharedPreferences getUser = getSharedPreferences(SaveAPPData.USER_ID_Token, MODE_PRIVATE);
        String token = getUser.getString(SaveAPPData.TOKEN, null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)) {
            //用户没有登录
            Intent login = new Intent();
            login.setClass(AfterSalesActivity.this, LoginActivity.class);
            startActivity(login);
        }

        OkGo.<String>post(Api.GET_SHOP_ORDER_AFTER_LIST).tag(this)
                .params("token", token)
                .params("page", page)
                .params("limit", limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sdjfsidsrsrsrfsjfsf", data);
                        JSONObject jsonObject = null;
                        int code = 0;
                        String msg = null;
                        try {
                            jsonObject = new JSONObject(data);
                            code = jsonObject.getInt("code");
                            msg = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code == AppConfig.SUCCESS) {
                            Gson gson=new Gson();
                            GetAfterSalesDataUtil getAfterSalesDataUtil=    gson.fromJson(data, GetAfterSalesDataUtil.class);
                            for (int i=0;i<getAfterSalesDataUtil.getData().getList().size();i++){
                                GetAfterSalesUtil getAfterSalesUtil=new GetAfterSalesUtil();
                                getAfterSalesUtil.setId(getAfterSalesDataUtil.getData().getList().get(i).getOrder_id());
                                getAfterSalesUtil.setShopImg(getAfterSalesDataUtil.getData().getList().get(i).getShop().get(0).getIcon());
                                getAfterSalesUtil.setTitle(getAfterSalesDataUtil.getData().getList().get(i).getShop().get(0).getTitle());
                                getAfterSalesUtil.setSpecifications(getAfterSalesDataUtil.getData().getList().get(i).getShop().get(0).getSpe());
                                getAfterSalesUtil.setState(getAfterSalesDataUtil.getData().getList().get(i).getRefund_status()+"");
                                getAfterSalesUtil.setStateType(getAfterSalesDataUtil.getData().getList().get(i).getType()+"");
                                getAfterSalesUtil.setPrice(getAfterSalesDataUtil.getData().getList().get(i).getPrice());
                                getAfterSalesUtil.setOrder_id(getAfterSalesDataUtil.getData().getList().get(i).getOrder_id());

                                afterSalesUtils.add(getAfterSalesUtil);
                            }
                        }
                        afterSalesAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(AfterSalesActivity.this,"网络错误");
                    }


                });
    }
}
