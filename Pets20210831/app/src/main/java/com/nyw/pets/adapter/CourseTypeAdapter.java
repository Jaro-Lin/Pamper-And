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
import com.nyw.pets.activity.course.CourseDataListActivity;
import com.nyw.pets.activity.util.CourseTypeUtil;

import java.util.List;

/**
 * 我的课程分类
 * Created by Administrator on 2016/12/5.
 */

public class CourseTypeAdapter extends RecyclerView.Adapter<CourseTypeAdapter.ViewHolder> {
    private Context context;
    private List<CourseTypeUtil> data;

    public CourseTypeAdapter(Context context, List<CourseTypeUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CourseTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_type_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTypeAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_name.setText(data.get(i).getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item
                Intent intent=new Intent();
                intent.setClass(context, CourseDataListActivity.class);
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("title",data.get(i).getName());
                intent.putExtra("type",data.get(i).getType());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }

    }

}
