package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.OrderDetailsActivity;
import com.nyw.pets.activity.shop.ShopEvaluateListActivity;
import com.nyw.pets.activity.shop.util.GetAlipayOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.GetWechatOrderSginDataUtil;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;
import com.nyw.pets.activity.util.GetShopOrderInfoUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.CancelOrderInterface;
import com.nyw.pets.pay.AlipayCallbackActivity;
import com.nyw.pets.pay.AuthResult;
import com.nyw.pets.pay.PayResult;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.CancelOrderInfoDialog;
import com.nyw.pets.view.PayTypeDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的的全部订单
 * Created by Administrator on 2016/12/5.
 */

public class MyAllOrderAdapter extends RecyclerView.Adapter<MyAllOrderAdapter.ViewHolder> {
    private Context context;
    private List<GetShopOrderInfoUtil> data;
    private OrderShopInfoAdapter orderShopInfoAdapter;
    private List<MyShopOrderInfoUtil> shopOrderList=new ArrayList<>();
    private JSONArray arr;

    //订单id
    private String order_id;
    //支付宝支付签名数据
    private GetAlipayOrderSginDataUtil getAlipayOrderSginDataUtil;
    //微信支付签名数据
    private GetWechatOrderSginDataUtil getWechatOrderSginDataUtil;
    //支付宝
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String token;
    private CancelOrderInterface cancelOrderInterface;

    public void setCancelOrderInterface(CancelOrderInterface cancelOrderInterface) {
        this.cancelOrderInterface = cancelOrderInterface;
    }

