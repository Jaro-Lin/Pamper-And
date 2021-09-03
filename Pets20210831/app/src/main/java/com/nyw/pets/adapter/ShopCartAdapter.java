package com.nyw.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.nyw.pets.activity.shop.ShopCartActivity;
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.shop.util.DelShopCartData;
import com.nyw.pets.activity.shop.util.DelShopCartInfoData;
import com.nyw.pets.activity.shop.util.UpdateShopCartInfoNumberData;
import com.nyw.pets.activity.util.MyShopCartUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.IsSelectAllInterface;
import com.nyw.pets.interfaces.UpdataShopCartInfoInterface;
import com.nyw.pets.util.ToastManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import io.reactivex.internal.schedulers.NewThreadWorker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的购物车
 * Created by Administrator on 2016/12/5.
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ViewHolder> {
    private Context context;
    private List<MyShopCartUtil> data;
    private IsSelectAllInterface isSelectAllInterface;
    private int number;
    private boolean isSelect = false;
    private ShopCartActivity activity;
    private UpdataShopCartInfoInterface updataShopCartInfoInterface;
    private MyApplication myApplication;

    public void setUpdataShopCartInfoInterface(UpdataShopCartInfoInterface updataShopCartInfoInterface) {
        this.updataShopCartInfoInterface = updataShopCartInfoInterface;
    }

    public void setIsSelectAllInterface(IsSelectAllInterface isSelectAllInterface) {
        this.isSelectAllInterface = isSelectAllInterface;
    }

    public ShopCartAdapter(ShopCartActivity activity,Context context, List<MyShopCartUtil>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.activity=activity;
        this.myApplication= (MyApplication) ((Activity)context).getApplication();
    }

    @NonNull
    @Override
    public ShopCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shop_cart_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCartAdapter.ViewHolder viewHolder, final int i) {
        Log.i("dsfsddflksfds",data.get(i).getTitle());
        viewHolder.tv_title.setText(data.get(i).getTitle());
        viewHolder.tv_specifications.setText("规格: "+data.get(i).getSpecifications());
        viewHolder.tv_price.setText("￥ "+data.get(i).getPrice());
        viewHolder.tv_number.setText(data.get(i).getShopNumber());

        Glide.with(context).load(myApplication.getImgFilePathUrl()+data.get(i).getShopImg())
                .error(R.mipmap.http_error).placeholder(R.mipmap.http_error).into(viewHolder.iv_shopImg);

        //如果全选了，所有商品为全选状态
        if (data.get(i).isSelect()==true) {
            viewHolder.cb_select.setChecked(true);
        } else {
            viewHolder.cb_select.setChecked(false);
        }

        if (isSelectAllInterface!=null){

            for (int j = 0; j < data.size(); j++) {
                if (!data.get(j).isSelect()) {
                    isSelect = false;
                    break;
                } else {
                    isSelect = true;
                }
            }
            if (isSelectAllInterface!=null) {
                isSelectAllInterface.getIsSelectAll(isSelect, Float.parseFloat(data.get(i).getShopNumber()));
            }
        }

        viewHolder.cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i1 = 0;i1<data.size();i1++) {
                    if (viewHolder.cb_select.isChecked() == true) {
                        data.get(i).setSelect(true);

                    } else {
                        data.get(i).setSelect(false);
                    }
                }
                notifyDataSetChanged();



            }
        });
        viewHolder.iv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加
                int number=Integer.parseInt(viewHolder.tv_number.getText().toString()) ;
                number=number+1;
                Log.i("sdfsjfisfjsfghhjrttt",number+"");
                changeShopCartNumberInfo(viewHolder,i,"add",number);
