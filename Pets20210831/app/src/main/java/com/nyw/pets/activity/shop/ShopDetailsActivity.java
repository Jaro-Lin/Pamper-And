package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetShareDataUtil;
import com.nyw.pets.activity.shop.util.GetShopCommentDataUtil;
import com.nyw.pets.activity.shop.util.GetShopCommentInfoUtil;
import com.nyw.pets.activity.shop.util.GetShopDataUtil;
import com.nyw.pets.activity.util.GetShopCommentUtil;
import com.nyw.pets.activity.util.ImageUtils;
import com.nyw.pets.activity.util.ShopDetailedImgUtil;
import com.nyw.pets.adapter.ShopDetailedImgAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.interfaces.GetShopBuyTypeInterface;
import com.nyw.pets.util.GlideSimpleLoader;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.GetShopBuyDialog;
import com.nyw.pets.view.RoundImageView;
import com.nyw.pets.view.ServiceUsDialog;
import com.nyw.pets.view.ShoppingCartAnimationView;
import com.nyw.pets.view.XCRecyclerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

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
 * 商品详情
 */
public class ShopDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private MZBannerView banner;
    //图片集合
    private List<String> images = new ArrayList<String>();
    private Button btn_buy,btn_addShopCart;
    private ImageView iv_service,iv_myShopCart,iv_share;

    private View view;
    private XCRecyclerView xcr_dataImg;
    private List<ShopDetailedImgUtil> shopDetailedList=new ArrayList<>();

    private ShopDetailedImgAdapter shopDetailedImgAdapter;
    private TextView tv_number,tv_price,tv_pay_money_number,tv_msg,tv_user_name,tv_evaluationMsg,tv_noComment;
    private LinearLayout llt_number,llt_evaluation,llt_comment;
    private RoundImageView rv_user_img;
    //加入购物车
    private GetShopBuyDialog getShopBuyDialog;
    //联系客服
    private ServiceUsDialog serviceUsDialog;
    private TextView good_numtv;
    private String shopId;
    private  GetShopDataUtil getShopDataUtil;
    //商品规格和数量
    private String spe_id,number="1",speName;
    //评论
    private boolean isCommentNull=true;
    //商品价格
    private String myPrice;
    //分享
    //分享控件
    private LinearLayout llt_weibo, llt_Copylink, llt_Qzone,
            llt_qq_friends, llt_WeChat_friends, llt_WeChat_circle_friends;
    private String title,target,thumbnail,describe,invitation_link;
    private Button btn_share;
    // 打开分享界面PopupWindow对象
    private PopupWindow window;
    //取消分享
    private Button btn_cancel;
    //分页
    private   int page=1,limit=15;
    //商品评价图片
    private RecyclerView rcv_img;
    //九宫格显示图片
    private NineGridViewGroup nineGrid;
    private List<GetShopCommentDataUtil> shopComentList=new ArrayList<>();
    private  MyApplication application ;
    //图片放大左右滑动显示
    private ImageWatcherHelper iwHelper;
    private List<Uri> dataList = new ArrayList<>();
    //分享数据
    private  GetShareDataUtil getShareDataUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        application = (MyApplication)getApplication();
        initView();

    }



    private void initView() {



        xcr_dataImg=findViewById(R.id.xcr_dataImg);
        good_numtv=findViewById(R.id.good_numtv);


        xcr_dataImg.setLayoutManager(new LinearLayoutManager(this));
        view = LayoutInflater.from(this).inflate(R.layout.layout_shop_detailed_header,xcr_dataImg,false);
        xcr_dataImg.addHeaderView(view);

//        for (int i=0;i<6;i++){
//            ShopDetailedImgUtil shopDetailedImgUtil=new ShopDetailedImgUtil();
//            shopDetailedImgUtil.setImg("http://ml.nnddkj.com/img2/img2.jpg");
//            shopDetailedList.add(shopDetailedImgUtil);
//        }

        shopDetailedImgAdapter=new ShopDetailedImgAdapter(ShopDetailsActivity.this,shopDetailedList);
        xcr_dataImg.setAdapter(shopDetailedImgAdapter);


        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_buy=findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(this);
        btn_addShopCart=findViewById(R.id.btn_addShopCart);
        btn_addShopCart.setOnClickListener(this);
        iv_service=findViewById(R.id.iv_service);
        iv_service.setOnClickListener(this);
        iv_myShopCart=findViewById(R.id.iv_myShopCart);
        iv_myShopCart.setOnClickListener(this);
        tv_number=view.findViewById(R.id.tv_number);
        llt_number=view.findViewById(R.id.llt_number);
        llt_number.setOnClickListener(this);
        llt_evaluation=view.findViewById(R.id.llt_evaluation);
        llt_evaluation.setOnClickListener(this);
        iv_share=findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        tv_price=view.findViewById(R.id.tv_price);
        tv_pay_money_number=view.findViewById(R.id.tv_pay_money_number);
        tv_msg=view.findViewById(R.id.tv_msg);
        rv_user_img=view.findViewById(R.id.rv_user_img);
        tv_user_name=view.findViewById(R.id.tv_user_name);
        tv_evaluationMsg=view.findViewById(R.id.tv_evaluationMsg);
        llt_comment=view.findViewById(R.id.llt_comment);
        tv_noComment=view.findViewById(R.id.tv_noComment);
        rcv_img=view.findViewById(R.id.rcv_img);

         getShopBuyDialog=new GetShopBuyDialog(ShopDetailsActivity.this);
        getShopBuyDialog.setGetShopBuyTypeInterface(new GetShopBuyTypeInterface() {
            @Override
            public void getShopBuyType(boolean openType,String number, int itemNumber,String specifications, String specificationsId) {
                //获取到用户选择的商品规格和数量
                ShoppingCartAnimationView shoppingCartAnimationView = new ShoppingCartAnimationView(ShopDetailsActivity.this);
                int position[] = new int[2];
                view.getLocationInWindow(position);
                shoppingCartAnimationView.setStartPosition(new Point(position[0], position[1]));
                ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
                rootView.addView(shoppingCartAnimationView);
                int endPosition[] = new int[2];
                good_numtv.getLocationInWindow(endPosition);
                shoppingCartAnimationView.setEndPosition(new Point(endPosition[0], endPosition[1]));
                shoppingCartAnimationView.startBeizerAnimation();
                String data=good_numtv.getText().toString();
                good_numtv.setText(((Integer.parseInt(data))+Integer.parseInt(number)) + "");

                //规格ID
                spe_id=specificationsId;
                //规格名称
                speName=specifications;

                //拿到商品价格
                tv_price.setText("￥ "+getShopDataUtil.getData().getSpe().get(itemNumber).getPrice());
                myPrice=getShopDataUtil.getData().getSpe().get(itemNumber).getPrice();

                tv_number.setText(specifications);
                if (openType==false) {
                    //加入购物车
                    addShopCartInfo();
                }else {
                    //购买
                    Intent intent=new Intent();
                    intent.setClass(ShopDetailsActivity.this,OkNextOrderActivity.class);
                    intent.putExtra("shopImg",getShopDataUtil.getData().getIcon());
                    intent.putExtra("shopTitle",getShopDataUtil.getData().getTitle());
                    intent.putExtra("speId",spe_id);
                    intent.putExtra("speName",speName);
                    intent.putExtra("price",myPrice);
                    intent.putExtra("shopId",getShopDataUtil.getData().getId());
                    intent.putExtra("pets_id",getShopDataUtil.getData().getPets_id());
                    intent.putExtra("type_id",getShopDataUtil.getData().getType_id());
                    //库存
                    intent.putExtra("stock",getShopDataUtil.getData().getStock());
                    startActivity(intent);
                }
            }
        });

        serviceUsDialog=new ServiceUsDialog(ShopDetailsActivity.this);

        //商品评价图片
        nineGrid=view.findViewById(R.id.nineGrid);




    }
    private void initBanner() {
        //初始化图片放大左右滑动查看
        iwHelper = ImageWatcherHelper.with((this) , new GlideSimpleLoader());

        banner=view.findViewById(R.id.banner);

        //设置是否显示Indicator
        banner.setIndicatorVisible(true);
        //设置BannerView 的切换时间间隔
        banner. setDelayedTime(2000);
        //设置指示器显示位置
//        banner.setIndicatorAlign(CENTER);
        //设置ViewPager（Banner）切换速度
        banner.setDuration(500);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {

            @Override
            public void onPageClick(View view, int i) {
                //点击banner打开，这里分别有h5和项目信息
                iwHelper.show(dataList, i);
            }
        });

        //设置banner数据
        images.clear();
        dataList.clear();
       String server= application.getImgFilePathUrl();
        for (int i=0;i<getShopDataUtil.getData().getBanner().size();i++){
            images.add( server+getShopDataUtil.getData().getBanner().get(i).getImage());
            dataList.add(ImageUtils.getUriFromPath(application.getImgFilePathUrl()+getShopDataUtil.getData().getBanner().get(i).getImage()));
        }
        banner.setPages(images, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.start();//开始轮播

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_buy:
                //购买
                Intent intent=new Intent();
                intent.setClass(ShopDetailsActivity.this,OkNextOrderActivity.class);
                intent.putExtra("shopImg",application.getImgFilePathUrl()+getShopDataUtil.getData().getIcon());
                intent.putExtra("shopTitle",getShopDataUtil.getData().getTitle());
                intent.putExtra("speId",spe_id);
                intent.putExtra("speName",speName);
                intent.putExtra("price",myPrice);
                intent.putExtra("shopId",getShopDataUtil.getData().getId()+"");
                intent.putExtra("pets_id",getShopDataUtil.getData().getPets_id());
                intent.putExtra("type_id",getShopDataUtil.getData().getType_id());

                //discount为 1打折 0 不打折
                intent.putExtra("discount",getShopDataUtil.getData().getOrigin_price()+"");
                //原价 discount为1该字段可用
                intent.putExtra("couponsPrice",getShopDataUtil.getData().getOrigin_price()+"");
                //库存
                intent.putExtra("stock",getShopDataUtil.getData().getStock());

                startActivity(intent);
                finish();
                break;
            case R.id.btn_addShopCart:
                //加入购买车
                getShopBuyDialog.show();
                getShopBuyDialog.setData(1,getShopDataUtil,application.getImgFilePathUrl()+getShopDataUtil.getData().getIcon(),
                        getShopDataUtil.getData().getPrice(),getShopDataUtil.getData().getSpe().get(0).getStock()+"");
                break;
            case R.id.iv_service:
                //客服
                serviceUsDialog.show();
                break;
            case R.id.iv_myShopCart:
                //我的购物车
                Intent shopCart=new Intent();
                shopCart.setClass(ShopDetailsActivity.this,ShopCartActivity.class);
                startActivity(shopCart);
                break;
            case R.id.llt_number:
                //选择数量规格，进行购买
                getShopBuyDialog.show();
                getShopBuyDialog.setData(0,getShopDataUtil,getShopDataUtil.getData().getIcon(),
                        getShopDataUtil.getData().getPrice(),getShopDataUtil.getData().getSpe().get(0).getStock()+"");
                break;
            case R.id.llt_evaluation:
                //查看全部评价
                if (isCommentNull==true){
                    ToastManager.showShortToast(ShopDetailsActivity.this,"暂时无评论");
                    return;
                }
                Intent intentEvaluation=new Intent();
                intentEvaluation.setClass(ShopDetailsActivity.this,ShopEvaluationActivity.class);
                intentEvaluation.putExtra("shopId",shopId);
                startActivity(intentEvaluation);
                break;
            case R.id.iv_share:
                //分享
                showSharePopwindow();
                break;

            case R.id.llt_WeChat_circle_friends:
//                微信朋友圈分享
                window.dismiss();
                //分享图片  title,target,thumbnail,describe,invitation_link;
                UMImage image = new UMImage(ShopDetailsActivity.this,thumbnail);
                //设置缩略图
                image.setThumb(image);
                //分享链接(分享图片和链接二选一)
                UMWeb web = new UMWeb(target);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(describe);//描述
                new ShareAction(ShopDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .withText("hello")
                        .withMedia(web)
                        .setCallback(umShareListener).share();

                break;
            case R.id.llt_WeChat_friends:
                //微信好友
                window.dismiss();
                //分享图片title,target,thumbnail,describe,invitation_link;
                UMImage imageWeChat_friends = new UMImage(ShopDetailsActivity.this,thumbnail);
                //设置缩略图
                imageWeChat_friends.setThumb(imageWeChat_friends);
                //分享链接(分享图片和链接二选一)
                UMWeb web_imageWeChat_friends = new UMWeb(target);
                web_imageWeChat_friends.setTitle(title);//标题
                web_imageWeChat_friends.setThumb(imageWeChat_friends);  //缩略图
                web_imageWeChat_friends.setDescription(describe);//描述
                new ShareAction(ShopDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("hello")
                        .withMedia(web_imageWeChat_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_qq_friends:
                //QQ好友
                window.dismiss();
                //分享图片 title,target,thumbnail,describe,invitation_link;
                UMImage imageqq_friends = new UMImage(ShopDetailsActivity.this, thumbnail);
                //设置缩略图
                imageqq_friends.setThumb(imageqq_friends);
//                //分享链接(分享图片和链接二选一)
                UMWeb web_qq_friends = new UMWeb(target);
//                UMWeb web = new UMWeb("http://nnddkj.com");
                web_qq_friends.setTitle(title);//标题
                web_qq_friends.setThumb(imageqq_friends);  //缩略图
                web_qq_friends.setDescription(describe);//描述
                new ShareAction(ShopDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("hello")
                        .withMedia(web_qq_friends)
//                        .withMedia(imageqq_friends)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Qzone:
                //QQ空间  title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageQzone = new UMImage(ShopDetailsActivity.this, thumbnail);
                //设置缩略图
                imageQzone.setThumb(imageQzone);
                //分享链接(分享图片和链接二选一)
                UMWeb web_Qzone = new UMWeb(target);
                web_Qzone.setTitle(title);//标题
                web_Qzone.setThumb(imageQzone);  //缩略图
                web_Qzone.setDescription(describe);//描述
                new ShareAction(ShopDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
//                        .withText("hello")
                        .withMedia(web_Qzone)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_weibo:
                //webbo   title,target,thumbnail,describe,invitation_link;
                window.dismiss();
                UMImage imageweibo = new UMImage(ShopDetailsActivity.this,thumbnail);
                //设置缩略图
                imageweibo.setThumb(imageweibo);
                UMWeb web_weibo = new UMWeb(target);
                web_weibo.setTitle(title);//标题
                web_weibo.setThumb(imageweibo);  //缩略图
                web_weibo.setDescription(describe);//描述
                new ShareAction(ShopDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
//                        .withText("hello")
//                        .withMedia(imageweibo)
                        .withMedia(web_weibo)
                        .setCallback(umShareListener).share();
                break;
            case R.id.llt_Copylink:
                //复制
                window.dismiss();
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
//                cmb.setText(mProductDetailsUtil.getBanner().get(0).getShoppingBannerLink());
                cmb.setText(target);
                Toast.makeText(ShopDetailsActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     * 获取商品详情
     */
    private void getShopInfo() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
    String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        shopDetailedList.clear();
    String url= Api.GET_SHOP_INFO;
        Log.i("sdfsiofskfsffg",url);
    OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("shop_id",shopId)
                .execute(new StringCallback() {
        @Override
        public void onSuccess(Response<String> response) {
            String data=response.body();
            Log.i("smdfnbqwegvbnhhsfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);

                Gson gson = new Gson();
                getShopDataUtil = gson.fromJson(data, GetShopDataUtil.class);
                if (getShopDataUtil.getCode() == AppConfig.SUCCESS) {
                    initBanner();
                    for (int i = 0; i < getShopDataUtil.getData().getDetails().size(); i++) {
                        ShopDetailedImgUtil shopDetailedImgUtil = new ShopDetailedImgUtil();
                        shopDetailedImgUtil.setImg(application.getImgFilePathUrl()+getShopDataUtil.getData().getDetails().get(i).getImg());
                        shopDetailedList.add(shopDetailedImgUtil);
                    }

                shopDetailedImgAdapter.notifyDataSetChanged();

                tv_price.setText("￥ " + getShopDataUtil.getData().getPrice());
                tv_pay_money_number.setText(getShopDataUtil.getData().getSale() + "人付款");
                tv_msg.setText(getShopDataUtil.getData().getTitle());
                tv_number.setText(getShopDataUtil.getData().getDefault_spe().getSpe());

                spe_id = getShopDataUtil.getData().getDefault_spe().getId() + "";
                speName = getShopDataUtil.getData().getDefault_spe().getSpe();
                myPrice = getShopDataUtil.getData().getDefault_spe().getPrice();
                describe=getShopDataUtil.getData().getTitle();

                thumbnail=getShopDataUtil.getData().getBanner().get(0).getImage();
         }







        }

        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            ToastManager.showShortToast(ShopDetailsActivity.this,"网络错误");
        }
    });
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

                        try {
                            Gson gson = new Gson();
                            GetShopCommentUtil getShopCommentUtil = gson.fromJson(data, GetShopCommentUtil.class);

                            if (getShopCommentUtil.getData() != null && getShopCommentUtil.getData().getDatalist().size() > 0) {
                                isCommentNull = false;
                                llt_comment.setVisibility(View.VISIBLE);
                                tv_evaluationMsg.setVisibility(View.VISIBLE);
                                tv_noComment.setVisibility(View.GONE);
                                String avatar=getShopCommentUtil.getData().getDatalist().get(0).getUser().getAvatar();
                                if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                                    Glide.with(ShopDetailsActivity.this).
                                            load( getShopCommentUtil.getData().getDatalist().get(0).getUser().getAvatar())
                                            .placeholder(R.mipmap.http_error).error(R.mipmap.http_error)
                                            .into(rv_user_img);
                                }else {
                                    Glide.with(ShopDetailsActivity.this).
                                            load(application.getImgFilePathUrl() + getShopCommentUtil.getData().getDatalist().get(0).getUser().getAvatar())
                                            .placeholder(R.mipmap.http_error).error(R.mipmap.http_error)
                                            .into(rv_user_img);
                                }
                                tv_user_name.setText(getShopCommentUtil.getData().getDatalist().get(0).getUser().getNickname());
                                tv_evaluationMsg.setText(getShopCommentUtil.getData().getDatalist().get(0).getContent());

                                //产品图片
                                for (int i = 0; i < getShopCommentUtil.getData().getDatalist().size(); i++) {
                                    ArrayList<MyMedia> mediaList10 = new ArrayList<>();

                                    for (int j = 0; j < getShopCommentUtil.getData().getDatalist().get(i).getImgs().size(); j++) {
                                        //评价图片,图片数量
                                        MyMedia myMedia = new MyMedia();
                                        myMedia.setImageUrl(application.getImgFilePathUrl()+getShopCommentUtil.getData().getDatalist().get(i).getImgs().get(j));
                                        mediaList10.add(myMedia);


                                    }
                                    GetShopCommentDataUtil getShopCommentDataUtil = new GetShopCommentDataUtil();
                                    getShopCommentDataUtil.setMediaList(mediaList10);
                                    shopComentList.add(getShopCommentDataUtil);

                                }

                                // 为满足九宫格适配器数据要求，需要构造对应的List

                                GetShopCommentDataUtil shopCommentItem = shopComentList.get(0);
                                ArrayList<MyMedia> mediaList = shopCommentItem.getMediaList();
                                // 没有数据就没有九宫格
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

                            } else {
                                isCommentNull = true;
                                llt_comment.setVisibility(View.GONE);
                                tv_evaluationMsg.setVisibility(View.GONE);
                                tv_noComment.setVisibility(View.VISIBLE);
                            }


//                        if (getShopDataUtil.getCode()== AppConfig.SUCCESS) {
//                            initBanner();
//                            for (int i=0;i<getShopDataUtil.getData().getDetails().size();i++){
//                                ShopDetailedImgUtil shopDetailedImgUtil=new ShopDetailedImgUtil();
//                                shopDetailedImgUtil.setImg( getShopDataUtil.getData().getDetails().get(i).getImg());
//                                shopDetailedImgUtil.setId( getShopDataUtil.getData().getDefault_spe().getShop_id()+"");
//                                shopDetailedList.add(shopDetailedImgUtil);
//                            }
//                        }

                            //商品评论
//                        Glide.with(this).load(getShopDataUtil.getData().get)
                        }catch (Exception e){}




                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取分享信息
     */
    private void getShareInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.GET_POST_ABOUT_SHARE;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type","shop")
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
                        ToastManager.showShortToast(ShopDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 加入购物车
     */
    private void addShopCartInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.ADD_SHOP_CART;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("shop_id",shopId)
                .params("spe_id",spe_id)
                .params("number",number)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfiaddsfdsfg",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String msg=jsonObject.getString("message");
                            ToastManager.showShortToast(ShopDetailsActivity.this,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopDetailsActivity.this,"网络错误");
                    }
                });
    }
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(final Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, String s) {
            // 数据绑定
            Glide.with(context).load(s).placeholder(R.mipmap.http_error).error(R.mipmap.http_error)
                    .into(mImageView);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if (banner!=null) {
            banner.pause();//暂停轮播
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle=getIntent().getExtras();
        try{
            shopId= bundle.getString("id",null);
            Log.i("sdfsifsifsfsvcxvx",shopId);
//            ToastManager.showShortToast(ShopDetailsActivity.this,shopId);

        }catch (Exception e){}
        getShopInfo();
        getCommentInfo();
        getShareInfo();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 分享
     */
    private void showSharePopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        window.showAtLocation(findViewById(R.id.btn_buy), Gravity.BOTTOM, 0,
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
            Toast.makeText(ShopDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShopDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
                transparentDialog(1f);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            transparentDialog(1f);
            Toast.makeText(ShopDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
