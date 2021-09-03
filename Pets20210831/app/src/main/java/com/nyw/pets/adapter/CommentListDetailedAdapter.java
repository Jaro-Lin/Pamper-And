package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.CommentListDetailedReplyUtil;
import com.nyw.pets.activity.util.CommentListDetailedUtil;
import com.nyw.pets.activity.util.DelCommentData;
import com.nyw.pets.activity.util.DynamicData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.DelCommentUpdateInterface;
import com.nyw.pets.interfaces.GetAdressInterface;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 评论中的评论item
 * Created by Administrator on 2016/12/5.
 */

public class CommentListDetailedAdapter extends RecyclerView.Adapter<CommentListDetailedAdapter.ViewHolder> {
    private Context context;
    private List<CommentListDetailedUtil> data;
    private GetAdressInterface getAdressInterface;

    private CommentListDetailedReplyAdapter commentListDetailedReplyAdapter;
    private List<CommentListDetailedReplyUtil> commentListDetailedReply=new ArrayList<>();
    public ClickItemData clickItemData;
    private MyApplication myApplication;
    String myGoods;
    //是否点赞，默认无点赞
    private boolean isThumbs=false;

    private DelCommentUpdateInterface delCommentUpdateInterface;

    public void setDelCommentUpdateInterface(DelCommentUpdateInterface delCommentUpdateInterface) {
        this.delCommentUpdateInterface = delCommentUpdateInterface;
    }



    public void setClickItemData(ClickItemData clickItemData) {
        this.clickItemData = clickItemData;
    }

    public interface  ClickItemData{
        void  clickItemData(int i,String name,String commentId );
    }



    public void setGetAdressInterface(GetAdressInterface getAdressInterface) {
        this.getAdressInterface = getAdressInterface;
    }

    public CommentListDetailedAdapter(Context context, List<CommentListDetailedUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public CommentListDetailedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment_list_detailed_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListDetailedAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_name.setText(data.get(i).getName());
        viewHolder.tv_msg.setText(data.get(i).getMsg());
        viewHolder.tv_number.setText(data.get(i).getNumber());

        if (TextUtils.isEmpty(data.get(i).getCommentName())){
            viewHolder. llt_comment.setVisibility(View.GONE);
        }else {
            viewHolder. llt_comment.setVisibility(View.VISIBLE);
            viewHolder.tv_commentUserName.setText("@"+data.get(i).getCommentName());
        }

        if (data.get(i).getIs_owner()==1){
            viewHolder.tv_delComment.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_delComment.setVisibility(View.GONE);
        }
        viewHolder.tv_delComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除评论
                delCommentary(viewHolder,i);
            }
        });

        //显示头像
        String avatar=  data.get(i).getUserImg();
        if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
            Glide.with(context).load(avatar)
                    .error(R.mipmap.user_app_default)
                    .placeholder(R.mipmap.user_app_default).into(viewHolder.riv_name_img);
        }else {
            Glide.with(context).load(myApplication.getImgFilePathUrl() + data.get(i).getUserImg()).
                    placeholder(R.mipmap.ic_logo)
                    .error(R.mipmap.ic_logo).into(viewHolder.riv_name_img);
        }

        if (data.get(i).isIf_good()==false){
            viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
            isThumbs=false;
            myGoods="1";
        }else {
            viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
            isThumbs=true;
            myGoods="0";
        }


        viewHolder.tv_add_Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回复
                if (clickItemData!=null){
                    clickItemData.clickItemData(i,data.get(i).getName(),data.get(i).getUserId());
                }
            }
        });
        viewHolder.iv_thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞与取消点赞
                sendThumbsAnd(viewHolder,i, Api.GET_POST_ABOUT_GOOD);
            }
        });

        //回复的评论列表显示
        viewHolder.rcv_data.setLayoutManager(new LinearLayoutManager(context));
        commentListDetailedReply.clear();
        for (int j=0;j<3;j++){
            CommentListDetailedReplyUtil commentListDetailedReplyUtil=new CommentListDetailedReplyUtil();
            commentListDetailedReplyUtil.setId(j+"");
            commentListDetailedReplyUtil.setReplyUser("李小"+j);
            commentListDetailedReplyUtil.setReplyUserMsg("这是回复 的评论");
            commentListDetailedReply.add(commentListDetailedReplyUtil);
        }
        commentListDetailedReplyAdapter=new CommentListDetailedReplyAdapter(context,commentListDetailedReply);
        viewHolder.rcv_data.setAdapter(commentListDetailedReplyAdapter);


    }

    /**
     * 删除评论
     * @param viewHolder
     * @param i
     * @param
     */
    private void delCommentary(CommentListDetailedAdapter.ViewHolder viewHolder,int i) {
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
    private void sendThumbsAnd(CommentListDetailedAdapter.ViewHolder viewHolder,int i,String url) {
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
                            if (code== AppConfig.SUCCESS){

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
        private TextView tv_name,tv_commentUserName,tv_delComment;
        private TextView tv_msg,replyUser,tv_replyUserMsg,tv_add_Reply,tv_number;
        private ImageView iv_thumbs,riv_name_img;
        private LinearLayout llt_comment;

        //评论
        private RecyclerView rcv_data;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            tv_add_Reply=itemView.findViewById(R.id.tv_add_Reply);
            iv_thumbs=itemView.findViewById(R.id.iv_thumbs);
            tv_number=itemView.findViewById(R.id.tv_number);
            riv_name_img=itemView.findViewById(R.id.riv_name_img);
            tv_commentUserName=itemView.findViewById(R.id.tv_commentUserName);
            llt_comment=itemView.findViewById(R.id.llt_comment);
            tv_delComment=itemView.findViewById(R.id.tv_delComment);

            rcv_data=itemView.findViewById(R.id.rcv_data);
        }

    }

}
