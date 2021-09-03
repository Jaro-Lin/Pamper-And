package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;
import com.nyw.pets.activity.shop.util.ShopEvaluationData;
import com.nyw.pets.activity.shop.util.ShopEvaluationInfoData;
import com.nyw.pets.activity.util.GetUserImgUtil;
import com.nyw.pets.activity.util.ImageUtils;
import com.nyw.pets.activity.util.SaveImgUitl;
import com.nyw.pets.adapter.SendPhotoAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.OnChangeImgNameListener;
import com.nyw.pets.util.MyGlideEngine;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.util.UpdataPhotoUtil;
import com.nyw.pets.view.ClearEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 用户发布商品评价
 */
public class SendShopEvaluationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide,iv_addImg;
    private Button btn_ok;

    //图片上传
    private List<Uri> mSelected;
    private final static int REQUEST_CODE_CHOOSE=786;
    private  String imgPath;
    //显示图片
    private RecyclerView rcv_img;
    private SendPhotoAdapter sendPhotoAdapter;
    //多个图片文件上传
    public static List<File> fileList = new ArrayList<>();
    //获取banner集合，点击banner显示图片
    public static List<Uri> imageBanner  = new ArrayList<>();
    private List<UpdataPhotoUtil> listImg = new ArrayList<>();
    //保存图片路径
    private List<SaveImgUitl> saveImgList=new ArrayList<>();

    private String token;
    private List<ShopEvaluationInfoData> shopEvaluationList=new ArrayList<>();
    private List<String> imgList=new ArrayList<>();
    private String orderId;
    private ClearEditText ct_msg;
    private TextView tv_title,tv_spe_id;
    private ImageView iv_shopImg;
    private LinearLayout llt_shop_info_item;
    private List<MyShopOrderInfoUtil> shopList;
    private int clickNumberItem;
    private String imgUpdatePhotoData=null;
    private String uid;
    private MyApplication myApplication;
    //评分
    private int shopScore=1;
    private LinearLayout llt_difference_evaluation,llt_middle_evaluation,llt_good;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_shop_evaluation);
        myApplication= (MyApplication) getApplication();
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        iv_addImg=findViewById(R.id.iv_addImg);
        iv_addImg.setOnClickListener(this);
        ct_msg=findViewById(R.id.ct_msg);
        tv_title=findViewById(R.id.tv_title);
        tv_spe_id=findViewById(R.id.tv_spe_id);
        iv_shopImg=findViewById(R.id.iv_shopImg);
        llt_shop_info_item=findViewById(R.id.llt_shop_info_item);
        llt_shop_info_item.setOnClickListener(this);
        llt_difference_evaluation=findViewById(R.id.llt_difference_evaluation);
        llt_difference_evaluation.setOnClickListener(this);

        llt_middle_evaluation=findViewById(R.id.llt_middle_evaluation);
        llt_middle_evaluation.setOnClickListener(this);

        llt_good=findViewById(R.id.llt_good);
        llt_good.setOnClickListener(this);

        rcv_img=findViewById(R.id.rcv_img);

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        token= getUser.getString(SaveAPPData.TOKEN,null);
        uid= getUser.getString(SaveAPPData.USER_ID,null);
        Log.i("sdjfsifsjfsf",token);


        Bundle bundle=getIntent().getExtras();
        try {
            orderId = bundle.getString("orderId", null);
            clickNumberItem=Integer.parseInt(bundle.getString("clickNumberItem", null));
            shopList= (List<MyShopOrderInfoUtil>) getIntent().getSerializableExtra("shopList");

            Glide.with(this).load(myApplication.getImgFilePathUrl()+shopList.get(clickNumberItem).getShopImg())
                    .placeholder(R.mipmap.http_error)
                    .error(R.mipmap.http_error).into(iv_shopImg);

            tv_spe_id.setText("规格："+shopList.get(clickNumberItem).getSpecifications());
            tv_title.setText(shopList.get(clickNumberItem).getTitle());

        }catch (Exception e){}

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_img.setLayoutManager(gridLayoutManager);
        sendPhotoAdapter = new SendPhotoAdapter(SendShopEvaluationActivity.this, listImg, imageBanner);
        rcv_img.setAdapter(sendPhotoAdapter);
        fileList.clear();
        sendPhotoAdapter.setOnChangeImgNameListener(new OnChangeImgNameListener() {
            @Override
            public void changePhoto(int postion, String data) {
                //删除图片
//                if (edit.equals(AppConfig.EDIT_INFO)){
//                    if (listImg.size()>0) {
//                        //编辑的获取服务器的图片
//                        //删除APP显示
//                        listImg.remove(postion);
//                        //删除服务器上的图片在下面写请求接口
//
//
//                    }
//                }else {
//                    //上传获取服务器的图片
//                    if (saveImgList.size() > 0) {
//                        saveImgList.remove(postion);
//                    }
//                }
                //上传获取服务器的图片
//                Toast.makeText(SendProjectActivity.this,postion+" "+saveImgList.size(),Toast.LENGTH_SHORT).show();
                if (saveImgList.size() > 0) {
                    saveImgList.remove(postion);
                    //删除服务器图片
                    String[] imgArray=data.split("http://qiniupu.rongtaijt.com/");
                    String imgName=imgArray[1];
//                    delImg(imgName);
                }
                if (listImg.size()>0){
                    listImg.remove(postion);
                }
                if (imageBanner.size()>0){
                    imageBanner.remove(postion);
                }

                sendPhotoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                //处理调用系统图库
                //这是uri的要转格式
                mSelected = Matisse.obtainResult(data);
                Log.d("Matisse", "mSelected: " + mSelected);
                imgPath= Matisse.obtainPathResult(data).get(0);
                //更新头像信息
//                UpdataPhotoUtil updataPhotoUtil = new UpdataPhotoUtil();
//                updataPhotoUtil.setImg(imgPath);
//                listImg.add(updataPhotoUtil);
//                sendPhotoAdapter.notifyDataSetChanged();
                //上传图片
                updateImage(imgPath);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_ok:
                //提交 评价
                String msg=ct_msg.getText().toString();

                if (TextUtils.isEmpty(msg)){
                    ToastManager.showShortToast(SendShopEvaluationActivity.this,"请输入评论内容");
                    return;
                }

                sendShopComment(msg);
                break;
            case R.id.iv_addImg:
                //添加 图片
                Matisse.from(this)
                        .choose(MimeType.ofAll())//照片视频全部显示MimeType.allOf()
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(9)//最大选择数量为9
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._240px))//图片显示表格的大小
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                        .thumbnailScale(0.85f)//缩放比例
                        .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                        .imageEngine(new MyGlideEngine())//图片加载方式，Glide4需要自定义实现
                        .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                        //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .captureStrategy(new CaptureStrategy(true,getPackageName()))//存储到哪里
                        .forResult(REQUEST_CODE_CHOOSE);//请求码
                break;
            case R.id.llt_shop_info_item:
                //进入 商品详情
                Intent intent=new Intent();
                intent.setClass(SendShopEvaluationActivity.this, ShopDetailsActivity.class);
                intent.putExtra("id",shopList.get(clickNumberItem).getId());
                startActivity(intent);
                break;
            case R.id.llt_middle_evaluation:
                //选择中评
                shopScore=2;
                ToastManager.showShortToast(SendShopEvaluationActivity.this,"选择中评");

                break;
            case R.id.llt_good:
                //选择好评
                shopScore=1;
                ToastManager.showShortToast(SendShopEvaluationActivity.this,"选择好评");
                break;
            case R.id.llt_difference_evaluation:
                //选择差评
                shopScore=3;
                ToastManager.showShortToast(SendShopEvaluationActivity.this,"选择差评");
                break;

        }
    }

    /**
     * 商品评论
     */
    private void  sendShopComment(String msg){

        shopEvaluationList.clear();

        ShopEvaluationInfoData shopEvaluationInfoData=new ShopEvaluationInfoData();
        shopEvaluationInfoData.setContent(msg);
        shopEvaluationInfoData.setShop_id(shopList.get(clickNumberItem).getId());
        shopEvaluationInfoData.setStar(  shopScore+"");


        shopEvaluationInfoData.setImgs(imgList);
        shopEvaluationList.add(shopEvaluationInfoData);

        ShopEvaluationData shopEvaluationData=new ShopEvaluationData();
        shopEvaluationData.setOrder_id(orderId);
        shopEvaluationData.setToken(token);

        shopEvaluationData.setShop(shopEvaluationList);

        Gson gson=new Gson();
       String shopDataJson= gson.toJson(shopEvaluationData);
       Log.i("sjdfisfjsisfnxvcv",shopDataJson);
        //这里是使用RequestBody 请求，把json商品数据传给后台
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), shopDataJson);

        OkGo.<String>post(Api.GET_SHOP_ORDER_COMMENT).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfkjsdnvbggd",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj = new JSONObject(data);
                            code = (obj.getInt("code"));
                            message = (obj.getString("message") + "");
                            ToastManager.showShortToast(SendShopEvaluationActivity.this, message);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        }catch (Exception e){}
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(SendShopEvaluationActivity.this,"网络错误，评价失败");
                    }
                });
    }

    /**
     * 上传图片
     * @param path
     */
    private void updateImage(String path) {

            SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
            String token= getUser.getString(SaveAPPData.TOKEN,null);
            Log.i("sdjfsifsjfsf",token);

            OkGo.<String>post(Api.SEND_IMG_AND_VIDEO_FILE).tag(this)
                    .params("token",token)
                    .params("file",new File(path))
                    .params("type","image")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String data= response.body();
                            Log.i("dfskdfddesofkdostt",data);
                            JSONObject obj=null;
                            int code=2;
                            String message=null;
                            try {
                                obj=new JSONObject(data);
                                code=(obj.getInt("code"));
                                message=(obj.getString("message")+"");
                                ToastManager.showShortToast(SendShopEvaluationActivity.this,message);

                                Gson gson=new Gson();
                                GetUserImgUtil getUserImgUtil=gson.fromJson(data,GetUserImgUtil.class);

                                if (code== AppConfig.SUCCESS) {
                                    //上传图片成功，记录图片路径
                                    imgUpdatePhotoData= getUserImgUtil.getData().getImg();
                                    imgList.add(imgUpdatePhotoData);
                                    UpdataPhotoUtil updataPhotoUtil = new UpdataPhotoUtil();
                                    updataPhotoUtil.setImg(getUserImgUtil.getData().getImg());
                                    updataPhotoUtil.setServiceUrl(getUserImgUtil.getData().getServer());
                                    listImg.add(updataPhotoUtil);
                                    imageBanner.add(ImageUtils.getUriFromPath(getUserImgUtil.getData().getServer()+getUserImgUtil.getData().getImg()));
                                    sendPhotoAdapter.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                            ToastManager.showShortToast(SendShopEvaluationActivity.this,"网络错误");
                        }
                    });
        }



}
