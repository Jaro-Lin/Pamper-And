package com.nyw.pets.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetReportDataUtil;
import com.nyw.pets.activity.util.GetReportTypeUtil;
import com.nyw.pets.adapter.ReportTypeAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.GetReportTypeInterface;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ReportDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态详情举报界面
 */
public class ReportDynamicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private Button btn_report;
    private ReportDialog reportDialog;
    private String post_id,comment_id,type_id=null;

    private RecyclerView rcv_data;
    private List<GetReportTypeUtil> reportTypeUtilList = new ArrayList<>();
    private ReportTypeAdapter reportTypeAdapter;
    private String reportTitle=null;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dynamic);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        btn_report=findViewById(R.id.btn_report);
        btn_report.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);
        tv_name=findViewById(R.id.tv_name);

        try {
            Bundle bundle = getIntent().getExtras();
            String name = bundle.getString("name");
            post_id=bundle.getString("post_id");
            tv_name.setText("  " + name);
        }catch (Exception e){}

        reportDialog=new ReportDialog(ReportDynamicActivity.this);
        reportDialog.setDialogCallback(new ReportDialog.Dialogcallback() {

            @Override
            public void ok(String string) {
                //确定
                finish();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_data.setLayoutManager(gridLayoutManager);
        reportTypeAdapter=new ReportTypeAdapter(ReportDynamicActivity.this,reportTypeUtilList);
        rcv_data.setAdapter(reportTypeAdapter);

        reportTypeAdapter.setGetReportTypeInterface(new GetReportTypeInterface() {
            @Override
            public void getReport(String id, String title) {
                //获取到选择的举报类型
                type_id=id;
                reportTitle=title;
            }
        });
        getReport();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;
            case R.id.btn_report:
                //提交举报

                if (TextUtils.isEmpty(type_id)){
                    ToastManager.showShortToast(ReportDynamicActivity.this,"请选择举报类型");
                    return;
                }
                sendReport();
                break;
        }
    }

    /**
     * 提交 举报类型
     */
    private void sendReport() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        OkGo.<String>post(Api.GET_POST_ABOUT_REPORT_POST).tag(this)
                .params("token",token)
                .params("post_id",post_id)
                .params("type_id",type_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                      String data=  response.body();
                        Log.i("sdjsifsfsf",data);
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            int code=jsonObject.getInt("code");
                            String  message=jsonObject.getString("message");
                            if (code== AppConfig.SUCCESS){
                                reportDialog.show();
                            }
                            ToastManager.showShortToast(ReportDynamicActivity.this,message);
                        } catch (JSONException e) {
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ReportDynamicActivity.this,"网络错误 ");
                    }
                });
    }

    /**
     * 获取举报类型
     */
    private void getReport() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token, Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);
        OkGo.<String>get(Api.GET_COMMENT_GET_REPORT_TYPE).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=  response.body();
                        Log.i("sdjsifsfsf",data);

                        Gson gson=new Gson();
                        GetReportDataUtil getReportDataUtil= gson.fromJson(data, GetReportDataUtil.class);

                        for (int i=0;i<getReportDataUtil.getData().size();i++){
                            GetReportTypeUtil getReportTypeUtil=new GetReportTypeUtil();
                            getReportTypeUtil.setId(getReportDataUtil.getData().get(i).getId()+"");
                            getReportTypeUtil.setTitle(getReportDataUtil.getData().get(i).getType()+"");
                            reportTypeUtilList.add(getReportTypeUtil);
                        }

                        reportTypeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ReportDynamicActivity.this,"网络错误 ");
                    }
                });
    }

}
