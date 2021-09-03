package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.ExplanationActivity;
import com.nyw.pets.activity.GetSickTypeActivity;
import com.nyw.pets.activity.course.CourseDataListActivity;
import com.nyw.pets.activity.util.CourseTypeUtil;
import com.nyw.pets.activity.util.GetSickTypeTitleUtil;
import com.nyw.pets.config.Api;

import java.util.List;

/**
 * 生病类型选择
 * Created by Administrator on 2016/12/5.
 */

public class GetSickTypeTitleAdapter extends RecyclerView.Adapter<GetSickTypeTitleAdapter.ViewHolder> {
    private Context context;
    private List<GetSickTypeTitleUtil> data;

    public GetSickTypeTitleAdapter(Context context, List<GetSickTypeTitleUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public GetSickTypeTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sick_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GetSickTypeTitleAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.btn_name.setText(data.get(i).getTitle());

        viewHolder.btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item
                Intent intent=new Intent();
                intent.setClass(context, ExplanationActivity.class);
                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("title",data.get(i).getTitle());
                intent.putExtra("url", Api.GET_PETS_MALADY_SHOW_ID+data.get(i).getId());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_name;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_name = (Button) itemView.findViewById(R.id.btn_name);
        }

    }

}
