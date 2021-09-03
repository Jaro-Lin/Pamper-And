package com.nyw.pets.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetShopBuyTypeUtil;
import com.nyw.pets.interfaces.GetShopSpecifications;

import java.util.List;

/**
 * 选择商品规格
 * Created by Administrator on 2016/12/5.
 */

public class ShopBuyTypeAdapter extends RecyclerView.Adapter<ShopBuyTypeAdapter.ViewHolder> {
    private Context context;
    private List<GetShopBuyTypeUtil> data;
    private int index = -1;//标记当前选择的选项
    private GetShopSpecifications getShopSpecifications;

    public void setGetShopSpecifications(GetShopSpecifications getShopSpecifications) {
        this.getShopSpecifications = getShopSpecifications;
    }

    public ShopBuyTypeAdapter(Context context, List<GetShopBuyTypeUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ShopBuyTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_buy_type_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopBuyTypeAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_msg.setText(data.get(i).getTitle());

        viewHolder.llt_msg_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=i;
                notifyDataSetChanged();
                if (getShopSpecifications!=null){
                    getShopSpecifications.getSpecifications(data.get(i).getId(),data.get(i).getPrice(),data.get(i).getStock(),i,data.get(i).getTitle());
                }

            }
        });
        if (index==i){
            viewHolder.llt_msg_bg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.get_shop_buy_btn_select_gb));
            viewHolder.tv_msg.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            viewHolder.llt_msg_bg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.get_shop_buy_btn_gb));
            viewHolder.tv_msg.setTextColor(context.getResources().getColor(R.color.black));
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //账单详情

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
        private TextView  tv_msg;
        private LinearLayout llt_msg_bg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            llt_msg_bg=itemView.findViewById(R.id.llt_msg_bg);
        }

    }

}
