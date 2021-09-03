package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.RequestAfterSalesActivity;
import com.nyw.pets.activity.shop.SendShopEvaluationActivity;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 我的评伦商品列表
 * Created by Administrator on 2016/12/5.
 */

public class ShopEvaluateListAdapter extends RecyclerView.Adapter<ShopEvaluateListAdapter.ViewHolder> {
    private Context context;
    private List<MyShopOrderInfoUtil> data;
    private MyApplication myApplication;

    public ShopEvaluateListAdapter(Context context, List<MyShopOrderInfoUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public ShopEvaluateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_evaluate_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopEvaluateListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_spe_id.setText("规格："+data.get(i).getSpecifications());

        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getShopImg()).placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_shopImg);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择商品进行评价
                if (data.get(i).getOpenType().equals("1")){

                Intent after_sales=new Intent();
                after_sales.setClass(context, SendShopEvaluationActivity.class);
                after_sales.putExtra("orderId",data.get(i).getOrderId()+"");
                after_sales.putExtra("clickNumberItem",i+"");
                //商品数据
                after_sales.putExtra("shopList",(Serializable)data);
                context.startActivity(after_sales);
                ( (Activity)context).finish();
                }else if (data.get(i).getOpenType().equals("0")){
                    Intent after_sales=new Intent();
                    after_sales.setClass(context, RequestAfterSalesActivity.class);
                    after_sales.putExtra("orderId",data.get(i).getOrderId()+"");
                    after_sales.putExtra("clickNumberItem",i+"");
                    //商品数据
                    after_sales.putExtra("shopList",(Serializable)data);
                    context.startActivity(after_sales);
                    ( (Activity)context).finish();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_spe_id;
        private ImageView iv_shopImg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_spe_id=itemView.findViewById(R.id.tv_spe_id);
            iv_shopImg=itemView.findViewById(R.id.iv_shopImg);
        }

    }

}
