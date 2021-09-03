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
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.SearchShopActivity;
import com.nyw.pets.activity.user.WalletDetailActivity;
import com.nyw.pets.activity.util.GetWalletUtil;
import com.nyw.pets.activity.util.ShopTypeTitleDataUtil;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.GetPriceUtil;
import com.nyw.pets.view.RoundImageView;

import java.util.List;

/**
 * 商品分类
 * Created by Administrator on 2016/12/5.
 */

public class ShopTypeTitleAdapter extends RecyclerView.Adapter<ShopTypeTitleAdapter.ViewHolder> {
    private Context context;
    private List<ShopTypeTitleDataUtil> data;
    private MyApplication myApplication;

    public ShopTypeTitleAdapter(Context context, List<ShopTypeTitleDataUtil>  data,MyApplication myApplication) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.myApplication=myApplication;
    }

    @NonNull
    @Override
    public ShopTypeTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_type_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopTypeTitleAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());

        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getImg()).error(R.mipmap.http_error)
                .placeholder(R.mipmap.http_error).into(viewHolder.rv_img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入商品列表

                Intent intent=new Intent();
                intent.setClass(context, SearchShopActivity.class);
                intent.putExtra("shopType",data.get(i).getId());
                intent.putExtra("petsType",data.get(i).getPetsType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView rv_img;
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            rv_img = itemView.findViewById(R.id.rv_img);
            tv_title=itemView.findViewById(R.id.tv_title);
        }

    }

}
