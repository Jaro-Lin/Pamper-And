package com.nyw.pets.activity.user;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetShareDataUtil;
import com.nyw.pets.activity.util.GetDynamicOtherDataUtil;
import com.nyw.pets.activity.util.GetMyDynamicDetailsUtil;
import com.nyw.pets.adapter.MyDynamicDetailsAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyDynamicDetailsUtil;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.interfaces.ClickShareDataInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.RoundImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心 用户动态详情界面
 */
public class MyDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {
    //数据
    private MyDynamicDetailsAdapter myDynamicDetailsAdapter;
    private List<MyDynamicDetailsUtil> myDynamicDetailsList = new ArrayList<>();
    //列表数据
    private RecyclerView rcv_data;
    private ImageView iv_follow,iv_hide;
    //是否关注，默认无关注
    private boolean isFollow=false;
    private  String token, uid;
    private TextView tv_name,tv_city,tv_msg,tv_comment,tv_thumbs;
    private ImageView iv_sex;
    private   int limit=15,page=1;
    //列表播放视频 标志
    public final   String  PLAY_TAG="PLAY_TAG";
    private RoundImageView riv_name_img;
    private GetMyDynamicDetailsUtil getMyDynamicDetailsUtil;
    //刷新
    private MeiTuanRefreshView refreshview;

    //分享数据
    private GetShareDataUtil getShareDataUtil;
    //分享控件
    private LinearLayout llt_weibo, llt_Copylink, llt_Qzone,
            llt_qq_friends, llt_WeChat_friends, llt_WeChat_circle_friends;
    private String title,target,thumbnail,describe,invitation_link;
    private Button btn_share;
    // 打开分享界面PopupWindow对象
    private PopupWindow window;
    //取消分享
    private Button btn_cancel;
    private  View view;
    private TextView tv_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic_details);
        initView();
    }

    private void initView() {
        rcv_data=findViewById(R.id.rcv_data);
        iv_follow=findViewById(R.id.iv_follow);
        iv_follow.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_name=findViewById(R.id.tv_name);
        iv_sex=findViewById(R.id.iv_sex);
        tv_city=findViewById(R.id.tv_city);
        tv_msg=findViewById(R.id.tv_msg);
        tv_comment=findViewById(R.id.tv_comment);
        tv_thumbs=findViewById(R.id.tv_thumbs);
        riv_name_img=findViewById(R.id.riv_name_img);
        riv_name_img.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);
        tv_item=findViewById(R.id.tv_item);


        rcv_data.setLayoutManager(new LinearLayoutManager(MyDynamicDetailsActivity.this));
