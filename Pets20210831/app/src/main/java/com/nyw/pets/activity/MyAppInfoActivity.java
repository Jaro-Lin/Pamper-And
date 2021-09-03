package com.nyw.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.AfterSalesActivity;
import com.nyw.pets.activity.shop.MyOrderListActivity;
import com.nyw.pets.activity.shop.ShopCartActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.user.UserInfoActivity;
import com.nyw.pets.activity.util.GetMyDynamicDataUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.RoundImageView;
import com.nyw.pets.view.ServiceUsDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我的
 */
public class MyAppInfoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llt_my_info;
    private LinearLayout llt_collection,llt_focus_on,llt_myFans,llt_coupons,llt_dynamic,llt_community,llt_pets
            ,llt_service,llt_after_sales,llt_order,llt_shopCart;
    private TextView tv_focus_on,tv_myFansNumber,tv_dynamic;
    //联系客服
    private ServiceUsDialog serviceUsDialog;
    private RoundImageView  rv_userImg;
    private TextView tv_name,tv_msg,tv_coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my2);
        initVew();
    }

    private void initVew() {
        llt_my_info=findViewById(R.id.llt_my_info);
        llt_my_info.setOnClickListener(this);
        llt_collection=findViewById(R.id.llt_collection);
        llt_collection.setOnClickListener(this);
        llt_focus_on=findViewById(R.id.llt_focus_on);
        llt_focus_on.setOnClickListener(this);
        tv_focus_on=findViewById(R.id.tv_focus_on);
        llt_myFans=findViewById(R.id.llt_myFans);
        llt_myFans.setOnClickListener(this);
        tv_myFansNumber=findViewById(R.id.tv_myFansNumber);
        llt_coupons=findViewById(R.id.llt_coupons);
        llt_coupons.setOnClickListener(this);
        llt_dynamic=findViewById(R.id.llt_dynamic);
        llt_dynamic.setOnClickListener(this);
        tv_dynamic=findViewById(R.id.tv_dynamic);
        llt_community=findViewById(R.id.llt_community);
        llt_community.setOnClickListener(this);
        llt_pets=findViewById(R.id.llt_pets);
        llt_pets.setOnClickListener(this);
        llt_service=findViewById(R.id.llt_service);
        llt_service.setOnClickListener(this);
        llt_after_sales=findViewById(R.id.llt_after_sales);
        llt_after_sales.setOnClickListener(this);
        llt_order=findViewById(R.id.llt_order);
        llt_order.setOnClickListener(this);
        llt_shopCart=findViewById(R.id.llt_shopCart);
        llt_shopCart.setOnClickListener(this);
        rv_userImg=findViewById(R.id.rv_userImg);
        tv_name=findViewById(R.id.tv_name);
        tv_msg=findViewById(R.id.tv_msg);
        tv_coupons=findViewById(R.id.tv_coupons);

        serviceUsDialog=new ServiceUsDialog(MyAppInfoActivity.this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.llt_my_info:
                //我的信息资料
                Intent my_info=new Intent();
                my_info.setClass(MyAppInfoActivity.this, UserInfoActivity.class);
                startActivity(my_info);
                break;
            case R.id.llt_collection:
                //收藏
                Intent collection=new Intent();
                collection.setClass(MyAppInfoActivity.this,MyCollectionActivity.class);
                startActivity(collection);
                break;
            case R.id.llt_focus_on:
                //关注
                Intent focus=new Intent();
                focus.setClass(MyAppInfoActivity.this,MyFocusOnActivity.class);
                startActivity(focus);
                break;
            case R.id.llt_myFans:
                //我的粉丝
                Intent myFans=new Intent();
                myFans.setClass(MyAppInfoActivity.this,MyFansActivity.class);
                startActivity(myFans);
                break;
            case R.id.llt_coupons:
                //我的优惠卷
                Intent coupons=new Intent();
                coupons.setClass(MyAppInfoActivity.this,MyCouponsActivity.class);
                startActivityForResult(coupons,AppConfig.OPEN_TYPE_COUPON);
                break;
            case R.id.llt_dynamic:
                //动态
                Intent dynamic=new Intent();
                dynamic.setClass(MyAppInfoActivity.this,MyDynamicActivity.class);
                startActivity(dynamic);
                break;
            case R.id.llt_community:
                //社区
                Intent community=new Intent();
                community.setClass(MyAppInfoActivity.this,MyCommunityActivity.class);
                startActivity(community);
                break;
            case R.id.llt_pets:
                //我的宠物
                Intent pest=new Intent();
                pest.setClass(MyAppInfoActivity.this,MyPetsListActivity.class);
                startActivity(pest);
                break;
            case R.id.llt_service:
                //联系客服
                serviceUsDialog.show();
                break;
            case R.id.llt_after_sales:
                //售后
                Intent after=new Intent();
                after.setClass(MyAppInfoActivity.this, AfterSalesActivity.class);
                startActivity(after);

                break;
            case R.id.llt_order:
                //我的订单
                Intent order=new Intent();
                order.setClass(MyAppInfoActivity.this, MyOrderListActivity.class);
                startActivity(order);

                break;
            case R.id.llt_shopCart:
                //我的购物车
                Intent intentShopCart=new Intent();
                intentShopCart.setClass(MyAppInfoActivity.this, ShopCartActivity.class);
                startActivity(intentShopCart);
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(){

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(MyAppInfoActivity.this, LoginActivity.class);
            startActivity(login);
        }
        OkGo.<String>post(Api.GET_USER_INFO).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsifsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){
                            //登录后的正常获取到数据
                            Gson gson=new Gson();
                            GetUserInfoUtil getUserInfoUtil=  gson.fromJson(data, GetUserInfoUtil.class);
                            String avatar=  getUserInfoUtil.getData().getAvatar();
                            if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                                Glide.with(MyAppInfoActivity.this).load(getUserInfoUtil.getData().getAvatar())
                                        .error(R.mipmap.user_app_default)
                                        .placeholder(R.mipmap.user_app_default).into(rv_userImg);
                            }else {
                                Glide.with(MyAppInfoActivity.this).load(getUserInfoUtil.getData().getServer() + getUserInfoUtil.getData().getAvatar())
                                        .placeholder(R.mipmap.user_app_default).error(R.mipmap.user_app_default).into(rv_userImg);
                            }

                            tv_name.setText(getUserInfoUtil.getData().getNickname());
                            tv_msg.setText(getUserInfoUtil.getData().getSpe());
                            //关注总数量
                            tv_focus_on.setText(getUserInfoUtil.getData().getFollow_count()+"");
                            //粉丝总数量
                            tv_myFansNumber.setText(getUserInfoUtil.getData().getFans_count()+"");
                            //动态总数量
                            tv_dynamic.setText(getUserInfoUtil.getData().getPost_count()+"");
                            //优惠卷
                            tv_coupons.setText(getUserInfoUtil.getData().getCoupon_count()+"");

                        }else {
                            //未登录的
                            Intent login=new Intent();
                            login.setClass(MyAppInfoActivity.this, LoginActivity.class);
                            startActivity(login);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.GET_USER_PERSONAL_POST_USER).tag(this)
                .params("token",token)
                .params("page",1)
                .params("limit",15)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsifdjsfofgtgt",data);
                        Gson gson=new Gson();
                        GetMyDynamicDataUtil getMyDynamicDataUtil= gson.fromJson(data, GetMyDynamicDataUtil.class);
                        tv_dynamic.setText(getMyDynamicDataUtil.getData().getTotal()+"");



                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(MyAppInfoActivity.this,"网络错误");
                    }
                });
    }
}
