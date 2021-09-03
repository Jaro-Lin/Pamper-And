package com.nyw.pets.adapter;

import android.app.Activity;
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
import com.nyw.pets.activity.course.CourseDetailsActivity;
import com.nyw.pets.activity.util.GetCurriculumListUtil;

import java.util.List;

/**
 * 课程列表
 * Created by Administrator on 2016/12/5.
 */

public class CurriculumListAdapter extends RecyclerView.Adapter<CurriculumListAdapter.ViewHolder> {
    private Context context;
    private List<GetCurriculumListUtil> data;
    private MyApplication myApplication;

    public CurriculumListAdapter(Context context, List<GetCurriculumListUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public CurriculumListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_curriclulum_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurriculumListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_number.setText(data.get(i).getLearningNumber());

        //显示图片
        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getImg()).placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开课程详情
                Intent intent=new Intent();
                intent.setClass(context, CourseDetailsActivity.class);
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("type",data.get(i).getType());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_number;
        private ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_number=itemView.findViewById(R.id.tv_number);
            iv_img=itemView.findViewById(R.id.iv_img);
        }

    }

}
