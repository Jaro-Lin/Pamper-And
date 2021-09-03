package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

/**
 * 申请售后
 */
public class RequestAfterSalesActivity extends BaseActivity implements View.OnClickListener {
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

    //分类
    private Spinner sp_afterSalesType;
    private List<String> afterSalesTypeList;
    private ArrayAdapter<String> afterSalesTypeAdapter;
    //商品状态
    private Spinner sp_shopStateType;
    private List<String> shopStateTypeList;
    private ArrayAdapter<String> shopStateTypeAdapter;
    //退货原因
    private Spinner sp_no_shop_why;
    private List<String> no_shop_whyList;
    private ArrayAdapter<String> no_shop_whyAdapter;
    private ImageView iv_shopImg;
    private TextView tv_title,tv_spe,tv_order_number;
    private ClearEditText ct_price,ct_text;
    private String orderId;
    private List<MyShopOrderInfoUtil> shopList;
    private int clickNumberItem;
    private MyApplication myApplication;
    private String imgUpdatePhotoData=null;
    private List<String> imgList=new ArrayList<>();
    private String token;
    private String afterSalesType,shopStateType,no_shop_why_type,shopAfterSalesImg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_after_sales);
        myApplication= (MyApplication) getApplication();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
         token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        iv_addImg=findViewById(R.id.iv_addImg);
        iv_addImg.setOnClickListener(this);
        rcv_img=findViewById(R.id.rcv_img);
        tv_title=findViewById(R.id.tv_title);
        tv_spe=findViewById(R.id.tv_spe);
        tv_order_number=findViewById(R.id.tv_order_number);
        ct_price=findViewById(R.id.ct_price);
        ct_text=findViewById(R.id.ct_text);
        iv_shopImg=findViewById(R.id.iv_shopImg);

        Bundle bundle=getIntent().getExtras();
        try {
            orderId = bundle.getString("orderId", null);
            clickNumberItem=Integer.parseInt(bundle.getString("clickNumberItem", null));
            shopList= (List<MyShopOrderInfoUtil>) getIntent().getSerializableExtra("shopList");

            Glide.with(this).load(myApplication.getImgFilePathUrl()+shopList.get(clickNumberItem).getShopImg())
                    .placeholder(R.mipmap.http_error)
                    .error(R.mipmap.http_error).into(iv_shopImg);

            tv_spe.setText("规格："+shopList.get(clickNumberItem).getSpecifications());
            tv_title.setText(shopList.get(clickNumberItem).getTitle());
            tv_order_number.setText("订单编号："+shopList.get(clickNumberItem).getOrderNumber());

        }catch (Exception e){}



        sp_afterSalesType=findViewById(R.id.sp_afterSalesType);
        sp_afterSalesType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(RequestAfterSalesActivity.this,sp_afterSalesType.getSelectedItem().toString());
                afterSalesType=sp_afterSalesType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        afterSalesTypeList = new ArrayList<String>();
        afterSalesTypeList.add(  "申请退款退货");
        afterSalesTypeList.add(  "申请仅退款");
        afterSalesTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, afterSalesTypeList);

        //设置样式
        afterSalesTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_afterSalesType.setAdapter(afterSalesTypeAdapter);

        //商品状态
        sp_shopStateType=findViewById(R.id.sp_shopStateType);
        sp_shopStateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(RequestAfterSalesActivity.this,sp_shopStateType.getSelectedItem().toString());
//                  shopStateType=sp_shopStateType.getSelectedItem().toString();
                  if (position==0){
                      shopStateType="0";
                  }else {
                      shopStateType="1";
                  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shopStateTypeList = new ArrayList<String>();
        shopStateTypeList.add(  "未收到货");
        shopStateTypeList.add(  "已经收到货");
        shopStateTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shopStateTypeList);

        //设置样式
        shopStateTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_shopStateType.setAdapter(shopStateTypeAdapter);

        //退货原因
        sp_no_shop_why=findViewById(R.id.sp_no_shop_why);
        sp_no_shop_why.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastManager.showShortToast(RequestAfterSalesActivity.this,sp_no_shop_why.getSelectedItem().toString());
                  no_shop_why_type=sp_no_shop_why.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        no_shop_whyList = new ArrayList<String>();
        no_shop_whyList.add(  "质量问题");
        no_shop_whyList.add(  "卖家发错货");
        no_shop_whyList.add(  "不想要了");
        no_shop_whyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, no_shop_whyList);

        //设置样式
        no_shop_whyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_no_shop_why.setAdapter(no_shop_whyAdapter);




        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_img.setLayoutManager(gridLayoutManager);
        sendPhotoAdapter = new SendPhotoAdapter(RequestAfterSalesActivity.this, listImg, imageBanner);
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
                //确定 申请售后
                requestAfterSales();
                break;
            case R.id.iv_addImg:
                //添加图片
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

        }
    }

    /**
     * 申请售后
     */
    private void requestAfterSales(){
        Log.i("sfjsfdknvixfdf",token);
        Log.i("sfjsfdknvixfdf",orderId);
        Log.i("sfjsfdknvixfdf",afterSalesType);
        Log.i("sfjsfdknvixfdf",ct_price.getText().toString());
        Log.i("sfjsfdknvixfdf",shopStateType);
        Log.i("sfjsfdknvixfdf",no_shop_why_type);
        Log.i("sfjsfdknvixfdf",shopAfterSalesImg);

        OkGo.<String>post(Api.GET_SHOP_ORDER_AFTER_SALES).tag(this)
                .params("token",token)
                .params("order_id",orderId)
                .params("type",afterSalesType)
                .params("price",ct_price.getText().toString())
                .params("state",shopStateType)
                .params("reason",no_shop_why_type)
                .params("img",shopAfterSalesImg)
                .params("mark",ct_text.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data=  response.body();
                       Log.i("sdlfjdsfivfgsfjsgg",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj = new JSONObject(data);
                            code = (obj.getInt("code"));
                            message = (obj.getString("message") + "");
                            ToastManager.showShortToast(RequestAfterSalesActivity.this, message);
                            if (code==AppConfig.SUCCESS){
                                //申请售后成功
                              finish();

                            }


                        }catch (Exception e){}
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sdkfsfnsgdgthfdyu",response.getRawResponse().message());
                        ToastManager.showShortToast(RequestAfterSalesActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 上传图片
     * @param path
     */
    private void updateImage(String path) {

//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);

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
                            ToastManager.showShortToast(RequestAfterSalesActivity.this,message);

                            Gson gson=new Gson();
                            GetUserImgUtil getUserImgUtil=gson.fromJson(data,GetUserImgUtil.class);

                            if (code== AppConfig.SUCCESS) {
                                //上传图片成功，记录图片路径
                                imgUpdatePhotoData= getUserImgUtil.getData().getImg();
                                imgList.add(imgUpdatePhotoData);
                                if(TextUtils.isEmpty(shopAfterSalesImg)){
                                    shopAfterSalesImg=shopAfterSalesImg+imgUpdatePhotoData;
                                }else {
                                    shopAfterSalesImg=shopAfterSalesImg+","+imgUpdatePhotoData;
                                }

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

                        ToastManager.showShortToast(RequestAfterSalesActivity.this,"网络错误");
                    }
                });
    }

}
