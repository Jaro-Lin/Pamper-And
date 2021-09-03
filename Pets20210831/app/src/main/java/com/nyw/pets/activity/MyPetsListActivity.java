package com.nyw.pets.activity;

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
import com.nyw.pets.activity.user.AddPetsActivity;
import com.nyw.pets.activity.util.GetPetsListDataUtil;
import com.nyw.pets.activity.util.GetPetsListUitl;
import com.nyw.pets.adapter.PetsListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的宠物列表
 */
public class MyPetsListActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_add;
    private ImageView iv_hide;
    private PetsListAdapter petsListAdapter;
    private List<GetPetsListUitl> petsListUitls = new ArrayList<>();
    private RecyclerView rcv_data;
    private   int limit=15,page=1;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets_list);
        initView();
    }

    private void initView() {
        tv_add=findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//
//        GetPetsListUitl getPetsListUitl=new GetPetsListUitl();
//        getPetsListUitl.setName("咪咪");
//        getPetsListUitl.setId("1");
//        getPetsListUitl.setImg(R.mipmap.donghead);
//        petsListUitls.add(getPetsListUitl);

        petsListAdapter=new PetsListAdapter(MyPetsListActivity.this,petsListUitls);
        rcv_data.setAdapter(petsListAdapter);
        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                petsListUitls.clear();
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
        petsListUitls.clear();
        getData();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tv_add:
                //添加
                Intent intent=new Intent();
                intent.setClass(MyPetsListActivity.this, AddPetsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_hide:
                finish();
                break;

        }
    }

    /**
     * 获取宠物列表
     */
    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        OkGo.<String>post(Api.GET_PETS_INFO).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsifedjsfofgtgt",data);
                        Gson gson=new Gson();
                        GetPetsListDataUtil getPetsListDataUtil= gson.fromJson(data, GetPetsListDataUtil.class);


                        for (int i=0;i<getPetsListDataUtil.getData().size();i++) {
                            GetPetsListUitl getPetsListUitl = new GetPetsListUitl();
                            getPetsListUitl.setName(getPetsListDataUtil.getData().get(i).getNickname());
                            getPetsListUitl.setId(getPetsListDataUtil.getData().get(i).getPid());
                            getPetsListUitl.setImg(getPetsListDataUtil.getData().get(i).getServer() +
                                    getPetsListDataUtil.getData().get(i).getAvatar() + "");
                            getPetsListUitl.setImgName(getPetsListDataUtil.getData().get(i).getAvatar());

                            getPetsListUitl.setBreed(getPetsListDataUtil.getData().get(i).getVarieties_name());
                            getPetsListUitl.setSex(getPetsListDataUtil.getData().get(i).getSex()+"");
                            getPetsListUitl.setAge(getPetsListDataUtil.getData().get(i).getAge()+"");
                            getPetsListUitl.setWeight(getPetsListDataUtil.getData().get(i).getSex()+"");
                            getPetsListUitl.setSterilization(getPetsListDataUtil.getData().get(i).getSterilization()+"");
                            getPetsListUitl.setBirth(getPetsListDataUtil.getData().get(i).getBrithday());

                            getPetsListUitl.setType_id(getPetsListDataUtil.getData().get(i).getType_id()+"");
                            getPetsListUitl.setVarieties_id(getPetsListDataUtil.getData().get(i).getVarieties_id()+"");
                            getPetsListUitl.setPid(getPetsListDataUtil.getData().get(i).getPid()+"");
                            getPetsListUitl.setHealthy(getPetsListDataUtil.getData().get(i).getHealthy()+"");


                            petsListUitls.add(getPetsListUitl);

                        }

                        petsListAdapter.notifyDataSetChanged();



                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyPetsListActivity.this,"网络错误");
                    }
                });
    }
}
