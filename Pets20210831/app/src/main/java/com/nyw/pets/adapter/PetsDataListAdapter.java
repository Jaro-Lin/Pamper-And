package com.nyw.pets.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetPetsDataListUtil;

import java.util.List;

/**
 * 我的宠物列表显示
 * Created by Administrator on 2016/12/5.
 */

public class PetsDataListAdapter extends RecyclerView.Adapter<PetsDataListAdapter.ViewHolder> {
    private Context context;
    private List<GetPetsDataListUtil> data;
    private int index = -1;//标记当前选择的选项
    private boolean onBind;
    private SelectPetsInterface selectPetsInterface;

    public SelectPetsInterface getSelectPetsInterface() {
        return selectPetsInterface;
    }

    public void setSelectPetsInterface(SelectPetsInterface selectPetsInterface) {
        this.selectPetsInterface = selectPetsInterface;
    }

    public PetsDataListAdapter(Context context, List<GetPetsDataListUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PetsDataListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_get_pets_list_data_item,viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final PetsDataListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getPetsName());
        viewHolder.tv_title.setText(data.get(i).getPetsName());


        viewHolder.rb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    Toast.makeText(context,"你选择的选项是"+data.get(i),Toast.LENGTH_SHORT).show();
                    index = i;
                    if(!onBind) {
                        notifyDataSetChanged();
                    }


                }

            }
        });
        if(index==i){
            onBind = true;
            viewHolder.rb_select.setChecked(true);
            onBind = false;

        } else {
            onBind = true;
            viewHolder.rb_select.setChecked(false);
            onBind = false;
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item点击事件
                viewHolder.rb_select.setChecked(true);
                if (selectPetsInterface!=null){
                    selectPetsInterface.selectPetsItem(i);
                }

            }
        });
        viewHolder.rb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择宠物
                viewHolder.rb_select.setChecked(true);
                if (selectPetsInterface!=null){
                    selectPetsInterface.selectPetsItem(i);
                }
            }
        });

    }
    public  interface  SelectPetsInterface{
        void selectPetsItem(int i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private RadioButton rb_select;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rb_select=itemView.findViewById(R.id.rb_select);
        }

    }

}
