package com.nyw.pets.adapter;

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

import com.nyw.pets.R;
import com.nyw.pets.activity.user.DynamicDetailsActivity;
import com.nyw.pets.activity.user.MyDynamicDetailsActivity;
import com.nyw.pets.activity.util.CommunityListUtil;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;

/**
 * 进入社区列表
 * Created by Administrator on 2016/12/5.
 */

public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.ViewHolder> {
    private Context context;
    private List<CommunityListUtil> data;
    //是否点赞，默认无点赞
    private boolean isThumbs=false;
    //是否收藏，默认无收藏
    private boolean isCollection=false;


    public CommunityListAdapter(Context context, List<CommunityListUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommunityListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_community_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CommunityListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_name.setText(data.get(i).getName());
        viewHolder.tv_time.setText(new TimeUtil().timeStamp(data.get(i).getTime()));
        viewHolder.tv_msg.setText(data.get(i).getMsg());
        viewHolder.tv_comment.setText(data.get(i).getComment());
        viewHolder.tv_thumbs.setText(data.get(i).getThumbs());
        viewHolder.tv_collection.setText(data.get(i).getCollection());
        viewHolder.riv_name_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开用户动态详情界面
                Intent intent=new Intent();
                intent.setClass(context, MyDynamicDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏与取消收藏
                if (isCollection==true){
                    viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
                    isCollection=false;
                }else {
                    viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
                    isCollection=true;
                }
            }
        });
        viewHolder.iv_thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞与取消点赞
                if (isThumbs==true){
                    viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                    isThumbs=false;
                }else {
                    viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
                    isThumbs=true;
                }

            }
        });



//        // 没有数据就没有九宫格
//        if (data != null && data.size() > 0) {
//            ArrayList<NineGridItem> nineGridItemList = new ArrayList<>();
//            for (int j = 0;j<data.size();j++){
//                String thumbnailUrl = data.get(j).getImg();
//                String bigImageUrl = thumbnailUrl;
//                String videoUrl = data.get(j).getImg();
//                nineGridItemList.add(new NineGridItem(thumbnailUrl, bigImageUrl, videoUrl));
//            }
//            NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(nineGridItemList);
//            viewHolder.nineGrid.setAdapter(nineGridViewAdapter);
//        }

        // 为满足九宫格适配器数据要求，需要构造对应的List
        ArrayList<MyMedia> mediaList = data.get(i).getMediaList();
        // 没有数据就没有九宫格
        if (mediaList != null && mediaList.size() > 0) {
            ArrayList<NineGridItem> nineGridItemList = new ArrayList<>();
            for (MyMedia myMedia : mediaList) {
                String thumbnailUrl = myMedia.getImageUrl();
                String bigImageUrl = thumbnailUrl;
                String videoUrl = myMedia.getVideoUrl();
                nineGridItemList.add(new NineGridItem(thumbnailUrl, bigImageUrl, videoUrl));
            }
            NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(nineGridItemList);
            viewHolder.nineGrid.setAdapter(nineGridViewAdapter);
        }




        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, DynamicDetailsActivity.class);
//                intent.putExtra("id",data.get(i).getId());
//                intent.putExtra("projectName",data.get(i).getProjectName());
//                intent.putExtra("orderID",data.get(i).getOrderID());
//                intent.putExtra("message",data.get(i).getMessage());
//                intent.putExtra("time",data.get(i).getTime());
//                intent.putExtra("projectID",data.get(i).getProjectID());
//                intent.putExtra("price",data.get(i).getPrice());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView riv_name_img;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_msg,tv_comment,tv_thumbs,tv_collection;
        private ImageView iv_collection,iv_thumbs;
        //九宫格显示图片和视频
        private NineGridViewGroup nineGrid;

        public ViewHolder(View itemView) {
            super(itemView);
            riv_name_img=itemView.findViewById(R.id.riv_name_img);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_thumbs=itemView.findViewById(R.id.tv_thumbs);
            tv_collection=itemView.findViewById(R.id.tv_collection);
            iv_collection=itemView.findViewById(R.id.iv_collection);
            iv_thumbs=itemView.findViewById(R.id.iv_thumbs);
            nineGrid=itemView.findViewById(R.id.nineGrid);

        }

    }

}
