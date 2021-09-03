package com.nyw.pets.adapter;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.course.CourseDetailsActivity;
import com.nyw.pets.activity.util.GetMyCollectionVideoUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 我的收藏视频
 * Created by Administrator on 2016/12/5.
 */

public class MyCollectionVideoAdapter extends RecyclerView.Adapter<MyCollectionVideoAdapter.ViewHolder> {
    private Context context;
    private List<GetMyCollectionVideoUtil> data;
    //是否收藏，默认无收藏
    private boolean isCollection=false;
    private MyApplication myApplication;

    public MyCollectionVideoAdapter(Context context, List<GetMyCollectionVideoUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public MyCollectionVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_collection_my_video_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCollectionVideoAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());

        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getVideoImg())
                .placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(viewHolder.iv_img);

        if (data.get(i).getIsCollection().equals("1")){
            viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
            isCollection=true;
        }else {
            viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
            isCollection=false;
        }


        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCollecton(i,viewHolder);
//                //收藏与取消收藏
//                if (isCollection==true){
//                    viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
//                    isCollection=false;
//                }else {
//                    viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
//                    isCollection=true;
//                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击查看详细的视频

                Intent intent=new Intent();
                intent.setClass(context, CourseDetailsActivity.class);
                intent.putExtra("type",data.get(i).getType_id());
                intent.putExtra("id",data.get(i).getVideo_id());
                context.startActivity(intent);
            }
        });

    }
    /**
     * 收藏 与取消收藏
     */
    private void sendCollecton(int i,MyCollectionVideoAdapter.ViewHolder viewHolder) {

        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        Log.i("sdfjfisdjsiffsfdg",data.get(i).getType_id()+"    "+data.get(i).getVideo_id());

        String url= Api.GET_COURSE_COLLECTION;

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type_id",data.get(i).getType_id())
                .params("course_id",data.get(i).getVideo_id())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdferrjsiopptudlfsf",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  msg=jsonObject.getString("message");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){
                                if (isCollection==true){
                                    viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
                                    isCollection=false;
                                }else {
                                    viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
                                    isCollection=true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (context!=null) {
                            ToastManager.showShortToast(context, "网络错误");
                        }
                    }
                });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_price;
        private ImageView iv_collection,iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_collection=itemView.findViewById(R.id.iv_collection);
            iv_img=itemView.findViewById(R.id.iv_img);
        }

    }

}
