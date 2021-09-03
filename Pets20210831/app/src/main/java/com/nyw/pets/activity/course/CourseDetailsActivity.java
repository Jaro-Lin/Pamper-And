package com.nyw.pets.activity.course;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.GetCourseDataUtil;
import com.nyw.pets.activity.util.GetCurriculumDataUtil;
import com.nyw.pets.activity.util.GetCurriculumListUtil;
import com.nyw.pets.adapter.CurriculumListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程详情页面
 */
public class CourseDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private ImageView iv_collection;
    private boolean isCollection=false;

    private RecyclerView rcv_data;
    private CurriculumListAdapter curriculumListAdapter;
    private List<GetCurriculumListUtil> curriculumList=new ArrayList<>();

    //视频播放
    private StandardGSYVideoPlayer detail_player;
    private boolean isPlay;
    private boolean isPause;
    private  OrientationUtils   orientationUtils;
    private ImageView iv_img;
    //视频标题和封面
    private String videoUrl=null,videoTitle="课程";
    //view
    private TextView tv_courseTitle,tv_number;
    //视频封面
    private String voiceImg=null;
    //分页
    private int limit=15,page=1;
    //刷新
    private MeiTuanRefreshView refreshview;
    //分类id
    String id;
    //课程id
    String  type;
    //是否已经加载数据了
    private  boolean isInitViceo=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        initView();
    }



    private void initView() {
        Bundle bundle=getIntent().getExtras();
         id= bundle.getString("id",null);
         type=bundle.getString("type",null);

        Log.i("sdfjfisdjsiffsfdg",id+"    "+type);

        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_collection=findViewById(R.id.iv_collection);
        iv_collection.setOnClickListener(this);
        tv_courseTitle=findViewById(R.id.tv_courseTitle);
        tv_number=findViewById(R.id.tv_number);
        refreshview=findViewById(R.id.refreshview);


        rcv_data=findViewById(R.id.rcv_data);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_data.setLayoutManager(gridLayoutManager);
//        for (int j=0;j<5;j++){
//            GetCurriculumListUtil getCurriculumListUtil=new GetCurriculumListUtil();
//            getCurriculumListUtil.setTitle("这是热门课程"+j);
//            getCurriculumListUtil.setLearningNumber(j+26+"人已学习");
//            curriculumList.add(getCurriculumListUtil);
//        }
        curriculumListAdapter=new CurriculumListAdapter(CourseDetailsActivity.this,curriculumList);
        rcv_data.setAdapter(curriculumListAdapter);

        //播放视频
        detail_player=findViewById(R.id.detail_player);
        iv_img=findViewById(R.id.iv_img);

        getData();

        getOtherCurriculum();

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                curriculumList.clear();
                getOtherCurriculum();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getOtherCurriculum();
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


        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        Bundle bundle=getIntent().getExtras();
        String id= bundle.getString("id",null);
        String type=bundle.getString("type",null);

        Log.i("sdfjfisdjsiffsfdg",id+"    "+type);

        String url=Api.GET_COURSE_VIDEO;

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type_id",type)
                .params("course_id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdferrjsiopyhptudlfsf",data);
                        Gson gson=new Gson();
                        GetCurriculumDataUtil getCurriculumDataUtil=   gson.fromJson(data, GetCurriculumDataUtil.class);
                        if (getCurriculumDataUtil.getCode()== AppConfig.SUCCESS) {
                            //访问成功，显示数据
                            tv_courseTitle.setText(getCurriculumDataUtil.getData().getTitle());
                            tv_number.setText("播放量： "+getCurriculumDataUtil.getData().getStudy_number()+"");
                            //就否收藏
                            int isFollow=getCurriculumDataUtil.getData().getFollow();

                            if (isFollow==0){
                                isCollection=false;
                                iv_collection.setImageResource(R.mipmap.no_collection_img);

                            }else {
                                isCollection=true;
                                iv_collection.setImageResource(R.mipmap.collection_img);
                            }

                            //视频
                            if (isInitViceo==true){
                                isInitViceo=false;
                                videoUrl = getCurriculumDataUtil.getData().getServer() + getCurriculumDataUtil.getData().getVideo();
                            //视频封面 图片
                                voiceImg = getCurriculumDataUtil.getData().getServer() + getCurriculumDataUtil.getData().getImg();

                                initVideo();
                            }



                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 收藏 与取消收藏
     */
    private void sendCollecton() {

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        String url=Api.GET_COURSE_COLLECTION;

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type_id",type)
                .params("course_id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfersdrjsiopptudlfsf",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(CourseDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                getData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取相关推荐课程
     */
    private void getOtherCurriculum() {

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        Bundle bundle=getIntent().getExtras();
        String id= bundle.getString("id",null);
        String type=bundle.getString("type",null);

        Log.i("sdfjfisdjsiffsfdg",id+"    "+type);

        String url=Api.GET_COURSE_REC_ABOUT;


        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type_id",type)
                .params("course_id",id)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdferrjsiopptudlfsf",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                            ToastManager.showShortToast(CourseDetailsActivity.this,msg);

                            Gson gson=new Gson();
                            GetCourseDataUtil getCourseDataUtil=gson.fromJson(data,GetCourseDataUtil.class);

                            for (int i=0;i<getCourseDataUtil.getData().getList().size();i++){
                                GetCurriculumListUtil getCurriculumListUtil=new GetCurriculumListUtil();
                                getCurriculumListUtil.setTitle(getCourseDataUtil.getData().getList().get(i).getTitle());
                                getCurriculumListUtil.setLearningNumber(getCourseDataUtil.getData().getList().get(i).getStudy_number()+"人已学习");
                                getCurriculumListUtil.setId(getCourseDataUtil.getData().getList().get(i).getId()+"");
                                getCurriculumListUtil.setType(getCourseDataUtil.getData().getList().get(i).getType_id()+"");
                                getCurriculumListUtil.setImg(getCourseDataUtil.getData().getList().get(i).getImg()+"");
                                curriculumList.add(getCurriculumListUtil);
                            }
                            curriculumListAdapter.notifyDataSetChanged();






                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(CourseDetailsActivity.this,"网络错误");
                    }
                });
    }

    private void initVideo() {
//        videoUrl="http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
        //增加封面
//        videoUrl="https://vdept.bdstatic.com/72706c7164376970656e465552726945/3556535671386a6b/51ae49a19f72147f10cd97d558d3fd7b44127efbc5c8097f9dcdf382f331b47749d93cadbb0a8989763d9b0e632be072.mp4?auth_key=1592132406-0-0-7a96dc93f090fcf86ad432cf93cb0519";
//     try {
         Log.i("sjfsidoeirjecxa", videoUrl);
         ImageView imageView = new ImageView(this);
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.collection_img);
         Glide.with(this).load(voiceImg).error(R.mipmap.http_error).placeholder(R.mipmap.http_error).into(imageView);

         //增加title
         detail_player.getTitleTextView().setVisibility(View.GONE);
         detail_player.getBackButton().setVisibility(View.GONE);

         //外部辅助的旋转，帮助全屏
         orientationUtils = new OrientationUtils(this, detail_player);
         //初始化不打开外部的旋转
         orientationUtils.setEnable(false);

         GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
         gsyVideoOption.setThumbImageView(imageView)
                 .setIsTouchWiget(true)
                 .setRotateViewAuto(false)
                 .setLockLand(false)
                 .setAutoFullWithSize(true)
                 .setShowFullAnimation(false)
                 .setNeedLockFull(true)
                 .setUrl(videoUrl)
                 .setCacheWithPlay(false)
                 .setVideoTitle(videoTitle)
                 .setVideoAllCallBack(new GSYSampleCallBack() {
                     @Override
                     public void onPrepared(String url, Object... objects) {
                         super.onPrepared(url, objects);
                         //开始播放了才能旋转和全屏
                         orientationUtils.setEnable(true);
                         isPlay = true;
                     }

                     @Override
                     public void onQuitFullscreen(String url, Object... objects) {
                         super.onQuitFullscreen(url, objects);
                         Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                         Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                         if (orientationUtils != null) {
                             orientationUtils.backToProtVideo();
                         }
                     }
                 }).setLockClickListener(new LockClickListener() {
             @Override
             public void onClick(View view, boolean lock) {
                 if (orientationUtils != null) {
                     //配合下方的onConfigurationChanged
                     orientationUtils.setEnable(!lock);
                 }
             }
         }).build(detail_player);

         detail_player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //直接横屏
                 orientationUtils.resolveByClick();

                 //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                 detail_player.startWindowFullscreen(CourseDetailsActivity.this, true, true);
             }
         });
//     }catch (Exception e){}
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                //关闭
                finish();
                break;
            case R.id.iv_collection:
                //收藏
//                if (isCollection==true){
//                    isCollection=false;
//                    iv_collection.setImageResource(R.mipmap.no_collection_img);
//
//                }else {
//                    isCollection=true;
//                    iv_collection.setImageResource(R.mipmap.collection_img);
//                }
                sendCollecton();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        //停止时播放视频
        detail_player.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        detail_player.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detail_player.getCurrentPlayer().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detail_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }



}
