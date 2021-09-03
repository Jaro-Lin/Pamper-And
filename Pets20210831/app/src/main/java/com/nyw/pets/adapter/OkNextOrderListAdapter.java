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
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.shop.util.GetShopOrderDataUtil;

import java.util.List;

/**
 * 订单结算页面
 * Created by Administrator on 2016/12/5.
 */

public class OkNextOrderListAdapter extends RecyclerView.Adapter<OkNextOrderListAdapter.ViewHolder> {
    private Context context;
    private List<GetShopOrderDataUtil> data;
    private MyApplication myApplication;

    public OkNextOrderListAdapter(Context context, List<GetShopOrderDataUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public OkNextOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_ok_next_order_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OkNextOrderListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_price.setText(""+data.get(i).getPrice());
        viewHolder.tv_productNumber.setText(""+data.get(i).getNumber());
        viewHolder.tv_specifications.setText("规格："+data.get(i).getSpecifications());

        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getShopImg())
                .error(R.mipmap.http_error)
                .placeholder(R.mipmap.http_error).into(viewHolder.iv_img);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //商品item  进入 商品详情页
                Intent intent=new Intent();
                intent.setClass(context, ShopDetailsActivity.class);
                intent.putExtra("id",data.get(i).getShopId());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_price,tv_specifications,tv_productNumber;
        private ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_specifications=itemView.findViewById(R.id.tv_specifications);
            tv_productNumber=itemView.findViewById(R.id.tv_productNumber);
        }

    }

}
