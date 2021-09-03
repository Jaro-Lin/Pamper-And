package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.AfterSalesDetailedActivity;
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.util.GetAfterSalesUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.DelShopInfoDialog;
import com.nyw.pets.view.LogisticsInfoDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 我的售后
 * Created by Administrator on 2016/12/5.
 */

public class AfterSalesAdapter extends RecyclerView.Adapter<AfterSalesAdapter.ViewHolder> {
    private Context context;
    private List<GetAfterSalesUtil> data;
    private DelShopInfoDialog delShopInfoDialog;
    private MyApplication myApplication;
    private LogisticsInfoDialog logisticsInfoDialog;
    public UpdateAfterSalesInfo updateAfterSalesInfo;

    public void setUpdateAfterSalesInfo(UpdateAfterSalesInfo updateAfterSalesInfo) {
        this.updateAfterSalesInfo = updateAfterSalesInfo;
    }

    public interface  UpdateAfterSalesInfo{
        void updateAfterSalesInfo();
    }

    public AfterSalesAdapter(Context context, List<GetAfterSalesUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
        logisticsInfoDialog=new LogisticsInfoDialog(context);
    }

    @NonNull
    @Override
    public AfterSalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_after_sales_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AfterSalesAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getShopImg()).placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_shopImg);
        viewHolder.tv_specifications.setText("规格："+data.get(i).getSpecifications());

        //售后类型
        viewHolder.tv_stateType.setText(data.get(i).getStateType());
        //价格
        viewHolder.tv_price.setText("￥ "+data.get(i).getPrice());

        //申请状态
        if (data.get(i).getState().equals("0")){
            viewHolder.tv_state.setText("等待买家退货");
            viewHolder.btn_del.setVisibility(View.GONE);
        }else {
            viewHolder.tv_state.setText("已退款");
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        }



        delShopInfoDialog=new DelShopInfoDialog(context);

        viewHolder.llt_shopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入 商品详情
                Intent intent=new Intent();
                intent.setClass(context, ShopDetailsActivity.class);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //售后详细

//                Intent intent=new Intent();
//                intent.setClass(context, WalletDetailActivity.class);
//                intent.putExtra("id",data.get(i).getId());
//                intent.putExtra("projectName",data.get(i).getProjectName());
//                intent.putExtra("orderID",data.get(i).getOrderID());
//                intent.putExtra("message",data.get(i).getMessage());
//                intent.putExtra("time",data.get(i).getTime());
//                intent.putExtra("projectID",data.get(i).getProjectID());
//                intent.putExtra("price",data.get(i).getPrice());
//                context.startActivity(intent);
            }
        });

        viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                delShopInfoDialog.show();
                delShopInfoDialog.setDialogCallback(new DelShopInfoDialog.Dialogcallback() {
                    @Override
                    public void del(String order_id) {
                        delAfterSales(order_id);
                    }
                });
            }
        });

        viewHolder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看详情
                Intent read=new Intent();
                read.setClass(context, AfterSalesDetailedActivity.class);

                read.putExtra("id",data.get(i).getId());
                read.putExtra("shopImg",data.get(i).getShopImg());
                read.putExtra("title",data.get(i).getTitle());
                read.putExtra("spe",data.get(i).getSpecifications());
                read.putExtra("type",data.get(i).getStateType());
                read.putExtra("price",data.get(i).getPrice());
                read.putExtra("state",data.get(i).getState());
                read.putExtra("order_id",data.get(i).getOrder_id());

                context.startActivity(read);
            }
        });

        viewHolder.btn_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //填写物流单号
                logisticsInfoDialog.show();
                logisticsInfoDialog.setDialogCallback(new LogisticsInfoDialog.Dialogcallback() {
                    @Override
                    public void logistics(String logistics_id) {
                        //上传物流单号
                        writeAfterSales(data.get(i).getOrder_id(),logistics_id);
                    }
                });

            }
        });


    }

    /**
     * 填写物流单号
     */
    private void writeAfterSales(String order_id,String logistics_id){
        SharedPreferences getUser = context.getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token = getUser.getString(SaveAPPData.TOKEN, null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }

        OkGo.<String>post(Api.GET_SHOP_ORDER_AFTER_LOGISTICE_ORDER).tag(this)
                .params("token", token)
                .params("order_id", order_id)
                .params("logistics_order", logistics_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sdjfsidsrsrsrfsjfsf", data);
                        JSONObject jsonObject = null;
                        int code = 0;
                        String msg = null;
                        try {
                            jsonObject = new JSONObject(data);
                            code = jsonObject.getInt("code");
                            msg = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ToastManager.showShortToast(context,msg);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }

    /**4
     * 删除售后订单记录
     * @param order_id
     * @param
     */
    private void delAfterSales(String order_id){
        SharedPreferences getUser = context.getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token = getUser.getString(SaveAPPData.TOKEN, null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }

        OkGo.<String>post(Api.GET_SHOP_ORDER_AFTER_DEL).tag(this)
                .params("token", token)
                .params("order_id", order_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Log.i("sdjfsidsrsrsrfsjfsf", data);
                        JSONObject jsonObject = null;
                        int code = 0;
                        String msg = null;
                        try {
                            jsonObject = new JSONObject(data);
                            code = jsonObject.getInt("code");
                            msg = jsonObject.getString("message");
                            if (code==AppConfig.SUCCESS){
                                if (updateAfterSalesInfo!=null){
                                    updateAfterSalesInfo.updateAfterSalesInfo();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ToastManager.showShortToast(context,msg);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_state,tv_price,tv_stateType,tv_specifications;
        private Button btn_read,btn_del,btn_logistics;
        private ImageView iv_shopImg;
        private LinearLayout llt_shopInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_read =  itemView.findViewById(R.id.btn_read);
            btn_del=itemView.findViewById(R.id.btn_del);
            tv_state=itemView.findViewById(R.id.tv_state);
            btn_logistics=itemView.findViewById(R.id.btn_logistics);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_stateType=itemView.findViewById(R.id.tv_stateType);
            tv_specifications=itemView.findViewById(R.id.tv_specifications);
            tv_title=itemView.findViewById(R.id.tv_title);
            iv_shopImg=itemView.findViewById(R.id.iv_shopImg);
            llt_shopInfo=itemView.findViewById(R.id.llt_shopInfo);
        }

    }

}
