package com.nyw.pets.adapter;

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
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;

import java.util.List;

/**
 * 我的订单商品列表
 * Created by Administrator on 2016/12/5.
 */

public class OrderShopInfoAdapter extends RecyclerView.Adapter<OrderShopInfoAdapter.ViewHolder> {
    private Context context;
    private List<MyShopOrderInfoUtil> data;

    public OrderShopInfoAdapter(Context context, List<MyShopOrderInfoUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public OrderShopInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_shop_info_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderShopInfoAdapter.ViewHolder viewHolder, final int i) {

        Glide.with(context).load(data.get(i).getServer()+data.get(i).getShopImg())
                .placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_shopImg);
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_specifications.setText("规格："+data.get(i).getSpecifications()+"  x"+data.get(i).getShopNumber());
        viewHolder.tv_price.setText("￥ "+data.get(i).getPrice());

        viewHolder.tv_title.setText(data.get(i).getTitle());

        Log.i("sdfjsifsfd",data.size()+"  "+data.get(i).getTitle());

        //0是交易完成，1是待支付，2是待发货，3是待收货，4是待评价
        if (i==0) {
//            if (data.get(i).getOrderState().equals("0")) {
//                viewHolder.tv_state.setText("交易完成");
//            } else if (data.get(i).getOrderState().equals("1")) {
//                viewHolder.tv_state.setText("待支付");
//            } else if (data.get(i).getOrderState().equals("2")) {
//                viewHolder.tv_state.setText("待发货");
//            } else if (data.get(i).getOrderState().equals("3")) {
//                viewHolder.tv_state.setText("待收货");
//            } else if (data.get(i).getOrderState().equals("4")) {
//                viewHolder.tv_state.setText("待评价");
//            }

            //显示订单状态
            if (data.get(i).getIs_cancel().equals("0")){
                //未取消订单
                if (data.get(i).getIs_pay().equals("0")){
                    //未支付
                    viewHolder.tv_state.setText("待支付");

                }else {
                    //已经支付
                    if (data.get(i).getIs_send().equals("0")){
                        //未发货
                            viewHolder.tv_state.setText("待发货");
                    }else {
                        //已经发货
                            viewHolder.tv_state.setText("待收货");

                        if (data.get(i).getIs_put().equals("0")){
                            //未收货
                                viewHolder.tv_state.setText("待收货");

                        }else {
                            //已经收货
                                viewHolder.tv_state.setText("已收货");

                            if (data.get(i).getIs_comment().equals("0")){
                                //未评论

                                    viewHolder.tv_state.setText("未评价");

                            }else {
                                //已经评论
                                    viewHolder.tv_state.setText("已评价");
                            }

                        }

                    }

                }


            }else {
                //已经取消订单
                if (data.get(i).getIs_pay().equals("0")){
                    viewHolder.tv_state.setText("订单关闭");
                }
            }










            if (data.get(i).getIs_salelate().equals("0")){
                //未发起售后
            }else {
                //已经发起售后
            }
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item进入商品详情

                Intent intent=new Intent();
                intent.setClass(context, ShopDetailsActivity.class);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_price;
        private ImageView iv_shopImg;
        private TextView tv_specifications,tv_state,tv_shopNumber;


        public ViewHolder(View itemView) {
            super(itemView);

            iv_shopImg=itemView.findViewById(R.id.iv_shopImg);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_state=itemView.findViewById(R.id.tv_state);
            tv_specifications=itemView.findViewById(R.id.tv_specifications);

            tv_shopNumber=itemView.findViewById(R.id.tv_shopNumber);

        }

    }

}
