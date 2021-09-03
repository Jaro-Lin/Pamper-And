package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetShopCommentDataUtil;
import com.nyw.pets.activity.shop.util.GetShopCommentInfoUtil;
import com.nyw.pets.activity.util.EvauationListDataUtil;
import com.nyw.pets.activity.util.GetShopCommentUtil;
import com.nyw.pets.adapter.EvaluationListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 商品评价列表
 */
public class ShopEvaluationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private EvaluationListAdapter evaluationListAdapter;
    private List<EvauationListDataUtil> evauationList=new ArrayList<>();
    //九宫格显示图片
    private NineGridViewGroup nineGrid;
    private List<GetShopCommentDataUtil> shopComentList=new ArrayList<>();
    //分页
    private   int page=1,limit=15;
    private String shopId;
    private MyApplication application ;
    //刷新
    private MeiTuanRefreshView refreshview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_evaluation);
        application= (MyApplication) getApplication();
        initView();
    }


    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        refreshview=findViewById(R.id.refreshview);

        rcv_data=findViewById(R.id.rcv_data);

        try{
           Bundle bundle= getIntent().getExtras();
            shopId= bundle.getString("shopId",null);

        }catch (Exception e){}

        rcv_data.setLayoutManager(new LinearLayoutManager(this));
//        for (int i=0;i<5;i++){
//            EvauationListDataUtil evauationListDataUtil=new EvauationListDataUtil();
//            evauationListDataUtil.setId(i+"");
//            evauationListDataUtil.setUserImg("");
//            evauationListDataUtil.setName("李五"+i);
//            evauationListDataUtil.setTime("1586189565");
//            evauationListDataUtil.setMsg("怎么说呢，个人觉得物有所值吧，很棒，关键客服很有耐心，等用了之后再来后续评价");
//            evauationListDataUtil.setPraiseNumber(63+2+"");
//            evauationListDataUtil.setEvaluationNumber(36+i+"");
//            evauationList.add(evauationListDataUtil);
//            ArrayList<MyMedia> mediaList10 = new ArrayList<>();
//            for (int j = 0;j<5;j++){
//                MyMedia myMedia=new MyMedia();
//                myMedia.setImageUrl("http://ml.nnddkj.com/img/img1.png");
////                myMedia.setVideoUrl("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
//                mediaList10.add(myMedia);
//            }
//            evauationListDataUtil.setMediaList(mediaList10);
//            evauationList.add(evauationListDataUtil);
//
//        }
        evaluationListAdapter=new EvaluationListAdapter(ShopEvaluationActivity.this,evauationList);
        rcv_data.setAdapter(evaluationListAdapter);

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                evauationList.clear();
                getCommentInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getCommentInfo();
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

    @Override
    public void onResume() {
        super.onResume();
        evauationList.clear();
        getCommentInfo();
    }

    /**
     * 获取 商品评论列表
     */
    private void getCommentInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        //这里是使用RequestBody 请求，把json商品数据传给后台

        GetShopCommentInfoUtil getShopCommentInfoUtil=new GetShopCommentInfoUtil();
        getShopCommentInfoUtil.setToken(token);
        getShopCommentInfoUtil.setModule("shop");
        getShopCommentInfoUtil.setModule_id(shopId);
        getShopCommentInfoUtil.setLimit(limit);
        getShopCommentInfoUtil.setPage(page);
        String sendData= new Gson().toJson(getShopCommentInfoUtil);

        Log.i("sdfjsifirewrfwer",sendData);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendData);
        String url= Api.GET_COMMENT_COMMENT_LIST;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnbghhgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);

                        Gson gson=new Gson() ;
                        GetShopCommentUtil getShopCommentUtil= gson.fromJson(data, GetShopCommentUtil.class);

                        if (getShopCommentUtil.getData()!=null&&getShopCommentUtil.getData().getDatalist().size()>0) {



                            //产品图片
                            for (int i=0;i<getShopCommentUtil.getData().getDatalist().size();i++){
                                ArrayList<MyMedia> mediaList10 = new ArrayList<>();

                                EvauationListDataUtil evauationListDataUtil=new EvauationListDataUtil();
                                evauationListDataUtil.setId(getShopCommentUtil.getData().getDatalist().get(i).getId()+"");
                                evauationListDataUtil.setShopId(shopId);
                                evauationListDataUtil.setThumbs(getShopCommentUtil.getData().getDatalist().get(i).getTotal_like()+"");
                                evauationListDataUtil.setIf_good(getShopCommentUtil.getData().getDatalist().get(i).isIs_good());
                                evauationListDataUtil.setUserImg(getShopCommentUtil.getData().getDatalist().get(i).getUser().getAvatar());
                                evauationListDataUtil.setName(getShopCommentUtil.getData().getDatalist().get(i).getUser().getNickname());
                                evauationListDataUtil.setTime(getShopCommentUtil.getData().getDatalist().get(i).getCreate_time()+"");
                                evauationListDataUtil.setMsg(getShopCommentUtil.getData().getDatalist().get(i).getContent());
                                evauationListDataUtil.setPraiseNumber(getShopCommentUtil.getData().getDatalist().get(i).getTotal_like()+"");
                                evauationListDataUtil.setEvaluationNumber(getShopCommentUtil.getData().getDatalist().get(i).getParent_id()+"");


                                for (int j=0;j<getShopCommentUtil.getData().getDatalist().get(i).getImgs().size();j++){
                                    //评价图片,图片数量
                                    MyMedia myMedia = new MyMedia();
                                    myMedia.setImageUrl(application.getImgFilePathUrl()+getShopCommentUtil.getData().getDatalist().get(i).getImgs().get(j));
                                    mediaList10.add(myMedia);

                                }
                                evauationListDataUtil.setMediaList(mediaList10);
                                evauationList.add(evauationListDataUtil);

                            }
                            evaluationListAdapter.notifyDataSetChanged();


                        }else {
                            //没有评论
                        }




                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopEvaluationActivity.this,"网络错误");
                    }
                });
    }

}
