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
 * ?????????????????????????????????????????????
 */
public class PetsDataSetupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_return,btn_add_ok,btn_change_ok;
    private LinearLayout llt_sterilization,llt_create_data,llt_weight,llt_sex,llt_healthy;
    private ClearEditText ct_name;
    private ImageView rv_my_img,iv_del;
    //????????????
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
    //?????????????????????
    private TimePickerView pvTime;
    private TextView tv_create_data,tv_title;
    private TextView tv_sex;
    //????????????
    private OptionsPickerView  pvCustomOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();
    //??????????????????
    private OptionsPickerView pvsterilization;
    private ArrayList<CardBean> cardSterilizationItem = new ArrayList<>();
    private TextView tv_sterilization;
    //????????????
    private OptionsPickerView pvWeightPickerView;
    private ArrayList<CardBean> cardWeightItem = new ArrayList<>();
    private TextView tv_weight,tv_breed;
    //??????????????????
    private  AddPetsSuccessDialog addPetsSuccessDialog ;
    //??????????????????
    private DelPetsInfoDialog delPetsInfoDialog;
    private  String getPetsId,getPetsName;
    private        String avatar,type_id,varieties_id,nickname,sex,brithday,weight,sterilization,getPetsTypeId,age,pid;
    private boolean isEdit=false;//??????????????????????????????
    //??????,??????  ?????????
    private String healthy="??????";
    private ClearEditText ct_healthy;
    //????????????????????????
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
        //??????????????????
        initTimePicker();
        //????????????
        initCustomOptionPicker();
        //????????????
        initSterilizationPicker();
        //??????
        initWeightPicker();
        //??????????????????
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
                //?????????????????? ??????
                delData(Api.GET_PETS_PETS_DEL);

            }
        });
         try{
             String type=bundle.getString("type",null);
             String  breed=bundle.getString("breed",null);
             tv_breed.setText("????????? "+breed);

             if (type.equals("change")){
                 isEdit=true;
                 tv_title.setText("????????????");
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
                     tv_sex.setText("???");
                 }else {
                     tv_sex.setText("???");
                 }

                 tv_weight.setText(weight);

                 if (!TextUtils.isEmpty(brithday)){


                     String year=brithday.substring(0,4);
                     String months=brithday.substring(4,6);
                     String shit=brithday.substring(6,8);
                     tv_create_data.setText(year+"-"+months+"-"+shit);
                 }

                 if (sterilization.equals("1")){
                     tv_sterilization.setText("?????????");
                 }else {
                     tv_sterilization.setText("?????????");
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
                //????????????
                addPetsData(Api.ADD_PETS);
                break;
            case R.id.llt_sterilization:
                //??????????????????
                pvsterilization.show();
                break;
            case R.id.llt_create_data:
                //??????????????????
                pvTime.show();
                break;
            case R.id.llt_weight:
                //????????????
                pvWeightPickerView.show();
                break;
            case R.id.llt_sex:
                //????????????
                pvCustomOptions.show();
                break;
            case R.id.rv_my_img:
                //????????????
                showMyDialog();
                break;
            case R.id.iv_del:
                //??????
                delPetsInfoDialog.show();


                break;
            case R.id.btn_change_ok:
                //?????????????????? ??????
                //????????????
                addPetsData(Api.GET_PETS_UPDATA_PETS);
                break;
            case R.id.llt_healthy:
                //????????????????????????
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
                        ToastManager.showShortToast(PetsDataSetupActivity.this,"????????????");
                    }
                });
    }

    /**
     * ????????????
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
                //????????????????????????
                avatar = imgUpdatePhotoData;
            }
        }

        nickname=ct_name.getText().toString();
        if (TextUtils.isEmpty(avatar)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(type_id)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(varieties_id)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(nickname)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(sex)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(brithday)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"???????????????????????????");
            return;
        }else if (TextUtils.isEmpty(weight)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }else if (TextUtils.isEmpty(sterilization)){
            ToastManager.showShortToast(PetsDataSetupActivity.this,"?????????????????????");
            return;
        }
        healthy=tv_healthy.getText().toString();
        if (TextUtils.isEmpty(healthy)){
            healthy="??????";
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
                        ToastManager.showShortToast(PetsDataSetupActivity.this,"????????????");
                    }
                });

    }

    /**
     * ????????????
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
                                rv_my_img.setImageURI(cropUri);//????????????
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
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
                    imageUri = FileProvider.getUriForFile(PetsDataSetupActivity.this, getPackageName(), fileOut);//??????FileProvider????????????content?????????Uri
                }else {
                    imageUri = Uri.fromFile(fileOut);
                }
                SaveAPPData.SavePhoto(PetsDataSetupActivity.this, Uri.fromFile(fileOut).toString());
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
            ToastManager.showShortToast(PetsDataSetupActivity.this, "??????????????????????????????SD???????????????");
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
//                            imageUri = FileProvider.getUriForFile(PetsDataSetupActivity.this, "com.zz.fileprovider", fileUri);//??????FileProvider????????????content?????????Uri
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
            ToastManager.showShortToast(PetsDataSetupActivity.this, "???????????????????????????????????????SD???????????????");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // ???????????????Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(PetsDataSetupActivity.this, uri);
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
                imagePath = FileProvider.getUriForFile(PetsDataSetupActivity.this, getPackageName(), new File(data.getPath()));
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
//                rv_my_img.setImageURI(cropUri);//????????????
                break;
        }
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
        pvTime = new TimePickerBuilder(PetsDataSetupActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                // ?????????????????????v,??????show()???????????????????????? View ???????????????show??????????????????????????????v??????null
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

                if (tx.equals("???")){
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

        pvCustomOptions.setPicker(cardItem);//????????????


    }
    private void getCardData() {

        cardItem.add(new CardBean(0,"???"));
        cardItem.add(new CardBean(1,"???"));

        for (int i = 0; i < 2; i++) {
            cardItem.get(i).setCardNo(cardItem.get(i).getCardNo());
        }

    }

    /**
     * ????????????????????????
     */
    private void initHealthyCustomOptionPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        pvHealthyOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
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

        pvHealthyOptions.setPicker(cardHealthyItem);//????????????


    }
    private void getHealthyCardData() {

        cardHealthyItem.add(new CardBean(0,"??????"));
        cardHealthyItem.add(new CardBean(1,"??????"));

        for (int i = 0; i < 2; i++) {
            cardHealthyItem.get(i).setCardNo(cardHealthyItem.get(i).getCardNo());
        }

    }

    /**
     * ????????????
     */
    private void initSterilizationPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        pvsterilization = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
                String tx = cardSterilizationItem.get(options1).getPickerViewText();
                tv_sterilization.setText(tx);
                if (tx.equals("?????????")){
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

        pvsterilization.setPicker(cardSterilizationItem);//????????????


    }
    private void getSterilizationData() {

        cardSterilizationItem.add(new CardBean(0,"?????????"));
        cardSterilizationItem.add(new CardBean(1,"?????????"));

        for (int i = 0; i < 2; i++) {
            cardSterilizationItem.get(i).setCardNo(cardSterilizationItem.get(i).getCardNo());
        }

    }


    /**
     * ??????
     */
    private void initWeightPicker() {//??????????????????????????????????????????
        /**
         * @description
         *
         * ???????????????
         * ?????????????????????id??? optionspicker ?????? timepicker ??????????????????????????????????????????????????????????????????
         * ???????????????demo ????????????????????????layout?????????
         */
        pvWeightPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
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

        pvWeightPickerView.setPicker(cardWeightItem);//????????????


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
