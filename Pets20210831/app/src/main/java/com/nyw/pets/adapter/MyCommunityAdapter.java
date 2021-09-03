package com.nyw.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.ConversationDetailsActivity;
import com.nyw.pets.activity.util.GetCommunityUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 社区搜索话题
 * Created by Administrator on 2016/12/5.
 */

public class MyCommunityAdapter extends RecyclerView.Adapter<MyCommunityAdapter.ViewHolder> {
    private Context context;
    private List<GetCommunityUtil> data;
    public  UpdataInfo updataInfo;

    public void setUpdataInfo(UpdataInfo updataInfo) {
        this.updataInfo = updataInfo;
    }

    public interface  UpdataInfo{
        void  updataInfo();
    }

    public MyCommunityAdapter(Context context, List<GetCommunityUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyCommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_community_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCommunityAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());


//        viewHolder.tv_readNumber.setText("进入社区");
//        viewHolder.tv_readNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //进入社区
//                Intent intent=new Intent();
//                intent.setClass(context, CommunityListActivity.class);
//                intent.putExtra("theme_id",data.get(i).getId());
//                intent.putExtra("title",data.get(i).getTitle());
//                context.startActivity(intent);
//            }
//        });
        if (data.get(i).getIs_follow().equals("1")){
            viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
        }else {
            viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
        }

        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注与取消关注
                sendFollow(i);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                //item  打开弹话题所属动态
                Intent intent=new Intent();
                intent.setClass(context, ConversationDetailsActivity.class);
                intent.putExtra("theme_id",data.get(i).getId()+"");
                intent.putExtra("title",data.get(i).getTitle());
                context.startActivity(intent);

            }
        });

    }

    /**
     * 关注与取消关注
     */
    private void sendFollow(int i) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String url= Api.GET_POST_THEME_THEME_FOLLOW;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("theme_id",data.get(i).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                       Log.i("sldfknsidfsnfsftyt",data);
                        JSONObject obj=null;
                        int code=2;
                        String message=null;
                        try {
                            obj=new JSONObject(data);
                            code=(obj.getInt("code"));
                            message=(obj.getString("message")+"");
                            ToastManager.showShortToast(context,message);
                            if (updataInfo!=null){
                                updataInfo.updataInfo();
                            }
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private ImageView iv_collection;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_collection=itemView.findViewById(R.id.iv_collection);

        }

    }

}
