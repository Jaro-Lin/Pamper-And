package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
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
import com.nyw.pets.activity.util.ShopPreferentialUtil;

import java.util.List;

/**
 * 限时优惠和热门榜
 * Created by Administrator on 2016/12/5.
 */

public class ShopPreferentialAdapter extends RecyclerView.Adapter<ShopPreferentialAdapter.ViewHolder> {
    private Context context;
    private List<ShopPreferentialUtil> data;

    public ShopPreferentialAdapter(Context context, List<ShopPreferentialUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ShopPreferentialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_preferential_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopPreferentialAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getPrice());
        viewHolder.tv_price.setText("￥ "+data.get(i).getPrice());
        viewHolder.tv_preferential.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        viewHolder.tv_preferential.getPaint().setAntiAlias(true);
        viewHolder.tv_preferential.setText("￥ "+data.get(i).getPreferentialPrice());

        if (data.get(i).getIsDiscount().equals("1")){
            viewHolder.tv_preferential.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_preferential.setVisibility(View.GONE);
        }

        Glide.with(context).load(data.get(i).getShopImg())
                .placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开商品详情
                if (!TextUtils.isEmpty(data.get(i).getShopID())) {
                    Intent intent = new Intent();
                    intent.setClass(context, ShopDetailsActivity.class);
                    intent.putExtra("id", data.get(i).getShopID());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_price;
        private TextView tv_preferential;
        private ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_preferential=itemView.findViewById(R.id.tv_preferential);
            iv_img=itemView.findViewById(R.id.iv_img);
        }

    }

}
