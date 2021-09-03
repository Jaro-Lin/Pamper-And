package com.nyw.pets.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetWalletDetailUtil;
import com.nyw.pets.activity.util.GetWalletUtil;
import com.nyw.pets.adapter.WalletAdapter;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.TimeStampUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * /账单明细
 */
public class WalletListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Spinner timeType;
    private String time_type="0";
    private ArrayAdapter<String> timedAdapter;
//    private List<String> walletDetailList;
    private RecyclerView rcv_wallet;
    private GetWalletUtil getWalletUtil;
    private List<GetWalletUtil> walletUtilList = new ArrayList<>();
    private WalletAdapter walletAdapter;
    private  MeiTuanRefreshView refreshview;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_list);
        initView();
    }
    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        timeType=findViewById(R.id.timeType);

        List<String>  walletDetailList= new ArrayList<String>();
        walletDetailList.add("全部");
        timedAdapter = new ArrayAdapter<String>(WalletListActivity.this, android.R.layout.simple_spinner_item, walletDetailList);

        //设置样式
        timedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        timeType.setAdapter(timedAdapter);
        timeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(getContext(),tenderRecordType.getSelectedItem().toString());
//                walletDetailList.clear();
                time_type=String.valueOf(position);
                getTimeDetail();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rcv_wallet=findViewById(R.id.rcv_wallet);

        rcv_wallet.setLayoutManager(new LinearLayoutManager(WalletListActivity.this));
         walletAdapter=new WalletAdapter(WalletListActivity.this,walletUtilList);
        rcv_wallet.setAdapter(walletAdapter);

         refreshview=(MeiTuanRefreshView)findViewById(R.id.refreshview);
        walletUtilList.clear();
        getWalletData();

        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                walletUtilList.clear();
                page=1;
                getWalletData();
            }

            @Override
            public void onLoadMore() {
                // start load
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getWalletData();
            }
        });

    }

    /**
     * 加载订单数据记录
     */
    private void getWalletData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        String token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"page", String.valueOf(page)},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sgin = new SignConfig().getSign(WalletListActivity.this, key);

        OkGo.<String>post(ApiTest.GET_MONEY_LOG_LIST).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("page",String.valueOf(page))
                .params("timestamp",time)
                .params("sign",sgin)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("datadsxxcasdfsfsta",data);
                        Gson gson=new Gson();
                        GetWalletDetailUtil getWalletDetailUtil=gson.fromJson(data, GetWalletDetailUtil.class);
                        int code=getWalletDetailUtil.getCode();
                        if (code== AppConfig.SUCCESS){
                            for (int i=0;i<getWalletDetailUtil.getData().getData().size();i++) {
                                GetWalletUtil getWalletUtil = new GetWalletUtil();
                                getWalletUtil.setProjectName(getWalletDetailUtil.getData().getData().get(i).getTitle());
                                getWalletUtil.setPrice(getWalletDetailUtil.getData().getData().get(i).getMoney()+"");

                                getWalletUtil.setOrderID(getWalletDetailUtil.getData().getData().get(i).getOrder_id()+"");
                                getWalletUtil.setTime(getWalletDetailUtil.getData().getData().get(i).getCreate_time()+"");
                                getWalletUtil.setStatus_id(getWalletDetailUtil.getData().getData().get(i).getStatus_id());
                                getWalletUtil.setIn_out(getWalletDetailUtil.getData().getData().get(i).getIn_out());

                                walletUtilList.add(getWalletUtil);
                            }

                        }
                        walletAdapter.notifyDataSetChanged();
//                        Toast.makeText(WalletDetailActivity.this,getWalletDetailUtil.getMsg(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("datadata",response.getException().getMessage());
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
     * 获取时间item
     */
    private void getTimeDetail() {

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
