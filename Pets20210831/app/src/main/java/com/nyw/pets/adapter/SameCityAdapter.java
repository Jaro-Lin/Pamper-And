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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.DynamicDetailsActivity;
import com.nyw.pets.activity.user.MyDynamicDetailsActivity;
import com.nyw.pets.activity.util.DynamicData;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.MyMedia;
import com.nyw.pets.fragment.util.SameCityUtil;
import com.nyw.pets.interfaces.ClickShareDataInterface;
import com.nyw.pets.util.TimeUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.RoundImageView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 首页同城的数据
 * Created by Administrator on 2016/12/5.
 */

public class SameCityAdapter extends RecyclerView.Adapter<SameCityAdapter.ViewHolder> {
    private Context context;
    private List<SameCityUtil> data;
    //是否点赞，默认无点赞
    private boolean isThumbs=false;
    //是否收藏，默认无收藏
    private boolean isCollection=false;
    //列表播放视频 标志
    public final   String  PLAY_TAG="PLAY_TAG";


    public ClickShareDataInterface clickShareDataInterface;

    public void setClickShareDataInterface(ClickShareDataInterface clickShareDataInterface) {
        this.clickShareDataInterface = clickShareDataInterface;
    }
    public SameCityAdapter(Context context, List<SameCityUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SameCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_same_city_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SameCityAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getName());
        viewHolder.tv_name.setText(data.get(i).getName());
        viewHolder.tv_time.setText(new TimeUtil().timeStamp(data.get(i).getTime()));
        viewHolder.tv_msg.setText(data.get(i).getMsg());
        viewHolder.tv_comment.setText(data.get(i).getComment());
        viewHolder.tv_thumbs.setText(data.get(i).getThumbs());
        viewHolder.tv_collection.setText(data.get(i).getCollection());
        String avatar=  data.get(i).getImg();
        if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
            Glide.with(context).load(avatar)
                    .error(R.mipmap.user_app_default)
                    .placeholder(R.mipmap.user_app_default).into(viewHolder.riv_name_img);
        }else {
            Glide.with(context).load(data.get(i).getServer() + data.get(i).getImg()).placeholder(R.mipmap.http_error)
                    .error(R.mipmap.http_error).into(viewHolder.riv_name_img);
        }

        viewHolder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