    public MyAllOrderAdapter(Context context, List<GetShopOrderInfoUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyAllOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_info_order_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAllOrderAdapter.ViewHolder viewHolder, final int i) {

        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
         token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
//        order_id=data.get(i).getOrderId();

        viewHolder.tv_shopNumber.setText("共"+data.get(i).getShopOrderList().size()+"件商品 合计"+data.get(i).getTotalPrice()+"元");


        shopOrderList.clear();
        for (int j=0;j<data.get(i).getShopOrderList().size();j++){
            MyShopOrderInfoUtil myShopOrderInfoUtil=new MyShopOrderInfoUtil();
            myShopOrderInfoUtil.setId( data.get(i).getShopOrderList().get(j).getId());
            myShopOrderInfoUtil.setOrderId( data.get(i).getShopOrderList().get(j).getOrderId());
            myShopOrderInfoUtil.setTitle( data.get(i).getShopOrderList().get(j).getTitle());
            myShopOrderInfoUtil.setOrderState( data.get(i).getShopOrderList().get(j).getOrderState());
            myShopOrderInfoUtil.setPrice( data.get(i).getShopOrderList().get(j).getPrice());
            myShopOrderInfoUtil.setServer( data.get(i).getShopOrderList().get(j).getServer());
            myShopOrderInfoUtil.setShopImg( data.get(i).getShopOrderList().get(j).getShopImg());
            myShopOrderInfoUtil.setShopNumber( data.get(i).getShopOrderList().get(j).getShopNumber());
            myShopOrderInfoUtil.setSpecifications( data.get(i).getShopOrderList().get(j).getSpecifications());

            myShopOrderInfoUtil.setIs_pay(data.get(i).getShopOrderList().get(j).getIs_pay()+"");
            myShopOrderInfoUtil.setIs_send(data.get(i).getShopOrderList().get(j).getIs_send()+"");
            myShopOrderInfoUtil.setIs_put(data.get(i).getShopOrderList().get(j).getIs_put()+"");
            myShopOrderInfoUtil.setIs_comment(data.get(i).getShopOrderList().get(j).getIs_comment()+"");
            myShopOrderInfoUtil.setIs_cancel(data.get(i).getShopOrderList().get(j).getIs_cancel()+"");
            myShopOrderInfoUtil.setIs_salelate(data.get(i).getShopOrderList().get(j).getIs_salelate()+"");
            myShopOrderInfoUtil.setOrderNumber(data.get(i).getShopOrderList().get(j).getOrderNumber()+"");
            shopOrderList.add(myShopOrderInfoUtil);
        }

        //显示订单中的商品数据列表
        viewHolder.rcv_data.setLayoutManager(new SyLinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        orderShopInfoAdapter=new OrderShopInfoAdapter(context,shopOrderList);
        viewHolder.rcv_data.setAdapter(orderShopInfoAdapter);

        //显示订单状态
        if (data.get(i).getIs_cancel().equals("0")){
            //未取消订单
            viewHolder.btn_cancelOrder.setVisibility(View.VISIBLE);

            if (data.get(i).getIs_pay().equals("0")){
                //未支付
                viewHolder.btn_cancelOrder.setVisibility(View.VISIBLE);
                viewHolder.btn_pay.setVisibility(View.VISIBLE);

                viewHolder.btn_after_sales.setVisibility(View.GONE);
                viewHolder.btn_logistics.setVisibility(View.GONE);
                viewHolder.btn_ok_goods.setVisibility(View.GONE);
                viewHolder.btn_evaluation.setVisibility(View.GONE);
            }else {
                //已经支付

                viewHolder.btn_cancelOrder.setVisibility(View.GONE);
                viewHolder.btn_pay.setVisibility(View.GONE);
                viewHolder.btn_ok_goods.setVisibility(View.GONE);
                viewHolder.btn_evaluation.setVisibility(View.GONE);





                if (data.get(i).getIs_send().equals("0")){
                    //未发货
                    viewHolder.btn_after_sales.setVisibility(View.VISIBLE);
                    viewHolder.btn_logistics.setVisibility(View.GONE);
                }else {
                    //已经发货
                    viewHolder.btn_logistics.setVisibility(View.VISIBLE);
                    viewHolder.btn_after_sales.setVisibility(View.VISIBLE);

                    if (data.get(i).getIs_put().equals("0")){
                        //未收货
                        viewHolder.btn_logistics.setVisibility(View.VISIBLE);
                        viewHolder.btn_ok_goods.setVisibility(View.VISIBLE);
                    }else {
                        //已经收货
                        viewHolder.btn_logistics.setVisibility(View.GONE);
                        viewHolder.btn_ok_goods.setVisibility(View.GONE);

                        if (data.get(i).getIs_comment().equals("0")){
                            //未评论
                            viewHolder.btn_evaluation.setVisibility(View.VISIBLE);
                        }else {
                            //已经评论
                            viewHolder.btn_evaluation.setVisibility(View.GONE);
                        }

                    }


                }


            }

        }else {
            //已经取消订单
            viewHolder.btn_cancelOrder.setVisibility(View.GONE);
            viewHolder.btn_after_sales.setVisibility(View.GONE);
            viewHolder.btn_logistics.setVisibility(View.GONE);
            viewHolder.btn_ok_goods.setVisibility(View.GONE);
            viewHolder.btn_evaluation.setVisibility(View.GONE);
            viewHolder.btn_pay.setVisibility(View.GONE);
        }








        if (data.get(i).getIs_salelate().equals("0")){
            //未发起售后
        }else {
            //已经发起售后
        }

//        if ( data.get(i).getOrderState().equals("0")){
//            //交易完成
//        }else if(data.get(i).getOrderState().equals("1")){
//            //待支付
//            //显示
//            viewHolder.btn_cancelOrder.setVisibility(View.VISIBLE);
//            viewHolder.btn_pay.setVisibility(View.VISIBLE);
//            //不显示
//            viewHolder.btn_after_sales.setVisibility(View.GONE);
////            viewHolder.btn_delivery.setVisibility(View.GONE);
//            viewHolder.btn_logistics.setVisibility(View.GONE);
//            viewHolder.btn_ok_goods.setVisibility(View.GONE);
//            viewHolder.btn_evaluation.setVisibility(View.GONE);
//
//        }else if(data.get(i).getOrderState().equals("2")){
//            //待发货
//            //显示
////            viewHolder.btn_delivery.setVisibility(View.VISIBLE);
//            viewHolder.btn_after_sales.setVisibility(View.VISIBLE);
//
//            //不显示
//            viewHolder.btn_cancelOrder.setVisibility(View.GONE);
//            viewHolder.btn_pay.setVisibility(View.GONE);
//            viewHolder.btn_logistics.setVisibility(View.GONE);
//            viewHolder.btn_ok_goods.setVisibility(View.GONE);
//            viewHolder.btn_evaluation.setVisibility(View.GONE);
//
//        }else if(data.get(i).getOrderState().equals("3")){
//            //待收货
//
//            viewHolder.btn_after_sales.setVisibility(View.VISIBLE);
//            viewHolder.btn_logistics.setVisibility(View.VISIBLE);
//            viewHolder.btn_ok_goods.setVisibility(View.VISIBLE);
//
//            //不显示
////            viewHolder.btn_delivery.setVisibility(View.GONE);
//            viewHolder.btn_cancelOrder.setVisibility(View.GONE);
//            viewHolder.btn_pay.setVisibility(View.GONE);
//            viewHolder.btn_evaluation.setVisibility(View.GONE);
//
//        }else if(data.get(i).getOrderState().equals("4")){
//            //待评价
//
//            viewHolder.btn_evaluation.setVisibility(View.VISIBLE);
//
//
//            //不显示
//            viewHolder.btn_cancelOrder.setVisibility(View.GONE);
//            viewHolder.btn_pay.setVisibility(View.GONE);
//            viewHolder.btn_logistics.setVisibility(View.GONE);
//            viewHolder.btn_ok_goods.setVisibility(View.GONE);
////            viewHolder.btn_delivery.setVisibility(View.GONE);
//            viewHolder.btn_after_sales.setVisibility(View.GONE);
//
//
//        }
        viewHolder.btn_ok_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定收货
                okOrderShop(data,i);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入订单详情页

                Intent intent=new Intent();
                intent.setClass(context, OrderDetailsActivity.class);
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("orderId",data.get(i).getOrderId());
                context.startActivity(intent);
            }
        });

        viewHolder.btn_cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消订单
                CancelOrderInfoDialog cancelOrderInfoDialog=new CancelOrderInfoDialog(context);
                cancelOrderInfoDialog.show();
                cancelOrderInfoDialog.setDialogCallback(new CancelOrderInfoDialog.Dialogcallback() {
                    @Override
                    public void cancelOrder(String string) {
                        //取消订单
                        cancelOrderShop(data,i);
                    }
                });
            }
        });

        viewHolder.btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去付款
                PayTypeDialog payTypeDialog=new PayTypeDialog(context);
                payTypeDialog.show();
                payTypeDialog.setDialogCallback(new PayTypeDialog.Dialogcallback() {
                    @Override
                    public void payType(boolean payType) {
                        //支付
                        //默认支付宝为false
                        Log.i("sdkfjsifnxcvkjgsdf",payType+"");
                        order_id=data.get(i).getId();
                        if (payType==true){
                            createPayInfoData(1);
                        }else {
                            createPayInfoData(0);
                        }
                    }
                });

            }
        });

        viewHolder.btn_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制物流单号
                if (TextUtils.isEmpty(data.get(i).getLogistics_id())){
                    ToastManager.showShortToast(context,"没有找到物流订单号，请联系客服");
                    return;
                }
                if (data.get(i).getLogistics_id().equals("null")){
                    ToastManager.showShortToast(context,"没有找到物流订单号，请联系客服");
                    return;
                }
                if (data.get(i).getLogistics_id().equals(null)){
                    ToastManager.showShortToast(context,"没有找到物流订单号，请联系客服");
                    return;
                }

                copyMsg(data.get(i).getLogistics_id());
                ToastManager.showShortToast(context,data.get(i).getPut_type()+"  物流订单号复制物流单号成功");

            }
        });
        viewHolder.btn_after_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请售后
