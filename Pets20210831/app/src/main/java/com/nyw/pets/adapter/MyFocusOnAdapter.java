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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.user.MyDynamicDetailsActivity;
import com.nyw.pets.activity.util.GetMyFocusOnUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.CancelFocusOnDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 我的关注
 * Created by Administrator on 2016/12/5.
 */

public class MyFocusOnAdapter extends RecyclerView.Adapter<MyFocusOnAdapter.ViewHolder> {
    private Context context;
    private List<GetMyFocusOnUtil> data;
    //是否关注，默认无关注
    private boolean isFollow=false;

    private CancelFocusOnDialog cancelFocusOnDialog;
    private OnFocusSuccess onFocusSuccess;
    public interface  OnFocusSuccess{
        void onFocusSuccess();
    }

    public void setOnFocusSuccess(OnFocusSuccess onFocusSuccess) {
        this.onFocusSuccess = onFocusSuccess;
    }

    public MyFocusOnAdapter(Context context, List<GetMyFocusOnUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyFocusOnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_my_focus_on_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFocusOnAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_msg.setText(data.get(i).getMsg());

        cancelFocusOnDialog=new CancelFocusOnDialog(context);

        if (data.get(i).getIsFocusOn().equals("1")) {
            viewHolder.iv_focus.setImageResource(R.mipmap.follow_img);
        } else {
            viewHolder.iv_focus.setImageResource(R.mipmap.no_follow_img);
        }


            Glide.with(context).load(data.get(i).getUserImg()).placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(viewHolder.iv_img);



        cancelFocusOnDialog.setDialogCallback(new CancelFocusOnDialog.Dialogcallback() {

            @Override
            public void cancelFocusOn(String string) {
                //是否关注与取消关注
                sendFollow(i);
            }
        });


        viewHolder.iv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (cancelFocusOnDialog != null) {
                        cancelFocusOnDialog.show();
                    }


            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击查看详细的视频
                Intent intent=new Intent();
                intent.setClass(context, MyDynamicDetailsActivity.class);
                intent.putExtra("uid",data.get(i).getUid()+"");
                context.startActivity(intent);
            }
        });

    }
    /**
     * 关注用户或取消关注
     */
    private void sendFollow(int i) {

        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        String url= Api.GET_USER_ABOUT_USER_FOLLOW;
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("uid",data.get(i).getUid())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("fjdsjifnvnxirytggdd",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String msg= jsonObject.getString("message");
                            int code= jsonObject.getInt("code");
                            if (code== AppConfig.SUCCESS){
                                //关注
                                if (onFocusSuccess!=null){
                                    onFocusSuccess.onFocusSuccess();
                                }
                            }
                            ToastManager.showShortToast(context,msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误，获取数据失败");
                    }
                });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title,tv_msg;
        private TextView tv_price;
        private ImageView iv_focus,iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_focus=itemView.findViewById(R.id.iv_focus);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_msg=itemView.findViewById(R.id.tv_msg);

        }

    }

}
