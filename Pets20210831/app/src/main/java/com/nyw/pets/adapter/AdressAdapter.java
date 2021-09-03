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

import com.nyw.pets.R;
import com.nyw.pets.activity.shop.NewAdressActivity;
import com.nyw.pets.activity.util.MyAdressUtil;
import com.nyw.pets.interfaces.GetAdressInterface;

import java.util.List;

/**
 * 我的地址列表
 * Created by Administrator on 2016/12/5.
 */

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder> {
    private Context context;
    private List<MyAdressUtil> data;
    private GetAdressInterface getAdressInterface;

    public void setGetAdressInterface(GetAdressInterface getAdressInterface) {
        this.getAdressInterface = getAdressInterface;
    }

    public AdressAdapter(Context context, List<MyAdressUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AdressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_adress_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdressAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_title.setText(data.get(i).getName());
        viewHolder.tv_phone.setText(data.get(i).getPhone());
        viewHolder.tv_adress.setText(data.get(i).getAdress());

        if (data.get(i).isDefaultAdress()==true){
            viewHolder.iv_default.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_default.setVisibility(View.GONE);
        }
        viewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRess=new Intent();
                addRess.setClass(context, NewAdressActivity.class);
                addRess.putExtra("name",data.get(i).getName());
                addRess.putExtra("phone",data.get(i).getPhone());
                addRess.putExtra("id",data.get(i).getId());
                addRess.putExtra("adress",data.get(i).getAdress());
                addRess.putExtra("is_default",data.get(i).isDefaultAdress());

                addRess.putExtra("province",data.get(i).getProvince());
                addRess.putExtra("city",data.get(i).getCity());
                addRess.putExtra("area",data.get(i).getArea());
                addRess.putExtra("detailed",data.get(i).getDetailed());
                context.startActivity(addRess);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //我的地址
                if (getAdressInterface!=null){
                    getAdressInterface.getAdress(data.get(i).getAdress(),data.get(i).getId(),data.get(i).getName(),data.get(i).getPhone());
                }

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
        private TextView tv_title;
        private TextView tv_phone,tv_adress;
        private ImageView iv_default,iv_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_adress=itemView.findViewById(R.id.tv_adress);
            iv_default=itemView.findViewById(R.id.iv_default);
            iv_edit=itemView.findViewById(R.id.iv_edit);
        }

    }

}