//                Intent after_sales=new Intent();
//                after_sales.setClass(context, RequestAfterSalesActivity.class);
//                context.startActivity(after_sales);

                Intent sendAfter_sales=new Intent();
                sendAfter_sales.setClass(context, ShopEvaluateListActivity.class);
                sendAfter_sales.putExtra("orderId",data.get(i).getOrderId());
                sendAfter_sales.putExtra("openType","0");
                //
                sendAfter_sales.putExtra("shopList",(Serializable)data.get(i).getShopOrderList());
                context.startActivity(sendAfter_sales);
            }
        });

        viewHolder.btn_evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商品评价
                Intent after_sales=new Intent();
//                after_sales.setClass(context, SendShopEvaluationActivity.class);
                after_sales.setClass(context, ShopEvaluateListActivity.class);
                after_sales.putExtra("orderId",data.get(i).getId());
                after_sales.putExtra("openType","1");
                //
                after_sales.putExtra("shopList",(Serializable)data.get(i).getShopOrderList());
                context.startActivity(after_sales);
            }
        });

    }

    /**
     * 复制信息
     * @param data
     */
    private void copyMsg(String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
        // newHtmlText、
        // newIntent、
        // newUri、
        // newRawUri
        ClipData clipData = ClipData.newPlainText(null, data);

        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_price,tv_specifications,tv_state,tv_shopNumber;

        private Button btn_cancelOrder,btn_pay,btn_after_sales,btn_delivery,btn_logistics,btn_ok_goods,btn_evaluation;
        private RecyclerView rcv_data;

        public ViewHolder(View itemView) {
            super(itemView);




            btn_cancelOrder=itemView.findViewById(R.id.btn_cancelOrder);
            btn_pay=itemView.findViewById(R.id.btn_pay);
            btn_after_sales=itemView.findViewById(R.id.btn_after_sales);
            btn_delivery=itemView.findViewById(R.id.btn_delivery);
            btn_logistics=itemView.findViewById(R.id.btn_logistics);
            btn_ok_goods=itemView.findViewById(R.id.btn_ok_goods);
            btn_evaluation=itemView.findViewById(R.id.btn_evaluation);
            tv_shopNumber=itemView.findViewById(R.id.tv_shopNumber);

            rcv_data=itemView.findViewById(R.id.rcv_data);



        }

    }

    /**
     * 确定收货
     * @param data
     * @param i
     */
    private void okOrderShop( List<GetShopOrderInfoUtil> data,int i){
        String url=Api.GET_SHOP_ABOUT_RECEIPT;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",data.get(i).getOrderId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("smdfnkgfgdisfdsfg", data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){
                                //刷新数据
                                if (cancelOrderInterface!=null){
                                    cancelOrderInterface.cancelOrder();
                                }
                            }
                        } catch (JSONException e) {
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }
    /**
     * 取消订单
     */
    private void cancelOrderShop( List<GetShopOrderInfoUtil> data,int i){
        String url=Api.GET_CANCEL_ORDER_INFO;
        Log.i("sdlkfsifsdmfsf",data.get(i).getOrderId());
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("order_id",data.get(i).getOrderId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("smdfnkgfgdisfdsfg", data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){
                                //刷新数据
                               if (cancelOrderInterface!=null){
                                   cancelOrderInterface.cancelOrder();
                               }
                            }
                        } catch (JSONException e) {
                        }

                    }


        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            ToastManager.showShortToast(context,"网络错误");
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
                            IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
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
                        ToastManager.showShortToast(context,"网络错误");
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
                PayTask alipay = new PayTask((Activity) context);
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
                        pay.setClass(context, AlipayCallbackActivity.class);
                        pay.putExtra("state","0");
                        pay.putExtra("payType","1");
                        context.startActivity(pay);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastManager.showShortToast(context, "支付失败"+ payResult);
                        Log.i("sdfjasflkdsjfdksf","支付失败"+ payResult);
                        //显示支付失败界面
                        Intent pay=new Intent();
                        pay.setClass(context,AlipayCallbackActivity.class);
                        pay.putExtra("state","1");
                        pay.putExtra("payType","1");
                       context. startActivity(pay);
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
                        ToastManager.showShortToast(context, "身份验证成功"+ authResult);
                        Log.i("sdfjasflkdsjfdksf","身份验证成功"+ authResult);
                    } else {
                        // 其他状态值则为授权失败
                        ToastManager.showShortToast(context, "身份验证失败"+ authResult);
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
