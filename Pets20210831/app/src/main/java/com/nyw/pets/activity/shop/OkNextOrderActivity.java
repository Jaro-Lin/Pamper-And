package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.MyCouponsActivity;
import com.nyw.pets.activity.shop.util.GetAlipayOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.GetMyCouponsDataUtil;
import com.nyw.pets.activity.shop.util.GetShopOrderDataUtil;
import com.nyw.pets.activity.shop.util.GetShopOrderInfoDataUtil;
import com.nyw.pets.activity.shop.util.GetUserAdressListDataUtil;
import com.nyw.pets.activity.shop.util.GetWechatOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.OrderData;
import com.nyw.pets.activity.shop.util.ShopOrderDataUtil;
import com.nyw.pets.adapter.OkNextOrderListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.pay.AlipayCallbackActivity;
import com.nyw.pets.pay.AuthResult;
import com.nyw.pets.pay.PayResult;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ClearEditText;
import com.nyw.pets.view.PayTypeDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ????????????
 */
public class OkNextOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private ImageView iv_reduce,iv_increase,iv_img;
    private TextView tv_number,tv_specifications,tv_price,tv_productNumber,tv_allPrice,tv_allNumber,tv_title;
    private ClearEditText ct_note;
    private Button btn_ok;
    private LinearLayout llt_add_adress,llt_my_adress;
    private TextView tv_adress,tv_phone,tv_name,tv_null;
    private   String shopImg,shopTitle,speId,speName,price,shopId,pets_id,type_id,stock;
    private  int page=1,limit=15;

    private RecyclerView rcv_data;
    private OkNextOrderListAdapter okNextOrderListAdapter;
    private List<GetShopOrderDataUtil> orderList=new ArrayList<>();
    //????????????
    private LinearLayout llt_oneShop;
    //????????????id
    private String adressId;
    //????????????
    private String shopData;
    //?????????id ???????????????0
    private String coupon_id="";
    //??????????????????
    private int ShopNumber=1;
    //???????????????JSON??????
    public ShopOrderDataUtil shopOrderDataUtil;
    private List<ShopOrderDataUtil> shopOrdertList=new ArrayList<>();
    //????????????????????????JSON??????
    private  OrderData orderData;
    private String token;
    //??????id
    private String order_id;
    //???????????????????????????
    private GetAlipayOrderSginDataUtil getAlipayOrderSginDataUtil;
    //????????????????????????
    private GetWechatOrderSginDataUtil getWechatOrderSginDataUtil;
    //?????????
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    //?????????ID
    private List<String> cart_id=new ArrayList<>();
    private TextView tv_coupon;
    private LinearLayout llt_coupon;
    private    MyApplication myApplication;
    //????????????
    private String couponsPrice;
    private String discount;
    private LinearLayout llt_shopItem;
    //???????????????
    private  float allMoney=0;
    //???????????????
    private String myCouponsPrice="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_next_order);
        myApplication= (MyApplication) getApplication();
        initView();
    }

    private void initView() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_reduce=findViewById(R.id.iv_reduce);
        iv_reduce.setOnClickListener(this);
        tv_number=findViewById(R.id.tv_number);
        iv_increase=findViewById(R.id.iv_increase);
        iv_increase.setOnClickListener(this);
        ct_note=findViewById(R.id.ct_note);
        tv_specifications=findViewById(R.id.tv_specifications);
        tv_price=findViewById(R.id.tv_price);
        tv_productNumber=findViewById(R.id.tv_productNumber);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        tv_allPrice=findViewById(R.id.tv_allPrice);
        tv_allNumber=findViewById(R.id.tv_allNumber);
        llt_add_adress=findViewById(R.id.llt_add_adress);
        llt_add_adress.setOnClickListener(this);
        llt_my_adress=findViewById(R.id.llt_my_adress);
        llt_my_adress.setOnClickListener(this);
        tv_adress=findViewById(R.id.tv_adress);
        tv_phone=findViewById(R.id.tv_phone);
        tv_name=findViewById(R.id.tv_name);
        iv_img=findViewById(R.id.iv_img);
        tv_title=findViewById(R.id.tv_title);
        llt_oneShop=findViewById(R.id.llt_oneShop);
        tv_null=findViewById(R.id.tv_null);
        tv_coupon=findViewById(R.id.tv_coupon);
        llt_coupon=findViewById(R.id.llt_coupon);
        llt_coupon.setOnClickListener(this);
        llt_shopItem=findViewById(R.id.llt_shopItem);
        llt_shopItem.setOnClickListener(this);


        rcv_data=findViewById(R.id.rcv_data);

        rcv_data.setLayoutManager(new LinearLayoutManager(this));
        try {
            orderList = (List<GetShopOrderDataUtil>) getIntent().getSerializableExtra("orderData");
            if (orderList.size()>0) {
                tv_null.setVisibility(View.GONE);
                llt_oneShop.setVisibility(View.GONE);
                rcv_data.setVisibility(View.VISIBLE);

                int allNumber=0;

                    for (int i=0;i<orderList.size();i++){
                        int number;
                        number= 1* (Integer.parseInt(orderList.get(i).getNumber())) ;

                        allNumber=allNumber+number;
                        float myMoney =( Float.parseFloat(orderList.get(i).getPrice()))*( Float.parseFloat(orderList.get(i).getNumber()));
                        allMoney=allMoney+myMoney;

                        ShopOrderDataUtil shopOrderDataUtil = new ShopOrderDataUtil();
                        shopOrderDataUtil.setNumber(orderList.get(i).getNumber() + "");
                        shopOrderDataUtil.setShop_id(orderList.get(i).getShopId());
                        shopOrderDataUtil.setSpe_id(orderList.get(i).getSpecificationsId());
                        shopOrdertList.add(shopOrderDataUtil);

                    }

                    if (orderList.size()>0) {
                        tv_allNumber.setText("???" + allNumber + "???");
                        tv_allPrice.setText("??? " + allMoney);
                    }


            }

        }catch (Exception e){
            tv_null.setVisibility(View.VISIBLE);
            llt_oneShop.setVisibility(View.VISIBLE);
            rcv_data.setVisibility(View.GONE);

        }
        okNextOrderListAdapter=new OkNextOrderListAdapter(this,orderList);
        rcv_data.setAdapter(okNextOrderListAdapter);


        try {
            Bundle bundle = getIntent().getExtras();
            shopImg = bundle.getString("shopImg", null);
            shopTitle = bundle.getString("shopTitle", null);
            speId = bundle.getString("speId", null);
            speName = bundle.getString("speName", null);
            price = bundle.getString("price", null);
//            ToastManager.showShortToast(this, price);
            tv_price.setText(price);
             allMoney=Float.parseFloat(price);
            if (orderList==null){
                tv_allPrice.setText("??? " + price);
            }


            shopId = bundle.getString("shopId", null);
            pets_id = bundle.getString("pets_id", null);
            type_id = bundle.getString("type_id", null);
            //??????
            stock = bundle.getString("stock", null);

            //  //discount??? 1?????? 0 ?????????
            discount = bundle.getString("discount", null);
            //  //?????? discount???1???????????????
            couponsPrice = bundle.getString("couponsPrice", null);
//            if (discount.equals("1")){
//                tv_coupon.setText(couponsPrice+" ????????????");
//            }else {
//                tv_coupon.setText(couponsPrice+" ?????????????????????");
//            }

            //????????????
            Glide.with(this).load(shopImg).placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(iv_img);
            tv_title.setText(shopTitle);
            tv_specifications.setText("????????? " + speName);






        }catch (Exception e){
            //???????????????????????????????????????
//            int allNumber=0;
//            float money=0;
//            if (orderList.size()>0){
//                for (int i=0;i<orderList.size();i++){
//                  int number= i* (Integer.parseInt(orderList.get(i).getNumber())) ;
//                    allNumber=allNumber+number;
//                    float myMoney =( Float.parseFloat(orderList.get(i).getPrice()))*( Float.parseFloat(orderList.get(i).getNumber()));
//                    money=money+myMoney;
//                }
//                tv_allNumber.setText("???"+allNumber+"???");
//                tv_allPrice.setText("??? " + money);
//            }

        }




        getMyCouponsInfo();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==AppConfig.ADRESS_SELECT&&resultCode==AppConfig.ADRESS_SELECT){
            Bundle bundle=data.getExtras();
           String adress= bundle.getString("adress");
            String phone=bundle.getString("phone");
           if (adress!=null) {
               tv_adress.setText(adress);
           }
           if (phone!=null) {
               tv_phone.setText(phone);
           }
            String name=bundle.getString("name");
           if(name!=null){
            tv_name.setText(name);
           }
             adressId=bundle.getString("id");
        }else if (   requestCode==AppConfig.OPEN_TYPE_COUPON&&resultCode==AppConfig.OPEN_TYPE_COUPON){
           Bundle bundle= data.getExtras();
           String selectPrice=bundle.getString("price",null);
            String conditionPrice=bundle.getString("conditionPrice",null);
            String couponsId=bundle.getString("couponsId",null);
            coupon_id=couponsId;
            myCouponsPrice=selectPrice;
            //??????????????????????????????
            if (allMoney >= Float.parseFloat(conditionPrice)) {
                tv_coupon.setText(selectPrice + " ????????????");
               float myAllPrice=allMoney- (Float.parseFloat(selectPrice));
                tv_allPrice.setText("??? "+myAllPrice);
            }else {
                tv_coupon.setText(selectPrice + " ???????????? ??????????????????");
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
            case R.id.iv_reduce:
                //??????
                int number=Integer.parseInt(tv_number.getText().toString());
                if (number<=1){
                    number=1;
                }else {
                    number=number-1;
                }
                tv_number.setText(number+"");
                tv_productNumber.setText(number+"");
                tv_allNumber.setText("???"+number+"???");
                float priceToReduce=Float.parseFloat(price);
                tv_allPrice.setText("??? "+(priceToReduce*number));
                ShopNumber=number;
                allMoney=priceToReduce*number;
                getMyCouponsInfo();
                break;
            case R.id.iv_increase:
                //??????
                int increaseNumber=Integer.parseInt(tv_number.getText().toString())+1;
                tv_number.setText(increaseNumber+"");
                tv_productNumber.setText(increaseNumber+"");
                tv_allNumber.setText("???"+increaseNumber+"???");
                float priceToIncrease=Float.parseFloat(price);
                tv_allPrice.setText("??? "+(priceToIncrease*increaseNumber));
                ShopNumber=increaseNumber;
                allMoney=priceToIncrease*increaseNumber;
                getMyCouponsInfo();
                break;
            case R.id.llt_coupon:
                //?????????

                myApplication.setOrder(true);
                Intent okIntent=new Intent();
                okIntent.setClass(OkNextOrderActivity.this, MyCouponsActivity.class);
                startActivityForResult(okIntent,AppConfig.OPEN_TYPE_COUPON);
                break;
            case R.id.llt_shopItem:
                //??????????????????
                Intent intent=new Intent();
                intent.setClass(OkNextOrderActivity.this, ShopDetailsActivity.class);
                intent.putExtra("id",shopId);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_ok:

                if (TextUtils.isEmpty(adressId)){
                    ToastManager.showShortToast(OkNextOrderActivity.this,"?????????????????????");
                    return;
                }
                if (orderList==null) {
                    shopOrdertList.clear();
                            //?????????JSON????????????
                    shopOrderDataUtil = new ShopOrderDataUtil();
                    shopOrderDataUtil.setNumber(ShopNumber + "");
                    shopOrderDataUtil.setShop_id(shopId);
                    shopOrderDataUtil.setSpe_id(speId);
//                    ToastManager.showShortToast(OkNextOrderActivity.this,shopId);


                    shopOrdertList.add(shopOrderDataUtil);

                    orderData = new OrderData();
                    orderData.setShop(shopOrdertList);
                    orderData.setToken(token);
                    orderData.setCoupon_id(coupon_id);
                    orderData.setAddress_id(adressId);

                    Gson gson = new Gson();
                    shopData = gson.toJson(orderData);
                }else {
                    orderData = new OrderData();
                    orderData.setShop(shopOrdertList);
                    orderData.setToken(token);
                    orderData.setCoupon_id(coupon_id);
                    orderData.setAddress_id(adressId);

                    for (int i=0;i<orderList.size();i++){
                        cart_id.add(orderList.get(i).getId());
                    }
                    orderData.setCart_id(cart_id);

                    Gson gson=new Gson();

                    shopData = gson.toJson(orderData);
                }




                Log.i("sjsdfwertisfjvvh",shopData);

                //????????????
                PayTypeDialog payTypeDialog=new PayTypeDialog(OkNextOrderActivity.this);
                payTypeDialog.show();
                payTypeDialog.setDialogCallback(new PayTypeDialog.Dialogcallback() {
                    @Override
                    public void payType(boolean payType) {
                        //??????
                        //??????????????????false
                        if (payType==true){
                            getShopOrderInfoData(true);
                        }else {
                            getShopOrderInfoData(false);
                        }
                    }
                });
                break;
            case R.id.llt_add_adress:
                //?????????????????????
                Intent adress=new Intent();
                adress.setClass(OkNextOrderActivity.this,NewAdressActivity.class);
                startActivity(adress);
                break;
            case R.id.llt_my_adress:
                //????????????????????????
                Intent adressList=new Intent();
                adressList.setClass(OkNextOrderActivity.this,AdressListActivity.class);
                startActivityForResult(adressList, AppConfig.ADRESS_SELECT);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserShopListData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApplication.setOrder(false);
    }

    /**
     * ????????????????????????
     */
    private void getUserShopListData(){



        String url= Api.GET_SHOP_USER_LIST;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfsdenkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        Gson gson=new Gson() ;
                        GetUserAdressListDataUtil getUserAdressListDataUtil= gson.fromJson(data,GetUserAdressListDataUtil.class);

                        if (getUserAdressListDataUtil.getData()!=null) {

                            for (int i = 0; i < getUserAdressListDataUtil.getData().getList().size(); i++) {
                                if (getUserAdressListDataUtil.getData().getList().get(i).getIs_default() == 1) {
                                    //??????????????????????????????
                                    adressId = getUserAdressListDataUtil.getData().getList().get(i).getId() + "";
                                    String name = getUserAdressListDataUtil.getData().getList().get(i).getUsername();
                                    String phone = getUserAdressListDataUtil.getData().getList().get(i).getPhone();
                                    //??? ??????????????????????????????
                                    String province = getUserAdressListDataUtil.getData().getList().get(i).getProvince();
                                    String city = getUserAdressListDataUtil.getData().getList().get(i).getCity();
                                    String area = getUserAdressListDataUtil.getData().getList().get(i).getArea();
                                    String detailed = getUserAdressListDataUtil.getData().getList().get(i).getDetailed();

                                    llt_my_adress.setVisibility(View.VISIBLE);
                                    llt_add_adress.setVisibility(View.GONE);

                                    tv_name.setText(name);
                                    tv_phone.setText(phone);
                                    tv_adress.setText(province + city + area + detailed);
//                                    ToastManager.showShortToast(OkNextOrderActivity.this,"??????????????????");

                                }
                            }
                        }else {
                            llt_my_adress.setVisibility(View.GONE);
                            llt_add_adress.setVisibility(View.VISIBLE);
                        }







                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OkNextOrderActivity.this,"????????????");
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    private void getShopOrderInfoData(boolean payType){
//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);


        String url= Api.GET_ORDER_SHOP_INFO;
        Log.i("sdfsiofskfsffg",url);
        Log.i("sdfsiofskfsffg",shopData);
        //???????????????RequestBody ????????????json????????????????????????
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), shopData);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("coupon_id",coupon_id)
                .params("address_id",adressId)
                .params("mark",ct_note.getText().toString())
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
//                        ToastManager.showShortToast(OkNextOrderActivity.this,data);
                        Gson gson=new Gson() ;
                        GetShopOrderInfoDataUtil getShopOrderInfoDataUtil=gson.fromJson(data,GetShopOrderInfoDataUtil.class);

                         //???????????????????????????????????????
                         if (getShopOrderInfoDataUtil.getCode()==AppConfig.SUCCESS) {
                             String amount=getShopOrderInfoDataUtil.getData().getOrder().getShop_total_price()+"";
                             SaveAPPData saveAPPData=new SaveAPPData();
                             saveAPPData.SavePayMoney(OkNextOrderActivity.this,String.valueOf(amount));

                             order_id=getShopOrderInfoDataUtil.getData().getOrder().getId()+"";
                             if (payType == true) {
                                 //??????????????????????????????
                                 createPayInfoData(1);
                             } else {
                                 //?????????????????????????????????
                                 //????????????????????????????????????????????????

                                 createPayInfoData(0);
                             }
                         }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OkNextOrderActivity.this,"????????????");
                    }
                });
    }

    /**
     *??????????????????????????????????????????  0???????????????1?????????  ???????????? wechat ???????????????alipay ???????????????
     */
    private void createPayInfoData(int type){
//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);

        String pay_type="alipay";
        if (type==0){
            pay_type="alipay";
        }else {
            pay_type="wechat";
        }

        String url= Api.GET_PAY_ORDER_SHOP_INFO;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",order_id)
                .params("pay_type",pay_type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfnkgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
//                        ToastManager.showShortToast(OkNextOrderActivity.this,data);
                        Gson gson=new Gson() ;

                        if (type==0) {
                            //???????????????????????????
                             getAlipayOrderSginDataUtil = gson.fromJson(data, GetAlipayOrderSginDataUtil.class);
                            payTreasure(getAlipayOrderSginDataUtil.getData().getData());
                        }else {
                            //????????????????????????
                             getWechatOrderSginDataUtil = gson.fromJson(data, GetWechatOrderSginDataUtil.class);

                           String appId = getWechatOrderSginDataUtil.getData().getData().getAppid();
                            String  partnerId = getWechatOrderSginDataUtil.getData().getData().getPartnerid();
                            String  prepayId = getWechatOrderSginDataUtil.getData().getData().getPrepayid();
                            String packageValue = getWechatOrderSginDataUtil.getData().getData().getPackageX();
                            String nonceStr = getWechatOrderSginDataUtil.getData().getData().getNoncestr();
                            String timeStamp = getWechatOrderSginDataUtil.getData().getData().getTimestamp() + "";
                            String sign = getWechatOrderSginDataUtil.getData().getData().getSign();


                            //????????????SDK??????????????????
                            IWXAPI api = WXAPIFactory.createWXAPI(OkNextOrderActivity.this, appId);
                            api.registerApp(appId);
                            PayReq request = new PayReq();
                            ////??????ID
                            request.appId = appId;
                            //?????????
                            request.partnerId = partnerId;
                            //?????????????????????ID
                            request.prepayId = prepayId;
                            //????????????
                            request.packageValue = packageValue;
                            //???????????????
                            request.nonceStr = nonceStr;
                            //?????????
                            request.timeStamp = timeStamp;
                            //??????
                            request.sign = sign;
                            api.sendReq(request);
                            finish();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("sdkfsifsndfsfksfst",response.getRawResponse().message());
                        Log.i("sdkfsifsndfsfksfst",response.getException().getMessage());
                        ToastManager.showShortToast(OkNextOrderActivity.this,"????????????");
                    }
                });
    }

    /**
     * ???????????????
     */
    private void payTreasure(final  String orderInfo) {
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OkNextOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // ??????????????????
        Thread payThread = new Thread(payRunnable);
        payThread.start();
        finish();
    }
    /**
     * ???????????????????????????
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
//            Result result = new Result((String) msg.obj);
//            Toast.makeText(RechargeActivity.this, result.getResult(),
//                    Toast.LENGTH_LONG).show();

            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
//                        ToastManager.showShortToast(ProjectDetailsActivity.this, "????????????"+ payResult);
                        Log.i("sdfjasflkdsjfdksf","????????????"+ payResult);
                        finish();

                        //????????????????????????
                        Intent pay=new Intent();
                        pay.setClass(OkNextOrderActivity.this, AlipayCallbackActivity.class);
                        pay.putExtra("state","0");
                        pay.putExtra("payType","1");
                        startActivity(pay);

                    } else {
                        // ???????????????????????????????????????????????????????????????????????????
                        ToastManager.showShortToast(OkNextOrderActivity.this, "????????????"+ payResult);
                        Log.i("sdfjasflkdsjfdksf","????????????"+ payResult);
                        //????????????????????????
                        Intent pay=new Intent();
                        pay.setClass(OkNextOrderActivity.this,AlipayCallbackActivity.class);
                        pay.putExtra("state","1");
                        pay.putExtra("payType","1");
                        startActivity(pay);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
                        ToastManager.showShortToast(OkNextOrderActivity.this, "??????????????????"+ authResult);
                        Log.i("sdfjasflkdsjfdksf","??????????????????"+ authResult);
                    } else {
                        // ?????????????????????????????????
                        ToastManager.showShortToast(OkNextOrderActivity.this, "??????????????????"+ authResult);
                        Log.i("sdfjasflkdsjfdksf","??????????????????"+ authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * ?????????????????????
     */
    private void getMyCouponsInfo() {
        String url= Api.GET_SHOP_COUPON_COUPON_LIST;
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .params("type","false")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("jfsifsnvxkvjdxgvd",data);
                        Gson gson=new Gson();
                        GetMyCouponsDataUtil getMyCouponsDataUtil=  gson.fromJson(data, GetMyCouponsDataUtil.class);
                        if (getMyCouponsDataUtil.getCode()== AppConfig.SUCCESS) {
                            if (getMyCouponsDataUtil.getData().getList() != null) {
                                if (getMyCouponsDataUtil.getData().getList().size() > 0) {
                                    //?????????????????????????????????
                                    int isPrice = 0;
                                    //????????????
                                    for (int i = 0; i < getMyCouponsDataUtil.getData().getList().size(); i++) {
                                        //???????????? ??????
                                        float price1 = Float.parseFloat(getMyCouponsDataUtil.getData().getList().get(i).getHas_price());
                                        //????????????
                                        float price2 = Float.parseFloat(getMyCouponsDataUtil.getData().getList().get(i).getPrice());
                                        Log.i("dsflsdfisfjsfsf", allMoney + "");
                                        if (allMoney >= price1) {
                                            isPrice = 1;
                                            coupon_id=getMyCouponsDataUtil.getData().getList().get(i).getId()+"";
                                            myCouponsPrice=getMyCouponsDataUtil.getData().getList().get(i).getPrice();
                                            tv_coupon.setText(getMyCouponsDataUtil.getData().getList().get(i).getPrice() + " ????????????");
                                            float myAllPrice=allMoney- (Float.parseFloat(myCouponsPrice));
                                            tv_allPrice.setText("??? "+myAllPrice);
                                        }
                                    }
                                    if (isPrice == 0) {
                                        tv_coupon.setText(" ?????????????????????");
                                    }


                                } else {
                                    //????????????
                                    tv_coupon.setText(" ?????????????????????");
                                }
                            }else {
                                tv_coupon.setText(" ?????????????????????");
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OkNextOrderActivity.this,"????????????????????????????????????");
                    }
                });
    }

}
