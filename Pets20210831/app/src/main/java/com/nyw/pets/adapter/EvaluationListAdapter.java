package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.CommentThumbsData;
import com.nyw.pets.activity.util.EvauationListDataUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 评价
 * Created by Administrator on 2016/12/5.
 */

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.ViewHolder> {
    private Context context;
    private List<EvauationListDataUtil> data;
    //是否点赞，默认无点赞
    private boolean isThumbs=false;
     private MyApplication application ;
     //点赞
    private int good=0;

    public EvaluationListAdapter(Context context, List<EvauationListDataUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        application= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public EvaluationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_evaluation_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_name.setText(data.get(i).getName());
        viewHolder.tv_time.setText(new TimeUtil().timeStampDate(data.get(i).getTime()));
        viewHolder.tv_msg.setText(data.get(i).getMsg());
        viewHolder.tv_thumbs.setText(data.get(i).getPraiseNumber());
        viewHolder.tv_comment.setText(data.get(i).getEvaluationNumber());
        viewHolder.tv_specifications.setText(data.get(i).getSpecifications());

        if (data.get(i).getIf_good()==false){
            //没有点赞
            viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
            isThumbs=false;

        }else {
            viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
            isThumbs=true;

        }

        viewHolder.iv_thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞与取消点赞
                //点赞与取消点赞
                sendThumbsAndcollection(viewHolder,i, Api.GET_COMMENT_GOOD);
//                if (isThumbs==true){
//                    viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
//                    isThumbs=false;
//                }else {
//                    viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
//                    isThumbs=true;
//                }
            }
        });
        String avatar=data.get(i).getUserImg();
        if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
            Glide.with(context).load(data.get(i).getUserImg())
                    .placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(viewHolder.riv_name_img);
        }else {
            Glide.with(context).load(application.getImgFilePathUrl() + data.get(i).getUserImg())
                    .placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(viewHolder.riv_name_img);

        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击评价item

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

    }
    /**
     * 点赞与取消点赞
     */
    private void sendThumbsAndcollection(EvaluationListAdapter.ViewHolder viewHolder,int i,String url) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        if (data.get(i).getIf_good()==true){
            //1为点赞 0为取消点赞
            //
            good=0;
        }else {
            good=1;
        }

        CommentThumbsData commentThumbsData=new CommentThumbsData();
        commentThumbsData.setToken(token);
        commentThumbsData.setModule_id(data.get(i).getShopId());
        commentThumbsData.setModule("shop");
        commentThumbsData.setGood(good+"");
        commentThumbsData.setComment_id(data.get(i).getId()+"");


        Gson gson=new Gson();
        String sendData = gson.toJson(commentThumbsData);
        Log.i("sdfjfisfjsdfs",sendData);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendData);

        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String myData=response.body();
                        Log.i("dsfjsjifdsjfsfsf",myData);
                        try {
                            JSONObject jsonObject=new JSONObject(myData);
                            String msg=jsonObject.getString("message");
                            int code=jsonObject.getInt("code");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){

                                    if ( data.get(i).getIf_good()==true) {
                                        data.get(i).setIf_good(false);
                                        int number= Integer.parseInt(data.get(i).getThumbs())-1;

                                        if (number<=0){
                                            number=0;
                                        }
                                        isThumbs=false;
                                        data.get(i).setThumbs(number+"");
                                        viewHolder.tv_thumbs.setText(data.get(i).getThumbs());
                                        viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                                    }else {
                                        data.get(i).setIf_good(true);
                                        int number= Integer.parseInt(data.get(i).getThumbs())+1;
                                        isThumbs=true;
                                        data.get(i).setThumbs(number+"");
                                        viewHolder.tv_thumbs.setText(data.get(i).getThumbs());
                                        viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);


                                    }
                                }

                        } catch (JSONException e) {
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
        private ImageView riv_name_img,iv_thumbs;
        private TextView tv_name;
        private TextView tv_time,tv_msg,tv_comment,tv_thumbs,tv_specifications;
        private NineGridViewGroup nineGrid;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            riv_name_img=itemView.findViewById(R.id.riv_name_img);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_thumbs=itemView.findViewById(R.id.tv_thumbs);
            iv_thumbs=itemView.findViewById(R.id.iv_thumbs);
            tv_specifications=itemView.findViewById(R.id.tv_specifications);
            nineGrid=itemView.findViewById(R.id.nineGrid);


        }

    }

}
