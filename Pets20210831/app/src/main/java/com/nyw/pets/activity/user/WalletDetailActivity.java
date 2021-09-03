package com.nyw.pets.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.util.GetPriceUtil;
import com.nyw.pets.util.TimeUtil;

/**
 * 账单明细
 */
public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private  String id,projectName,orderID,message,time,projectID,price;
    private TextView tv_money,tv_projectName,tv_orderID,tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        tv_money=findViewById(R.id.tv_money);
        tv_orderID=findViewById(R.id.tv_orderID);
        tv_projectName=findViewById(R.id.tv_projectName);
        tv_time=findViewById(R.id.tv_time);



        try {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getString("id");
            projectName = bundle.getString("projectName");
            tv_projectName.setText(projectName);
            orderID = bundle.getString("orderID");
            tv_orderID.setText("订单号： "+orderID);
            message = bundle.getString("message");
            time = bundle.getString("time");
            tv_time.setText(new TimeUtil().timeStamp(time));
            projectID = bundle.getString("projectID");
            price=bundle.getString("price");
            tv_money.setText("￥ "+new GetPriceUtil().getPrice(price));


        }catch (Exception e){}

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
        }
    }
}
