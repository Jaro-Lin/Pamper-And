package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetChoiceConversationUtil;
import com.nyw.pets.activity.util.GetConversationUtil;
import com.nyw.pets.adapter.ChoiceConversationAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.SelectChoiceConversation;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择话题
 */
public class ChoiceConversationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private ChoiceConversationAdapter choiceConversationAdapter;
    private List<GetConversationUtil> conversationList=new ArrayList<>();
    private TextView tv_new_info;
    private ClearEditText ct_input_msg,ct_inputChoiceMsg;
    private int page=1;
    private int limit=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_conversation);
        initView();
    }

    /**
     * view
     */
    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_new_info=findViewById(R.id.tv_new_info);
        tv_new_info.setOnClickListener(this);
        ct_input_msg=findViewById(R.id.ct_input_msg);
        ct_inputChoiceMsg=findViewById(R.id.ct_inputChoiceMsg);

        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));

        choiceConversationAdapter=new ChoiceConversationAdapter(ChoiceConversationActivity.this,conversationList);
        rcv_data.setAdapter(choiceConversationAdapter);

        choiceConversationAdapter.setSelectChoiceConversation(new SelectChoiceConversation() {
            @Override
            public void onClickGetDataId(String id,String title) {
                //把数据传给发布动态界面
                Intent intent=new Intent();
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                setResult(AppConfig.CHOICE_CONVERSATION_STATE_DATA,intent);
                finish();

            }
        });
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
        getChoice();
    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
            finish();
            break;
            case R.id.tv_new_info:
                //新建立的话题
                saveChoice();
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
                .params("limit","30")
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
                            ToastManager.showShortToast(ChoiceConversationActivity.this,message);
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
                            choiceConversationAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ChoiceConversationActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 保存话题
     */
    private void saveChoice(){


        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String title=ct_input_msg.getText().toString();

        OkGo.<String>post(Api.ADD_SEND_NEW_THEME).tag(this)
                .params("token",token)
                .params("title",title)
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
                            ToastManager.showShortToast(ChoiceConversationActivity.this,message);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (code==AppConfig.SUCCESS){
                            Intent intent=new Intent();
                            intent.putExtra("title",title);
                            setResult(AppConfig.CHOICE_CONVERSATION_STATE_DATA,intent);
                            finish();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ChoiceConversationActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取话题
     */
    private void getChoice(){


        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        OkGo.<String>post(Api.GET_POST_THEME).tag(this)
                .params("token",token)
                .params("limit",limit)
                .params("page",page)
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
//                            ToastManager.showShortToast(ChoiceConversationActivity.this,message);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        if (code== AppConfig.SUCCESS){

                            Gson gson=new Gson();
                            GetChoiceConversationUtil getChoiceConversationUtil= gson.fromJson(data, GetChoiceConversationUtil.class);
                        for (int i=0;i<getChoiceConversationUtil.getData().getList().size();i++){
                            GetConversationUtil getConversationUtil=new GetConversationUtil();
                            getConversationUtil.setImg(getChoiceConversationUtil.getData().getList().get(i).getId()+"");
                            getConversationUtil.setId(getChoiceConversationUtil.getData().getList().get(i).getId()+"");
                            getConversationUtil.setTitle(getChoiceConversationUtil.getData().getList().get(i).getTheme_title());
                            getConversationUtil.setReadNumber(getChoiceConversationUtil.getData().getList().get(i).getUse_num()+"热度");
                            conversationList.add(getConversationUtil);
                        }
                        choiceConversationAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ChoiceConversationActivity.this,"网络错误");
                    }
                });
    }


}
