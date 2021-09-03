package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.activity.user.WalletDetailActivity;
import com.nyw.pets.activity.util.GetWalletUtil;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.GetPriceUtil;

import java.util.List;

/**
 * 我的明细
 * Created by Administrator on 2016/12/5.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Context context;
    private List<GetWalletUtil> data;

    public WalletAdapter(Context context, List<GetWalletUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wallet_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getProjectName());
        viewHolder.tv_title.setText(data.get(i).getProjectName());
        if (data.get(i).getIn_out()==AppConfig.SUCCESS){
            //支出
            viewHolder.tv_price.setText("- "+new GetPriceUtil().getPrice(data.get(i).getPrice())+" ￥");
        }else {
            //收入
            viewHolder.tv_price.setText("+ "+new GetPriceUtil().getPrice(data.get(i).getPrice())+" ￥");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //账单详情

                Intent intent=new Intent();
                intent.setClass(context, WalletDetailActivity.class);
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("projectName",data.get(i).getProjectName());
                intent.putExtra("orderID",data.get(i).getOrderID());
                intent.putExtra("message",data.get(i).getMessage());
                intent.putExtra("time",data.get(i).getTime());
                intent.putExtra("projectID",data.get(i).getProjectID());
                intent.putExtra("price",data.get(i).getPrice());
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

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
        }

    }

}
