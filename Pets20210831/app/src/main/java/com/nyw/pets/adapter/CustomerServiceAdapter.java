package com.nyw.pets.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.util.GetCustomerServiceUtil;

import java.util.List;

/**
 * 在线客服
 * Created by Administrator on 2016/12/5.
 */

public class CustomerServiceAdapter extends RecyclerView.Adapter<CustomerServiceAdapter.ViewHolder> {
    private Context context;
    private List<GetCustomerServiceUtil> data;

    public CustomerServiceAdapter(Context context, List<GetCustomerServiceUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomerServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_customer_service_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerServiceAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_phone.setText(data.get(i).getMsg());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //账单详情

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_phone=itemView.findViewById(R.id.tv_phone);
        }

    }

}
