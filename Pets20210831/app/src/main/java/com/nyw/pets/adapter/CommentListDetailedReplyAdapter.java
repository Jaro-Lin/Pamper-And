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
import com.nyw.pets.activity.util.CommentListDetailedReplyUtil;
import com.nyw.pets.interfaces.GetAdressInterface;

import java.util.List;

/**
 * 评论中的评论item的回复评论
 * Created by Administrator on 2016/12/5.
 */

public class CommentListDetailedReplyAdapter extends RecyclerView.Adapter<CommentListDetailedReplyAdapter.ViewHolder> {
    private Context context;
    private List<CommentListDetailedReplyUtil> data;
    private GetAdressInterface getAdressInterface;



    public void setGetAdressInterface(GetAdressInterface getAdressInterface) {
        this.getAdressInterface = getAdressInterface;
    }

    public CommentListDetailedReplyAdapter(Context context, List<CommentListDetailedReplyUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentListDetailedReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment_list_detailed_reply_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListDetailedReplyAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getReplyUser());
        viewHolder.tv_replyUser.setText(data.get(i).getReplyUser());
        viewHolder.tv_replyUserMsg.setText(data.get(i).getReplyUserMsg());




    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_replyUser;
        private TextView tv_msg,tv_replyUserMsg,tv_add_Reply,tv_number;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_replyUser = (TextView) itemView.findViewById(R.id.tv_replyUser);
            tv_replyUserMsg=itemView.findViewById(R.id.tv_replyUserMsg);

        }

    }

}
