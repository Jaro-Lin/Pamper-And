package com.nyw.pets.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetCustomerServiceDataUtil;
import com.nyw.pets.adapter.CustomerServiceAdapter;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.util.GetCustomerServiceUtil;
import com.nyw.pets.util.TimeStampUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线客服
 */
public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rcv_customer_service;
    private CustomerServiceAdapter customerServiceAdapter;
    private List<GetCustomerServiceUtil> customerList=new ArrayList<>();
    private GetCustomerServiceDataUtil getCustomerServiceDataUtil;
    private ImageView iv_hide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        initView();

    }

    private void initView() {
        rcv_customer_service=findViewById(R.id.rcv_customer_service);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);

        rcv_customer_service.setLayoutManager(new LinearLayoutManager(this));
        customerServiceAdapter=new CustomerServiceAdapter(CustomerServiceActivity.this,customerList);
        rcv_customer_service.setAdapter(customerServiceAdapter);

        getData();

    }

    private void getData() {
        String url= ApiTest.GET_PLATFORM_SYS_INDEX;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String   uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(CustomerServiceActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfsfbnngggdfsf",data);
                        Gson gson=new Gson();
                         getCustomerServiceDataUtil=gson.fromJson(data,GetCustomerServiceDataUtil.class);
                         for (int i=0;i<getCustomerServiceDataUtil.getData().size();i++){
                             GetCustomerServiceUtil getCustomerServiceUtil=new GetCustomerServiceUtil();
                             getCustomerServiceUtil.setId(getCustomerServiceDataUtil.getData().get(i).getId()+"");
                             getCustomerServiceUtil.setMsg(getCustomerServiceDataUtil.getData().get(i).getValue()+"");
                             getCustomerServiceUtil.setTitle(getCustomerServiceDataUtil.getData().get(i).getName()+"");
                             getCustomerServiceUtil.setType(getCustomerServiceDataUtil.getData().get(i).getType()+"");
                             customerList.add(getCustomerServiceUtil);
                         }
                        customerServiceAdapter.notifyDataSetChanged();



                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        new AppNetUtil(CustomerServiceActivity.this).appInternetError();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
        }
    }
}