//        for (int i=0;i<8;i++){
//            MyDynamicDetailsUtil myDynamicDetailsUtil=new MyDynamicDetailsUtil();
//            myDynamicDetailsUtil.setName("王五"+i);
//            myDynamicDetailsUtil.setTime("1585396422");
//            myDynamicDetailsUtil.setMsg("今天的狗狗真可爱，现在带狗狗出去逛街了，开心。");
//            myDynamicDetailsUtil.setComment(86+i+"");
//            myDynamicDetailsUtil.setThumbs(96+i+"");
//            myDynamicDetailsUtil.setCollection(266+i+"");
//            myDynamicDetailsList.add(myDynamicDetailsUtil);
//
//        }
        myDynamicDetailsAdapter=new MyDynamicDetailsAdapter(MyDynamicDetailsActivity.this,myDynamicDetailsList);
        rcv_data.setAdapter(myDynamicDetailsAdapter);

        myDynamicDetailsAdapter.setClickShareDataInterface(new ClickShareDataInterface() {
            @Override
            public void clickShareDataListener(int i, String msg) {
                //执行分享
//                ToastManager.showShortToast(getContext(),"点击分享");
                describe=msg;
                showSharePopwindow();
            }




        });

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        try{
            Bundle bundle=getIntent().getExtras();
            uid= bundle.getString("uid",null);
        }catch (Exception e){}

        initVideo();

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                myDynamicDetailsList.clear();
                getDynamicData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getDynamicData();
            }
        });

        getShareInfo();

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

    private void initVideo() {

        rcv_data.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = dx + dy;
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(PLAY_TAG)
                            && (position < dx || position > lastVisibleItem)) {
                        if(GSYVideoManager.isFullState(MyDynamicDetailsActivity.this)) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        myDynamicDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }


    /**
     * 获取基础信息数据
     */
    private void getData() {

        OkGo.<String>post(Api.GET_USER_INFO_OTHER).tag(this)
                .params("token",token)
                .params("uid",uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sfskdfoisderswsfsf", data);
                        Gson gson = new Gson();
                         getMyDynamicDetailsUtil = gson.fromJson(data, GetMyDynamicDetailsUtil.class);

                        if (getMyDynamicDetailsUtil.getCode() == AppConfig.SUCCESS) {

                        tv_name.setText(getMyDynamicDetailsUtil.getData().getNickname());

                        if (getMyDynamicDetailsUtil.getData().getSex() == AppConfig.SUCCESS) {
                            iv_sex.setImageResource(R.mipmap.male_img);
                        } else {
                            iv_sex.setImageResource(R.mipmap.no_male_img);
                        }
                        //城市
                        tv_city.setText(getMyDynamicDetailsUtil.getData().getCity());
                        tv_msg.setText(getMyDynamicDetailsUtil.getData().getSpe());

                        if (getMyDynamicDetailsUtil.getData().getIf_follow() == 1) {
                            iv_follow.setImageResource(R.mipmap.follow_img);
                        } else {
                            iv_follow.setImageResource(R.mipmap.no_follow_img);
                        }





                        tv_comment.setText(getMyDynamicDetailsUtil.getData().getFans() + "");
                        tv_thumbs.setText(getMyDynamicDetailsUtil.getData().getFollow() + "");

                            String avatar=  getMyDynamicDetailsUtil.getData().getAvatar();
                            if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                                Glide.with(MyDynamicDetailsActivity.this).load(avatar)
                                        .error(R.mipmap.user_app_default)
                                        .placeholder(R.mipmap.user_app_default).into(riv_name_img);
                            }else {
                                Glide.with(MyDynamicDetailsActivity.this).load(getMyDynamicDetailsUtil.getData().getServer()
                                        + getMyDynamicDetailsUtil.getData().getAvatar()).placeholder(R.mipmap.http_error)
                                        .error(R.mipmap.http_error).into(riv_name_img);
                            }

                    }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyDynamicDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取动态数据
     */
    private void getDynamicData() {
        SharedPreferences getUser = getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        token = getUser.getString(SaveAPPData.TOKEN, null);
        Log.i("sdjfsifsjfsf", token);


        OkGo.<String>post(Api.GET_PERSONAL_OTHER_USER_POST).tag(this)
                .params("token", token)
                .params("uid", uid)
                .params("page", page)
                .params("limit", limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sfskdfoisfsf", data);
                        Gson gson=new Gson();
                        GetDynamicOtherDataUtil getDynamicOtherDataUtil= gson.fromJson(data, GetDynamicOtherDataUtil.class);

                        for (int i=0;i< getDynamicOtherDataUtil.getData().getList().size();i++){
                            MyDynamicDetailsUtil myDynamicDetailsUtil=new MyDynamicDetailsUtil();
                            myDynamicDetailsUtil.setUid( getDynamicOtherDataUtil.getData().getList().get(i).getUid());
                            myDynamicDetailsUtil.setName( getDynamicOtherDataUtil.getData().getList().get(i).getNickname());
                            myDynamicDetailsUtil.setTime( getDynamicOtherDataUtil.getData().getList().get(i).getAdd_time());
                            myDynamicDetailsUtil.setMsg( getDynamicOtherDataUtil.getData().getList().get(i).getContent());
                            myDynamicDetailsUtil.setComment( getDynamicOtherDataUtil.getData().getList().get(i).getComment_total()+"");
                            myDynamicDetailsUtil.setThumbs(getDynamicOtherDataUtil.getData().getList().get(i).getGood()+"");
                            myDynamicDetailsUtil.setCollection(getDynamicOtherDataUtil.getData().getList().get(i).getCollection()+"");
                            myDynamicDetailsUtil.setPost_id(getDynamicOtherDataUtil.getData().getList().get(i).getPost_id()+"");
                            myDynamicDetailsUtil.setUid(getDynamicOtherDataUtil.getData().getList().get(i).getUid()+"");

                            myDynamicDetailsUtil.setIf_collection(getDynamicOtherDataUtil.getData().getList().get(i).getIf_collection()+"");
                            myDynamicDetailsUtil.setIf_good(getDynamicOtherDataUtil.getData().getList().get(i).getIf_good()+"");

                            myDynamicDetailsUtil.setComment_id(getDynamicOtherDataUtil.getData().getList().get(i).getComment_id()+"");


                            myDynamicDetailsUtil.setServer(getDynamicOtherDataUtil.getData().getList().get(i).getServer());
                            myDynamicDetailsUtil.setImg(getDynamicOtherDataUtil.getData().getList().get(i).getAvatar());

                            ArrayList<MyMedia> mediaList10 = new ArrayList<>();

                            if (getDynamicOtherDataUtil.getData().getList().get(i).getImage().size()>=1
                                    &&!getDynamicOtherDataUtil.getData().getList().get(i).getImage().equals("[]")
                                    &&getDynamicOtherDataUtil.getData().getList().get(i).getImage()!=null) {
                                for (int j = 0; j < getDynamicOtherDataUtil.getData().getList().get(i).getImage().size(); j++) {
                                    MyMedia myMedia = new MyMedia();

                                    myMedia.setImageUrl(getDynamicOtherDataUtil.getData().getList().get(i).getServer()
                                            + getDynamicOtherDataUtil.getData().getList().get(i).getImage().get(j).getAddress());
                                    mediaList10.add(myMedia);
                                }
                                myDynamicDetailsUtil.setImg(true);

                            }else {
                                myDynamicDetailsUtil.setImg(false);
                            }

                            if (getDynamicOtherDataUtil.getData().getList().get(i).getVideo().size()>=1
                                    &&!getDynamicOtherDataUtil.getData().getList().get(i).getVideo().equals("[]")
                                    &&getDynamicOtherDataUtil.getData().getList().get(i).getVideo()!=null) {
                                for (int j = 0; j < getDynamicOtherDataUtil.getData().getList().get(i).getVideo().size(); j++) {
                                    MyMedia myMedia = new MyMedia();

                                    myMedia.setVideoUrl(getDynamicOtherDataUtil.getData().getList().get(i).getServer()
                                            + getDynamicOtherDataUtil.getData().getList().get(i).getVideo().get(j).getAddress());
                                    mediaList10.add(myMedia);
                                }
                                myDynamicDetailsUtil.setVideo(true);
                            }else {
                                myDynamicDetailsUtil.setVideo(false);
                            }




                            myDynamicDetailsUtil.setMediaList(mediaList10);
                            myDynamicDetailsList.add(myDynamicDetailsUtil);
                        }
                        myDynamicDetailsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyDynamicDetailsActivity.this,"网络错误");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_follow:
                //关注与取消关注
                sendFollow();

                break;
            case R.id.iv_hide:
                finish();
                break;
            case R.id.llt_WeChat_circle_friends:
//                微信朋友圈分享
                window.dismiss();
                //分享图片  title,target,thumbnail,describe,invitation_link;
                UMImage image = new UMImage(MyDynamicDetailsActivity.this,thumbnail);
                //设置缩略图
                image.setThumb(image);
                //分享链接(分享图片和链接二选一)
                UMWeb web = new UMWeb(target);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(describe);//描述
                new ShareAction(MyDynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .withText("hello")
                        .withMedia(web)
                        .setCallback(umShareListener).share();

                break;
            case R.id.llt_WeChat_friends:
                //微信好友
                window.dismiss();
                //分享图片title,target,thumbnail,describe,invitation_link;
                UMImage imageWeChat_friends = new UMImage(MyDynamicDetailsActivity.this,thumbnail);
                //设置缩略图
                imageWeChat_friends.setThumb(imageWeChat_friends);
                //分享链接(分享图片和链接二选一)
                UMWeb web_imageWeChat_friends = new UMWeb(target);
                web_imageWeChat_friends.setTitle(title);//标题
                web_imageWeChat_friends.setThumb(imageWeChat_friends);  //缩略图
                web_imageWeChat_friends.setDescription(describe);//描述
                new ShareAction(MyDynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("hello")
                        .withMedia(web_imageWeChat_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_qq_friends:
                //QQ好友
                window.dismiss();
                //分享图片 title,target,thumbnail,describe,invitation_link;
                UMImage imageqq_friends = new UMImage(MyDynamicDetailsActivity.this, thumbnail);
                //设置缩略图
                imageqq_friends.setThumb(imageqq_friends);
//                //分享链接(分享图片和链接二选一)
                UMWeb web_qq_friends = new UMWeb(target);
//                UMWeb web = new UMWeb("http://nnddkj.com");
                web_qq_friends.setTitle(title);//标题
                web_qq_friends.setThumb(imageqq_friends);  //缩略图
                web_qq_friends.setDescription(describe);//描述
                new ShareAction(MyDynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("hello")
                        .withMedia(web_qq_friends)
//                        .withMedia(imageqq_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Qzone:
                //QQ空间  title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageQzone = new UMImage(MyDynamicDetailsActivity.this, thumbnail);
                //设置缩略图
                imageQzone.setThumb(imageQzone);
                //分享链接(分享图片和链接二选一)
                UMWeb web_Qzone = new UMWeb(target);
                web_Qzone.setTitle(title);//标题
                web_Qzone.setThumb(imageQzone);  //缩略图
                web_Qzone.setDescription(describe);//描述
                new ShareAction(MyDynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
//                        .withText("hello")
                        .withMedia(web_Qzone)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_weibo:
                //webbo   title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageweibo = new UMImage(MyDynamicDetailsActivity.this,thumbnail);
                //设置缩略图
                imageweibo.setThumb(imageweibo);
                UMWeb web_weibo = new UMWeb(target);
                web_weibo.setTitle(title);//标题
                web_weibo.setThumb(imageweibo);  //缩略图
                web_weibo.setDescription(describe);//描述
                new ShareAction(MyDynamicDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
//                        .withText("hello")
//                        .withMedia(imageweibo)
                        .withMedia(web_weibo)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Copylink:
                //复制
                window.dismiss();
                ClipboardManager cmb = (ClipboardManager)MyDynamicDetailsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
//                cmb.setText(mProductDetailsUtil.getBanner().get(0).getShoppingBannerLink());
                cmb.setText(target);
                Toast.makeText(MyDynamicDetailsActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 关注用户或取消关注
     */
    private void sendFollow() {

        String url=Api.GET_USER_ABOUT_USER_FOLLOW;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("uid",uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                       Log.i("fjdsjifnvnxirytggdd",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                           String msg= jsonObject.getString("message");
                            int code= jsonObject.getInt("code");
                            if (code==AppConfig.SUCCESS){
                                getData();
                            }
                            ToastManager.showShortToast(MyDynamicDetailsActivity.this,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyDynamicDetailsActivity.this,"网络错误，获取数据失败");
                    }
                });
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
        myDynamicDetailsList.clear();

        getDynamicData();
    }


    /**
     * 分享
     */
    private void showSharePopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewShare = inflater.inflate(R.layout.share, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(viewShare, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 必须要给调用这个方法，否则点击popWindow以外部分，popWindow不会消失
        window.setBackgroundDrawable(new BitmapDrawable());

        // // 实例化一个ColorDrawable颜色为半透明
//		 ColorDrawable dw = new ColorDrawable(Color.RED);//0xb0000000
//		 window.setBackgroundDrawable(dw);
//		 backgroundAlpha(1f);
        // 设置背景颜色变暗
//        transparentDialog(0.5f);


        // 在参照的View控件下方显示
//		 window.showAsDropDown(FacialMaskActivity.this.findViewById(R.id.start));

        // 设置popWindow的显示和消失动画
        // window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation((findViewById(R.id.tv_item)), Gravity.BOTTOM, 0,
                0);
        btn_cancel= (Button) viewShare.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 取消
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

        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
//                transparentDialog(1f);
            }
        });

    }
    /**
     * 分享的时候dialog变亮，背景为更改成透明度
     * @param alphe  0~1f
     */
    private void transparentDialog(float alphe) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams lp =getWindow().getAttributes();
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
            Toast.makeText(MyDynamicDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyDynamicDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
                transparentDialog(1f);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            transparentDialog(1f);
            Toast.makeText(MyDynamicDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 获取分享信息
     */
    private void getShareInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
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
                        ToastManager.showShortToast(MyDynamicDetailsActivity.this,"网络错误");
                    }
                });
    }

}
