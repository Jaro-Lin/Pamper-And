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
import android.widget.LinearLayout;
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
import com.nyw.pets.activity.CommentListDetailedActivity;
import com.nyw.pets.activity.util.ComentDataListUtil;
import com.nyw.pets.activity.util.DelCommentData;
import com.nyw.pets.activity.util.DynamicData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.DelCommentUpdateInterface;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 评论列表显示
 * Created by Administrator on 2016/12/5.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private Context context;
    private List<ComentDataListUtil> data;
    //是否点赞，默认无点赞
    private boolean isThumbs=false;
    //是否收藏，默认无收藏
    private boolean isCollection=false;
    String myGoods;
    private MyApplication myApplication;
    private DelCommentUpdateInterface delCommentUpdateInterface;

    public void setDelCommentUpdateInterface(DelCommentUpdateInterface delCommentUpdateInterface) {
        this.delCommentUpdateInterface = delCommentUpdateInterface;
    }

    public CommentListAdapter(Context context, List<ComentDataListUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.ViewHolder viewHolder, final int i) {
//        Log.i("dsfsddflksfds",data.get(i).getProjectName());
        viewHolder.tv_name.setText(data.get(i).getTitle());
        viewHolder.tv_msg.setText(data.get(i).getMsg());
        viewHolder.tv_number.setText(data.get(i).getThumbs());

        viewHolder.tv_commentNumber.setText("共 "+data.get(i).getCommentNumber()+" 条回复");
        viewHolder.tv_time.setText(new TimeUtil().timeStamp(data.get(i).getTime()));
        viewHolder.tv_sendComment.setText("评论");

        if (data.get(i).getIs_owner()==1){
            viewHolder.tv_delDynamic.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_delDynamic.setVisibility(View.GONE);
        }

        viewHolder.tv_delDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除评论
                delCommentary(viewHolder,i);
            }
        });

        viewHolder.tv_sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入评论
                Intent intent=new Intent();
                intent.setClass(context, CommentListDetailedActivity.class);
                String avatar=  data.get(i).getImg();
                if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                    intent.putExtra("img",  data.get(i).getImg());
                }else {
                    intent.putExtra("img", myApplication.getImgFilePathUrl() + data.get(i).getImg());
                }
                intent.putExtra("time",new TimeUtil().timeStamp(data.get(i).getTime()));
                intent.putExtra("name",data.get(i).getTitle());
                intent.putExtra("msg",data.get(i).getMsg());
                intent.putExtra("thumbsNumber",data.get(i).getThumbs());
                intent.putExtra("if_good",data.get(i).isIf_good());
                intent.putExtra("post_id",data.get(i).getPost_id());
                intent.putExtra("comment_id",data.get(i).getComment_id());
                intent.putExtra("id",data.get(i).getId()+"");
                context.startActivity(intent);
            }
        });
