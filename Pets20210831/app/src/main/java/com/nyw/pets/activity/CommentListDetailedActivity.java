package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.SendCommentData;
import com.nyw.pets.activity.user.UserInfoActivity;
import com.nyw.pets.activity.util.CommentListDetailedUtil;
import com.nyw.pets.activity.util.DynamicData;
import com.nyw.pets.activity.util.GetCommentDataUtil;
import com.nyw.pets.activity.util.SendDynamicDetailsData;
import com.nyw.pets.adapter.CommentListDetailedAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.DelCommentUpdateInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.DisplayUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.PopWinMenu;
import com.nyw.pets.view.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 评论中回复评论
 */
public class CommentListDetailedActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide,iv_more_report;
    private RecyclerView rcv_data;
    private CommentListDetailedAdapter commentListDetailedAdapter;
    private List<CommentListDetailedUtil> commentListDetailed=new ArrayList<>();
    private ImageView iv_sendComment;
    private ClearEditText ct_input_msg;
    private    String img,time,name,msg,thumbsNumber,post_id,comment_id;
    private boolean if_good;
    private RoundImageView riv_name_img;
    private TextView tv_name,tv_number,tv_msg;
    private ImageView iv_thumbs;
    private String isGood;//0不点赞，默认无点赞
    private  String id;//父级评论的id
    private GetCommentDataUtil getCommentDataUtil;
    //选择哪个评论
    private int userNumberItem=0;
    private String userName;
    private String commentId;
    //弹出菜单
    private PopWinMenu popWinmenu;
    private TextView tv_time;
    private LinearLayout llt_user;
    //刷新
    private MeiTuanRefreshView refreshview;
    private   int page=1,limit=15;
    private TextView tv_sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_detailed);
        initView();
    }

    /**
     * view
     */
    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_more_report=findViewById(R.id.iv_more_report);
        iv_more_report.setOnClickListener(this);
        iv_sendComment=findViewById(R.id.iv_sendComment);
        iv_sendComment.setOnClickListener(this);
        ct_input_msg=findViewById(R.id.ct_input_msg);
        riv_name_img=findViewById(R.id.riv_name_img);
        tv_name=findViewById(R.id.tv_name);
        tv_number=findViewById(R.id.tv_number);
        tv_msg=findViewById(R.id.tv_msg);
        iv_thumbs=findViewById(R.id.iv_thumbs);
        iv_thumbs.setOnClickListener(this);
        tv_time=findViewById(R.id.tv_time);
        llt_user=findViewById(R.id.llt_user);
        llt_user.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        tv_sendData=findViewById(R.id.tv_sendData);
        tv_sendData.setOnClickListener(this);




        try{

            Bundle bundle=getIntent().getExtras();
            img= bundle.getString("img",null);
            time= bundle.getString("time",null);
            name= bundle.getString("name",null);
            msg= bundle.getString("msg",null);
            thumbsNumber= bundle.getString("thumbsNumber",null);
            if_good= bundle.getBoolean("if_good");
            post_id= bundle.getString("post_id",null);
            comment_id= bundle.getString("comment_id",null);
            id= bundle.getString("id",null);


            Glide.with(this).load(img).placeholder(R.mipmap.http_error).error(R.mipmap.http_error)
                    .into(riv_name_img);
            tv_msg.setText(msg);
            tv_name.setText(name);
            tv_number.setText(thumbsNumber);
            tv_time.setText(time);

            if (if_good==true){
                iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
                isGood="0";
            }else {
                iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                isGood="1";
            }



        }catch (Exception e){}


        rcv_data=findViewById(R.id.rcv_data);

        rcv_data.setLayoutManager(new LinearLayoutManager(this));

