package com.nyw.pets.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.ExplanationData;
import com.nyw.pets.activity.util.GetExplanationUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import wendu.dsbridge.DWebView;

/**
 * 说明 查看
 */
public class ExplanationActivity extends BaseActivity implements View.OnClickListener {

    private String msg,title,id;
    private TextView tv_msg,tv_title;
    private ImageView iv_hide;
    private DWebView dwebview;
    private String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);
        try{
           Bundle bundle= getIntent().getExtras();
            title=bundle.getString("title",null);
            msg=bundle.getString("msg",null);
            url=bundle.getString("url",null);
        }catch (Exception e){}

        try{
            Bundle bundle= getIntent().getExtras();
            id=bundle.getString("id",null);
        }catch (Exception e){}

        initView();
    }

    private void initView() {
        tv_msg=findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText(title);
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        dwebview=findViewById(R.id.dwebview);


        dwebview.getSettings().setJavaScriptEnabled(true);




        //添加滚动功能 ，在xml里要添加 android:scrollbars="vertical"
        tv_msg.setMovementMethod(ScrollingMovementMethod.getInstance());

//        if (!TextUtils.isEmpty(id)) {
//            getData();
//        }
        if (!TextUtils.isEmpty(url)) {
            dwebview.loadUrl(url);
        }

    }

    private void getData() {

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }

        ExplanationData explanationData=new ExplanationData();
        explanationData.setMalady_id(id);
        explanationData.setToken(token);
        Gson gson=new Gson();
        String recordPetsInitData=gson.toJson(explanationData);

        Log.i("jdfsifsnnvcufmfkv",recordPetsInitData);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordPetsInitData);

        OkGo.<  String>post(Api.GET_PETS_MALADY_DETAIL).tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                        Log.i("sfjdsifsjnfkgoittyghdf",data);
                        GetExplanationUtil getExplanationUtil= gson.fromJson(data,GetExplanationUtil.class);
                       tv_msg.setText(getExplanationUtil.getData().getData().getContent());

                        StringBuffer sb = new StringBuffer();

                        //添加html

                        sb.append("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'>");

                        sb.append("<meta charset='utf-8'  content='1'></head><body style='color: black'><p></p>");
//                        sb.append(getExplanationUtil.getData().getData().getContent());

                        sb.append("</div>\n" +
                                "                   <p><span style=\"font-family: '宋体'; font-size: 10.5pt; mso-spacerun: 'yes'\"><o:p></o:p></span><span style=\"font-family: '宋体'; color: rgb(39,39,39); font-size: 10.5pt; mso-spacerun: 'yes'\"><o:p>&nbsp; </o:p></span><span style=\"font-family: '宋体'; color: rgb(39,39,39); font-size: 10.5pt; mso-spacerun: 'yes'\"><o:p>&nbsp;</o:p></span></p >\n" +
                                "<p><strong>1.正品承诺：</strong>动品网出售的大部分商品已获得品牌商或总代授权，进货渠道正规，所出售的产品均为正品行货，承诺假一罚二。</p >\n" +
                                "<p>&nbsp;<strong><br>\n" +
                                "2.平民价格：</strong>动品网在产品定价上以让利顾客为主，更为合理的价格就能让顾客享受到专业体育器材的品质；再加上网站定期会推出&nbsp;厂家让利活动，所以不再议价。</p >\n" +
                                "<p><br>\n" +
                                "<strong>3.产品色差：</strong>所有产品均为实物拍摄，我们的照片尽可能的与实物颜色保持一致，货品图片颜色大小因拍摄或计算机屏幕设定产生差异会&nbsp;略有不同，以实际货品颜色大小为准。</p >\n" +
                                "<p>&nbsp;</p >\n" +
                                "<p><strong>4.收货提醒：</strong>动品网使用第三方快递公司发货。您在收到产品包裹时务必当着第三方快递送货员的面拆包验货，确保货品完好。如果发现货品压损，可以直接拒收，然后联系客服中心（<span style=\"color: rgb(64, 64, 64); font-family: arial; font-size: 15px; line-height: 22px; text-align: center;\">010-64663105或者010-56245509</span>）为您重新换发您订购的产品。如没有进行检查货品，事后货品如损坏，动品网将无法赔付。<br>\n" +
                                "&nbsp;</p >\n" +
                                "<p><strong>5.关于缺货：</strong>动品网所上架的商品在销售过程中可能出现短期缺货的情况，而没有及时下架，造成您的订单中产品缺货的，动品网会及时告知，您可以根据自己的情况进行退款，取消订单或者换货。</p >\n" +
                                "<p>&nbsp;<br>\n" +
                                "<strong>6.订单取消：</strong>所有订单在客服确认后三天没有付款的订单系统将会自动取消。在使用动品网券下单后，取消订单将导致动品网券失效，则无法返还。</p >\n" +
                                "<p>&nbsp;</p >\n" +
                                "<p>&nbsp;</p >                  </div>");

                        sb.append("</body></html>");
                        //< meta http-equiv="refresh"content="time" url="url" >
                        dwebview.getSettings().setDefaultTextEncodingName("utf-8") ;
                        dwebview.loadDataWithBaseURL(null,url,"text/html", "utf-8", null);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ExplanationActivity.this,"网络错误 ");
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
