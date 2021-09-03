package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetMyCouponsDataUtil;
import com.nyw.pets.activity.util.GetCouponsUtil;
import com.nyw.pets.adapter.MyCouponsAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的优惠卷
 */
public class MyCouponsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private MyCouponsAdapter myCouponsAdapter;
    private List<GetCouponsUtil> couponsUtilList=new ArrayList<>();
    private RecyclerView rcv_data;
    private int page=1,limit=15;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<10;i++){
//            GetCouponsUtil getCouponsUtil=new GetCouponsUtil();
//            getCouponsUtil.setCouponsImg("");
//            getCouponsUtil.setId(""+i);
//            couponsUtilList.add(getCouponsUtil);
//        }
        myCouponsAdapter=new MyCouponsAdapter(MyCouponsActivity.this,couponsUtilList);
        rcv_data.setAdapter(myCouponsAdapter);

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                couponsUtilList.clear();
                getMyCouponsInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getMyCouponsInfo();
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
        couponsUtilList.clear();
        getMyCouponsInfo();
    }

    /**
     * 获取优惠卷列表
     */
    private void getMyCouponsInfo() {
        String url= Api.GET_SHOP_COUPON_COUPON_LIST;
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .params("type","false")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                      String data= response.body();
                      Log.i("jfsifsnvxkvjdxgvd",data);
                        Gson gson=new Gson();
                        GetMyCouponsDataUtil getMyCouponsDataUtil=  gson.fromJson(data, GetMyCouponsDataUtil.class);
                        if (getMyCouponsDataUtil.getCode()== AppConfig.SUCCESS){
                            for (int i =0;i<getMyCouponsDataUtil.getData().getList().size();i++){
                                GetCouponsUtil getCouponsUtil=new GetCouponsUtil();
                                getCouponsUtil.setId(getMyCouponsDataUtil.getData().getList().get(i).getId()+"");
                                getCouponsUtil.setTitle(getMyCouponsDataUtil.getData().getList().get(i).getTitle());
                                getCouponsUtil.setPrice(getMyCouponsDataUtil.getData().getList().get(i).getPrice());
                                getCouponsUtil.setStart_time(getMyCouponsDataUtil.getData().getList().get(i).getStart_time());
                                getCouponsUtil.setEnd_time(getMyCouponsDataUtil.getData().getList().get(i).getEnd_time());
                                getCouponsUtil.setContent(getMyCouponsDataUtil.getData().getList().get(i).getContent());
                                getCouponsUtil.setConditionPrice(getMyCouponsDataUtil.getData().getList().get(i).getHas_price()+"");
                                couponsUtilList.add(getCouponsUtil);
                            }
                            myCouponsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyCouponsActivity.this,"网络错误");
                    }
                });
    }

}
