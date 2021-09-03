package com.nyw.pets.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.view.ClearEditText;

public class SetNameActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_ok;
    private ClearEditText ct_inputBody;
    private TextView tv_title;
    private  String body;
    private ImageView iv_hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);
        initView();
    }
    private void initView() {
        btn_ok=findViewById(R.id.btn_ok);
        ct_inputBody=findViewById(R.id.ct_inputBody);
        tv_title=findViewById(R.id.tv_title);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);


        Bundle bundle= getIntent().getExtras();
        final String title= bundle.getString("title");
        body= bundle.getString("body");
        ct_inputBody.setText(body);

        tv_title.setText(title);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= ct_inputBody.getText().toString().trim();
                Intent intent=new Intent();
                if (title.equals("设置呢称")) {
                    intent.putExtra(AppConfig.NAME, msg);
                }else if(title.equals("设置手机号码")){
                    intent.putExtra(AppConfig.PHONE, msg);
                }else if(title.equals("简介")){
                    intent.putExtra(AppConfig.OCCUPATION, msg);
                }else if(title.equals("设置邮箱")){
                    intent.putExtra(AppConfig.MAILBOX, msg);
                }
                setResult(AppConfig.SETUP_USER_INFO, intent);
                finish();
            }
        });
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
