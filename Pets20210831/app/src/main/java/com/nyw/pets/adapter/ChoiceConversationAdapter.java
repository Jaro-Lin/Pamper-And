package com.nyw.pets.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetConversationUtil;
import com.nyw.pets.interfaces.SelectChoiceConversation;

import java.util.List;

/**
 * 选择话题
 * Created by Administrator on 2016/12/5.
 */

public class ChoiceConversationAdapter extends RecyclerView.Adapter<ChoiceConversationAdapter.ViewHolder> {
    private Context context;
    private List<GetConversationUtil> data;
    private SelectChoiceConversation selectChoiceConversation;

    public void setSelectChoiceConversation(SelectChoiceConversation selectChoiceConversation) {
        this.selectChoiceConversation = selectChoiceConversation;
    }

    public ChoiceConversationAdapter(Context context, List<GetConversationUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChoiceConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_conversation_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceConversationAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_readNumber.setText(data.get(i).getReadNumber());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item
                if (selectChoiceConversation!=null){
                    selectChoiceConversation.onClickGetDataId(data.get(i).getId(),data.get(i).getTitle());
                }

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
        private TextView tv_readNumber;
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_readNumber=itemView.findViewById(R.id.tv_readNumber);
        }

    }

}
