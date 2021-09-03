package com.nyw.pets.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.view.ServiceUsDialog;

/**
 * 售后详情
 */
public class AfterSalesDetailedActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_hide,iv_service,iv_shopImg;
    //联系客服
    private ServiceUsDialog serviceUsDialog;
    private TextView tv_order_id,tv_state,tv_price,tv_stateType,tv_specifications
            ,tv_title,tv_logistics_id;
    private  String shopImg,title,spe,type,price,state,order_id,shopId;
    private MyApplication myApplication;
    private LinearLayout llt_logistics_id,llt_shopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales_detailed);
        myApplication= (MyApplication) getApplication();
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        iv_service=findViewById(R.id.iv_service);
        iv_service.setOnClickListener(this);
        tv_order_id=findViewById(R.id.tv_order_id);
        tv_state=findViewById(R.id.tv_state);
        tv_price=findViewById(R.id.tv_price);
        tv_stateType=findViewById(R.id.tv_stateType);
        tv_specifications=findViewById(R.id.tv_specifications);
        tv_title=findViewById(R.id.tv_title);
        iv_shopImg=findViewById(R.id.iv_shopImg);
        tv_logistics_id=findViewById(R.id.tv_logistics_id);
        llt_logistics_id=findViewById(R.id.llt_logistics_id);
        llt_shopInfo=findViewById(R.id.llt_shopInfo);
        llt_shopInfo.setOnClickListener(this);


        serviceUsDialog=new ServiceUsDialog(AfterSalesDetailedActivity.this);

        try{

           Bundle bundle= getIntent().getExtras();
            shopImg=bundle.getString("shopImg",null);
            title=bundle.getString("title",null);
            spe=bundle.getString("spe",null);
            type=bundle.getString("type",null);
            price=bundle.getString("price",null);
            state=bundle.getString("state",null);
            order_id=bundle.getString("order_id",null);
            shopId=bundle.getString("id",null);

            Glide.with(this).load(myApplication.getImgFilePathUrl()+shopImg).placeholder(R.mipmap.http_error)
                    .error(R.mipmap.http_error).into(iv_shopImg);
            tv_title.setText(title);
            tv_specifications.setText(spe);
            tv_stateType.setText(type);
            tv_price.setText("￥ "+price);
            if (type.equals("0")){
                tv_state.setText("已退款");
                llt_logistics_id.setVisibility(View.VISIBLE);
                tv_logistics_id.setText("");
            }else {
                tv_state.setText("等待买家退货");
                llt_logistics_id.setVisibility(View.GONE);
            }
            tv_order_id.setText(order_id);


        }catch (Exception e){}

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.iv_service:
                // 联系客服
                serviceUsDialog.show();
                break;
            case R.id.llt_shopInfo:
                //进入商品详情
                Intent intent=new Intent();
                intent.setClass(AfterSalesDetailedActivity.this, ShopDetailsActivity.class);
                intent.putExtra("id",shopId);
                startActivity(intent);
                break;

        }
    }
}
