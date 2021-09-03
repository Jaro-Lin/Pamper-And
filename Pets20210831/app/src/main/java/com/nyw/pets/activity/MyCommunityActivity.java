package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetChoiceConversationUtil;
import com.nyw.pets.activity.util.GetCommunityUtil;
import com.nyw.pets.activity.util.GetMyChoiceConversationUtil;
import com.nyw.pets.adapter.MyCommunityAdapter;
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
 * 我的社区
 */
public class MyCommunityActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llt_community;
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private MyCommunityAdapter myCommunityAdapter;
    private List<GetCommunityUtil> communityUtils=new ArrayList<>();
    //动态页码与页数
    private int limit=15;
    private int page=1;
    private ClearEditText ct_inputChoiceMsg;
    private TextView tv_search;
    //刷新
    private MeiTuanRefreshView refreshview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_community);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        ct_inputChoiceMsg=findViewById(R.id.ct_inputChoiceMsg);
        tv_search=findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        ct_inputChoiceMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //搜索
//                String keyword=ct_inputChoiceMsg.getText().toString();
//                if (TextUtils.isEmpty(keyword)){
//                    communityUtils.clear();
//                    getFocusTopicInfo();
//                    return;
//                }
//                communityUtils.clear();
//
//                getSearchChoice();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<20;i++){
//            GetCommunityUtil getCommunityUtil=new GetCommunityUtil();
//            getCommunityUtil.setImg("");
//            getCommunityUtil.setTitle("这是话题数据内容，在测试"+i);
//            getCommunityUtil.setReadNumber("进入社区");
//            communityUtils.add(getCommunityUtil);
//        }
        myCommunityAdapter=new MyCommunityAdapter(MyCommunityActivity.this,communityUtils);
        rcv_data.setAdapter(myCommunityAdapter);
        myCommunityAdapter.setUpdataInfo(new MyCommunityAdapter.UpdataInfo() {
            @Override
            public void updataInfo() {
                //更新数据
                communityUtils.clear();
                getFocusTopicInfo();
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
                communityUtils.clear();
                getFocusTopicInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getFocusTopicInfo();
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
            case R.id.tv_search:
                //搜索
                String keyword=ct_inputChoiceMsg.getText().toString();
                if (TextUtils.isEmpty(keyword)){
                    communityUtils.clear();
                    getFocusTopicInfo();
                    return;
                }
                getSearchChoice();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        communityUtils.clear();
        getFocusTopicInfo();
    }

    /**
     * 获取关注的话题数据
     */
    private void getFocusTopicInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        OkGo.<String>post(Api.GET_USER_PERSONAL_THEME_FOLLOW).tag(this)
                .params("token",token)
                .params("limit",limit)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sfdjdertsifsjfsdfs",data);

                        Gson gson=new Gson();
                        GetMyChoiceConversationUtil getChoiceConversationUtil= gson.fromJson(data, GetMyChoiceConversationUtil.class);
                        for (int i=0;i<getChoiceConversationUtil.getData().getList().size();i++){

                            GetCommunityUtil getCommunityUtil=new GetCommunityUtil();
                            getCommunityUtil.setId(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                            getCommunityUtil.setImg("");
                            getCommunityUtil.setTitle(getChoiceConversationUtil.getData().getList().get(i).getTheme_title());
                            getCommunityUtil.setIs_follow("1");
                            communityUtils.add(getCommunityUtil);


                        }
                        myCommunityAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyCommunityActivity.this, "网络错误");
                    }
                });

    }

    /**
     * 搜索话题
     */
    private void getSearchChoice() {
        communityUtils.clear();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String keyword=ct_inputChoiceMsg.getText().toString();

        OkGo.<String>post(Api.SEARCH_THEME_).tag(this)
                .params("token",token)
                .params("keyword",keyword)
                .params("page","1")
                .params("limit","30")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sjdsifjswsdifmnvvg",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("message")+"");
//                            ToastManager.showShortToast(MyCommunityActivity.this,message);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS) {

                            Gson gson = new Gson();
                            GetChoiceConversationUtil getChoiceConversationUtil = gson.fromJson(data, GetChoiceConversationUtil.class);
                            for (int i = 0; i < getChoiceConversationUtil.getData().getList().size(); i++) {
                                GetCommunityUtil getCommunityUtil = new GetCommunityUtil();
                                getCommunityUtil.setImg(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                                getCommunityUtil.setId(getChoiceConversationUtil.getData().getList().get(i).getId() + "");
                                getCommunityUtil.setTitle(getChoiceConversationUtil.getData().getList().get(i).getTheme_title());
                                getCommunityUtil.setReadNumber(getChoiceConversationUtil.getData().getList().get(i).getUse_num() + "热度");
                                getCommunityUtil.setIs_follow(getChoiceConversationUtil.getData().getList().get(i).getIs_follow());
                                communityUtils.add(getCommunityUtil);
                            }
                        }
                        myCommunityAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyCommunityActivity.this,"网络错误");
                    }
                });
    }

}
