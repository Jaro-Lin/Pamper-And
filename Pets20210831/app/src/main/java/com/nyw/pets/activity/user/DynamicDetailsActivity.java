package com.nyw.pets.activity.user;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.ReportDynamicActivity;
import com.nyw.pets.activity.shop.util.GetShareDataUtil;
import com.nyw.pets.activity.shop.util.SendCommentData;
import com.nyw.pets.activity.util.ComentDataListUtil;
import com.nyw.pets.activity.util.DynamicData;
import com.nyw.pets.activity.util.GetCommentDataUtil;
import com.nyw.pets.activity.util.SendDynamicDetailsData;
import com.nyw.pets.adapter.CommentListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.interfaces.DelCommentUpdateInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.DisplayUtil;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.PopWinMenu;
import com.nyw.pets.view.RoundImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ??????????????????????????????
 */
public class DynamicDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rcv_data;
    private CommentListAdapter commentListAdapter;
    private List<ComentDataListUtil> comentDataList=new ArrayList<>();
    private ImageView iv_hide;
    private ImageView iv_report,iv_share;
    //????????????
    private PopWinMenu popWinmenu;
    //??????ID
    private String id;
    private String img_user,name,time,msg,video;
    private ArrayList<MyMedia> imgList;

    private RoundImageView riv_name_img;
    private TextView tv_name,tv_time,tv_msg;

    //??????????????????????????????
    private NineGridViewGroup nineGrid;
    //???????????????
    private StandardGSYVideoPlayer gsyVideoPlayer;
    //?????????????????? ??????
    public final   String  PLAY_TAG="PLAY_TAG";
    private  boolean isVideo=false,isImg=false;
    private  String collection,good,comment;
    private TextView tv_comment,tv_good,tv_collection;
    private int page=1,limit=15;
    private String post_id;
    private ImageView iv_sendComment;
    private ClearEditText ct_input_msg;
    private ImageView iv_collection,iv_thumbs;

    private String isCollection,isGood;//0????????????????????????????????????????????????????????????
    //??????id
    private String comment_id;
    //??????id
    private String module_id;
    private MyApplication myApplication;

    //????????????
    private LinearLayout llt_weibo, llt_Copylink, llt_Qzone,
            llt_qq_friends, llt_WeChat_friends, llt_WeChat_circle_friends;
    private String title,target,thumbnail,describe,invitation_link;
    private Button btn_share;
    // ??????????????????PopupWindow??????
    private PopupWindow window;
    //????????????
    private Button btn_cancel;
    //????????????
    private  GetShareDataUtil getShareDataUtil;
    //??????
    private MeiTuanRefreshView refreshview;
    private TextView tv_sendData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        myApplication= (MyApplication) getApplication();
        initView();

    }



    private void initView() {


        try{
            Bundle bundle=getIntent().getExtras();
            post_id=bundle.getString("post_id",null);
            module_id=post_id;
            img_user=bundle.getString("img_user",null);
            name=bundle.getString("name",null);
            time=bundle.getString("time",null);
            msg=bundle.getString("msg",null);
            isVideo=bundle.getBoolean("isVideo");
            isImg=bundle.getBoolean("isImg");
            imgList=  (ArrayList<MyMedia>)getIntent().getSerializableExtra("img");
            isCollection=bundle.getString("isCollection");
            isGood=bundle.getString("isGood");
            comment_id=bundle.getString("comment_id");


            good=bundle.getString("good",null);
            comment=bundle.getString("comment",null);
            collection=bundle.getString("collection",null);

            tv_comment=findViewById(R.id.tv_comment);
            tv_comment.setText("??????  ???"+comment+"???");
            tv_good=findViewById(R.id.tv_good);
            tv_good.setText("??????  ???"+good+"???");
            tv_collection=findViewById(R.id.tv_collection);
            tv_collection.setText("??????  ???"+collection+"???");







        }catch (Exception e){}
        rcv_data=findViewById(R.id.rcv_data);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_report=findViewById(R.id.iv_report);
        iv_report.setOnClickListener(this);
        iv_share=findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        riv_name_img=findViewById(R.id.riv_name_img);
        tv_name=findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_time=findViewById(R.id.tv_time);
        tv_time.setText(new TimeUtil().timeStamp(time));
        tv_msg=findViewById(R.id.tv_msg);
        tv_msg.setText(msg);

            Glide.with(this).load(img_user).placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(riv_name_img);

        nineGrid=findViewById(R.id.nineGrid);
        gsyVideoPlayer=findViewById(R.id.detail_player);
        iv_sendComment=findViewById(R.id.iv_sendComment);
        iv_sendComment.setOnClickListener(this);
        ct_input_msg=findViewById(R.id.ct_input_msg);
        iv_collection=findViewById(R.id.iv_collection);
        iv_collection.setOnClickListener(this);
        iv_thumbs=findViewById(R.id.iv_thumbs);
        iv_thumbs.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        tv_sendData=findViewById(R.id.tv_sendData);
        tv_sendData.setOnClickListener(this);
        describe=msg;


        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<10;i++){
//            ComentDataListUtil comentDataListUtil=new ComentDataListUtil();
//            comentDataListUtil.setTitle("??????"+i);
//            comentDataListUtil.setMsg("????????????????????????"+i);
//            comentDataList.add(comentDataListUtil);
//        }
        commentListAdapter=new CommentListAdapter(DynamicDetailsActivity.this,comentDataList);
        rcv_data.setAdapter(commentListAdapter);
        commentListAdapter.setDelCommentUpdateInterface(new DelCommentUpdateInterface() {
            @Override
            public void delCommentUpdateInterface() {
                //??????????????????
                page=1;
                comentDataList.clear();
                getData();
            }
        });

        // ???????????????????????????????????????????????????????????????List

        ArrayList<MyMedia> mediaList =imgList;
//        ToastManager.showShortToast(DynamicDetailsActivity.this,imgList.size()+"");
        // ??????????????????????????????
        if (mediaList != null && mediaList.size() > 0) {
            ArrayList<NineGridItem> nineGridItemList = new ArrayList<>();
            for (MyMedia myMedia : mediaList) {
                String thumbnailUrl = myMedia.getImageUrl();
                String bigImageUrl = thumbnailUrl;
                String videoUrl = myMedia.getVideoUrl();
                nineGridItemList.add(new NineGridItem(thumbnailUrl, bigImageUrl, videoUrl));
            }
            NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(nineGridItemList);
            nineGrid.setAdapter(nineGridViewAdapter);
        }

        //????????????
        if (isVideo==true){
            gsyVideoPlayer.setVisibility(View.VISIBLE);
        }else {
            gsyVideoPlayer.setVisibility(View.GONE);
        }
        //????????????
        if (isImg==true){
            nineGrid.setVisibility(View.VISIBLE);
        }else {
            nineGrid.setVisibility(View.GONE);
        }

        if (imgList.size()>0) {
            if (imgList.get(0).getVideoUrl()!=null) {

                //?????????????????????
                //String videoUrl="http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
                gsyVideoPlayer.setUpLazy(imgList.get(0).getVideoUrl(), true, null, null, "");
                //??????title
                gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
                //???????????????
                gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
                //????????????????????????
                gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gsyVideoPlayer.startWindowFullscreen(DynamicDetailsActivity.this, false, true);
                    }
                });
                //??????????????????
                gsyVideoPlayer.setPlayTag(PLAY_TAG);
                gsyVideoPlayer.setPlayPosition(1);
                //?????????????????????????????????????????????????????????????????????
                gsyVideoPlayer.setAutoFullWithSize(false);
                //?????????????????????????????????
                gsyVideoPlayer.setReleaseWhenLossAudio(false);
                //????????????
                gsyVideoPlayer.setShowFullAnimation(true);
                //????????????????????????
                gsyVideoPlayer.setIsTouchWiget(false);
            }
        }
        //????????????
        if (isCollection.equals("0")){
            iv_collection.setImageResource(R.mipmap.no_collection_img);

        }else {
            iv_collection.setImageResource(R.mipmap.collection_img);

        }
        //????????????
        if (isGood.equals("0")){
            iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
            isGood="1";
        }else {
            iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
            isGood="0";

        }
        getShareInfo();

        //??????
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //??????
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                comentDataList.clear();
                getData();
            }

            @Override
            public void onLoadMore() {
                //????????????
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
     * ??????????????????????????????
     */
    private void getData() {
        comentDataList.clear();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        SendDynamicDetailsData sendDynamicDetailsData=new SendDynamicDetailsData();
        sendDynamicDetailsData.setToken(token);
        sendDynamicDetailsData.setPage("1");
        sendDynamicDetailsData.setLimit("15");
        sendDynamicDetailsData.setModule("post");
        sendDynamicDetailsData.setModule_id(post_id);
        sendDynamicDetailsData.setComment_id(comment_id);
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
                        Log.i("ncjfjiskdkfflp",data);
                        Gson gson=new Gson();
                        GetCommentDataUtil getCommentDataUtil=gson.fromJson(data, GetCommentDataUtil.class);
                        for (int i=0;i<getCommentDataUtil.getData().getDatalist().size();i++){
                                ComentDataListUtil comentDataListUtil=new ComentDataListUtil();
                                comentDataListUtil.setPost_id(post_id+"");
                                comentDataListUtil.setComment_id(getCommentDataUtil.getData().getDatalist().get(i).getId()+"");
                                comentDataListUtil.setIf_good(getCommentDataUtil.getData().getDatalist().get(i).isIs_good());
                                comentDataListUtil.setGood_total(getCommentDataUtil.getData().getDatalist().get(i).getComment_total()+"");
                                comentDataListUtil.setTitle(getCommentDataUtil.getData().getDatalist().get(i).getUser().getNickname());
                                comentDataListUtil.setThumbs(getCommentDataUtil.getData().getDatalist().get(i).getTotal_like()+"");
                                comentDataListUtil.setIs_owner(getCommentDataUtil.getData().getDatalist().get(i).getIs_owner());

                                comentDataListUtil.setCommentName(getCommentDataUtil.getData().getDatalist().get(i).getUser().getNickname());
                                comentDataListUtil.setCommentNumber(getCommentDataUtil.getData().getDatalist().get(i).getComment_total()+"");

//                                comentDataListUtil.setTime(getCommentDataUtil.getData().getList().get(i).get());
                                comentDataListUtil.setNumber(getCommentDataUtil.getData().getDatalist().get(i).getComment_total()+"");
                                comentDataListUtil.setTime(getCommentDataUtil.getData().getDatalist().get(i).getCreate_time()+"");

                                comentDataListUtil.setMsg(getCommentDataUtil.getData().getDatalist().get(i).getContent());
                                comentDataListUtil.setImg(getCommentDataUtil.getData().getDatalist().get(i).getUser().getAvatar());

                                comentDataListUtil.setId(getCommentDataUtil.getData().getDatalist().get(i).getId()+"");

                                comentDataList.add(comentDataListUtil);
                        }
                        commentListAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("jafdjafknkfddsfsdf","????????????"+response.getException().getMessage()
                                +"     "+response.getRawResponse().message());

                        ToastManager.showShortToast(DynamicDetailsActivity.this,"????????????"+response.getException().getMessage()
                        +"     "+response.getRawResponse().message());
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
            case R.id.iv_report:
                //??????
                selectMenu();
                break;
            case R.id.iv_share:
                //??????
                showSharePopwindow();
                break;
            case R.id.iv_sendComment:
                //????????????
               String msg= ct_input_msg.getText().toString();
               if (TextUtils.isEmpty(msg)){
                   ToastManager.showShortToast(DynamicDetailsActivity.this,"?????????????????????");
                   return;
               }
               sendComment(msg);
            case R.id.tv_sendData:
                 msg= ct_input_msg.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    ToastManager.showShortToast(DynamicDetailsActivity.this,"?????????????????????");
                    return;
                }
                sendComment(msg);
                break;
            case R.id.iv_collection:
                //??????
                sendCollection(Api.GET_POST_ABOUT_COLLECTION);
                break;
            case R.id.iv_thumbs:
                //??????
                sendThumbs(Api.GET_POST_ABOUT_GOOD);
                break;
            case R.id.llt_WeChat_circle_friends:
//                ?????????????????????
                window.dismiss();
                //????????????  title,target,thumbnail,describe,invitation_link;
                UMImage image = new UMImage(DynamicDetailsActivity.this,thumbnail);
                //???????????????
                image.setThumb(image);
                //????????????(??????????????????????????????)
                UMWeb web = new UMWeb(target);
                web.setTitle(title);//??????
                web.setThumb(image);  //?????????
                web.setDescription(describe);//??????
                new ShareAction(DynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .withText("hello")
                        .withMedia(web)
                        .setCallback(umShareListener).share();

                break;
            case R.id.llt_WeChat_friends:
                //????????????
                window.dismiss();
                //????????????title,target,thumbnail,describe,invitation_link;
                UMImage imageWeChat_friends = new UMImage(DynamicDetailsActivity.this,thumbnail);
                //???????????????
                imageWeChat_friends.setThumb(imageWeChat_friends);
                //????????????(??????????????????????????????)
                UMWeb web_imageWeChat_friends = new UMWeb(target);
                web_imageWeChat_friends.setTitle(title);//??????
                web_imageWeChat_friends.setThumb(imageWeChat_friends);  //?????????
                web_imageWeChat_friends.setDescription(describe);//??????
                new ShareAction(DynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("hello")
                        .withMedia(web_imageWeChat_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_qq_friends:
                //QQ??????
                window.dismiss();
                //???????????? title,target,thumbnail,describe,invitation_link;
                UMImage imageqq_friends = new UMImage(DynamicDetailsActivity.this, thumbnail);
                //???????????????
                imageqq_friends.setThumb(imageqq_friends);
//                //????????????(??????????????????????????????)
                UMWeb web_qq_friends = new UMWeb(target);
//                UMWeb web = new UMWeb("http://nnddkj.com");
                web_qq_friends.setTitle(title);//??????
                web_qq_friends.setThumb(imageqq_friends);  //?????????
                web_qq_friends.setDescription(describe);//??????
                new ShareAction(DynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("hello")
                        .withMedia(web_qq_friends)
//                        .withMedia(imageqq_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Qzone:
                //QQ??????  title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageQzone = new UMImage(DynamicDetailsActivity.this, thumbnail);
                //???????????????
                imageQzone.setThumb(imageQzone);
                //????????????(??????????????????????????????)
                UMWeb web_Qzone = new UMWeb(target);
                web_Qzone.setTitle(title);//??????
                web_Qzone.setThumb(imageQzone);  //?????????
                web_Qzone.setDescription(describe);//??????
                new ShareAction(DynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
//                        .withText("hello")
                        .withMedia(web_Qzone)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_weibo:
                //webbo   title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageweibo = new UMImage(DynamicDetailsActivity.this,thumbnail);
                //???????????????
                imageweibo.setThumb(imageweibo);
                UMWeb web_weibo = new UMWeb(target);
                web_weibo.setTitle(title);//??????
                web_weibo.setThumb(imageweibo);  //?????????
                web_weibo.setDescription(describe);//??????
                new ShareAction(DynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
//                        .withText("hello")
//                        .withMedia(imageweibo)
                        .withMedia(web_weibo)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Copylink:
                //??????
                window.dismiss();
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
//                cmb.setText(mProductDetailsUtil.getBanner().get(0).getShoppingBannerLink());
                cmb.setText(target);
                Toast.makeText(DynamicDetailsActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     * ?????????????????????   ?????? ???????????????
     */
    private void sendThumbs(  String url) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
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
                            ToastManager.showShortToast(DynamicDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                    if ( isGood.equals("0")) {
                                        int number= Integer.parseInt(good)-1;

                                        if (number<=0){
                                            number=0;
                                        }
                                        isGood="1";
                                       good=(number+"");
                                        iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                                    }else {
                                        isGood="0";
                                        int number= Integer.parseInt(good)+1;
                                        good=(number+"");
                                        iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);


                                    }

                            }
                            tv_good.setText("??????  ???"+good+"???");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(DynamicDetailsActivity.this,"????????????");
                    }
                });

    }
    private void sendCollection( String url) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("post_id",post_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String myData=response.body();
                        Log.i("dsfjsjifdsjfsfsf",myData);
                        try {
                            JSONObject jsonObject=new JSONObject(myData);
                            String msg=jsonObject.getString("message");
                            int code=jsonObject.getInt("code");
                            ToastManager.showShortToast(DynamicDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){

                                    if (isCollection.equals("1")) {
                                        //?????????
                                        int number= Integer.parseInt(collection)-1;

                                        if (number<=0){
                                            number=0;
                                        }
                                        isCollection="0";
                                        collection=(number+"");
                                        iv_collection.setImageResource(R.mipmap.no_collection_img);

                                    }else {
                                        //??????
                                        isCollection="1";
                                        int number= Integer.parseInt(collection);
                                        collection=(number+1+"");
                                        iv_collection.setImageResource(R.mipmap.collection_img);
                                    }

                            }
                            tv_collection.setText("??????  ???"+collection+"???");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(DynamicDetailsActivity.this,"????????????");
                    }
                });

    }



    /**
     * ????????????
     */
    private void sendComment(String content) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        SendCommentData sendCommentData=new SendCommentData();
        sendCommentData.setComment_id(comment_id);
        sendCommentData.setContent(content);
        sendCommentData.setModule("post");
        sendCommentData.setModule_id(post_id);
        sendCommentData.setToken(token);
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
                                comentDataList.clear();
                                comment= ((Integer.parseInt(comment))+1)+"";
                                tv_comment.setText("??????  ???"+comment+"???");
                                getData();
                            }
                            ToastManager.showShortToast(DynamicDetailsActivity.this,msg);
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(DynamicDetailsActivity.this,"????????????");
                    }
                });
    }

    /**
     * ????????????
     */
    private void selectMenu() {
        if(popWinmenu==null) {
            //????????????????????????
            OnClickLintener paramOnClickListener = new OnClickLintener();
            //??????????????????????????????????????????????????????
            popWinmenu = new PopWinMenu(DynamicDetailsActivity.this,paramOnClickListener,
                    DisplayUtil.dip2px(this, 130), DisplayUtil.dip2px(this, 75));
            //???????????????????????????????????????????????????????????????
            popWinmenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popWinmenu.dismiss();
                    }
                }
            });
        }
        //????????????????????????
        popWinmenu.setFocusable(true);
        //??????????????????x???y????????????????????????????????????
        popWinmenu.showAsDropDown(iv_report, 0, 0);
        //??????????????????????????????
        popWinmenu.update();
    }
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id){
                case R.id.layout_report:
                    //??????
                    popWinmenu.dismiss();
                    Intent intent=new Intent();
                    intent.setClass(DynamicDetailsActivity.this, ReportDynamicActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("post_id",post_id);
                    startActivity( intent);

                    break;
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        getData();
    }
    /**
     * ??????
     */
    private void showSharePopwindow() {
        // ??????layoutInflater??????View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewShare = inflater.inflate(R.layout.share, null);

        // ?????????????????????????????????????????? getWindow().getDecorView().getWidth()

        window = new PopupWindow(viewShare, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // ??????popWindow?????????????????????????????????????????????????????????true
        window.setFocusable(true);

        // ?????????????????????????????????????????????popWindow???????????????popWindow????????????
        window.setBackgroundDrawable(new BitmapDrawable());

        // // ???????????????ColorDrawable??????????????????
//		 ColorDrawable dw = new ColorDrawable(Color.RED);//0xb0000000
//		 window.setBackgroundDrawable(dw);
//		 backgroundAlpha(1f);
        // ????????????????????????
//        transparentDialog(0.5f);


        // ????????????View??????????????????
//		 window.showAsDropDown(FacialMaskActivity.this.findViewById(R.id.start));

        // ??????popWindow????????????????????????
        // window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // ???????????????
        window.showAtLocation(findViewById(R.id.ct_input_msg), Gravity.BOTTOM, 0,
                0);
        btn_cancel= (Button) viewShare.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // ??????
                window.dismiss();
//                transparentDialog(1f);

            }
        });
        llt_WeChat_circle_friends=(LinearLayout) viewShare.findViewById(R.id.llt_WeChat_circle_friends);
        llt_WeChat_circle_friends.setOnClickListener(this);
        llt_WeChat_friends=(LinearLayout) viewShare.findViewById(R.id.llt_WeChat_friends);
        llt_WeChat_friends.setOnClickListener(this);
        llt_qq_friends=(LinearLayout) viewShare.findViewById(R.id.llt_qq_friends);
        llt_qq_friends.setOnClickListener(this);
        llt_Qzone=(LinearLayout) viewShare.findViewById(R.id.llt_Qzone);
        llt_Qzone.setOnClickListener(this);
        llt_Copylink=(LinearLayout) viewShare.findViewById(R.id.llt_Copylink);
        llt_Copylink.setOnClickListener(this);
        llt_weibo=(LinearLayout) viewShare.findViewById(R.id.llt_weibo);
        llt_weibo.setOnClickListener(this);

        // popWindow??????????????????
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow??????");
//                transparentDialog(1f);
            }
        });

    }
    /**
     * ???????????????dialog????????????????????????????????????
     * @param alphe  0~1f
     */
    private void transparentDialog(float alphe) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alphe;
        getWindow().setAttributes(lp);
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            transparentDialog(1f);
            Toast.makeText(DynamicDetailsActivity.this, platform + " ???????????????", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(DynamicDetailsActivity.this,platform + " ???????????????", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
                transparentDialog(1f);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            transparentDialog(1f);
            Toast.makeText(DynamicDetailsActivity.this,platform + " ???????????????", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * ??????????????????
     */
    private void getShareInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.GET_POST_ABOUT_SHARE;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type","post")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfiaddsfdsfg",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String msg=jsonObject.getString("message");
                            int code=jsonObject.getInt("code");
//                            ToastManager.showShortToast(ShopDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                Gson gson=new Gson();
                                getShareDataUtil= gson.fromJson(data, GetShareDataUtil.class);
                                title=getShareDataUtil.getData().getTitle();
                                target=getShareDataUtil.getData().getDownload();
                                thumbnail=getShareDataUtil.getData().getIcon();
//                                describe=getShareDataUtil.getData().getContent();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(DynamicDetailsActivity.this,"????????????");
                    }
                });
    }

}
