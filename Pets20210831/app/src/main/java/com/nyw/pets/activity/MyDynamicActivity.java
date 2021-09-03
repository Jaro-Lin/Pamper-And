package com.nyw.pets.activity;

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

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetShareDataUtil;
import com.nyw.pets.activity.util.GetMyDynamicDataUtil;
import com.nyw.pets.activity.util.MyDynamicUtil;
import com.nyw.pets.adapter.MyDynamicdAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.interfaces.ClickShareDataInterface;
import com.nyw.pets.interfaces.DelDynamicdUpdateInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
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
 * 我的动态
 */
public class MyDynamicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private MyDynamicdAdapter myDynamicdAdapter;
    private List<MyDynamicUtil> myDynamicUtilList=new ArrayList<>();
    private  int page=1,limit=15;
    //列表播放视频 标志
    public final   String  PLAY_TAG="PLAY_TAG";
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
        setContentView(R.layout.activity_my_dynamic);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);
        refreshview=findViewById(R.id.refreshview);
        tv_item=findViewById(R.id.tv_item);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));

//        for (int i=0;i<8;i++){
//            MyDynamicUtil myDynamicUtil=new MyDynamicUtil();
//            myDynamicUtil.setName("王五"+i);
//            myDynamicUtil.setTime("1585396422");
//            myDynamicUtil.setMsg("今天的狗狗真可爱，现在带狗狗出去逛街了，开心。");
//            myDynamicUtil.setComment(86+i+"");
//            myDynamicUtil.setThumbs(96+i+"");
//            myDynamicUtil.setCollection(266+i+"");
//            ArrayList<MyMedia> mediaList10 = new ArrayList<>();
//            for (int j = 0;j<5;j++){
//                MyMedia myMedia=new MyMedia();
//                myMedia.setImageUrl("http://ml.nnddkj.com/img/img1.png");
////                myMedia.setVideoUrl("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
//                mediaList10.add(myMedia);
//            }
//            myDynamicUtil.setMediaList(mediaList10);
//            myDynamicUtilList.add(myDynamicUtil);
//
//        }

        myDynamicdAdapter=new MyDynamicdAdapter(MyDynamicActivity.this,myDynamicUtilList);
        rcv_data.setAdapter(myDynamicdAdapter);

        myDynamicdAdapter.setClickShareDataInterface(new ClickShareDataInterface() {
            @Override
            public void clickShareDataListener(int i, String msg) {
                //执行分享
//                ToastManager.showShortToast(getContext(),"点击分享");
                describe=msg;
                showSharePopwindow();
            }




        });
        myDynamicdAdapter.setDelDynamicdUpdateInterface(new DelDynamicdUpdateInterface() {
            @Override
            public void delDynamicdUpdateInterface() {
                //动态更新
                page=1;
                myDynamicUtilList.clear();
                getData();

            }
        });


        getShareInfo();
        initVideo();

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                myDynamicUtilList.clear();
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
    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.GET_USER_PERSONAL_POST_USER).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsifdjsfofgtgt",data);
                        Gson gson=new Gson();
                        GetMyDynamicDataUtil getMyDynamicDataUtil= gson.fromJson(data, GetMyDynamicDataUtil.class);

                        if (getMyDynamicDataUtil.getData()!=null){
                            if (getMyDynamicDataUtil.getData().getList()!=null){

                        for (int i=0;i<getMyDynamicDataUtil.getData().getList().size();i++){

                            MyDynamicUtil myDynamicUtil=new MyDynamicUtil();
                            myDynamicUtil.setUid( getMyDynamicDataUtil.getData().getList().get(i).getUid());
                            myDynamicUtil.setPost_id( getMyDynamicDataUtil.getData().getList().get(i).getPost_id()+"");
                            myDynamicUtil.setName( getMyDynamicDataUtil.getData().getList().get(i).getNickname());
                            myDynamicUtil.setTime( getMyDynamicDataUtil.getData().getList().get(i).getAdd_time());
                            myDynamicUtil.setMsg( getMyDynamicDataUtil.getData().getList().get(i).getContent());
                            myDynamicUtil.setComment( getMyDynamicDataUtil.getData().getList().get(i).getComment_total()+"");
                            myDynamicUtil.setThumbs(getMyDynamicDataUtil.getData().getList().get(i).getGood()+"");
                            myDynamicUtil.setCollection(getMyDynamicDataUtil.getData().getList().get(i).getCollection()+"");

                            myDynamicUtil.setIf_collection(getMyDynamicDataUtil.getData().getList().get(i).getIf_collection()+"");
                            myDynamicUtil.setIf_good(getMyDynamicDataUtil.getData().getList().get(i).isIf_good()+"");

                            myDynamicUtil.setServer(getMyDynamicDataUtil.getData().getList().get(i).getServer());
                            myDynamicUtil.setImg(getMyDynamicDataUtil.getData().getList().get(i).getAvatar());
                            myDynamicUtil.setComment_id(getMyDynamicDataUtil.getData().getList().get(i).getComment_id()+"");

                            ArrayList<MyMedia> mediaList10 = new ArrayList<>();

                            if (getMyDynamicDataUtil.getData().getList().get(i).getImages().size()>=1
                                    &&!getMyDynamicDataUtil.getData().getList().get(i).getImages().equals("[]")
                                    &&getMyDynamicDataUtil.getData().getList().get(i).getImages()!=null) {
                                for (int j = 0; j < getMyDynamicDataUtil.getData().getList().get(i).getImages().size(); j++) {
                                    MyMedia myMedia = new MyMedia();

                                    myMedia.setImageUrl(getMyDynamicDataUtil.getData().getList().get(i).getServer()
                                            + getMyDynamicDataUtil.getData().getList().get(i).getImages().get(j).getAddress());
                                    mediaList10.add(myMedia);
                                }
                                myDynamicUtil.setImg(true);

                            }else {
                                myDynamicUtil.setImg(false);
                            }

                            if (getMyDynamicDataUtil.getData().getList().get(i).getVideo().size()>=1
                                    &&!getMyDynamicDataUtil.getData().getList().get(i).getVideo().equals("[]")
                                    &&getMyDynamicDataUtil.getData().getList().get(i).getVideo()!=null) {
                                for (int j = 0; j < getMyDynamicDataUtil.getData().getList().get(i).getVideo().size(); j++) {
                                    MyMedia myMedia = new MyMedia();

                                    myMedia.setVideoUrl(getMyDynamicDataUtil.getData().getList().get(i).getServer()
                                            + getMyDynamicDataUtil.getData().getList().get(i).getVideo().get(j).getAddress());
                                    mediaList10.add(myMedia);
                                }
                                myDynamicUtil.setVideo(true);
                            }else {
                                myDynamicUtil.setVideo(false);
                            }





                            myDynamicUtil.setMediaList(mediaList10);
                            myDynamicUtilList.add(myDynamicUtil);


                        }

                            }
                        }

                        myDynamicdAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyDynamicActivity.this,"网络错误");
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
            case R.id.llt_WeChat_circle_friends:
