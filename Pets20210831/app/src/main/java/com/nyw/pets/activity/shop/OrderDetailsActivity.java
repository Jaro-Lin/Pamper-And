package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetAlipayOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.GetOrderDataDetailsUtil;
import com.nyw.pets.activity.shop.util.GetWechatOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.OrderDetailsShopInfoUtil;
import com.nyw.pets.adapter.OrderDetailsShopAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.pay.AlipayCallbackActivity;
import com.nyw.pets.pay.AuthResult;
import com.nyw.pets.pay.PayResult;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.PayTypeDialog;
import com.nyw.pets.view.ServiceUsDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_service,iv_hide;
    //联系客服
    private ServiceUsDialog serviceUsDialog;
    private Button btn_after_sales;
    private String order_id;
    private String id;
    private TextView tv_state,tv_adress,tv_name,tv_phone,orderId,tv_logistics,tv_create_time,tv_pay_time;
    private RecyclerView rcv_data;
    private OrderDetailsShopAdapter orderDetailsShopAdapter;
    private List<OrderDetailsShopInfoUtil> orderList=new ArrayList<>();
    private MyApplication myApplication;
    private Button btn_cancelOrder,btn_pay,btn_logistics,btn_ok_goods,btn_evaluation;
    private   GetOrderDataDetailsUtil getOrderDataDetailsUtil;

    //支付宝支付签名数据
    private GetAlipayOrderSginDataUtil getAlipayOrderSginDataUtil;
    //微信支付签名数据
    private GetWechatOrderSginDataUtil getWechatOrderSginDataUtil;
    //支付宝
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        myApplication=(MyApplication)getApplication();
        initView();
    }

    private void initView() {
        iv_service=findViewById(R.id.iv_service);
        iv_service.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_after_sales=findViewById(R.id.btn_after_sales);
        btn_after_sales.setOnClickListener(this);
        tv_state=findViewById(R.id.tv_state);
        tv_adress=findViewById(R.id.tv_adress);
        tv_name=findViewById(R.id.tv_name);
        tv_phone=findViewById(R.id.tv_phone);
        orderId=findViewById(R.id.orderId);
        tv_logistics=findViewById(R.id.tv_logistics);
        tv_create_time=findViewById(R.id.tv_create_time);
        tv_pay_time=findViewById(R.id.tv_pay_time);
        rcv_data=findViewById(R.id.rcv_data);
        btn_cancelOrder=findViewById(R.id.btn_cancelOrder);
        btn_cancelOrder.setOnClickListener(this);
        btn_pay=findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
        btn_logistics=findViewById(R.id.btn_logistics);
        btn_logistics.setOnClickListener(this);
        btn_ok_goods=findViewById(R.id.btn_ok_goods);
        btn_ok_goods.setOnClickListener(this);
        btn_evaluation=findViewById(R.id.btn_evaluation);
        btn_evaluation.setOnClickListener(this);

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
        orderDetailsShopAdapter=new OrderDetailsShopAdapter(OrderDetailsActivity.this,orderList);
        rcv_data.setAdapter(orderDetailsShopAdapter);

        serviceUsDialog=new ServiceUsDialog(OrderDetailsActivity.this);

        try{
           Bundle bundle= getIntent().getExtras();
           order_id=bundle.getString("orderId",null);
            id=bundle.getString("id",null);

        }catch ( Exception e){}

        getData();




    }

    /**
     * 获取订单详情数据
     */
    private void getData() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String url= Api.GET_ORDER_DETAILS;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",order_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                     String data=   response.body();
                        Log.i("sdfskjfsifsfsf",data);
                        Gson gson=new Gson();
                         getOrderDataDetailsUtil= gson.fromJson(data, GetOrderDataDetailsUtil.class);
                        if (getOrderDataDetailsUtil.getCode()== AppConfig.SUCCESS) {
                            showState(getOrderDataDetailsUtil);
                            tv_phone.setText(getOrderDataDetailsUtil.getData().getAddress().getPhone());
                            tv_name.setText(getOrderDataDetailsUtil.getData().getAddress().getUsername());
                            tv_adress.setText(getOrderDataDetailsUtil.getData().getAddress().getProvince()
                                    + getOrderDataDetailsUtil.getData().getAddress().getCity() + getOrderDataDetailsUtil.getData().getAddress().getArea() +
                                    getOrderDataDetailsUtil.getData().getAddress().getDetailed());

                            orderId.setText(getOrderDataDetailsUtil.getData().getOrder_id()+"");
                            if (TextUtils.isEmpty(getOrderDataDetailsUtil.getData().getLogistics_id())||getOrderDataDetailsUtil.getData().getLogistics_id()==null||
                                    getOrderDataDetailsUtil.getData().getLogistics_id().equals("null")){
                                tv_logistics.setText("暂时没有物流信息");
                            }else {
                                tv_logistics.setText(getOrderDataDetailsUtil.getData().getLogistics_id() + "");
                            }
                            tv_create_time.setText(new TimeUtil().timeStamp(getOrderDataDetailsUtil.getData().getCreate_time()));
                            if (getOrderDataDetailsUtil.getData().getPay_time()!=null) {
                                tv_pay_time.setText(new TimeUtil().timeStamp(getOrderDataDetailsUtil.getData().getPay_time()));
                            }else {
                                tv_pay_time.setText("未支付");
                            }

                            for (int i=0;i<getOrderDataDetailsUtil.getData().getShop_list().size();i++){
                                OrderDetailsShopInfoUtil orderDetailsShopInfoUtil=new OrderDetailsShopInfoUtil();
                                orderDetailsShopInfoUtil.setImg(myApplication.getImgFilePathUrl()
                                        +getOrderDataDetailsUtil.getData().getShop_list().get(i).getShop().getIcon());
                                orderDetailsShopInfoUtil.setSpe(getOrderDataDetailsUtil.getData().getShop_list().get(i).getSpe().getSpe());
                                orderDetailsShopInfoUtil.setOrderId(getOrderDataDetailsUtil.getData().getOrder_id());
                                orderDetailsShopInfoUtil.setTitle(getOrderDataDetailsUtil.getData().getShop_list().get(i).getShop().getTitle());
                                orderList.add(orderDetailsShopInfoUtil);
                            }
                            orderDetailsShopAdapter.notifyDataSetChanged();

                        }



                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OrderDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 显示订单状态
     * @param getOrderDataDetailsUtil
     */
    private void showState(GetOrderDataDetailsUtil getOrderDataDetailsUtil) {


        if ( getOrderDataDetailsUtil.getData().getIs_cancel()==0){
            //未取消订单
            if ( getOrderDataDetailsUtil.getData().getIs_pay()==0){
                //未支付
                tv_state.setText("待支付");
                btn_cancelOrder.setVisibility(View.VISIBLE);
                btn_pay.setVisibility(View.VISIBLE);

                btn_after_sales.setVisibility(View.GONE);
                btn_logistics.setVisibility(View.GONE);
                btn_ok_goods.setVisibility(View.GONE);
                btn_evaluation.setVisibility(View.GONE);

            }else {
                //已经支付
                btn_cancelOrder.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
                btn_ok_goods.setVisibility(View.GONE);
                btn_evaluation.setVisibility(View.GONE);

                if ( getOrderDataDetailsUtil.getData().getIs_send()==0){
                    //未发货
                    tv_state.setText("待发货");
                    btn_after_sales.setVisibility(View.VISIBLE);
                    btn_logistics.setVisibility(View.GONE);
                }else {
                    //已经发货
                    btn_logistics.setVisibility(View.VISIBLE);
                    btn_after_sales.setVisibility(View.VISIBLE);
                    tv_state.setText("待收货");

                    if ( getOrderDataDetailsUtil.getData().getIs_put()==0){
                        //未收货
                        tv_state.setText("待收货");
                        btn_logistics.setVisibility(View.VISIBLE);
                        btn_ok_goods.setVisibility(View.VISIBLE);

                    }else {
                        //已经收货
                        tv_state.setText("已收货");
                        btn_logistics.setVisibility(View.GONE);
                        btn_ok_goods.setVisibility(View.GONE);

                        if ( getOrderDataDetailsUtil.getData().getIs_comment()==0){
                            //未评论
                            btn_evaluation.setVisibility(View.VISIBLE);
                            tv_state.setText("未评价");

                        }else {
                            //已经评论
                            tv_state.setText("已评价");
                            btn_evaluation.setVisibility(View.GONE);
                        }

                    }

                }

            }


        }else {
            //已经取消订单
            if ( getOrderDataDetailsUtil.getData().getIs_pay()==0){
                tv_state.setText("订单关闭");
            }
            btn_cancelOrder.setVisibility(View.GONE);
            btn_after_sales.setVisibility(View.GONE);
            btn_logistics.setVisibility(View.GONE);
            btn_ok_goods.setVisibility(View.GONE);
            btn_evaluation.setVisibility(View.GONE);
            btn_pay.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_service:
                //客服
                serviceUsDialog.show();
                break;
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_after_sales:
                //申请售后
                Intent after_sales=new Intent();
                after_sales.setClass(OrderDetailsActivity.this, RequestAfterSalesActivity.class);
                startActivity(after_sales);
                break;
            case R.id.btn_cancelOrder:
                //取消订单
                cancelOrderShop();
                break;
            case R.id.btn_pay:
                //支付
                PayTypeDialog payTypeDialog=new PayTypeDialog(OrderDetailsActivity.this);
                payTypeDialog.show();
                payTypeDialog.setDialogCallback(new PayTypeDialog.Dialogcallback() {
                    @Override
                    public void payType(boolean payType) {
                        //支付
                        //默认支付宝为false
                        Log.i("sdkfjsifnxcvkjgsdf",payType+"");

                        if (payType==true){
                            createPayInfoData(1);
                        }else {
                            createPayInfoData(0);
                        }
                    }
                });
                break;
            case R.id.btn_logistics:
                //物流单号
                //复制物流单号
                if (TextUtils.isEmpty(getOrderDataDetailsUtil.getData().getLogistics_id())){
                    ToastManager.showShortToast(OrderDetailsActivity.this,"没有找到物流订单号，请联系客服");
                    return;
                }
                if (getOrderDataDetailsUtil.getData().getLogistics_id().equals("null")){
                    ToastManager.showShortToast(OrderDetailsActivity.this,"没有找到物流订单号，请联系客服");
                    return;
                }
                if (getOrderDataDetailsUtil.getData().getLogistics_id().equals(null)){
                    ToastManager.showShortToast(OrderDetailsActivity.this,"没有找到物流订单号，请联系客服");
                    return;
                }

                copyMsg(getOrderDataDetailsUtil.getData().getLogistics_id());
                ToastManager.showShortToast(OrderDetailsActivity.this,getOrderDataDetailsUtil.getData().getPut_type()+"  物流订单号复制物流单号成功");
                break;
            case R.id.btn_ok_goods:
                //确定收货
                okOrderShop();
                break;
            case R.id.btn_evaluation:
                //评价
                Intent evaluation=new Intent();
//                after_sales.setClass(context, SendShopEvaluationActivity.class);
                evaluation.setClass(OrderDetailsActivity.this, ShopEvaluateListActivity.class);
                evaluation.putExtra("orderId",getOrderDataDetailsUtil.getData().getId());
                evaluation.putExtra("openType","1");
                //
                evaluation.putExtra("shopList",(Serializable)getOrderDataDetailsUtil.getData().getShop_list());
                startActivity(evaluation);
                break;

        }

    }
    /**
     * 复制信息
     * @param data
     */
    private void copyMsg(String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
        // newHtmlText、
        // newIntent、
        // newUri、
        // newRawUri
        ClipData clipData = ClipData.newPlainText(null, data);

        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }
    /**
     * 确定收货
     */
    private void okOrderShop(){
        String url=Api.GET_SHOP_ABOUT_RECEIPT;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",order_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("smdfnkgfgdisfdsfg", data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(OrderDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                //刷新数据
                                orderList.clear();
                                getData();
                            }
                        } catch (JSONException e) {
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OrderDetailsActivity.this,"网络错误");
                    }
                });
    }
    /**
     * 取消订单
     */
    private void cancelOrderShop( ){
        String url=Api.GET_CANCEL_ORDER_INFO;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",order_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("smdfnkgfgdisfdsfg", data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(OrderDetailsActivity.this,msg);
                            if (code==AppConfig.SUCCESS){
                                finish();
                            }
                        } catch (JSONException e) {
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OrderDetailsActivity.this,"网络错误");
                    }
                });
    }
    /**
     *创建微信或支付宝订单签名数据  0是支付宝，1是微信  支付类型 wechat 微信支付，alipay 支付宝支付
     */
    private void createPayInfoData(int type){




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
                .params("order_id",id)
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
                            //拿到支付宝验签数据
                            getAlipayOrderSginDataUtil = gson.fromJson(data, GetAlipayOrderSginDataUtil.class);
                            payTreasure(getAlipayOrderSginDataUtil.getData().getData());
                        }else {
                            //拿到微信验签数据
                            getWechatOrderSginDataUtil = gson.fromJson(data, GetWechatOrderSginDataUtil.class);

                            String appId = getWechatOrderSginDataUtil.getData().getData().getAppid();
                            String  partnerId = getWechatOrderSginDataUtil.getData().getData().getPartnerid();
                            String  prepayId = getWechatOrderSginDataUtil.getData().getData().getPrepayid();
                            String packageValue = getWechatOrderSginDataUtil.getData().getData().getPackageX();
                            String nonceStr = getWechatOrderSginDataUtil.getData().getData().getNoncestr();
                            String timeStamp = getWechatOrderSginDataUtil.getData().getData().getTimestamp() + "";
                            String sign = getWechatOrderSginDataUtil.getData().getData().getSign();


                            //调用微信SDK调起微信支付
                            IWXAPI api = WXAPIFactory.createWXAPI(OrderDetailsActivity.this, appId);
                            api.registerApp(appId);
                            PayReq request = new PayReq();
                            ////应用ID
                            request.appId = appId;
                            //商户号
                            request.partnerId = partnerId;
                            //预支付交易会话ID
                            request.prepayId = prepayId;
                            //扩展字段
                            request.packageValue = packageValue;
                            //随机字符串
                            request.nonceStr = nonceStr;
                            //时间戳
                            request.timeStamp = timeStamp;
                            //签名
                            request.sign = sign;
                            api.sendReq(request);
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(OrderDetailsActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 支付宝支付
     */
    private void payTreasure(final  String orderInfo) {
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask( OrderDetailsActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    /**
     * 支付宝支付回调数据
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
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastManager.showShortToast(ProjectDetailsActivity.this, "支付成功"+ payResult);
                        Log.i("sdfjasflkdsjfdksf","支付成功"+ payResult);
//                        finish();

                        //显示支付完成界面
                        Intent pay=new Intent();
                        pay.setClass(OrderDetailsActivity.this, AlipayCallbackActivity.class);
                        pay.putExtra("state","0");
                        pay.putExtra("payType","1");
                        startActivity(pay);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastManager.showShortToast(OrderDetailsActivity.this, "支付失败"+ payResult);
                        Log.i("sdfjasflkdsjfdksf","支付失败"+ payResult);
                        //显示支付失败界面
                        Intent pay=new Intent();
                        pay.setClass(OrderDetailsActivity.this,AlipayCallbackActivity.class);
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

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastManager.showShortToast(OrderDetailsActivity.this, "身份验证成功"+ authResult);
                        Log.i("sdfjasflkdsjfdksf","身份验证成功"+ authResult);
                    } else {
                        // 其他状态值则为授权失败
                        ToastManager.showShortToast(OrderDetailsActivity.this, "身份验证失败"+ authResult);
                        Log.i("sdfjasflkdsjfdksf","身份验证失败"+ authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
}
