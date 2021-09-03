package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.course.CourseDataListActivity;
import com.nyw.pets.activity.util.GetCurriculumTypeUtil;

import java.util.List;

/**
 * 课程类型选择
 */
public class CurriculumTypeAdapter extends RecyclerView.Adapter<CurriculumTypeAdapter.ViewHolder> {

    private Context context;
    private List<GetCurriculumTypeUtil> data;


    public CurriculumTypeAdapter(Context context, List<GetCurriculumTypeUtil> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        int number=data.size();
        return number;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int postion) {
        // TODO Auto-generated method stub
        //数据邦定
        viewHolder.btn_msg_type.setText(data.get(postion).getTypeName());

        viewHolder.btn_msg_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item筛选课程类型
                Intent intent=new Intent();
                intent.setClass(context, CourseDataListActivity.class);
                intent.putExtra("id",data.get(postion).getId());
                intent.putExtra("title",data.get(postion).getTypeName());
                intent.putExtra("type",data.get(postion).getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //邦定xml
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_curriculum_type_item,viewGroup, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_msg_type;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_msg_type = (Button) itemView.findViewById(R.id.btn_msg_type);
        }

    }
}

