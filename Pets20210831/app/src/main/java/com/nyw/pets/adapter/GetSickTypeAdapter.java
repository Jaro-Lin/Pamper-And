package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.user.WalletDetailActivity;
import com.nyw.pets.activity.util.GetSickTypeTitleUtil;
import com.nyw.pets.activity.util.GetSickTypeUtil;
import com.nyw.pets.activity.util.GetWalletUtil;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.GetPriceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 生病类型
 * Created by Administrator on 2016/12/5.
 */

public class GetSickTypeAdapter extends RecyclerView.Adapter<GetSickTypeAdapter.ViewHolder> {
    private Context context;
    private List<GetSickTypeUtil> data;
    private GetSickTypeTitleAdapter getSickTypeTitleAdapter;
    private List<GetSickTypeTitleUtil> getSickTypeTitleList=new ArrayList<>();

    public GetSickTypeAdapter(Context context, List<GetSickTypeUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public GetSickTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_get_sick_type_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GetSickTypeAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        getSickTypeTitleList.clear();
        for ( int j=0;j<data.get(i).getData().size();j++){
            GetSickTypeTitleUtil getSickTypeTitleUtil=new GetSickTypeTitleUtil();
            getSickTypeTitleUtil.setTitle(data.get(i).getData().get(j).getTitle());
            getSickTypeTitleUtil.setId(data.get(i).getData().get(j).getId());
            getSickTypeTitleList.add(getSickTypeTitleUtil);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        viewHolder.rcv_data.setLayoutManager(gridLayoutManager);
        getSickTypeTitleAdapter=new GetSickTypeTitleAdapter(context,getSickTypeTitleList);
        viewHolder.rcv_data.setAdapter(getSickTypeTitleAdapter);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //点击进入 详情
//                Intent intent=new Intent();
//                intent.setClass(context, WalletDetailActivity.class);
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
        private RecyclerView  rcv_data;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rcv_data=itemView.findViewById(R.id.rcv_data);
        }

    }

}
