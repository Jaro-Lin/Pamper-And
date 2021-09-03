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
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.util.ShopListDataUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 商品列表item
 * Created by Administrator on 2016/12/5.
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
    private Context context;
    private List<ShopListDataUtil> data;

    public ShopListAdapter(Context context, List<ShopListDataUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_search_list_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getPrice());
        viewHolder.tv_price.setText(" ￥ "+data.get(i).getPrice());
        viewHolder.tv_preferential.setText(data.get(i).getPayNumber()+" 人付款");
        viewHolder.tv_title.setText(data.get(i).getTitle());

        Glide.with(context).load(data.get(i).getShopImg()).placeholder(R.mipmap.http_error)
                .error(R.mipmap.http_error).into(viewHolder.iv_img);

        viewHolder.iv_add_shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加到购物车
                addShopCartInfo(i);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开商品详情
                Intent intent=new Intent();
                intent.setClass(context, ShopDetailsActivity.class);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });

    }
    /**
     * 加入购物车
     */
    private void addShopCartInfo(int i){
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        String url= Api.ADD_SHOP_CART;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("shop_id",data.get(i).getShopId())
                .params("spe_id",data.get(i).getSpe_id())
                .params("number","1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("smdfiaddsfdsfg",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String msg=jsonObject.getString("message");
                            ToastManager.showShortToast(context,msg);
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
        private TextView tv_price,tv_title;
        private TextView tv_preferential;
        private ImageView iv_img,iv_add_shopCart;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_preferential=itemView.findViewById(R.id.tv_preferential);
            tv_title=itemView.findViewById(R.id.tv_title);
            iv_img=itemView.findViewById(R.id.iv_img);
            iv_add_shopCart=itemView.findViewById(R.id.iv_add_shopCart);
        }

    }

}
