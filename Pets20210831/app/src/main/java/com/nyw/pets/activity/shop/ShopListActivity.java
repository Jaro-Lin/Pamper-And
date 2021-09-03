package com.nyw.pets.activity.shop;

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
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetPreferentialDataUtil;
import com.nyw.pets.activity.util.ShopListDataUtil;
import com.nyw.pets.adapter.ShopListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表
 */
public class ShopListActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private String title;
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private List<ShopListDataUtil> shopDataList=new ArrayList<>();
    private ShopListAdapter shopListAdapter;
    //分页
    private  int limit=15,page=1;
    private MyApplication myApplication;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        myApplication=(MyApplication)getApplication();
        initView();
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);

        Bundle bundle=getIntent().getExtras();
        title= bundle.getString("openTypeTitle",null);

        tv_title.setText(title);

        if (title.equals("热销榜")){
            getHotData();
        }else {
            getPreferentialData();
        }

        //显示商品数据
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_data.setLayoutManager(gridLayoutManager);
//        for (int i=0;i<10;i++){
//            ShopListDataUtil shopSearchDataUtil=new ShopListDataUtil();
//            shopSearchDataUtil.setId(""+i);
//            shopSearchDataUtil.setTitle("狗狗食物超级好吃的零食营养的食物优惠");
//            shopSearchDataUtil.setPayNumber("1992人付款");
//            shopSearchDataUtil.setPrice("￥9.9");
//            shopSearchDataUtil.setShopImg("");
//            shopDataList.add(shopSearchDataUtil);
//        }
        shopListAdapter=new ShopListAdapter(ShopListActivity.this,shopDataList);
        rcv_data.setAdapter(shopListAdapter);
        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                shopDataList.clear();
                if (title.equals("热销榜")){
                    getHotData();
                }else {
                    getPreferentialData();
                }
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                if (title.equals("热销榜")){
                    getHotData();
                }else {
                    getPreferentialData();
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
    /**
     * 获取热销榜
     */
    private void getHotData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        //discount是优惠，hot是热销
        OkGo.<String>post(Api.GET_SHOP_PREFERENTIAL).tag(this)
                .params("token",token)
                .params("type","hot")
                .params("page",page)
                .params("limit",10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjwertsifsf",data) ;
                        Gson gson=new Gson();
                        GetPreferentialDataUtil   getPreferentialDataUtil= gson.fromJson(data, GetPreferentialDataUtil.class);

                        for (int i = 0;i<getPreferentialDataUtil.getData().getList().size();i++){

                            ShopListDataUtil shopSearchDataUtil=new ShopListDataUtil();
                            shopSearchDataUtil.setId(getPreferentialDataUtil.getData().getList().get(i).getId()+"");
                            shopSearchDataUtil.setShopId(getPreferentialDataUtil.getData().getList().get(i).getId()+"");
                            shopSearchDataUtil.setSpe_id(getPreferentialDataUtil.getData().getList().get(i).getSpe_id()+"");
                            shopSearchDataUtil.setTitle(getPreferentialDataUtil.getData().getList().get(i).getTitle());
                            shopSearchDataUtil.setPayNumber(getPreferentialDataUtil.getData().getList().get(i).getStock()+"");
                            shopSearchDataUtil.setPrice(getPreferentialDataUtil.getData().getList().get(i).getPrice()+"");
                            shopSearchDataUtil.setShopImg(myApplication.getImgFilePathUrl()+getPreferentialDataUtil.getData().getList().get(i).getIcon());
                            shopDataList.add(shopSearchDataUtil);


                        }
                        shopListAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopListActivity.this,"网络错误");
                    }
                });

    }
    /**
     * 获取限时优惠
     */
    private void getPreferentialData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        //discount是优惠，hot是热销
        OkGo.<String>post(Api.GET_SHOP_PREFERENTIAL).tag(this)
                .params("token",token)
                .params("type","discount")
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjwertsifsf",data) ;
                        Gson gson=new Gson();
                        GetPreferentialDataUtil getPreferentialDataUtil= gson.fromJson(data, GetPreferentialDataUtil.class);

                        for (int i = 0;i<getPreferentialDataUtil.getData().getList().size();i++){


                            ShopListDataUtil shopSearchDataUtil=new ShopListDataUtil();
                            shopSearchDataUtil.setId(getPreferentialDataUtil.getData().getList().get(i).getId()+"");
                            shopSearchDataUtil.setSpe_id(getPreferentialDataUtil.getData().getList().get(i).getSpe_id()+"");
                            shopSearchDataUtil.setShopId(getPreferentialDataUtil.getData().getList().get(i).getId()+"");
                            shopSearchDataUtil.setTitle(getPreferentialDataUtil.getData().getList().get(i).getTitle());
                            shopSearchDataUtil.setPayNumber(getPreferentialDataUtil.getData().getList().get(i).getStock()+"");
                            shopSearchDataUtil.setPrice(getPreferentialDataUtil.getData().getList().get(i).getPrice()+"");
                            shopSearchDataUtil.setShopImg(myApplication.getImgFilePathUrl()+getPreferentialDataUtil.getData().getList().get(i).getIcon());
                            shopDataList.add(shopSearchDataUtil);

                        }
                        shopListAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopListActivity.this,"网络错误");
                    }
                });

    }

}
