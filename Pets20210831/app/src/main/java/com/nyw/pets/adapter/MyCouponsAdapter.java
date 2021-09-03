package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetCouponsUtil;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.CancelFocusOnDialog;

import java.util.List;

/**
 * 优惠卷
 * Created by Administrator on 2016/12/5.
 */

public class MyCouponsAdapter extends RecyclerView.Adapter<MyCouponsAdapter.ViewHolder> {
    private Context context;
    private List<GetCouponsUtil> data;
    //是否关注，默认无关注
    private boolean isFollow=false;

    private CancelFocusOnDialog cancelFocusOnDialog;
    private MyApplication myApplication;

    public MyCouponsAdapter(Context context, List<GetCouponsUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_coupons_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCouponsAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getId());
        if (data.get(i).getPrice().equals("3.00")){
            viewHolder.iv_img.setImageResource(R.mipmap.coupon_img3);
        }else if (data.get(i).getPrice().equals("5.00")){
            viewHolder.iv_img.setImageResource(R.mipmap.coupon_img5);
        }else if (data.get(i).getPrice().equals("10.00")){
            viewHolder.iv_img.setImageResource(R.mipmap.coupon_img10);
        }else if (data.get(i).getPrice().equals("20.00")){
            viewHolder.iv_img.setImageResource(R.mipmap.coupon_img20);
        }






        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击使用优惠卷  ，没有启动商城，下面注释代码不启用
                ToastManager.showShortToast(context,"暂时未开放使用");
//               myApplication= (MyApplication) ((Activity) context).getApplication();
//               if (myApplication.isOrder==true){
//                   myApplication.setOrder(false);
//                   Intent intent=new Intent();
//                   intent.putExtra("couponsId",data.get(i).getId()+"");
//                   intent.putExtra("price",data.get(i).getPrice()+"");
//                   intent.putExtra("conditionPrice",data.get(i).getConditionPrice()+"");
//                   ((Activity) context).setResult(AppConfig.OPEN_TYPE_COUPON,intent);
//                   ((Activity) context).finish();
//
//               }else {
//                   myApplication.setOpenShop(true);
//                   ((Activity) context).finish();
//               }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img=itemView.findViewById(R.id.iv_img);

        }

    }

}
