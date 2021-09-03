package com.nyw.pets.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
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
import com.nyw.pets.view.LoadDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布动态数据消息
 */
public class SendDataActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide,iv_choiceConversation,iv_adress,iv_img,iv_send_data,iv_addTopics;
    private ClearEditText ct_inputData;
    //图片上传
    private List<Uri> mSelected;
    private final static int REQUEST_CODE_CHOOSE=786;
    private  String imgPath;
    //定位
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private TextView tv_adress,tv_choiceTitle;
    //显示图片
    private RecyclerView rcv_Img;
    private SendPhotoAdapter sendPhotoAdapter;
    //多个图片文件上传
    public static List<File> fileList = new ArrayList<>();
    //获取banner集合，点击banner显示图片private    List<Uri> dataList
    public static List<Uri> imageBanner  = new ArrayList<>();
    private List<UpdataPhotoUtil> listImg = new ArrayList<>();
    //保存图片路径
    private List<SaveImgUitl> saveImgList=new ArrayList<>();
    private String photo;

    //发布动态消息
    private String content=null,theme_id="0",post_city=null,longitude=null,latitude=null,subsidiary_type=null,address=null;
    //上传动画
    private LoadDialog loadDialog;
    //城市
    private String city=null;
    //打开GPS定位功能
    private int REQUEST_CODE_OPEN_GPS = 566;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
        loadDialog=new LoadDialog(this,true,"请稍等，上传中......");
        initView();
        initAdress();
    }

    private void initAdress() {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，
        // 启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //拿到定位信息
            Log.i("sdfjsdfisjfs",aMapLocation.getCity());

           //省
           String province=aMapLocation.getProvince();
           //市
           String cityName=aMapLocation.getCity();
            city=cityName;
           ////城区信息
            String district=aMapLocation.getDistrict();
            //街道信息
            String street=aMapLocation.getStreet();
            Log.i("sdfjsdfisjfs",aMapLocation.getStreet());
            //街道门牌号信息
            String streetNum=aMapLocation.getStreetNum();

            String adress=province+cityName+district+street+streetNum;

            Log.i("sdfjsdfisjfs",adress);
            if (!TextUtils.isEmpty(adress)) {
                tv_adress.setText(adress);
            }

            longitude=aMapLocation.getLongitude()+"";
            latitude=aMapLocation.getLatitude()+"";

//            ToastManager.showShortToast(SendDataActivity.this,adress);

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_choiceConversation=findViewById(R.id.iv_choiceConversation);
        iv_choiceConversation.setOnClickListener(this);
        iv_adress=findViewById(R.id.iv_adress);
        iv_adress.setOnClickListener(this);
        iv_img=findViewById(R.id.iv_img);
        iv_img.setOnClickListener(this);
        iv_send_data=findViewById(R.id.iv_send_data);
        iv_send_data.setOnClickListener(this);
        ct_inputData=findViewById(R.id.ct_inputData);
        tv_adress=findViewById(R.id.tv_adress);
        rcv_Img=findViewById(R.id.rcv_Img);
        tv_choiceTitle=findViewById(R.id.tv_choiceTitle);
        iv_addTopics=findViewById(  R.id.iv_addTopics);
        iv_addTopics.setOnClickListener(this);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_Img.setLayoutManager(gridLayoutManager);
        sendPhotoAdapter = new SendPhotoAdapter(SendDataActivity.this, listImg, imageBanner);
        rcv_Img.setAdapter(sendPhotoAdapter);
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
                    Log.d("Matisse", "imgPath: " + imgPath);
                    if(imgPath.indexOf(".png")!=-1||imgPath.indexOf(".jpeg")!=-1||imgPath.indexOf(".jpg")!=-1||imgPath.indexOf(".bmp")!=-1){
                        //图片
                        updateImage(imgPath,"image");
                        subsidiary_type="image";
                    }else {
                        //视频
                        updateImage(imgPath,"video");
                        subsidiary_type="video";
                    }




                }

                //选择话题显示
                if (requestCode == AppConfig.CHOICE_CONVERSATION_STATE_DATA||resultCode==AppConfig.CHOICE_CONVERSATION_STATE_DATA) {
                   Bundle bundle= data.getExtras();
                    theme_id= bundle.getString("id",null);
                    String title= bundle.getString("title",null);
                    tv_choiceTitle.setText(title);
                }

              }
        if (requestCode==REQUEST_CODE_OPEN_GPS||resultCode==REQUEST_CODE_OPEN_GPS){
            if (isLocationEnabled()==true){
                ToastManager.showShortToast(this,"正在获取定位信息");
            }else {
                ToastManager.showShortToast(SendDataActivity.this,"未打开定位，无法定位");
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
            case R.id.iv_choiceConversation:
                //选择话题
                Intent intentChoice=new Intent();
                intentChoice.setClass(SendDataActivity.this,ChoiceConversationActivity.class);
                intentChoice.putExtra("type","1");
                startActivityForResult(intentChoice,AppConfig.CHOICE_CONVERSATION_STATE_DATA);
                break;
            case R.id.iv_adress:
                //地址
                openGPS();

                break;

            case  R.id.iv_img:
                //选择图片或视频
                Matisse.from(this)
                        .choose(MimeType.ofAll())//照片视频全部显示MimeType.allOf()
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(1)//最大选择数量为9
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
            case R.id.iv_send_data:
                //发布动态消息
                sendData();

                break;
            case R.id.iv_addTopics:
                //添加话题
                Intent addChoice=new Intent();
                addChoice.setClass(SendDataActivity.this,ChoiceConversationActivity.class);
                addChoice.putExtra("type","1");
                startActivityForResult(addChoice,AppConfig.CHOICE_CONVERSATION_STATE_DATA);
                break;
        }
    }
    /**
     * 打开GPS
     */
    private void openGPS(){
        if (isLocationEnabled()==false){
            Toast.makeText(SendDataActivity.this, "请你开启GPS定位", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
        }else {
            //可以定位
            ToastManager.showShortToast(this,"正在获取定位信息");
        }
    }
    /**
     * 打开定位
     */
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(SendDataActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(SendDataActivity.this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
    /**
     * 发布动态
     */
    private void sendData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        //文本内容 (单发招聘视频-可为空)
        content=ct_inputData.getText().toString();
        if (TextUtils.isEmpty(content)){
            ToastManager.showShortToast(SendDataActivity.this,"请输入内容");
            return;
        }

        //话题ID，话题ID  无附属话题上传 (必须) 无话题为0
        //theme_id;
        //城市
        post_city=city;
        //subsidiary_type  咐件，附件类型：null -  image  - video  (必须)



        //图片地址，图片地址-数组 字符串英问逗号（123.png,321.png） (必须-可为空)
        StringBuilder send_img_info = new StringBuilder();
        for (int i=0;i<listImg.size();i++){
                if (!TextUtils.isEmpty(listImg.get(i).getImg())) {
                    if (i==(listImg.size()-1)){
                        send_img_info.append(listImg.get(i).getImg());
                    }else {
                        send_img_info.append(listImg.get(i).getImg() + ",");
                    }
                }
        }
        Log.i("dfsjifnnvngrt",send_img_info.toString());

        address=send_img_info.toString();

        if (TextUtils.isEmpty(subsidiary_type)){
            subsidiary_type = "null";
        }


        OkGo.<String>post(Api.SEND_NEW_INFO_DATA).tag(this)
                .params("token",token)
                .params("content",content)
                .params("theme_id",theme_id)
                .params("post_city",post_city)
                .params("longitude",longitude)
                .params("latitude",latitude)
                .params("subsidiary_type",subsidiary_type)
                .params("address",address)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("fdjsidtidnvnvv",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            ToastManager.showShortToast(SendDataActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(SendDataActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 上传图片和视频
     */
    private void updateImage(String path,String updateType) {

        loadDialog.show();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.SEND_IMG_AND_VIDEO_FILE).tag(this)
                .params("token",token)
                .params("file",new File(path))
                .params("type",updateType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("dfskdfsofkdostt",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("message")+"");
                            ToastManager.showShortToast(SendDataActivity.this,message);
                            loadDialog.cancel();
                            Gson gson=new Gson();
                            GetUserImgUtil getUserImgUtil=gson.fromJson(data,GetUserImgUtil.class);

                            if (code== AppConfig.SUCCESS) {
                                //上传图片成功，记录图片路径

                                //显示图片
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
                        loadDialog.cancel();
                        ToastManager.showShortToast(SendDataActivity.this,"网络错误");
                    }
                });
    }

}
