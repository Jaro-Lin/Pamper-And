package com.nyw.pets.activity.shop;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;
import com.nyw.pets.adapter.ShopEvaluateListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评价列表
 */
public class ShopEvaluateListActivity extends BaseActivity implements View.OnClickListener {
    private String orderId;
    private List<MyShopOrderInfoUtil> shopList;
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private ShopEvaluateListAdapter shopEvaluateListAdapter;
    private List<MyShopOrderInfoUtil> shopEvaluateList=new ArrayList<>();
    private String openType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_evaluate_list);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);

        try{
          Bundle bundle=  getIntent().getExtras();
            orderId= bundle.getString("orderId",null);
            shopList= (List<MyShopOrderInfoUtil>) getIntent().getSerializableExtra("shopList");
            openType= bundle.getString("openType",null);

        }catch (Exception e){}

        rcv_data.setLayoutManager(new LinearLayoutManager(this));

        for (int i=0;i<shopList.size();i++){
            MyShopOrderInfoUtil myShopOrderInfoUtil=new MyShopOrderInfoUtil();
            myShopOrderInfoUtil.setShopImg(shopList.get(i).getShopImg());
            myShopOrderInfoUtil.setTitle(shopList.get(i).getTitle());
            myShopOrderInfoUtil.setSpecifications(shopList.get(i).getSpecifications());
            myShopOrderInfoUtil.setOrderId(orderId);
            myShopOrderInfoUtil.setId(shopList.get(i).getId());
            myShopOrderInfoUtil.setOpenType(openType);
            myShopOrderInfoUtil.setOrderNumber(shopList.get(i).getOrderId());
            shopEvaluateList.add(myShopOrderInfoUtil);
        }



        shopEvaluateListAdapter=new ShopEvaluateListAdapter(ShopEvaluateListActivity.this,shopEvaluateList);
        rcv_data.setAdapter(shopEvaluateListAdapter);


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;

        }
    }
}
