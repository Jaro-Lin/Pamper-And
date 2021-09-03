package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetChoiceConversationUtil;
import com.nyw.pets.activity.util.GetConversationUtil;
import com.nyw.pets.adapter.ConversationAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多话题，数据列表
 */
public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rcv_data;
    private ConversationAdapter conversationAdapter;
    private List<GetConversationUtil> conversationList=new ArrayList<>();
    private ImageView iv_hide;
    //动态页码与页数
    private int limit=15;
    private int page=1;
    private ClearEditText ct_inputChoiceMsg;
    //刷新
    private MeiTuanRefreshView refreshview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initView();
    }

    private void initView() {
        rcv_data=findViewById(R.id.rcv_data);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_inputChoiceMsg=findViewById(R.id.ct_inputChoiceMsg);
        refreshview=findViewById(R.id.refreshview);

        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<20;i++){
//            GetConversationUtil getConversationUtil=new GetConversationUtil();
//            getConversationUtil.setImg("");
//            getConversationUtil.setTitle("这是话题数据内容，在测试"+i);
//            getConversationUtil.setReadNumber(i+"w热度");
//            conversationList.add(getConversationUtil);
//        }
        conversationAdapter=new ConversationAdapter(ConversationActivity.this,conversationList);
        rcv_data.setAdapter(conversationAdapter);

        //搜索话题
        ct_inputChoiceMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*++ 文本每次改变就会跑这个方法 ++*/
                getSearchChoice();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getTopicInfo();

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                conversationList.clear();
                getTopicInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getTopicInfo();
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
    /**
     * 搜索话题
     */
    private void getSearchChoice() {
        conversationList.clear();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String keyword=ct_inputChoiceMsg.getText().toString();

        OkGo.<String>post(Api.SEARCH_THEME_).tag(this)
                .params("token",token)
                .params("keyword",keyword)
                .params("page","1")
                .params("limit","1000")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sjdsifjinvifisdifmnvvg",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("message")+"");
                            ToastManager.showShortToast(ConversationActivity.this,message);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS) {

                            Gson gson = new Gson();
                            GetChoiceConversationUtil getChoiceConversationUtil = gson.fromJson(data, GetChoiceConversationUtil.class);
                            for (int i = 0; i < getChoiceConversationUtil.getData().getList().size(); i++) {
                                GetConversationUtil getConversationUtil = new GetConversationUtil();
                                getConversationUtil.setImg(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                                getConversationUtil.setId(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                                getConversationUtil.setTitle(getChoiceConversationUtil.getData().getList().get(i).getTheme_title());
                                getConversationUtil.setReadNumber(getChoiceConversationUtil.getData().getList().get(i).getUse_num() + "热度");
                                conversationList.add(getConversationUtil);
                            }
                        }
                        conversationAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ConversationActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取话题数据
     */
    private void getTopicInfo(){
        OkGo.<String>post(Api.GET_POST_THEME).tag(this)
                .params("limit",limit)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sfdjsifsjfsdfs",data);

                        Gson gson=new Gson();
                        GetChoiceConversationUtil getChoiceConversationUtil= gson.fromJson(data, GetChoiceConversationUtil.class);
                        for (int i=0;i<getChoiceConversationUtil.getData().getList().size();i++){

                            GetConversationUtil getConversationUtil = new GetConversationUtil();
                            getConversationUtil.setId(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                            getConversationUtil.setTitle(getChoiceConversationUtil.getData().getList().get(i).getTheme_title());
                            getConversationUtil.setReadNumber(getChoiceConversationUtil.getData().getList().get(i).getUse_num() + "");

                             conversationList.add(getConversationUtil);

                        }
                        conversationAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                            ToastManager.showShortToast(ConversationActivity.this, "网络错误");
                    }
                });

    }

}
