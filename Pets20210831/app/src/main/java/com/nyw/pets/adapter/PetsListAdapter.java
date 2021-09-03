package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nyw.pets.R;
import com.nyw.pets.activity.PetsDataSetupActivity;
import com.nyw.pets.activity.util.GetPetsListUitl;
import com.nyw.pets.view.RoundImageView;

import java.util.List;

/**
 * 我的宠物列表
 * Created by Administrator on 2016/12/5.
 */

public class PetsListAdapter extends RecyclerView.Adapter<PetsListAdapter.ViewHolder> {
    private Context context;
    private List<GetPetsListUitl> data;

    public PetsListAdapter(Context context, List<GetPetsListUitl>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PetsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_pets_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PetsListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_add.setText(data.get(i).getName());
        viewHolder.tv_breed.setText("品种："+data.get(i).getBreed());
        if (data.get(i).getSex().equals("1")){
            viewHolder.tv_sex.setText("性别："+" 男");
        }else {
            viewHolder.tv_sex.setText("性别："+" 女");
        }
        //宠物类型
        if (data.get(i).getType_id().equals("1")){
            //猫
            viewHolder.iv_pets_icon.setImageResource(R.mipmap.cathead);
        }else {
            //狗
            viewHolder.iv_pets_icon.setImageResource(R.mipmap.donghead);
        }

        viewHolder.tv_age.setText("年龄："+data.get(i).getAge());
        viewHolder.tv_weight.setText("体重："+data.get(i).getWeight());
        if (data.get(i).getSterilization().equals("1")){
            viewHolder.tv_sterilization.setText("绝育情况："+"  已绝育");
        }else {
            viewHolder.tv_sterilization.setText("绝育情况："+"  未绝育");
        }
        String bh=data.get(i).getBirth();
        if (!TextUtils.isEmpty(bh)){


        String year=bh.substring(0,4);
        String months=bh.substring(4,6);
        String shit=bh.substring(6,8);
        viewHolder.tv_birth.setText("出生日期："+year+"-"+months+"-"+shit);

        }



//        viewHolder.iv_img.setImageResource(data.get(i).getImg());
        Glide.with(context).load(data.get(i).getImg()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(viewHolder.rv_img);

        viewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                Intent intent=new Intent();
                intent.setClass(context, PetsDataSetupActivity.class);
                intent.putExtra("type","change");
                intent.putExtra("breed",data.get(i).getBreed());
                intent.putExtra("sex",data.get(i).getSex());
                intent.putExtra("age",data.get(i).getAge());
                intent.putExtra("weight",data.get(i).getWeight());
                intent.putExtra("sterilization",data.get(i).getSterilization());
                intent.putExtra("birth",data.get(i).getBirth());
                intent.putExtra("uid",data.get(i).getUid());
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("imgName",data.get(i).getImgName());

                //宠物类型
                intent.putExtra("type_id",data.get(i).getType_id());
                //宠物品种
                intent.putExtra("varieties_id",data.get(i).getVarieties_id());
                //宠物ID
                intent.putExtra("pid",data.get(i).getPid());

                intent.putExtra("name",data.get(i).getName());
                intent.putExtra("img",data.get(i).getImg());
                intent.putExtra("healthy",data.get(i).getHealthy());


                context.startActivity(intent);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //宠物列表item
                viewHolder.llt_detailed.setVisibility(View.VISIBLE);

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
        private TextView tv_add;
        private ImageView iv_edit,iv_pets_icon;
        private RoundImageView rv_img;
        private LinearLayout llt_detailed;
        private TextView tv_breed,tv_sex,tv_age,tv_weight,tv_sterilization,tv_birth;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_add = (TextView) itemView.findViewById(R.id.tv_add);
            iv_edit=itemView.findViewById(R.id.iv_edit);
            rv_img=itemView.findViewById(R.id.rv_img);
            llt_detailed=itemView.findViewById(R.id.llt_detailed);
            tv_breed=itemView.findViewById(R.id.tv_breed);
            tv_sex=itemView.findViewById(R.id.tv_sex);
            tv_age=itemView.findViewById(R.id.tv_age);
            tv_weight=itemView.findViewById(R.id.tv_weight);
            tv_sterilization=itemView.findViewById(R.id.tv_sterilization);
            tv_birth=itemView.findViewById(R.id.tv_birth);
            iv_pets_icon=itemView.findViewById(R.id.iv_pets_icon);

        }

    }

}