//                微信朋友圈分享
                window.dismiss();
                //分享图片  title,target,thumbnail,describe,invitation_link;
                UMImage image = new UMImage(MyDynamicActivity.this,thumbnail);
                //设置缩略图
                image.setThumb(image);
                //分享链接(分享图片和链接二选一)
                UMWeb web = new UMWeb(target);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(describe);//描述
                new ShareAction(MyDynamicActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .withText("hello")
                        .withMedia(web)
                        .setCallback(umShareListener).share();

                break;
            case R.id.llt_WeChat_friends:
                //微信好友
                window.dismiss();
                //分享图片title,target,thumbnail,describe,invitation_link;
                UMImage imageWeChat_friends = new UMImage(MyDynamicActivity.this,thumbnail);
                //设置缩略图
                imageWeChat_friends.setThumb(imageWeChat_friends);
                //分享链接(分享图片和链接二选一)
                UMWeb web_imageWeChat_friends = new UMWeb(target);
                web_imageWeChat_friends.setTitle(title);//标题
                web_imageWeChat_friends.setThumb(imageWeChat_friends);  //缩略图
                web_imageWeChat_friends.setDescription(describe);//描述
                new ShareAction(MyDynamicActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("hello")
                        .withMedia(web_imageWeChat_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_qq_friends:
                //QQ好友
                window.dismiss();
                //分享图片 title,target,thumbnail,describe,invitation_link;
                UMImage imageqq_friends = new UMImage(MyDynamicActivity.this, thumbnail);
                //设置缩略图
                imageqq_friends.setThumb(imageqq_friends);
//                //分享链接(分享图片和链接二选一)
                UMWeb web_qq_friends = new UMWeb(target);
//                UMWeb web = new UMWeb("http://nnddkj.com");
                web_qq_friends.setTitle(title);//标题
                web_qq_friends.setThumb(imageqq_friends);  //缩略图
                web_qq_friends.setDescription(describe);//描述
                new ShareAction(MyDynamicActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("hello")
                        .withMedia(web_qq_friends)
//                        .withMedia(imageqq_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Qzone:
                //QQ空间  title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageQzone = new UMImage(MyDynamicActivity.this, thumbnail);
                //设置缩略图
                imageQzone.setThumb(imageQzone);
                //分享链接(分享图片和链接二选一)
                UMWeb web_Qzone = new UMWeb(target);
                web_Qzone.setTitle(title);//标题
                web_Qzone.setThumb(imageQzone);  //缩略图
                web_Qzone.setDescription(describe);//描述
                new ShareAction(MyDynamicActivity.this).setPlatform(SHARE_MEDIA.QZONE)
//                        .withText("hello")
                        .withMedia(web_Qzone)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_weibo:
                //webbo   title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageweibo = new UMImage(MyDynamicActivity.this,thumbnail);
                //设置缩略图
                imageweibo.setThumb(imageweibo);
                UMWeb web_weibo = new UMWeb(target);
                web_weibo.setTitle(title);//标题
                web_weibo.setThumb(imageweibo);  //缩略图
                web_weibo.setDescription(describe);//描述
                new ShareAction(MyDynamicActivity.this).setPlatform(SHARE_MEDIA.SINA)
//                        .withText("hello")
//                        .withMedia(imageweibo)
                        .withMedia(web_weibo)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Copylink:
                //复制
                window.dismiss();
                ClipboardManager cmb = (ClipboardManager)MyDynamicActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
//                cmb.setText(mProductDetailsUtil.getBanner().get(0).getShoppingBannerLink());
                cmb.setText(target);
                Toast.makeText(MyDynamicActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                break;

        }
    }
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
                        if(GSYVideoManager.isFullState(MyDynamicActivity.this)) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        myDynamicdAdapter.notifyDataSetChanged();
                    }
                }
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
        myDynamicUtilList.clear();
        getData();
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
            Toast.makeText(MyDynamicActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyDynamicActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
                transparentDialog(1f);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            transparentDialog(1f);
            Toast.makeText(MyDynamicActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
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
                            if (code== AppConfig.SUCCESS){
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
                        ToastManager.showShortToast(MyDynamicActivity.this,"网络错误");
                    }
                });
    }

}
