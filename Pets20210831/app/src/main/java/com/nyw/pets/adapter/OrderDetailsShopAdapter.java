package com.nyw.pets.adapter;

import android.content.Context;
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
import com.nyw.pets.activity.shop.util.OrderDetailsShopInfoUtil;

import java.util.List;

/**
 * 订单详情的商品列表显示
 * Created by Administrator on 2016/12/5.
 */

public class OrderDetailsShopAdapter extends RecyclerView.Adapter<OrderDetailsShopAdapter.ViewHolder> {
    private Context context;
    private List<OrderDetailsShopInfoUtil> data;

    public OrderDetailsShopAdapter(Context context, List<OrderDetailsShopInfoUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public OrderDetailsShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_details_shop_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsShopAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_spe.setText("规格: "+data.get(i).getSpe());
        viewHolder.tv_order_id.setText("订单编号: "+data.get(i).getOrderId());

        Glide.with(context).load(data.get(i).getImg())
                .placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_img);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开商品详情

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_spe,tv_order_id;
        private ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_spe=itemView.findViewById(R.id.tv_spe);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_order_id=itemView.findViewById(R.id.tv_order_id);
        }

    }

}
