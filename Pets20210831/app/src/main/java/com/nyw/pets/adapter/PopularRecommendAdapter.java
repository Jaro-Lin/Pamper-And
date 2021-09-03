package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.ConversationDetailsActivity;
import com.nyw.pets.fragment.util.PopularUtil;

import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 首页推荐的热门
 * Created by Administrator on 2016/12/5.
 */

public class PopularRecommendAdapter extends RecyclerView.Adapter<PopularRecommendAdapter.ViewHolder> {
    private Context context;
    private List<PopularUtil> data;


    public PopularRecommendAdapter(Context context, List<PopularUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PopularRecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recommend_popular_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRecommendAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_msg.setText(data.get(i).getMsg()+" 热度");
        viewHolder.avatar_grade.setTextAndColor((i+1)+"", context.getResources().getColor(R.color.red));


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, ConversationDetailsActivity.class);
                intent.putExtra("theme_id",data.get(i).getId()+"");
                intent.putExtra("title",data.get(i).getTitle());
                context.startActivity(intent);

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
        private TextView tv_msg;
        private AvatarImageView avatar_grade;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            avatar_grade=itemView.findViewById(R.id.avatar_grade);
        }

    }

}
