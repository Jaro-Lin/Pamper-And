package com.nyw.pets.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.CardBean;
import com.nyw.pets.activity.util.GetUserImgUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.FileUtil;
import com.nyw.pets.util.ImageUtils;
import com.nyw.pets.util.StringUtils;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ActionSheetDialog;
import com.nyw.pets.view.AddPetsSuccessDialog;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.DelPetsInfoDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 添加宠物，输入宠物相关属性信息
 */
public class PetsDataSetupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_return,btn_add_ok,btn_change_ok;
    private LinearLayout llt_sterilization,llt_create_data,llt_weight,llt_sex,llt_healthy;
    private ClearEditText ct_name;
    private ImageView rv_my_img,iv_del;
    //更新头像
    public static final int ACTION_TYPE_ALBUM = 0;//相册
    public static final int ACTION_TYPE_PHOTO = 1;//拍照
    private Uri imageUri;
    private Uri cropUri;
    private File protraitFile;
    private String protraitPath;
    private String theLarge;
    private final static int CROP = 200;
    //保存的图片地址
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/meltingstate/Portrait/";
    //获取裁剪后的头像名字
    private String cropFileName;
    private String imgUpdatePhotoData=null;
    //选择出生年月日
    private TimePickerView pvTime;
    private TextView tv_create_data,tv_title;
    private TextView tv_sex;
    //选择性别
    private OptionsPickerView  pvCustomOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();
    //选择绝育情况
    private OptionsPickerView pvsterilization;
    private ArrayList<CardBean> cardSterilizationItem = new ArrayList<>();
    private TextView tv_sterilization;
    //选择体重
    private OptionsPickerView pvWeightPickerView;
    private ArrayList<CardBean> cardWeightItem = new ArrayList<>();
    private TextView tv_weight,tv_breed;
    //添加成功弹窗
    private  AddPetsSuccessDialog addPetsSuccessDialog ;
    //删除宠物提示
    private DelPetsInfoDialog delPetsInfoDialog;
    private  String getPetsId,getPetsName;
    private        String avatar,type_id,varieties_id,nickname,sex,brithday,weight,sterilization,getPetsTypeId,age,pid;
    private boolean isEdit=false;//是否为编辑，默认为否
    //健康,生病  二选一
    private String healthy="健康";
    private ClearEditText ct_healthy;
    //显示宠物健康情况
    private OptionsPickerView  pvHealthyOptions;
    private ArrayList<CardBean> cardHealthyItem = new ArrayList<>();
    private TextView tv_healthy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_data_setup);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_return=findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        btn_add_ok=findViewById(R.id.btn_add_ok);
        btn_add_ok.setOnClickListener(this);
        llt_sterilization=findViewById(R.id.llt_sterilization);
        llt_sterilization.setOnClickListener(this);
        llt_create_data=findViewById(R.id.llt_create_data);
        llt_create_data.setOnClickListener(this);
        llt_weight=findViewById(R.id.llt_weight);
        llt_weight.setOnClickListener(this);
        llt_sex=findViewById(R.id.llt_sex);
        llt_sex.setOnClickListener(this);
        tv_sex=findViewById(R.id.tv_sex);
        ct_name=findViewById(R.id.ct_name);
        rv_my_img=findViewById(R.id.rv_my_img);
        rv_my_img.setOnClickListener(this);
        tv_create_data=findViewById(R.id.tv_create_data);
        tv_sterilization=findViewById(R.id.tv_sterilization);
        tv_weight=findViewById(R.id.tv_weight);
        tv_title=findViewById(R.id.tv_title);
        iv_del=findViewById(R.id.iv_del);
        iv_del.setOnClickListener(this);
        btn_change_ok=findViewById(R.id.btn_change_ok);
        btn_change_ok.setOnClickListener(this);
        tv_breed=findViewById(R.id.tv_breed);
        ct_healthy=findViewById(R.id.ct_healthy);
        llt_healthy=findViewById(R.id.llt_healthy);
        llt_healthy.setOnClickListener(this);
        tv_healthy=findViewById(R.id.tv_healthy);
        //选择出生日期
        initTimePicker();
        //选择性别
        initCustomOptionPicker();
        //绝育情况
        initSterilizationPicker();
        //体重
        initWeightPicker();
        //选择健康情况
        initHealthyCustomOptionPicker();
         addPetsSuccessDialog=new AddPetsSuccessDialog(PetsDataSetupActivity.this);
        addPetsSuccessDialog.setDialogCallback(new AddPetsSuccessDialog.Dialogcallback() {
            @Override
            public void ok(String string) {
                finish();
            }
        });

         Bundle  bundle=  getIntent().getExtras();
        delPetsInfoDialog=new DelPetsInfoDialog(PetsDataSetupActivity.this);
        delPetsInfoDialog.setDialogCallback(new DelPetsInfoDialog.Dialogcallback() {
            @Override
            public void delPets(String string) {
                //执行删除宠物 信息
                delData(Api.GET_PETS_PETS_DEL);

            }
        });
         try{
             String type=bundle.getString("type",null);
             String  breed=bundle.getString("breed",null);
             tv_breed.setText("品种： "+breed);

             if (type.equals("change")){
                 isEdit=true;
                 tv_title.setText("编辑信息");
                 iv_del.setVisibility(View.VISIBLE);
                 btn_change_ok.setVisibility(View.VISIBLE);

                 btn_return.setVisibility(View.GONE);
                 btn_add_ok.setVisibility(View.GONE);

                  sex=bundle.getString("sex",null);
                  weight=bundle.getString("weight",null);
                  sterilization=bundle.getString("sterilization",null);
                  brithday=bundle.getString("birth",null);
                  String img=bundle.getString("img",null);
                  nickname=bundle.getString("name",null);
                 avatar=bundle.getString("imgName",null);
                 type_id=bundle.getString("type_id",null);
                 varieties_id=bundle.getString("varieties_id",null);
                 pid=bundle.getString("pid",null);
                 tv_healthy.setText(bundle.getString("healthy",null));

                 ct_name.setText(nickname);
                 if (sex.equals("1")){
                     tv_sex.setText("男");
                 }else {
                     tv_sex.setText("女");
                 }

                 tv_weight.setText(weight);

                 if (!TextUtils.isEmpty(brithday)){


                     String year=brithday.substring(0,4);
                     String months=brithday.substring(4,6);
                     String shit=brithday.substring(6,8);
                     tv_create_data.setText(year+"-"+months+"-"+shit);
                 }

                 if (sterilization.equals("1")){
                     tv_sterilization.setText("已绝育");
                 }else {
                     tv_sterilization.setText("未绝育");
                 }

                 Glide.with(this).load(img).error(R.mipmap.ic_logo).placeholder(R.mipmap.ic_logo).into(rv_my_img);





             }

         }catch (Exception e){}

        try{

             getPetsName=bundle.getString("getPetsName",null);
             getPetsId=bundle.getString("getPetsId",null);
             getPetsTypeId=bundle.getString("getPetsTypeId",null);

        }catch (Exception e){}

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_add_ok:
                //添加宠物
                addPetsData(Api.ADD_PETS);
                break;
            case R.id.llt_sterilization:
                //选择绝育情况
                pvsterilization.show();
                break;
            case R.id.llt_create_data:
                //选择出生月日
                pvTime.show();
                break;
            case R.id.llt_weight:
                //选择体重
                pvWeightPickerView.show();
                break;
            case R.id.llt_sex:
                //选择性别
                pvCustomOptions.show();
                break;
            case R.id.rv_my_img:
                //上传头像
                showMyDialog();
                break;
            case R.id.iv_del:
                //删除
                delPetsInfoDialog.show();


                break;
            case R.id.btn_change_ok:
                //确定修改宠物 信息
                //编辑修改
                addPetsData(Api.GET_PETS_UPDATA_PETS);
                break;
            case R.id.llt_healthy:
                //宠物健康情况选择
                pvHealthyOptions.show();
                break;
        }
    }

    private void delData(String url) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("pid",pid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                       Log.i("sfdljsifsfsdgfisdg",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                           int code= jsonObject.getInt("code");
                            String  message= jsonObject.getString("message");
                            ToastManager.showShortToast(PetsDataSetupActivity.this,message);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(PetsDataSetupActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 添加宠物
     */
    private void addPetsData(String url) {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String typeTitle;

        if (isEdit==false) {
            avatar = imgUpdatePhotoData;
            type_id=getPetsTypeId;
            varieties_id=getPetsId;
            typeTitle="type_id";
        }else {
            typeTitle= "pid";
            type_id =pid;
            if (!TextUtils.isEmpty(imgUpdatePhotoData)){
                //已经选择更换头像
                avatar = imgUpdatePhotoData;
            }
        }

        nickname=ct_name.getText().toString();
        if (TextUtils.isEmpty(avatar)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请上传宠物头像");
            return;
        }else if (TextUtils.isEmpty(type_id)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请选择宠物类型");
            return;
        }else if (TextUtils.isEmpty(varieties_id)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请选择宠物品种");
            return;
        }else if (TextUtils.isEmpty(nickname)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请输入宠物名称");
            return;
        }else if (TextUtils.isEmpty(sex)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请选择宠物性别");
            return;
        }else if (TextUtils.isEmpty(brithday)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请选择宠物出生日期");
            return;
        }else if (TextUtils.isEmpty(weight)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请输入宠物体重");
            return;
        }else if (TextUtils.isEmpty(sterilization)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"请选择是否绝育");
            return;
        }
        healthy=tv_healthy.getText().toString();
        if (TextUtils.isEmpty(healthy)){
            healthy="健康";
        }

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("avatar",avatar)
                .params(typeTitle,type_id)
                .params("varieties_id",varieties_id)
                .params("nickname",nickname)
                .params("sex",sex)
                .params("brithday",brithday)
                .params("weight",weight)
                .params("healthy",healthy)
                .params("sterilization",sterilization)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sfsjfisjfsifsivnvv",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");
                            ToastManager.showShortToast(PetsDataSetupActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                addPetsSuccessDialog.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(PetsDataSetupActivity.this,"网络错误");
                    }
                });

    }

    /**
     * 更新头像
     */
    private void updateImage() {
        String path=protraitFile.toString();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        OkGo.<String>post(Api.UPDATE_USER_IMG).tag(this)
                .params("token",token)
                .params("file",new File(path))
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
                            Gson gson=new Gson();
                            GetUserImgUtil getUserImgUtil=gson.fromJson(data,GetUserImgUtil.class);
                            imgUpdatePhotoData=getUserImgUtil.getData().getImg();
                            ToastManager.showShortToast(PetsDataSetupActivity.this,message);
                            if (code==AppConfig.SUCCESS) {
                                rv_my_img.setImageURI(cropUri);//显示头像
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 底部弹出dialog选择相片
     */
    private void showMyDialog() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goToSelectPicture(ACTION_TYPE_PHOTO);//拍照
                            }
                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goToSelectPicture(ACTION_TYPE_ALBUM);//相册
                            }
                        }).show();
    }

    /**
     * 判断用户选择相册还是拍照
     * @param position
     */
    private void goToSelectPicture(int position) {
        switch (position) {
            case ACTION_TYPE_ALBUM:
                startImagePick();
                break;
            case ACTION_TYPE_PHOTO:
                startTakePhoto();
                break;
            default:
                break;
        }
    }
    /**
     * 打开系统图片管理器选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * 自动获取相机权限,安卓6.0需要添加的动态权限
     */
    private void autoObtainCameraPermission(File fileOut) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastManager.showShortToast(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_TYPE_PHOTO);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                //FileUriExposedException这个异常只会在Android 7.0 + 出现，
                // 当app使用file:// url 共享给其他app时， 会抛出这个异常。
                //因为在android 6.0 + 权限需要 在运行时候检查， 其他app 可能没有读写文件的权限，
                // 所以google在7.0的时候加上了这个限制。官方推荐使用 FileProvider 解决这个问题。
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(PetsDataSetupActivity.this, getPackageName(), fileOut);//通过FileProvider创建一个content类型的Uri
                }else {
                    imageUri = Uri.fromFile(fileOut);
                }
                SaveAPPData.SavePhoto(PetsDataSetupActivity.this, Uri.fromFile(fileOut).toString());
                Log.i("sdfsfsffdsf",imageUri.toString());
                //启动拍照
                Intent  intent = new Intent();
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION );
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,
                        ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
            } else {
                ToastManager.showShortToast(this, "设备没有SD卡！");
            }
        }
    }
    /**
     * 这是打开系统拍照
     */
    private void startTakePhoto() {
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/meltingstate/Camera/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            ToastManager.showShortToast(PetsDataSetupActivity.this, "无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String  fileName = "osc_" + timeStamp + ".jpg";// 照片命名
        File fileOut = new File(savePath, fileName);

        theLarge = savePath + fileName;// 该照片的绝对路径

        autoObtainCameraPermission(fileOut);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACTION_TYPE_PHOTO: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
//                        imageUri = Uri.fromFile(fileUri);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            imageUri = FileProvider.getUriForFile(PetsDataSetupActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
//                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastManager.showShortToast(this, "设备没有SD卡！");
                    }
                } else {

                    ToastManager.showShortToast(this, "请允许打开相机！！");
                }
                break;


            }
            case ACTION_TYPE_ALBUM://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastManager.showShortToast(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastManager.showShortToast(PetsDataSetupActivity.this, "无法保存上传的头像，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(PetsDataSetupActivity.this, uri);
        }
        String ext = FileUtil.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        cropFileName = "osc_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }

    /**
     * 裁剪图片
     *
     * @param data 原始图片
     *  @param  type ACTION_TYPE_ALBUM是相册， ACTION_TYPE_PHOTO是拍照
     */
    public void startActionCrop(Uri data,int type) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri  imagePath = null;
        imagePath=data;
        //判断安卓系统是否在安卓系统是7.0或7.0以上，如果是需要使用FileProvider获取到的路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //如果是拍照，就必须设置setDataAndType方法imagePath值为FileProvider获取到的路径
            //如果是相册，不需要。
            if (type==ACTION_TYPE_PHOTO) {
                imagePath = FileProvider.getUriForFile(PetsDataSetupActivity.this, getPackageName(), new File(data.getPath()));
            }else {
                imagePath=data;
            }
        }
        intent.setDataAndType(imagePath, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// //设置裁剪框高宽,输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        //返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        Uri newUri = null;
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                SharedPreferences sp = getSharedPreferences(SaveAPPData.PHOTO, MODE_PRIVATE);
                newUri=Uri.parse(sp.getString("path", null));

                Log.i("sdfsdfsfsdffsf",newUri.getPath());
                Log.i("sdfsdfsfsdffsf",newUri.toString());
                startActionCrop(newUri,ACTION_TYPE_PHOTO);// 拍照后裁剪

                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (hasSdcard()) {
                    startActionCrop(data.getData(),ACTION_TYPE_ALBUM);// 选图后裁剪
                    Log.i("sdfsdfsfsdffsf",data.getData().getPath());
                    Log.i("sdfsdfsfsdffsf",data.getData().toString());
                }else {
                    ToastManager.showShortToast(this, "设备没有SD卡！");
                }

                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                //上传头像
                updateImage();
//                rv_my_img.setImageURI(cropUri);//显示头像
                break;
        }
    }



    /**
     * 选择出生日期
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 9, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(PetsDataSetupActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tv_create_data.setText(getTime(date));
                String time=getTime(date);
                brithday=time.replace("-","");
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                                /*pvTime.dismiss();*/
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*pvTime.dismiss();*/
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }
    /**
     * 获取时间
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    /**
     * 性别
     */
    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                tv_sex.setText(tx);

                if (tx.equals("男")){
                    tx="1";
                }else {
                    tx="2";
                }
                sex=tx;
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_sex, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                        getCardData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(cardItem);//添加数据


    }
    private void getCardData() {

        cardItem.add(new CardBean(0,"男"));
        cardItem.add(new CardBean(1,"女"));

        for (int i = 0; i < 2; i++) {
            cardItem.get(i).setCardNo(cardItem.get(i).getCardNo());
        }

    }

    /**
     * 选择宠物健康情况
     */
    private void initHealthyCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvHealthyOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardHealthyItem.get(options1).getPickerViewText();
                tv_healthy.setText(tx);

            }
        })
                .setLayoutRes(R.layout.pickerview_custom_sex, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvHealthyOptions.returnData();
                                pvHealthyOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvHealthyOptions.dismiss();
                            }
                        });
                        getHealthyCardData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvHealthyOptions.setPicker(cardHealthyItem);//添加数据


    }
    private void getHealthyCardData() {

        cardHealthyItem.add(new CardBean(0,"健康"));
        cardHealthyItem.add(new CardBean(1,"生病"));

        for (int i = 0; i < 2; i++) {
            cardHealthyItem.get(i).setCardNo(cardHealthyItem.get(i).getCardNo());
        }

    }

    /**
     * 绝育情况
     */
    private void initSterilizationPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvsterilization = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardSterilizationItem.get(options1).getPickerViewText();
                tv_sterilization.setText(tx);
                if (tx.equals("已绝育")){
                    sterilization="1";
                }else {
                    sterilization="0";
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_sex, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvsterilization.returnData();
                                pvsterilization.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvsterilization.dismiss();
                            }
                        });
                        getSterilizationData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvsterilization.setPicker(cardSterilizationItem);//添加数据


    }
    private void getSterilizationData() {

        cardSterilizationItem.add(new CardBean(0,"已绝育"));
        cardSterilizationItem.add(new CardBean(1,"未绝育"));

        for (int i = 0; i < 2; i++) {
            cardSterilizationItem.get(i).setCardNo(cardSterilizationItem.get(i).getCardNo());
        }

    }


    /**
     * 体重
     */
    private void initWeightPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvWeightPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardWeightItem.get(options1).getPickerViewText();
                tv_weight.setText(tx);
                weight=tx;
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_sex, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvWeightPickerView.returnData();
                                pvWeightPickerView.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvWeightPickerView.dismiss();
                            }
                        });
                        getWeightData();


                    }
                })
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvWeightPickerView.setPicker(cardWeightItem);//添加数据


    }
    private void getWeightData() {

        for (int i=0;i<1000;i++){
            cardWeightItem.add(new CardBean(0,""+(i+1)));
        }

        for (int i = 0; i < 2; i++) {
            cardWeightItem.get(i).setCardNo(cardWeightItem.get(i).getCardNo());
        }

    }


}
