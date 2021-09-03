package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.nyw.pets.R;
import com.nyw.pets.interfaces.OnChangeImgNameListener;
import com.nyw.pets.util.GlideSimpleLoader;
import com.nyw.pets.util.UpdataPhotoUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendPhotoAdapter extends RecyclerView.Adapter<SendPhotoAdapter.ViewHolder> {

    private Context context;
    private List<UpdataPhotoUtil> data;
    //获取banner集合，点击banner显示图片
    public List<String> imageBanner;
    public OnChangeImgNameListener onChangeImgNameListener;
    //图片放大左右滑动显示
    private ImageWatcherHelper iwHelper;
    private    List<Uri> dataList = new ArrayList<>();


    public void setOnChangeImgNameListener(OnChangeImgNameListener onChangeImgNameListener) {
        this.onChangeImgNameListener = onChangeImgNameListener;
    }

    public SendPhotoAdapter(Context context, List data, List<Uri> dataList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.dataList=dataList;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        int number=data.size();
        if (number>=9){
            number=9;
        }
        return number;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int postion) {
        // TODO Auto-generated method stub
        //数据邦定
        File file = new File(data.get(postion).getImg());
        Glide.with(context).load(data.get(postion).getServiceUrl()+data.get(postion).getImg()).placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_photo);
        Log.i("sfsddfsf",data.get(postion).getImg());
        //初始化图片放大左右滑动查看
        iwHelper = ImageWatcherHelper.with((Activity) context, new GlideSimpleLoader());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                dataList.clear();
//                for (int i = 0; i < data.size(); i++) {
//                    dataList.add(ImageUtils.getUriFromPath(data.get(postion).getServiceUrl()+data.get(postion).getImg()));
//                }
                iwHelper.show(dataList, postion);
//                Intent banner = new Intent(context, PhotoDetailsPager.class);
//                banner.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,  (Serializable) imageBanner);
//                banner.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, postion);
//                banner.putExtra(PhotoDetailsPager.IS_DOWNLOAD, false);
//                context.startActivity(banner);
            }
        });
        viewHolder.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onChangeImgNameListener!=null){
                    onChangeImgNameListener.changePhoto(postion,data.get(postion).getImg());
                }
//                if (SendProjectActivity.isServiceImg==false) {
//                    data.remove(postion);
//                }
//                SendProjectActivity.fileList.remove(postion);
//                SendProjectActivity.imageBanner.remove(postion);
//                notifyDataSetChanged();
//                Toast.makeText(context,  data.size()+","+SendArticleActivity.fileList.size(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //邦定xml
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.update_photo_item,viewGroup, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_photo,img_close;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            img_close = (ImageView) itemView.findViewById(R.id.img_close);
        }

    }
}

