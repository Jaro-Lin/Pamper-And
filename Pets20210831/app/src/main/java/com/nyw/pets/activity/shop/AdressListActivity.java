package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.nyw.pets.activity.shop.util.GetUserAdressListDataUtil;
import com.nyw.pets.activity.util.MyAdressUtil;
import com.nyw.pets.adapter.AdressAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.GetAdressInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址列表
 */
public class AdressListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private TextView tv_add_adress;
    private RecyclerView rcv_data;

    private AdressAdapter adressAdapter;
    private List<MyAdressUtil> myAdressList=new ArrayList<>();
    private  int page=1,limit=15;

    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_add_adress=findViewById(R.id.tv_add_adress);
        tv_add_adress.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<10;i++){
//            MyAdressUtil myAdressUtil=new MyAdressUtil();
//            myAdressUtil.setId(i+"");
//            myAdressUtil.setName("张三"+i);
//            myAdressUtil.setPhone("1897729365"+i);
//            myAdressUtil.setAdress("广西壮族自治区南宁市西乡塘区西乡塘街道华尔街工谷");
//            myAdressList.add(myAdressUtil);
//        }
        adressAdapter=new AdressAdapter(AdressListActivity.this,myAdressList);
        rcv_data.setAdapter(adressAdapter);
        adressAdapter.setGetAdressInterface(new GetAdressInterface() {
            @Override
            public void getAdress(String adress, String adressId, String name, String phone) {
                //获取到收货地址
                Intent getAdress=new Intent();
                getAdress.putExtra("adress",adress);
                getAdress.putExtra("name",name);
                getAdress.putExtra("phone",phone);
                getAdress.putExtra("id",adressId);
                setResult(AppConfig.ADRESS_SELECT,getAdress);

//                ToastManager.showShortToast(AdressListActivity.this,adressId);
                finish();
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
                myAdressList.clear();
                getUserShopListData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getUserShopListData();
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
            case R.id.tv_add_adress:
                //新增加地址
                Intent addRess=new Intent();
                addRess.setClass(AdressListActivity.this,NewAdressActivity.class);
                startActivity(addRess);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdressList.clear();
        getUserShopListData();
    }

    /**
     * 获取用户收货地址
     */
    private void getUserShopListData(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.GET_SHOP_USER_LIST;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        Gson gson=new Gson() ;
                        GetUserAdressListDataUtil getUserAdressListDataUtil= gson.fromJson(data,GetUserAdressListDataUtil.class);

                        if (getUserAdressListDataUtil.getData()!=null) {
                            for (int i = 0; i < getUserAdressListDataUtil.getData().getList().size(); i++) {
                                MyAdressUtil myAdressUtil = new MyAdressUtil();
                                myAdressUtil.setId(getUserAdressListDataUtil.getData().getList().get(i).getId() + "");
                                myAdressUtil.setName(getUserAdressListDataUtil.getData().getList().get(i).getUsername());
                                myAdressUtil.setPhone(getUserAdressListDataUtil.getData().getList().get(i).getPhone());

                                myAdressUtil.setProvince(getUserAdressListDataUtil.getData().getList().get(i).getProvince());
                                myAdressUtil.setCity(getUserAdressListDataUtil.getData().getList().get(i).getCity());
                                myAdressUtil.setArea(getUserAdressListDataUtil.getData().getList().get(i).getArea());
                                myAdressUtil.setDetailed(getUserAdressListDataUtil.getData().getList().get(i).getDetailed());

                                if (getUserAdressListDataUtil.getData().getList().get(i).getIs_default() == 1) {
                                    //默认地址
                                    myAdressUtil.setDefaultAdress(true);
                                } else {
                                    myAdressUtil.setDefaultAdress(false);
                                }


                                myAdressUtil.setAdress(getUserAdressListDataUtil.getData().getList().get(i).getProvince() +
                                        getUserAdressListDataUtil.getData().getList().get(i).getCity() +
                                        getUserAdressListDataUtil.getData().getList().get(i).getArea() +
                                        getUserAdressListDataUtil.getData().getList().get(i).getDetailed());
                                myAdressList.add(myAdressUtil);
                            }
                        }
                        adressAdapter.notifyDataSetChanged();







                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(AdressListActivity.this,"网络错误");
                    }
                });
    }

}
