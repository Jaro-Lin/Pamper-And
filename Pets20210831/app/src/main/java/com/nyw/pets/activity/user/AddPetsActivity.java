package com.nyw.pets.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.PetsTypeActivity;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.activity.PetsDataSetupActivity;
import com.nyw.pets.activity.util.GetPetsDataListUtil;
import com.nyw.pets.activity.util.GetPetsVarietiesUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.GetPetsTypeDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加宠物
 */
public class AddPetsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide,iv_cat,iv_dog;
    private LinearLayout llt_dog,llt_cat,llt_type_name;
    private TextView tv_type_name;
    private GetPetsTypeDialog getPetsTypeDialog;
    private Button btn_next;
    private String petsType="2";//默认为狗
    private List<GetPetsDataListUtil> pestsList=new ArrayList<>();
    //宠物名字
    private String getPetsName,getPetsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        llt_dog=findViewById(R.id.llt_dog);
        llt_dog.setOnClickListener(this);
        llt_cat=findViewById(R.id.llt_cat);
        llt_cat.setOnClickListener(this);
        iv_dog=findViewById(R.id.iv_dog);
        iv_cat=findViewById(R.id.iv_cat);
        llt_type_name=findViewById(R.id.llt_type_name);
        llt_type_name.setOnClickListener(this);
        tv_type_name=findViewById(R.id.tv_type_name);
        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);


        getData();

    }

    private void getData() {
        pestsList.clear();
        OkGo.<String>post(Api.GET_PETS_VARIETIES).tag(this)
                .params("type_id",petsType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sfsjfisjfsifsivnvv",data);
                        Gson gson=new Gson();
                        GetPetsVarietiesUtil getPetsVarietiesUtil=gson.fromJson(data, GetPetsVarietiesUtil.class);
                        for (int i=0;i<getPetsVarietiesUtil.getData().size();i++){
                            GetPetsDataListUtil getPetsDataListUtil=new GetPetsDataListUtil();
                            getPetsDataListUtil.setPetsName(getPetsVarietiesUtil.getData().get(i).getVarieties_name());
                            getPetsDataListUtil.setId(getPetsVarietiesUtil.getData().get(i).getId()+"");
                            pestsList.add(getPetsDataListUtil);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(AddPetsActivity.this,"网络错误");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==AppConfig.SELECT_PETS_TYPE&&resultCode==AppConfig.SELECT_PETS_TYPE){
           Bundle bundle= data.getExtras();
            tv_type_name.setText(bundle.getString("name",null));
            int number=bundle.getInt("number");
            getPetsId=pestsList.get(number).getId();
            getPetsName=bundle.getString("name",null);
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.llt_dog:
                //选择狗狗
                llt_dog.setBackgroundResource(R.drawable.add_pets_type_bg_select);
                llt_cat.setBackgroundResource(R.drawable.add_pets_type_bg_no_select);
                iv_dog.setImageResource(R.mipmap.dog1);
                iv_cat.setImageResource(R.mipmap.cat_select_img);
                petsType="2";
                getData();
                break;
            case R.id.llt_cat:
                //选择猫
                llt_dog.setBackgroundResource(R.drawable.add_pets_type_bg_no_select);
                llt_cat.setBackgroundResource(R.drawable.add_pets_type_bg_select);
                iv_dog.setImageResource(R.mipmap.dog);
                iv_cat.setImageResource(R.mipmap.cat1);
                petsType="1";
                getData();
                break;
            case R.id.llt_type_name:
                //选择宠物类型
//                getPetsTypeDialog=new GetPetsTypeDialog(AddPetsActivity.this,pestsList);
//                getPetsTypeDialog.setDialogCallback(new GetPetsTypeDialog.Dialogcallback() {
//
//                    @Override
//                    public void ok(String data, String id) {
//                        tv_type_name.setText(data);
//                        getPetsId=id;
//                        getPetsName=data;
//
//                    }
//
//
//                });
//                getPetsTypeDialog.show();
                Intent intentAdd=new Intent();
                intentAdd.setClass(AddPetsActivity.this, PetsTypeActivity.class);
                intentAdd.putExtra("listData",(Serializable) pestsList);
                startActivityForResult(intentAdd,AppConfig.SELECT_PETS_TYPE);

                break;
            case R.id.btn_next:
                //下一步
                if (TextUtils.isEmpty(getPetsName)){
                    ToastManager.showShortToast(AddPetsActivity.this,"请选择宠物");
                    return;
                }
                Intent intent=new Intent();
                intent.setClass(AddPetsActivity.this, PetsDataSetupActivity.class);
                intent.putExtra("getPetsName",getPetsName);
                intent.putExtra("getPetsId",getPetsId);
                intent.putExtra("getPetsTypeId",petsType);
                intent.putExtra("breed",getPetsName);
                startActivity(intent);
                finish();
                break;

        }
    }
}