//        for (int i=0;i<10;i++){
//            CommentListDetailedUtil commentListDetailedUtil=new CommentListDetailedUtil();
//            commentListDetailedUtil.setName("张三"+i);
//            commentListDetailedUtil.setMsg("这是评论内容 "+i);
//            commentListDetailedUtil.setUserImg("");
//            commentListDetailedUtil.setNumber(45+i+"");
//            commentListDetailedUtil.setId(45+i+"");
//            commentListDetailedUtil.setThumbs(true);
//
//            commentListDetailed.add(commentListDetailedUtil);
//        }
        commentListDetailedAdapter=new CommentListDetailedAdapter(CommentListDetailedActivity.this,commentListDetailed);
        rcv_data.setAdapter(commentListDetailedAdapter);
        commentListDetailedAdapter.setClickItemData(new CommentListDetailedAdapter.ClickItemData() {
            @Override
            public void clickItemData(int i, String name, String mYcommentId) {
                //选择回复评论人
                userNumberItem=i;
                userName=name;
                commentId=mYcommentId;
                ct_input_msg.setHint("回复 "+name);

            }
        });
        commentListDetailedAdapter.setDelCommentUpdateInterface(new DelCommentUpdateInterface() {
            @Override
            public void delCommentUpdateInterface() {
                //删除评论更新
                page=1;
                commentListDetailed.clear();
                getData();
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
                commentListDetailed.clear();
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
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.iv_more_report:
                //举报
                selectMenu();
                break;
            case R.id.iv_sendComment:
                //回复评论
               String msg= ct_input_msg.getText().toString();
               if (TextUtils.isEmpty(msg)){
                   ToastManager.showShortToast(CommentListDetailedActivity.this,"请输入内容");
                   return;
               }
                sendComment(msg);
            case R.id.tv_sendData:
                 msg= ct_input_msg.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    ToastManager.showShortToast(CommentListDetailedActivity.this,"请输入内容");
                    return;
                }
                sendComment(msg);
                break;
            case R.id.iv_thumbs:
                //点赞
                sendThumbs(Api.GET_POST_ABOUT_GOOD);
                break;
            case R.id.llt_user:
                //
                commentId="";
                ct_input_msg.setHint("回复  "+name);
                break;
        }
    }
    /**
     * 发布评论
     */
    private void sendComment(String content) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        SendCommentData sendCommentData=new SendCommentData();
        sendCommentData.setComment_id(id);
        sendCommentData.setContent(content);
        sendCommentData.setModule("post");
        sendCommentData.setModule_id(post_id);
        sendCommentData.setToken(token);
        sendCommentData.setReply_id(commentId);
        Gson gson=new Gson();
        String sendComment=gson.toJson(sendCommentData );
        Log.i("sadlfkjsfisjfs",sendComment);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendComment);

        Log.i("sdjfsifsjfsf",token);
        OkGo.<String>post(Api.SEND_COMMENT_MY_SEND).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=  response.body();
                        Log.i("sdfksfosfgg",data);
                        JSONObject jsonObject=null;
                        int code=0;
                        String msg;
                        try {
                            jsonObject=new JSONObject(data);
                            code=jsonObject.getInt("code");
                            msg= jsonObject.getString("message");
                            if (code== AppConfig.SUCCESS){
                                ct_input_msg.setText("");
                                commentListDetailed.clear();
                                getData();
                            }
                            ToastManager.showShortToast(CommentListDetailedActivity.this,msg);
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CommentListDetailedActivity.this,"网络错误");
                    }
                });
    }
    /**
     * 点赞与取消点赞   收藏 与取消收藏
     */
    private void sendThumbs(  String url) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        DynamicData dynamicData=new DynamicData();
        dynamicData.setToken(token);
        dynamicData.setModule_id(post_id);
        dynamicData.setModule("post");
        dynamicData.setGood(isGood+"");
        dynamicData.setComment_id(comment_id);

        Gson gson=new Gson();
        String sendComment=gson.toJson(dynamicData);
        Log.i("sdfjsifrthvsfsfg",sendComment);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendComment);

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("post_id",post_id)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String myData=response.body();
                        Log.i("dsfjsjifdsjfsfsf",myData);
                        try {
                            JSONObject jsonObject=new JSONObject(myData);
                            String msg=jsonObject.getString("message");
                            int code=jsonObject.getInt("code");
                            ToastManager.showShortToast(CommentListDetailedActivity.this,msg);
                            if (code== AppConfig.SUCCESS){
                                if ( isGood.equals("0")) {
                                    int number= Integer.parseInt(thumbsNumber)-1;

                                    if (number<=0){
                                        number=0;
                                    }
                                    isGood="1";
                                    thumbsNumber=(number+"");
                                    iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                                }else {
                                    isGood="0";
                                    int number= Integer.parseInt(thumbsNumber)+1;
                                    thumbsNumber=(number+"");
                                    iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);


                                }

                            }
                            tv_number.setText(thumbsNumber);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CommentListDetailedActivity.this,"网络错误");
                    }
                });

    }

   @Override
    protected void onResume() {
        super.onResume();
       commentListDetailed.clear();

       getData();
    }

    /**
     * 获取当前动态评论列表
     */
    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        SendDynamicDetailsData sendDynamicDetailsData=new SendDynamicDetailsData();
        sendDynamicDetailsData.setToken(token);
        sendDynamicDetailsData.setPage(page+"");
        sendDynamicDetailsData.setLimit(limit+"");
        sendDynamicDetailsData.setModule("post");
        sendDynamicDetailsData.setModule_id(post_id);
        sendDynamicDetailsData.setComment_id(id);
        Gson gson=new Gson();
        String paramsData=gson.toJson(sendDynamicDetailsData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramsData);
        Log.i("sdlfjsifsjfdsfsg",paramsData);

        OkGo.<String>post(Api.GET_POST_ABOUT_COMMENT_LIST).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("ncerrdjfjiskdkfflp",data);
                        Gson gson=new Gson();
                         getCommentDataUtil=gson.fromJson(data, GetCommentDataUtil.class);

                        if (getCommentDataUtil.getCode()==AppConfig.SUCCESS) {

                            if (getCommentDataUtil.getData()!=null) {
                                if (getCommentDataUtil.getData().getDatalist()!=null&&getCommentDataUtil.getData().getDatalist().size()>0) {
                                    for (int i = 0; i < getCommentDataUtil.getData().getDatalist().size(); i++) {
                                        CommentListDetailedUtil commentListDetailedUtil = new CommentListDetailedUtil();

                                        commentListDetailedUtil.setName(getCommentDataUtil.getData().getDatalist().get(i).getUser().getNickname());
                                        commentListDetailedUtil.setMsg(getCommentDataUtil.getData().getDatalist().get(i).getContent());
                                        commentListDetailedUtil.setUserImg(getCommentDataUtil.getData().getDatalist().get(i).getUser().getAvatar());
                                        commentListDetailedUtil.setNumber(getCommentDataUtil.getData().getDatalist().get(i).getTotal_like() + "");
                                        commentListDetailedUtil.setId(getCommentDataUtil.getData().getDatalist().get(i).getId() + "");
                                        commentListDetailedUtil.setThumbs(getCommentDataUtil.getData().getDatalist().get(i).isIs_good());

                                        commentListDetailedUtil.setIs_owner(getCommentDataUtil.getData().getDatalist().get(i).getIs_owner());

                                        commentListDetailedUtil.setTime(getCommentDataUtil.getData().getDatalist().get(i).getCreate_time() + "");
                                        commentListDetailedUtil.setCommentName(getCommentDataUtil.getData().getDatalist().get(i).getReply().getNickname());
                                        commentListDetailedUtil.setCommentId(getCommentDataUtil.getData().getDatalist().get(i).getReply().getId()+"");
                                        commentListDetailedUtil.setUserId(getCommentDataUtil.getData().getDatalist().get(i).getUser().getId()+"");
                                        commentListDetailedUtil.setId(id);
                                        commentListDetailedUtil.setIf_good(getCommentDataUtil.getData().getDatalist().get(i).isIs_good());
                                        commentListDetailedUtil.setPost_id(post_id+"");
                                        commentListDetailedUtil.setComment_id(getCommentDataUtil.getData().getDatalist().get(i).getId()+"");
                                        commentListDetailedUtil.setThumbs(getCommentDataUtil.getData().getDatalist().get(i).getTotal_like()+"");

                                        commentListDetailed.add(commentListDetailedUtil);
                                    }
                                }
                            }
                        }
                        commentListDetailedAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("jafdjafknkfddsfsdf","网络错误"+response.getException().getMessage()
                                +"     "+response.getRawResponse().message());

                        ToastManager.showShortToast(CommentListDetailedActivity.this,"网络错误"+response.getException().getMessage()
                                +"     "+response.getRawResponse().message());
                    }
                });

    }

    /**
     * 选择菜单
     */
    private void selectMenu() {
        if(popWinmenu==null) {
            //自定义的单击事件
            OnClickLintener paramOnClickListener = new OnClickLintener();
            //控制窗口大小，第一个是宽，第二个是高
            popWinmenu = new PopWinMenu(CommentListDetailedActivity.this,paramOnClickListener,
                    DisplayUtil.dip2px(this, 130), DisplayUtil.dip2px(this, 75));
            //监听窗口的焦点事件，点击窗口外面则取消显示
            popWinmenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popWinmenu.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        popWinmenu.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        popWinmenu.showAsDropDown(iv_more_report, 0, 0);
        //如果窗口存在，则更新
        popWinmenu.update();
    }
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id){
                case R.id.layout_report:
                    //举报
                    popWinmenu.dismiss();
                    Intent intent=new Intent();
                    intent.setClass(CommentListDetailedActivity.this, ReportDynamicActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("post_id",post_id);
                    startActivity( intent);

                    break;
            }
        }
    }

}
