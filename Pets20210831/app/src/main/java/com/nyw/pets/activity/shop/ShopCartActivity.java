package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.GetShopCartDataUtil;
import com.nyw.pets.activity.shop.util.GetShopOrderDataUtil;
import com.nyw.pets.activity.util.MyShopCartUtil;
import com.nyw.pets.adapter.ShopCartAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.IsSelectAllInterface;
import com.nyw.pets.interfaces.UpdataShopCartInfoInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.DelShopInfoDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 */
public class ShopCartActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_editAndDel,tv_price;
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private ShopCartAdapter shopCartAdapter;
    private List<MyShopCartUtil> shopCartList=new ArrayList<>();
    private Button btn_settlement,btn_del;
    private CheckBox cb_allSelect;
    private LinearLayout  llt_del,llt_ok;
    private DelShopInfoDialog delShopInfoDialog;
    //页数
    private int limit=15,page=1;
    //json数据包
    private GetShopCartDataUtil getShopCartDataUtil;
    //删除购物车商品
    public DelShopCartInfoItemInterface delShopCartInfoItemInterface;
    //生成订单的数据
    private List<GetShopOrderDataUtil> orderList=new ArrayList<>();
    //刷新
    private MeiTuanRefreshView refreshview;


    public void setDelShopCartInfoItemInterface(DelShopCartInfoItemInterface delShopCartInfoItemInterface) {
        this.delShopCartInfoItemInterface = delShopCartInfoItemInterface;
    }

    public interface DelShopCartInfoItemInterface {
        void delShopCart(String shop_id,String spe_id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        initView();
    }

    private void initView() {
        tv_editAndDel=findViewById(R.id.tv_editAndDel);
        tv_editAndDel.setOnClickListener(this);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);
        btn_settlement=findViewById(R.id.btn_settlement);
        btn_settlement.setOnClickListener(this);
        tv_price=findViewById(R.id.tv_price);
        cb_allSelect=findViewById(R.id.cb_allSelect);
        btn_del=findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        llt_del=findViewById(R.id.llt_del);
        llt_ok=findViewById(R.id.llt_ok);
        refreshview=findViewById(R.id.refreshview);
        delShopInfoDialog=new DelShopInfoDialog(this);
        delShopInfoDialog.setDialogCallback(new DelShopInfoDialog.Dialogcallback() {

            @Override
            public void del(String string) {
                //删除购物车商品
                if (delShopCartInfoItemInterface!=null){
                    delShopCartInfoItemInterface.delShopCart("","");
                }
            }
        });

        cb_allSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_allSelect.isChecked()==true){
                    //全选
                   for (int i=0;i<shopCartList.size();i++){
                       shopCartList.get(i).setSelect(true);
                   }
                    shopCartAdapter.notifyDataSetChanged();

                }else {
                    //取消全选
                    for (int i=0;i<shopCartList.size();i++){
                        shopCartList.get(i).setSelect(false);
                    }
                    shopCartAdapter.notifyDataSetChanged();

                }
            }
        });

        rcv_data.setLayoutManager(new LinearLayoutManager(this));