//        Log.i("dsfskfjsfisfsf",myApplication.getImgFilePathUrl()+data.get(i).getImg());
        String avatar=  data.get(i).getImg();
        if (avatar.indexOf("http://thirdwx.qlogo.cn/")!=-1) {
            Glide.with(context).load(avatar)
                    .error(R.mipmap.user_app_default)
                    .placeholder(R.mipmap.user_app_default).into(viewHolder.riv_name_img);
        }else {
            Glide.with(context).load(myApplication.getImgFilePathUrl() + data.get(i).getImg()).placeholder(R.mipmap.http_error)
                    .error(R.mipmap.http_error).into(viewHolder.riv_name_img);
        }


        viewHolder.iv_thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞与取消点赞
                sendThumbsAnd(viewHolder,i,Api.GET_POST_ABOUT_GOOD);

            }
        });

        if (data.get(i).isIf_good()==false){
            viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
            isThumbs=false;
            myGoods="1";
        }else {
            viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
            isThumbs=true;
            myGoods="0";
        }

        viewHolder.llt_commentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //评论中回复评论
                Intent intent=new Intent();
                intent.setClass(context, CommentListDetailedActivity.class);
                String avatar=  data.get(i).getImg();
                if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {

                    intent.putExtra("img",  data.get(i).getImg());
                }else {
                    intent.putExtra("img", myApplication.getImgFilePathUrl() + data.get(i).getImg());
                }
                intent.putExtra("time",new TimeUtil().timeStamp(data.get(i).getTime()));
                intent.putExtra("name",data.get(i).getTitle());
                intent.putExtra("msg",data.get(i).getMsg());
                intent.putExtra("thumbsNumber",data.get(i).getThumbs());
                intent.putExtra("if_good",data.get(i).isIf_good());
                intent.putExtra("post_id",data.get(i).getPost_id());
                intent.putExtra("comment_id",data.get(i).getComment_id());
                intent.putExtra("id",data.get(i).getId()+"");
                context.startActivity(intent);
            }
        });


     }

    /**
     * 删除评论
     * @param viewHolder
     * @param i
     * @param
     */
    private void delCommentary(CommentListAdapter.ViewHolder viewHolder,int i) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        RequestBody body;
        String sendComment="";

        DelCommentData delCommentData=new DelCommentData();
        delCommentData.setToken(token);
        delCommentData.setComment_id(data.get(i).getComment_id());
        Gson gson = new Gson();
        sendComment = gson.toJson(delCommentData);
        Log.i("sdfjsifrthvsfsfg", sendComment);

        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendComment);

        String url=Api.DEL_COMMENT_DELETS;

        OkGo.<String>post(url).tag(this)
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
                                if (delCommentUpdateInterface!=null){
                                    delCommentUpdateInterface.delCommentUpdateInterface();
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
    /**
     * 点赞与取消点赞
     */
    private void sendThumbsAnd(CommentListAdapter.ViewHolder viewHolder,int i,String url) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        RequestBody body;
        String sendComment="";

        if (data.get(i).isIf_good()==false) {
            myGoods = "1";
        } else {
            myGoods = "0";
        }

        DynamicData dynamicData = new DynamicData();
        dynamicData.setToken(token);
        dynamicData.setModule_id(data.get(i).getPost_id());
        dynamicData.setModule("post");
        dynamicData.setGood(myGoods);
        dynamicData.setComment_id(data.get(i).getComment_id());

        Gson gson = new Gson();
        sendComment = gson.toJson(dynamicData);
        Log.i("sdfjsifrthvsfsfg", sendComment);

        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendComment);



        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("post_id",data.get(i).getPost_id())
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

                                if ( data.get(i).isIf_good()==true) {
                                    data.get(i).setIf_good(false);
                                    int number= Integer.parseInt(data.get(i).getThumbs())-1;

                                    if (number<=0){
                                        number=0;
                                    }
                                    isThumbs=false;
                                    data.get(i).setThumbs(number+"");
                                    viewHolder.tv_number.setText(data.get(i).getThumbs());
                                    viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                                }else {
                                    data.get(i).setIf_good(true);
                                    int number= Integer.parseInt(data.get(i).getThumbs())+1;
                                    isThumbs=true;
                                    data.get(i).setThumbs(number+"");
                                    viewHolder.tv_number.setText(data.get(i).getThumbs());
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
        private TextView tv_name,tv_delDynamic;
        private TextView tv_msg,tv_time,tv_number,tv_commentName,tv_commentNumber,tv_sendComment;
        private ImageView  iv_thumbs;
        private LinearLayout llt_commentReply;
        private RoundImageView riv_name_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            iv_thumbs=itemView.findViewById(R.id.iv_thumbs);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_number=itemView.findViewById(R.id.tv_number);
            llt_commentReply=itemView.findViewById(R.id.llt_commentReply);
            riv_name_img=itemView.findViewById(R.id.riv_name_img);
            tv_commentName=itemView.findViewById(R.id.tv_commentName);
            tv_commentNumber=itemView.findViewById(R.id.tv_commentNumber);
            tv_sendComment=itemView.findViewById(R.id.tv_sendComment);
            tv_delDynamic=itemView.findViewById(R.id.tv_delDynamic);




        }

    }

}
