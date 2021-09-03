package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.ImageUtils;
import com.nyw.pets.activity.util.ShopDetailedImgUtil;
import com.nyw.pets.util.GlideSimpleLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片，商品详情显示
 * Created by Administrator on 2016/12/5.
 */

public class ShopDetailedImgAdapter extends RecyclerView.Adapter<ShopDetailedImgAdapter.ViewHolder> {
    private Context context;
    private List<ShopDetailedImgUtil> data;
    //图片放大左右滑动显示
    private ImageWatcherHelper iwHelper;
    private List<Uri> dataList = new ArrayList<>();

    public ShopDetailedImgAdapter(Context context, List<ShopDetailedImgUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ShopDetailedImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_detailed_img_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopDetailedImgAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getImg());
        Glide.with(context).load(data.get(i).getImg()).placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(viewHolder.iv_img);

        //初始化图片放大左右滑动查看
        iwHelper = ImageWatcherHelper.with((Activity) context, new GlideSimpleLoader());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看图片
                dataList.clear();
                for (int i = 0; i < data.size(); i++) {
                    dataList.add(ImageUtils.getUriFromPath(data.get(i).getImg()));
                }
                iwHelper.show(dataList, i);

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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView  iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

    }

}
