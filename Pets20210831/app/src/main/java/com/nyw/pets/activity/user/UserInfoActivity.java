package com.nyw.pets.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.util.CardBean;
import com.nyw.pets.activity.util.GetJsonDataUtil;
import com.nyw.pets.activity.util.GetUserImgUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.activity.util.JsonBean;
import com.nyw.pets.activity.util.ProvinceBean;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.InitialSort;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.config.SignConfig;
import com.nyw.pets.net.util.AppNetUtil;
import com.nyw.pets.util.FileUtil;
import com.nyw.pets.util.ImageUtils;
import com.nyw.pets.util.StringUtils;
import com.nyw.pets.util.TimeStampUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ActionSheetDialog;
import com.nyw.pets.view.LoadDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ????????????????????????
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private LinearLayout llt_photo,llt_setName,llt_phone,llt_mailbox,
    llt_adress,llt_introduction,llt_setSex,llt_create;
    private ImageView rv_myphoto;
    private String uid,token;
    private   GetUserInfoUtil getUserInfoUtil;
    private TextView tv_name,tv_phone,tv_sex,tv_adress,
            tv_introduction;
    public static final int ACTION_TYPE_ALBUM = 0;//??????
    public static final int ACTION_TYPE_PHOTO = 1;//??????
    private Uri imageUri;
    private Uri cropUri;
    private File protraitFile;
    private String protraitPath;
    private String theLarge;
    private final static int CROP = 200;
    //?????????????????????
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/meltingstate/Portrait/";
    //??????????????????????????????
    private String cropFileName;
    private String imgUpdatePhotoData=null;
    private TextView tv_saveData,tv_create;
    private LoadDialog loadDiaUpdate;
    private LoadDialog loadDiaShow;

    private OptionsPickerView pvOptions, pvCustomOptions, pvNoLinkOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<CardBean> cardItem = new ArrayList<>();

    //????????????
    private List<JsonBean> optionsCityItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2CityItems = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3CityItems = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    //????????????
    private TimePickerView pvCustomTime;
    private TimePickerView pvTime;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rv_myphoto=findViewById(R.id.rv_myphoto);
        llt_setName=findViewById(R.id.llt_setName);
        llt_setName.setOnClickListener(this);
        llt_phone=findViewById(R.id.llt_phone);
        llt_phone.setOnClickListener(this);
        llt_adress=findViewById(R.id.llt_adress);
        llt_adress.setOnClickListener(this);
        llt_introduction=findViewById(R.id.llt_introduction);
        llt_introduction.setOnClickListener(this);
        llt_photo=findViewById(R.id.llt_photo);
        llt_photo.setOnClickListener(this);
        tv_saveData=findViewById(R.id.tv_saveData);
        tv_saveData.setOnClickListener(this);
        btn_exit=findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);

        tv_name=findViewById(R.id.tv_name);
        tv_phone=findViewById(R.id.tv_phone);
        tv_sex=findViewById(R.id.tv_sex);
        tv_adress=findViewById(R.id.tv_adress);
        tv_introduction=findViewById(R.id.tv_introduction);
        llt_setSex=findViewById(R.id.llt_setSex);
        llt_setSex.setOnClickListener(this);
        llt_create=findViewById(R.id.llt_create);
        llt_create.setOnClickListener(this);
        tv_create=findViewById(R.id.tv_create);





        loadDiaUpdate=new LoadDialog(UserInfoActivity.this,true,"???????????????");
        loadDiaShow=new LoadDialog(UserInfoActivity.this,true,"???????????????");
        getUserInfo();

        initTimePicker();
        initCustomOptionPicker();



    }

    /**??????????????????
     *
     */
    private void getUserInfo() {
        loadDiaShow.show();
        String url= Api.GET_USER_INFO;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        token=sharedPreferences.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(url).tag(this)
                .params("uid",uid)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfsfdfsf",data);
                        Gson gson=new Gson();
                        getUserInfoUtil=gson.fromJson(data, GetUserInfoUtil.class);
                        if (getUserInfoUtil.getCode()== AppConfig.SUCCESS) {
                            //???????????????????????????
                            setUserInfo();
                        }
                        loadDiaShow.cancel();
//                        Toast.makeText(UserInfoActivity.this, getUserInfoUtil.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDiaShow.cancel();
                        new AppNetUtil(UserInfoActivity.this).appInternetError();
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void setUserInfo() {
        String avatar=getUserInfoUtil.getData().getAvatar();
            if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                Glide.with(UserInfoActivity.this).load(getUserInfoUtil.getData().getAvatar())
                        .error(R.mipmap.user_app_default)
                        .placeholder(R.mipmap.user_app_default).into(rv_myphoto);
            }else {
                Glide.with(UserInfoActivity.this).load(getUserInfoUtil.getData().getServer()+getUserInfoUtil.getData().getAvatar())
                        .error(R.mipmap.user_app_default)
                        .placeholder(R.mipmap.user_app_default).into(rv_myphoto);
            }


        if (getUserInfoUtil.getData().getNickname() == null ||
                getUserInfoUtil.getData().getNickname().equals("")) {
            tv_name.setText("?????????");
        } else {
            tv_name.setText(getUserInfoUtil.getData().getNickname());
        }

        if (getUserInfoUtil.getData().getSex()+"" == null ||
                (getUserInfoUtil.getData().getSex()+"").equals("")) {
            tv_sex.setText("?????????");
        } else {
            int sex=getUserInfoUtil.getData().getSex();
            if (sex==1){
                tv_sex.setText("???");
            }else {
                tv_sex.setText("???");
            }

        }

        String birthday=getUserInfoUtil.getData().getBirthday();
        if (!TextUtils.isEmpty(birthday)) {
            String n = birthday.substring(0, 4);
            String y = birthday.substring(4, 6);
            String d = birthday.substring(6, 8);
            birthday = n + "-" + y + "-" + d;
        }
        if (getUserInfoUtil.getData().getBirthday()==null
                ||getUserInfoUtil.getData().getBirthday().equals("")){
            //????????????
            tv_create.setText("?????????");
        }else {
            tv_create.setText(birthday);
        }

        if (getUserInfoUtil.getData().getCity() == null ||
                getUserInfoUtil.getData().getCity().equals("")) {
            tv_adress.setText("?????????");
        } else {
            tv_adress.setText(getUserInfoUtil.getData().getCity());
        }

        if (getUserInfoUtil.getData().getSpe() == null ||
                getUserInfoUtil.getData().getSpe().equals("")) {
            tv_introduction.setText("?????????");
        } else {
            tv_introduction.setText(getUserInfoUtil.getData().getSpe());
        }



        if (getUserInfoUtil.getData().getPhone() == null ||
                getUserInfoUtil.getData().getPhone().equals("")) {
            tv_phone.setText("?????????");
        } else {
            tv_phone.setText(getUserInfoUtil.getData().getPhone());
        }


    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.llt_photo:
                //????????????
                showMyDialog();
                break;
            case R.id.llt_setName:
                //????????????
                Intent info=new Intent();
                info.setClass(UserInfoActivity.this,SetNameActivity.class);
                info.putExtra("title","????????????");
                info.putExtra("body",tv_name.getText().toString());
                startActivityForResult(info,AppConfig.SETUP_USER_INFO);
                break;
            case R.id.llt_phone :
                //??????????????????
//                String phone=getUserInfoUtil.getData().getInfo().getPhone();
//                startActivity(new Intent(this, BindPhoneActivity.class));

                break;
            case R.id.llt_adress:
                //????????????
                showPickerView();
                break;
            case R.id.llt_introduction:
                //??????
                Intent occupation=new Intent();
                occupation.setClass(UserInfoActivity.this,SetNameActivity.class);
                occupation.putExtra("title","??????");
                occupation.putExtra("body",tv_introduction.getText().toString());
                startActivityForResult(occupation,AppConfig.SETUP_USER_INFO);
                break;
            case R.id.tv_saveData:
                //??????????????????
                updateUserInfoData();
                break;
            case R.id.llt_setSex:
                //????????????
                pvCustomOptions.show();
                break;
            case R.id.llt_create:
                //??????????????????
                pvTime.show();
                break;
            case R.id.btn_exit:
                //????????????
                exitUserLonin();
                break;
        }
    }

    /**
     * ????????????
     */
    private void exitUserLonin() {
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token, MODE_PRIVATE);
        uid = sharedPreferences.getString(SaveAPPData.USER_ID, null);
        token = sharedPreferences.getString(SaveAPPData.TOKEN, null);

        OkGo.<String>post(Api.UPDATE_USER_CHANGE_EXIT_LOGIN).tag(this)
                .params("uid",uid)
                .params("token",token)
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
                            ToastManager.showShortToast(UserInfoActivity.this,message);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(UserInfoActivity.this,"????????????");
                    }
                });
    }

    /**
     * ????????????dialog????????????
     */
    private void showMyDialog() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("??????", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goToSelectPicture(ACTION_TYPE_PHOTO);//??????
                            }
                        })
                .addSheetItem("??????", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goToSelectPicture(ACTION_TYPE_ALBUM);//??????
                            }
                        }).show();
    }

    /**
     * ????????????????????????????????????
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
     * ?????????????????????????????????????????????
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "????????????"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "????????????"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }
    /**
     * ????????????????????????SDCard???????????????
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * ????????????????????????,??????6.0???????????????????????????
     */
    private void autoObtainCameraPermission(File fileOut) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastManager.showShortToast(this, "????????????????????????");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_TYPE_PHOTO);
        } else {//???????????????????????????????????????
            if (hasSdcard()) {
                //FileUriExposedException?????????????????????Android 7.0 + ?????????
                // ???app??????file:// url ???????????????app?????? ????????????????????????
                //?????????android 6.0 + ???????????? ???????????????????????? ??????app ????????????????????????????????????
                // ??????google???7.0??????????????????????????????????????????????????? FileProvider ?????????????????????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(UserInfoActivity.this, getPackageName(), fileOut);//??????FileProvider????????????content?????????Uri
                }else {
                    imageUri = Uri.fromFile(fileOut);
                }
                SaveAPPData.SavePhoto(UserInfoActivity.this, Uri.fromFile(fileOut).toString());
                Log.i("sdfsfsffdsf",imageUri.toString());
                //????????????
                Intent  intent = new Intent();
                //???????????????????????????????????????????????????Uri??????????????????
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION );
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//??????Action?????????
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,
                        ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
            } else {
                ToastManager.showShortToast(this, "????????????SD??????");
            }
        }
    }
    /**
     * ????????????????????????
     */
    private void startTakePhoto() {
        // ?????????????????????SD???
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

        // ????????????SD????????????????????????
        if (StringUtils.isEmpty(savePath)) {
            ToastManager.showShortToast(UserInfoActivity.this, "??????????????????????????????SD???????????????");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String  fileName = "osc_" + timeStamp + ".jpg";// ????????????
        File fileOut = new File(savePath, fileName);

        theLarge = savePath + fileName;// ????????????????????????

        autoObtainCameraPermission(fileOut);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACTION_TYPE_PHOTO: {//??????????????????????????????????????????
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
//                        imageUri = Uri.fromFile(fileUri);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            imageUri = FileProvider.getUriForFile(UserInfoActivity.this, "com.zz.fileprovider", fileUri);//??????FileProvider????????????content?????????Uri
//                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastManager.showShortToast(this, "????????????SD??????");
                    }
                } else {

                    ToastManager.showShortToast(this, "???????????????????????????");
                }
                break;


            }
            case ACTION_TYPE_ALBUM://????????????????????????Sdcard????????????
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastManager.showShortToast(this, "??????????????????SDCard??????");
                }
                break;
        }
    }

    // ???????????????????????????
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastManager.showShortToast(UserInfoActivity.this, "???????????????????????????????????????SD???????????????");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // ???????????????Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(UserInfoActivity.this, uri);
        }
        String ext = FileUtil.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // ????????????
        cropFileName = "osc_crop_" + timeStamp + "." + ext;
        // ???????????????????????????
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }

    /**
     * ????????????
     *
     * @param data ????????????
     *  @param  type ACTION_TYPE_ALBUM???????????? ACTION_TYPE_PHOTO?????????
     */
    public void startActionCrop(Uri data,int type) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri  imagePath = null;
        imagePath=data;
        //??????????????????????????????????????????7.0???7.0??????????????????????????????FileProvider??????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //?????????????????????????????????setDataAndType??????imagePath??????FileProvider??????????????????
            //??????????????????????????????
            if (type==ACTION_TYPE_PHOTO) {
                imagePath = FileProvider.getUriForFile(UserInfoActivity.this, getPackageName(), new File(data.getPath()));
            }else {
                imagePath=data;
            }
        }
        intent.setDataAndType(imagePath, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// ???????????????
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// //?????????????????????,??????????????????
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// ?????????
        intent.putExtra("scaleUpIfNeeded", true);// ?????????
        //????????????
        intent.putExtra("return-data", true);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.SETUP_USER_INFO && resultCode == AppConfig.SETUP_USER_INFO) {
            Bundle bundle = data.getExtras();
            String name=bundle.getString(AppConfig.NAME);
            String phone=bundle.getString(AppConfig.PHONE);
            String mailbox=bundle.getString(AppConfig.MAILBOX);
            String adress=bundle.getString(AppConfig.ADRESS);
            String occupation=bundle.getString(AppConfig.OCCUPATION);
            String skills=bundle.getString(AppConfig.SKILLS);
            String work=bundle.getString(AppConfig.WORK_EXPERIENCE);
            if (name!=null) {
                tv_name.setText(name);
                updateUserInfo("nickname",Api.UPDATE_USER_NAME,name);
            }else if (phone!=null){
                tv_phone.setText(phone);
            }else if (mailbox!=null){
                tv_sex.setText(mailbox);
                updateUserInfo("sex",Api.UPDATE_USER_SEX,mailbox);
            }else if (adress!=null){
                tv_adress.setText(adress);
                updateUserInfo("nickname",Api.UPDATE_USER_NAME,name);
            }else if (occupation!=null){
                tv_introduction.setText(occupation);
                updateUserInfo("spe",Api.UPDATE_USER_CHANGE_SPE,occupation);
            }
        }

        if (resultCode != Activity.RESULT_OK)
            return;
        Uri newUri = null;
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                SharedPreferences sp = getSharedPreferences(SaveAPPData.PHOTO, MODE_PRIVATE);
                newUri=Uri.parse(sp.getString("path", null));

                Log.i("sdfsdfsfsdffsf",newUri.getPath());
                Log.i("sdfsdfsfsdffsf",newUri.toString());
                startActionCrop(newUri,ACTION_TYPE_PHOTO);// ???????????????

                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (hasSdcard()) {
                    startActionCrop(data.getData(),ACTION_TYPE_ALBUM);// ???????????????
                    Log.i("sdfsdfsfsdffsf",data.getData().getPath());
                    Log.i("sdfsdfsfsdffsf",data.getData().toString());
                }else {
                    ToastManager.showShortToast(this, "????????????SD??????");
                }

                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                //????????????
                updateImage();
                rv_myphoto.setImageURI(cropUri);//????????????
                break;
        }
    }

    /**
     * ????????????
     */
    private void updateImage() {
        String path=protraitFile.toString();

        OkGo.<String>post(Api.UPDATE_USER_IMG).tag(this)
                .params("uid",uid)
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
                            if (code==AppConfig.SUCCESS) {
                                rv_myphoto.setImageURI(cropUri);//????????????
                                updateUserPhoto();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * ??????????????????????????????????????????
     */
    private void updateUserInfoData() {
        loadDiaUpdate.show();
        String url= ApiTest.USER_INFO_UPDATE;
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        uid=sharedPreferences.getString(SaveAPPData.USER_ID,null);
        token=sharedPreferences.getString(SaveAPPData.TOKEN,null);
        String nick=null,email=null,address=null,occupation=null,skill=null,experience=null;
        nick=tv_name.getText().toString();
        email=tv_sex.getText().toString();
        address=tv_adress.getText().toString();
        occupation=tv_introduction.getText().toString();

        Log.i("sfsdfsfdfsf",uid+"   "+token);
        String time = new TimeStampUtil().getTimeStamp();
        String[][] str = {{"app_key", AppConfig.APP_KEY},
                {"master_secret",AppConfig.MASTER_SECRET},
                {"uid",uid},
                {"token", token},
                {"nick", nick},
                {"email", email},
                {"address", address},
                {"occupation", occupation},
                {"skill", skill},
                {"experience", experience},
                {"timestamp", time}};
        String key = new InitialSort().getKey(str);
        String sign = new SignConfig().getSign(UserInfoActivity.this, key);
        OkGo.<String>post(url).tag(this)
                .params("app_key", AppConfig.APP_KEY)
                .params("master_secret",AppConfig.MASTER_SECRET)
                .params("uid",uid)
                .params("token",token)
                .params("nick",nick)
                .params("email",email)
                .params("address",address)
                .params("occupation",occupation)
                .params("skill",skill)
                .params("experience",experience)
                .params("timestamp",time)
                .params("sign",sign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sfsdfsfdfsf",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("msg")+"");
                            if (code==AppConfig.SUCCESS) {
                                rv_myphoto.setImageURI(cropUri);//????????????
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        loadDiaUpdate.cancel();
                        Toast.makeText(UserInfoActivity.this,message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDiaUpdate.cancel();
                        new AppNetUtil(UserInfoActivity.this).appInternetError();
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void updateUserPhoto() {
            loadDiaUpdate.show();
            String url = Api.UPDATE_USER_AVATAR;
            SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token, MODE_PRIVATE);
            uid = sharedPreferences.getString(SaveAPPData.USER_ID, null);
            token = sharedPreferences.getString(SaveAPPData.TOKEN, null);


            OkGo.<String>post(url).tag(this)
                    .params("uid", uid)
                    .params("token", token)
                    .params("file", imgUpdatePhotoData)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String data = response.body();
                            Log.i("sfsdfsfdfsf", data);
                            JSONObject obj = null;
                            int code = 2;
                            String message = null;
                            try {
                                obj = new JSONObject(data);
                                code = (obj.getInt("code"));
                                message = (obj.getString("message") + "");
                                if (code == AppConfig.SUCCESS) {
                                    rv_myphoto.setImageURI(cropUri);//????????????
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            loadDiaUpdate.cancel();
                            Toast.makeText(UserInfoActivity.this, message, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            loadDiaUpdate.cancel();
                            new AppNetUtil(UserInfoActivity.this).appInternetError();
                        }
                    });
    }

    /**
     * ????????????
     */
    private void updateUserInfo(String apiName,String url,String value) {
        loadDiaUpdate.show();
        SharedPreferences sharedPreferences = getSharedPreferences(SaveAPPData.USER_ID_Token, MODE_PRIVATE);
        uid = sharedPreferences.getString(SaveAPPData.USER_ID, null);
        token = sharedPreferences.getString(SaveAPPData.TOKEN, null);


        OkGo.<String>post(url).tag(this)
                .params("uid", uid)
                .params("token", token)
                .params(apiName, value)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sfsdfsfdfsf", data);
                        JSONObject obj = null;
                        int code = 2;
                        String message = null;
                        try {
                            obj = new JSONObject(data);
                            code = (obj.getInt("code"));
                            message = (obj.getString("message") + "");

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        loadDiaUpdate.cancel();
                        Toast.makeText(UserInfoActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadDiaUpdate.cancel();
                        new AppNetUtil(UserInfoActivity.this).appInternetError();
                    }
                });
    }



    /**
     * ??????????????????
     */
    private void initTimePicker() {
        //??????????????????(?????????????????????????????????????????????1900-2100???????????????????????????)
        //????????????Calendar???????????????0-11???,?????????????????????Calendar???set?????????????????????,???????????????????????????0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 9, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 28);
        //???????????????
        pvTime = new TimePickerBuilder(UserInfoActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                // ?????????????????????v,??????show()???????????????????????? View ???????????????show??????????????????????????????v??????null
                /*btn_Time.setText(getTime(date));*/
                tv_create.setText(getTime(date));
                String myData=getTime(date);
                myData=myData.replace("-","");
                updateUserInfo("birthday",Api.UPDATE_USER_CREATE,myData+"");
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
                .setLabel("", "", "", "", "", "") //???????????????????????????????????????   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//??????????????????????????????
    }

    /**
     * ????????????
     * @param date
     * @return
     */
    private String getTime(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * ????????????
     */
    private void showPickerView() {// ???????????????

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //?????????????????????????????????????????????
                String opt1tx = optionsCityItems.size() > 0 ?
                        optionsCityItems.get(options1).getPickerViewText() : "";

                String opt2tx = options2CityItems.size() > 0
                        && options2CityItems.get(options1).size() > 0 ?
                        options2CityItems.get(options1).get(options2) : "";

                String opt3tx = options2CityItems.size() > 0
                        && options3CityItems.get(options1).size() > 0
                        && options3CityItems.get(options1).get(options2).size() > 0 ?
                        options3CityItems.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
//                Toast.makeText(RegisteredActivity.this, tx, Toast.LENGTH_SHORT).show();
                tv_adress.setText(tx);
                updateUserInfo("city",Api.UPDATE_USER_CHANGE_CITY,tx+"");
            }
        })

                .setTitleText("????????????")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //???????????????????????????
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//???????????????
        pvOptions.setPicker(options1Items, options2Items);//???????????????*/
        pvOptions.setPicker(optionsCityItems, options2CityItems, options3CityItems);//???????????????
        pvOptions.show();
    }

    /**
     * ??????????????????
     */
    private void initJsonData() {//????????????

        /**
         * ?????????assets ????????????Json??????????????????????????????????????????????????????
         * ???????????????????????????
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//??????assets????????????json????????????

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//???Gson ????????????

        /**
         * ??????????????????
         *
         * ???????????????????????????JavaBean????????????????????????????????? IPickerViewData ?????????
         * PickerView?????????getPickerViewText????????????????????????????????????
         */
        optionsCityItems = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//????????????
            ArrayList<String> cityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//??????????????????????????????
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//????????????
                ArrayList<String> city_AreaList = new ArrayList<>();//??????????????????????????????

                //??????????????????????????????????????????????????????????????????null ?????????????????????????????????????????????
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//??????????????????????????????
            }

            /**
             * ??????????????????
             */
            options2CityItems.add(cityList);

            /**
             * ??????????????????
             */
            options3CityItems.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    public ArrayList<JsonBean> parseData(String result) {//Gson ??????
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//????????????????????????????????????????????????

//                        Toast.makeText(RegisteredActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        Log.i(".sdfmskfsfsf","Begin Parse Data");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // ?????????????????????????????????
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(RegisteredActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    Log.i(".sdfmskfsfsf","Parse Succeed");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Log.i(".sdfmskfsfsf","Parse Failed");
//                    Toast.makeText(RegisteredActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    /**
     * ??????
     */
    private void initCustomOptionPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
                String tx = cardItem.get(options1).getPickerViewText();
                tv_sex.setText(tx);
                int sexInt=1;
                if (tx.equals("???")){
                    sexInt=1;
                }else {
                    sexInt=0;
                }
                updateUserInfo("sex",Api.UPDATE_USER_SEX,sexInt+"");
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

        pvCustomOptions.setPicker(cardItem);//????????????


    }
    private void getCardData() {

        cardItem.add(new CardBean(0,"???"));
        cardItem.add(new CardBean(1,"???"));

        for (int i = 0; i < 2; i++) {
            cardItem.get(i).setCardNo(cardItem.get(i).getCardNo());
        }

    }


}
