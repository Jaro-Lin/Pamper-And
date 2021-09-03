package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetSearchShopDataUtil;
import com.nyw.pets.activity.util.ShopSearchDataUtil;
import com.nyw.pets.adapter.ShopSearchAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索商品
 */
public class SearchShopActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide,iv_shopCart;
    private RecyclerView rcv_data;
    private ShopSearchAdapter shopSearchAdapter;
    private List<ShopSearchDataUtil> shopSearchDataList=new ArrayList<>();
    private String petsType=null,shopType=null,sort="null";
    //分页
    private int page=1,limit=15;
    // 搜索商品
    private ClearEditText ct_search;
    private TextView tv_searchShop;
    private LinearLayout llt_price;
    //价格筛选，从高到低或从低到高
    private boolean isPriceScreen=true;
    //销量
    private LinearLayout llt_salesVolume;
    //销量筛选，从高到低或从低到高
    private boolean isSalesVolume=true;
    //新品
    private LinearLayout llt_newShop;
    private MyApplication myApplication;
    //刷新
    private MeiTuanRefreshView refreshview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication=(MyApplication)getApplication();
        setContentView(R.layout.activity_search_shop);
        initView();

    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_shopCart=findViewById(R.id.iv_shopCart);
        iv_shopCart.setOnClickListener(this);
        ct_search=findViewById(R.id.ct_search);
        tv_searchShop=findViewById(R.id.tv_searchShop);
        tv_searchShop.setOnClickListener(this);
        llt_price=findViewById(R.id.llt_price);
        llt_price.setOnClickListener(this);
        llt_salesVolume=findViewById(R.id.llt_salesVolume);
        llt_salesVolume.setOnClickListener(this);
        llt_newShop=findViewById(R.id.llt_newShop);
        llt_newShop.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        ct_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //搜索商品
//                searchShopInfo();

//                shopSearchDataList.clear();
//                getData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bundle bundle=getIntent().getExtras();

        try{
            //商品分类打开
            petsType=bundle.getString("petsType",null);
            shopType=bundle.getString("shopType",null);
            if (!TextUtils.isEmpty(petsType)&&!TextUtils.isEmpty(petsType)){
                //不是空的时候
//                shopSearchDataList.clear();
//                getData();
            }


        }catch (Exception e){}

        rcv_data=findViewById(R.id.rcv_data);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_data.setLayoutManager(gridLayoutManager);