//                viewHolder.tv_number.setText(number+"");
            }
        });
        viewHolder.iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //减少
                int number=Integer.parseInt(viewHolder.tv_number.getText().toString()) ;
                if (number<=1){
                    number=1;
                }else {
                    number=number-1;
                }
                changeShopCartNumberInfo(viewHolder,i,"dec",number);

            }
        });



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看商品详情
                Intent intent=new Intent();
                intent.setClass(context, ShopDetailsActivity.class);
                intent.putExtra("id",data.get(i).getShopId());
                context.startActivity(intent);
            }
        });

        activity.setDelShopCartInfoItemInterface(new ShopCartActivity.DelShopCartInfoItemInterface() {
            @Override
            public void delShopCart(String shop_id, String spe_id) {
                delShopCartInfo( data,viewHolder,i,"",shop_id,spe_id);
            }
        });

    }

    /**
     * 删除商品信息
     * @param viewHolder
     * @param i
     * @param type
     * @param shop_id
     * @param spe_id
     */
    private void delShopCartInfo(List<MyShopCartUtil> shopData,ShopCartAdapter.ViewHolder viewHolder,int i,String type,String shop_id, String spe_id) {
        String url= Api.DEL_SHOP_CAR_DEL_SHOP;
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        String shop_spe="";
        DelShopCartInfoData delShopCartInfoData=new DelShopCartInfoData();
        List<DelShopCartData> delShopList=new ArrayList<>();

        int size=0;
        for (int j = 0;j<shopData.size();j++) {
            if (shopData.get(j).isSelect() == true) {
                size=size+1;
            }
        }

        String [] arrayShopId= new String[size];


        for (int j = 0;j<shopData.size();j++){
            if (shopData.get(j).isSelect()==true){
                //选中的商品
                DelShopCartData delShopCartData=new DelShopCartData();
                delShopCartData.setShop_id(data.get(j).getShopId());
                delShopCartData.setSpe_id(data.get(j).getSpecificationsId());
                delShopList.add(delShopCartData);
                arrayShopId[j]=data.get(j).getShopId();
            }
        }
        delShopCartInfoData.setId(arrayShopId);
        delShopCartInfoData.setToken(token);
        delShopCartInfoData.setShop_spe(delShopList);
        Gson gson=new Gson();
        shop_spe=  gson.toJson(delShopCartInfoData);
        Log.i("ajdfisfnsvcittwf",shop_spe);

        //这里是使用RequestBody 请求，把json商品数据传给后台
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), shop_spe);


        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("shop_id",data.get(i).getShopId())
                .params("spe_id",data.get(i).getSpecificationsId())
                .params("shop_spe",shop_spe)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String myData=response.body();
                        Log.i("svghdgfgdisfdsfg",myData);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {
                            JSONObject jsonObject=new JSONObject(myData);
                            String msg= jsonObject.getString("message");
                            int  code= jsonObject.getInt("code");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){
                                //删除成功
                                if (updataShopCartInfoInterface!=null){
                                    updataShopCartInfoInterface.UpdataShopCartInfo();
                                }
                            }


                        }catch (Exception e){}






                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }

    /**
     * 增加或减少购物车商品数量
     */
    private void changeShopCartNumberInfo(ShopCartAdapter.ViewHolder viewHolder,int i,String type,int number) {
        String url= Api.GET_SHOP_CAR_NUMBER;
        SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        Gson gson=new Gson();
        UpdateShopCartInfoNumberData updateShopCartInfoNumberData=new UpdateShopCartInfoNumberData();
        updateShopCartInfoNumberData.setId(data.get(i).getId());
        updateShopCartInfoNumberData.setNumber(number+"");
        updateShopCartInfoNumberData.setToken(token);
       String  shop_number=  gson.toJson(updateShopCartInfoNumberData);
        Log.i("ajdfisfnsvcittwf",shop_number);

        //这里是使用RequestBody 请求，把json商品数据传给后台
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), shop_number);

        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .upRequestBody(body)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String myData=response.body();
                        Log.i("svghdgfgdisfdsfg",myData);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {
                            JSONObject jsonObject=new JSONObject(myData);
                            String msg= jsonObject.getString("message");
                            int  code= jsonObject.getInt("code");
                            ToastManager.showShortToast(context,msg);
                            if (code==AppConfig.SUCCESS){
                                //修改成功
                                viewHolder.tv_number.setText(number+"");
                                //更新商品数量重新统计商品价格
                                data.get(i).setShopNumber(number+"");
                                if (isSelectAllInterface!=null) {
                                    isSelectAllInterface.getIsSelectAll(isSelect, Float.parseFloat(data.get(i).getShopNumber()));
                                }
                            }


                        }catch (Exception e){}






                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("dsfjsifsjfsifsft",response.message()+"   "+response.code());
                        ToastManager.showShortToast(context,"网络错误");
                    }
                });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_increase,iv_reduce,iv_shopImg;
        private TextView tv_number,tv_price,tv_specifications,tv_title;
        private CheckBox cb_select;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_increase = itemView.findViewById(R.id.iv_increase);
            tv_number=itemView.findViewById(R.id.tv_number);
            iv_reduce=itemView.findViewById(R.id.iv_reduce);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_specifications=itemView.findViewById(R.id.tv_specifications);
            tv_title=itemView.findViewById(R.id.tv_title);
            iv_shopImg=itemView.findViewById(R.id.iv_shopImg);
            cb_select=itemView.findViewById(R.id.cb_select);
        }

    }

}