//        for ( int i=0;i<10;i++){
//            MyShopCartUtil myShopCartUtil=new MyShopCartUtil();
//            myShopCartUtil.setId(i+"");
//            myShopCartUtil.setTitle("狗狗食物超级好吃的零食营养的食物优惠"+i);
//            myShopCartUtil.setSpecifications("规格:"+123+i+" g");
//            myShopCartUtil.setPrice("￥"+i+156+"");
//            myShopCartUtil.setShopNumber(i+2+"");
//            shopCartList.add(myShopCartUtil);
//
//        }
        shopCartAdapter=new ShopCartAdapter(ShopCartActivity.this,ShopCartActivity.this,shopCartList);
        rcv_data.setAdapter(shopCartAdapter);

        shopCartAdapter.setIsSelectAllInterface(new IsSelectAllInterface() {
            @Override
            public void getIsSelectAll(boolean isSelectAll, float number) {
                //全选与取消全选
                if (isSelectAll==true){
                    //如果用户单个商品选择完成后，购物车所有商品被选中，这里全选 也要选中，则反
                    cb_allSelect.setChecked(true);
                }else{
                    cb_allSelect.setChecked(false);
                }
                //计算商品总价格
                float money = 0;
                orderList.clear();
                for (int i=0;i<shopCartList.size();i++){
                    if (shopCartList.get(i).isSelect()==true){
                        float myMoney =( Float.parseFloat(shopCartList.get(i).getPrice()))*( Float.parseFloat(shopCartList.get(i).getShopNumber()));
                        money=money+myMoney;
//                        ToastManager.showShortToast(ShopCartActivity.this,shopCartList.get(i).getShopNumber()+"");
                        GetShopOrderDataUtil getShopOrderDataUtil=new GetShopOrderDataUtil();
                        getShopOrderDataUtil.setId(shopCartList.get(i).getId());
                        getShopOrderDataUtil.setShopId(shopCartList.get(i).getShopId());
                        getShopOrderDataUtil.setTitle(shopCartList.get(i).getTitle());
                        getShopOrderDataUtil.setSpecificationsId(shopCartList.get(i).getSpecificationsId());
                        getShopOrderDataUtil.setSpecifications(shopCartList.get(i).getSpecifications());
                        getShopOrderDataUtil.setNumber(shopCartList.get(i).getShopNumber());
                        getShopOrderDataUtil.setPrice(shopCartList.get(i).getPrice());
                        getShopOrderDataUtil.setShopImg(shopCartList.get(i).getShopImg());

                        orderList.add(getShopOrderDataUtil);
                    }
                }
                //显示总价格
                tv_price.setText(money+"");

            }
        });
        shopCartAdapter.setUpdataShopCartInfoInterface(new UpdataShopCartInfoInterface() {
            @Override
            public void UpdataShopCartInfo() {
                //更新购物车商品信息
                shopCartList.clear();
                getShopCartInfo();
            }
        });


        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                shopCartList.clear();
                getShopCartInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getShopCartInfo();
            }
        });



    }
    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    refreshview.stopRefresh(true);
                    break;
                case 1:
                    refreshview.stopLoadMore(true);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        shopCartList.clear();
        getShopCartInfo();
    }

    /**
     * 获取购物车列表
     */
    private void getShopCartInfo() {
        String url=Api.GET_SHOP_CAR_LIST;
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);


        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("svghdgfgdisfdsfg",data);
//                        ToastManager.showShortToast(SearchShopActivity.this,data);
                        try {

                            Gson gson = new Gson();
                             getShopCartDataUtil=gson.fromJson(data, GetShopCartDataUtil.class);
                             for (int i=0;i<getShopCartDataUtil.getData().getData().size();i++){
                                 MyShopCartUtil myShopCartUtil=new MyShopCartUtil();
                                 myShopCartUtil.setShopId(getShopCartDataUtil.getData().getData().get(i).getId()+"");
                                 myShopCartUtil.setShopImg(getShopCartDataUtil.getData().getData().get(i).getIcon()+"");
                                 myShopCartUtil.setTitle(getShopCartDataUtil.getData().getData().get(i).getTitle()+"");
                                 myShopCartUtil.setSpecifications(getShopCartDataUtil.getData().getData().get(i).getSpe()+"");
                                 myShopCartUtil.setPrice(getShopCartDataUtil.getData().getData().get(i).getPrice()+"");
                                 myShopCartUtil.setShopNumber(getShopCartDataUtil.getData().getData().get(i).getNumber()+"");
                                 myShopCartUtil.setSpecificationsId(getShopCartDataUtil.getData().getData().get(i).getSpe_id()+"");
                                 myShopCartUtil.setId(getShopCartDataUtil.getData().getData().get(i).getId()+"");
                                 shopCartList.add(myShopCartUtil);
                             }
                            shopCartAdapter.notifyDataSetChanged();

                        }catch (Exception e){}






                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopCartActivity.this,"网络错误");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tv_editAndDel:
               String title= tv_editAndDel.getText().toString();
                if (title.equals("编辑")){
                    tv_editAndDel.setText("完成");
                    llt_ok.setVisibility(View.GONE);
                    llt_del.setVisibility(View.VISIBLE);
                }else {
                    tv_editAndDel.setText("编辑");
                    llt_ok.setVisibility(View.VISIBLE);
                    llt_del.setVisibility(View.GONE);
                }
            //编辑
            break;
            case R.id.iv_hide:
                //关闭
                finish();
                break;
            case R.id.btn_settlement:
                //结算
                if(orderList.size()>0) {
                    Intent intent = new Intent();
                    intent.setClass(ShopCartActivity.this, OkNextOrderActivity.class);
                    intent.putExtra("orderData", (Serializable) orderList);
                    startActivity(intent);
                }else {
                    ToastManager.showShortToast(ShopCartActivity.this,"请选择商品");
                }
                break;
            case R.id.btn_del:
                //删除
                if(orderList.size()>0) {
                    delShopInfoDialog.show();
                }else {
                    ToastManager.showShortToast(ShopCartActivity.this,"请选择商品");
                }
                break;
        }
    }

}