//        for (int i=0;i<10;i++){
//            ShopSearchDataUtil shopSearchDataUtil=new ShopSearchDataUtil();
//            shopSearchDataUtil.setId(""+i);
//            shopSearchDataUtil.setTitle("狗狗食物超级好吃的零食营养的食物优惠");
//            shopSearchDataUtil.setPayNumber("1992人付款");
//            shopSearchDataUtil.setPrice("￥9.9");
//            shopSearchDataUtil.setShopImg("");
//            shopSearchDataList.add(shopSearchDataUtil);
//        }
        shopSearchAdapter=new ShopSearchAdapter(SearchShopActivity.this,shopSearchDataList);
        rcv_data.setAdapter(shopSearchAdapter);

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                shopSearchDataList.clear();
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

    /**
     * 搜索商品，放弃使用了
     */
    private void searchShopInfo() {
        shopSearchDataList.clear();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String url=Api.GET_SEARCH_SHOP_INFO;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("content",ct_search.getText().toString())
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfisfsdfggfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);

                        Gson gson=new Gson() ;
                        GetSearchShopDataUtil getSearchShopDataUtil= gson.fromJson(data, GetSearchShopDataUtil.class);

                        if (getSearchShopDataUtil.getData()!=null) {
                            for (int i = 0; i < getSearchShopDataUtil.getData().getList().size(); i++) {
                                ShopSearchDataUtil shopSearchDataUtil = new ShopSearchDataUtil();
                                shopSearchDataUtil.setId(getSearchShopDataUtil.getData().getList().get(i).getId() + "");
                                shopSearchDataUtil.setTitle(getSearchShopDataUtil.getData().getList().get(i).getTitle() + "");
                                shopSearchDataUtil.setPayNumber(getSearchShopDataUtil.getData().getList().get(i).getSale() + "");
                                shopSearchDataUtil.setPrice(getSearchShopDataUtil.getData().getList().get(i).getPrice() + "");
                                shopSearchDataUtil.setShopImg(myApplication.getImgFilePathUrl()+getSearchShopDataUtil.getData().getList().get(i).getIcon());
                                shopSearchDataUtil.setType_id(getSearchShopDataUtil.getData().getList().get(i).getType_id() + "");
                                shopSearchDataUtil.setPets_id(getSearchShopDataUtil.getData().getList().get(i).getPets_id() + "");

                                shopSearchDataList.add(shopSearchDataUtil);
                            }
                        }
                        shopSearchAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(SearchShopActivity.this,"网络错误");
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
            case R.id.iv_shopCart:
                //打开购物车
                Intent shopCart=new Intent();
                shopCart.setClass(SearchShopActivity.this,ShopCartActivity.class);
                startActivity(shopCart);

                break;
            case R.id.tv_searchShop:
                //搜索商品
                shopSearchDataList.clear();
                getData();
                break;
            case R.id.llt_price:
                //价格筛选
                if (isPriceScreen==true){
                    isPriceScreen=false;
                    sort="price_up";
                }else {
                    isPriceScreen=true;
                    sort="price_down";
                }
                if (!TextUtils.isEmpty(petsType)&&!TextUtils.isEmpty(petsType)){
                    //不是空的时候
                    shopSearchDataList.clear();
                    getData();
                }
                break;
          case  R.id.llt_salesVolume:
            //销量筛选
              if (isSalesVolume==true){
                  isSalesVolume=false;
                  sort="sale_up";
              }else {
                  isSalesVolume=true;
                  sort="sale_down";
              }
              if (!TextUtils.isEmpty(petsType)&&!TextUtils.isEmpty(petsType)){
                  //不是空的时候
                  shopSearchDataList.clear();
                  getData();
              }
            break;
            case R.id.llt_newShop:
                //新品
                sort="new";
                if (!TextUtils.isEmpty(petsType)&&!TextUtils.isEmpty(petsType)){
                    //不是空的时候
                    shopSearchDataList.clear();
                    getData();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(petsType)&&!TextUtils.isEmpty(petsType)){
            //不是空的时候
            shopSearchDataList.clear();
            getData();
        }
    }

    /**
     * 加载分类商品数据
     */
    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String keyword=ct_search.getText().toString();
        String url=Api.GET_SHOP_LIST;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("pets_id",petsType)
                .params("type_id",shopType)
                .params("page",page)
                .params("limit",limit)
                .params("sort",sort)
                .params("keyword",keyword)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                         String data=response.body();
                        Log.i("smdfisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);

                        Gson gson=new Gson() ;
                        GetSearchShopDataUtil getSearchShopDataUtil= gson.fromJson(data, GetSearchShopDataUtil.class);

                        if (getSearchShopDataUtil.getData()!=null) {
                            for (int i = 0; i < getSearchShopDataUtil.getData().getList().size(); i++) {
                                ShopSearchDataUtil shopSearchDataUtil = new ShopSearchDataUtil();
                                shopSearchDataUtil.setId(getSearchShopDataUtil.getData().getList().get(i).getId() + "");
                                shopSearchDataUtil.setShopId(getSearchShopDataUtil.getData().getList().get(i).getDefault_spe().getShop_id() + "");
                                shopSearchDataUtil.setSpe_id(getSearchShopDataUtil.getData().getList().get(i).getDefault_spe().getId() + "");
                                shopSearchDataUtil.setTitle(getSearchShopDataUtil.getData().getList().get(i).getTitle() + "");
                                shopSearchDataUtil.setPayNumber(getSearchShopDataUtil.getData().getList().get(i).getSale() + "");
                                shopSearchDataUtil.setPrice(getSearchShopDataUtil.getData().getList().get(i).getPrice() + "");
                                shopSearchDataUtil.setShopImg(myApplication.getImgFilePathUrl()+getSearchShopDataUtil.getData().getList().get(i).getIcon() + "");
                                shopSearchDataUtil.setType_id(getSearchShopDataUtil.getData().getList().get(i).getType_id() + "");
                                shopSearchDataUtil.setPets_id(getSearchShopDataUtil.getData().getList().get(i).getPets_id() + "");

                                shopSearchDataList.add(shopSearchDataUtil);
                            }
                        }
                        shopSearchAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(SearchShopActivity.this,"网络错误");
                    }
                });
    }
}
