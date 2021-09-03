package com.nyw.pets.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetSickTypeDataUtil;
import com.nyw.pets.activity.util.GetSickTypeUtil;
import com.nyw.pets.activity.util.SickDataInfo;
import com.nyw.pets.adapter.GetSickTypeAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 生病列表
 */
public class GetSickTypeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private GetSickTypeAdapter getSickTypeAdapter;
    private List<GetSickTypeUtil> getSickTypeList=new ArrayList<>();
    private List <SickDataInfo> stringMsgList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sick_type);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);

        getData();

//        for (int i = 0;i<3;i++) {
//            GetSickTypeUtil getSickTypeUtil=new GetSickTypeUtil();
//            getSickTypeUtil.setTitle("标题"+i);
//            stringMsg.add("感帽");
//            getSickTypeUtil.setData(stringMsg);
//            getSickTypeList.add(getSickTypeUtil);
//        }
        rcv_data.setLayoutManager(new LinearLayoutManager(this));
        getSickTypeAdapter=new GetSickTypeAdapter(GetSickTypeActivity.this,getSickTypeList);
        rcv_data.setAdapter(getSickTypeAdapter);

    }

    private void getData() {
        OkGo.<String>post(Api.GET_PETS_MALADY_ALL_INFO).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                      String data=  response.body();
                        Log.i("sfjsfisfnkmcckjfff",data);
                        Gson gson=new Gson();
                        GetSickTypeDataUtil getSickTypeDataUtil=  gson.fromJson(data, GetSickTypeDataUtil.class);

                        for (int i=0;i<getSickTypeDataUtil.getData().getDatalist().size();i++){
                            GetSickTypeUtil getSickTypeUtil=new GetSickTypeUtil();
                            getSickTypeUtil.setTitle(getSickTypeDataUtil.getData().getDatalist().get(i).getTitle());

                            for (int j=0;j<getSickTypeDataUtil.getData().getDatalist().get(i).getChild().size();j++) {
                                SickDataInfo sickDataInfo=new SickDataInfo();
                                sickDataInfo.setId(getSickTypeDataUtil.getData().getDatalist().get(i).getChild().get(j).getId()+"");
                                sickDataInfo.setTitle(getSickTypeDataUtil.getData().getDatalist().get(i).getChild().get(j).getTitle());
                                stringMsgList.add(sickDataInfo);

                            }
                            getSickTypeUtil.setData(stringMsgList);
                            getSickTypeList.add(getSickTypeUtil);
                            getSickTypeAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(GetSickTypeActivity.this,"网络错误");
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