//                ToastManager.showShortToast(context,"点击分享adapter");
                if (clickShareDataInterface!=null){
                    clickShareDataInterface.clickShareDataListener(i,data.get(i).getMsg());
                }
            }
        });
        if (data.get(i).getIf_collection().equals("0")){
            //没有收藏
            viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);
            isCollection=false;
        }else {
            viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
            isCollection=true;

        }

        if (data.get(i).getIf_good().equals("0")){
            //没有点赞
            viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
            isThumbs=false;
        }else {
            viewHolder.iv_thumbs.setImageResource(R.mipmap.thumbs_my_img);
            isThumbs=true;
        }


        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //收藏与取消收藏
                sendCollection(viewHolder,i, Api.GET_POST_ABOUT_COLLECTION);
            }
        });
        viewHolder.iv_thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞与取消点赞
                sendThumbsAnd(viewHolder,i,Api.GET_POST_ABOUT_GOOD);

            }
        });

        // 为满足九宫格适配器数据要求，需要构造对应的List

        SameCityUtil recyclerViewItem =data.get(i);
        ArrayList<MyMedia> mediaList = recyclerViewItem.getMediaList();
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

        //视频显示
        if (data.get(i).isVideo()==true){
            viewHolder.gsyVideoPlayer.setVisibility(View.VISIBLE);
        }else {
            viewHolder.gsyVideoPlayer.setVisibility(View.GONE);
        }
        //图片显示
        if (data.get(i).isImg()==true){
            viewHolder.nineGrid.setVisibility(View.VISIBLE);
        }else {
            viewHolder.nineGrid.setVisibility(View.GONE);
        }

        if (data.get(i).getMediaList().size()>0) {
            if (data.get(i).getMediaList().get(0).getVideoUrl()!=null) {

                //视频播放器列表
                //String videoUrl="http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
                viewHolder.gsyVideoPlayer.setUpLazy(data.get(i).getMediaList().get(0).getVideoUrl(), true, null, null, "");
                //增加title
                viewHolder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
                //设置返回键
                viewHolder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
                //设置全屏按键功能
                viewHolder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.gsyVideoPlayer.startWindowFullscreen(context, false, true);
                    }
                });
                //防止错位设置
                viewHolder.gsyVideoPlayer.setPlayTag(PLAY_TAG);
                viewHolder.gsyVideoPlayer.setPlayPosition(i);
                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                viewHolder.gsyVideoPlayer.setAutoFullWithSize(false);
                //音频焦点冲突时是否释放
                viewHolder.gsyVideoPlayer.setReleaseWhenLossAudio(false);
                //全屏动画
                viewHolder.gsyVideoPlayer.setShowFullAnimation(true);
                //小屏时不触摸滑动
                viewHolder.gsyVideoPlayer.setIsTouchWiget(false);
            }
        }

        viewHolder.riv_name_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开用户动态详情界面
                Intent intent=new Intent();
                intent.setClass(context, MyDynamicDetailsActivity.class);
                intent.putExtra("uid",data.get(i).getUid()+"");
                context.startActivity(intent);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, DynamicDetailsActivity.class);
                intent.putExtra("post_id",data.get(i).getPost_id());
                String avatar=  data.get(i).getImg();
                if (avatar.indexOf(Api.GET_WEIXIN_IMG_URL)!=-1) {
                    intent.putExtra("img_user",  data.get(i).getImg());
                }else {
                    intent.putExtra("img_user", data.get(i).getServer() + data.get(i).getImg());
                }
                intent.putExtra("name",data.get(i).getName());
                intent.putExtra("time",data.get(i).getTime());
                intent.putExtra("msg",data.get(i).getMsg());
                intent.putExtra("img", (Serializable) data.get(i).getMediaList());
                intent.putExtra("isVideo",data.get(i).isVideo());
                intent.putExtra("isImg",data.get(i).isImg());
                intent.putExtra("good",data.get(i).getThumbs());
                intent.putExtra("comment",data.get(i).getComment());
                intent.putExtra("collection",data.get(i).getCollection());

                intent.putExtra("isCollection",data.get(i).getIf_collection());
                intent.putExtra("isGood",data.get(i).getIf_good());
                intent.putExtra("comment_id",data.get(i).getComment_id());

                context.startActivity(intent);
            }
        });




    }

    /**
     * 点赞与取消点赞
     */
    private void sendThumbsAnd(SameCityAdapter.ViewHolder viewHolder,int i,String url) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        RequestBody body;
        String sendComment="";
        String myGoods;
        if (data.get(i).getIf_good().equals("0")) {
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

                                if ( data.get(i).getIf_good().equals("1")) {
                                    data.get(i).setIf_good("0");
                                    int number= Integer.parseInt(data.get(i).getThumbs())-1;

                                    if (number<=0){
                                        number=0;
                                    }
                                    isThumbs=false;
                                    data.get(i).setThumbs(number+"");
                                    viewHolder.tv_thumbs.setText(data.get(i).getThumbs());
                                    viewHolder.iv_thumbs.setImageResource(R.mipmap.no_thumbs_my_img);
                                }else {
                                    data.get(i).setIf_good("1");
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

    /**
     * 收藏
     * @param viewHolder
     * @param i
     * @param url
     */
    private void sendCollection(SameCityAdapter.ViewHolder viewHolder ,int i,String url) {
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);



        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("post_id",data.get(i).getPost_id())
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

                                if (data.get(i).getIf_collection().equals("1")) {
                                    //不收藏
                                    data.get(i).setIf_collection("0");

                                    int number= Integer.parseInt(data.get(i).getCollection())-1;
                                    isCollection=false;
                                    if (number<=0){
                                        number=0;
                                    }
                                    data.get(i).setCollection(number+"");
                                    viewHolder.tv_collection.setText(data.get(i).getCollection());
                                    viewHolder.iv_collection.setImageResource(R.mipmap.no_collection_img);

                                }else {
                                    //收藏
                                    isCollection=true;
                                    data.get(i).setIf_collection("1");
                                    int number= Integer.parseInt(data.get(i).getCollection());
                                    data.get(i).setCollection(number+1+"");
                                    viewHolder.tv_collection.setText(data.get(i).getCollection());
                                    viewHolder.iv_collection.setImageResource(R.mipmap.collection_img);
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
        private RoundImageView riv_name_img;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_msg,tv_comment,tv_thumbs,tv_collection;
        private ImageView iv_collection,iv_thumbs;
        //九宫格显示图片和视频
        private NineGridViewGroup nineGrid;
        //视频播放器
        private StandardGSYVideoPlayer gsyVideoPlayer;
        private ImageView iv_share;

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
            gsyVideoPlayer=itemView.findViewById(R.id.detail_player);
            iv_share=itemView.findViewById(R.id.iv_share);

        }

    }

}
