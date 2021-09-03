package com.nyw.pets.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.nyw.pets.R;
import com.nyw.pets.activity.BaseActivity;
import com.nyw.pets.config.ApiTest;
import com.nyw.pets.config.JsApi;
import com.nyw.pets.myinterface.JsCallback;

import wendu.dsbridge.CompletionHandler;
import wendu.dsbridge.DWebView;

/**
 * 用户注册协议
 */
public class UserAgreementActivity extends BaseActivity implements JsCallback, View.OnClickListener {
    private DWebView dWebView;
    private ImageView iv_hide;

    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        dWebView=findViewById(R.id.dwebview);

        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);

        dWebView.loadUrl(ApiTest.GET_USER_AGREEMENT);


        dWebView.addJavascriptObject(new JsApi(this), null);
        //打开调试模式
        DWebView.setWebContentsDebuggingEnabled(true);
//        //原生控件点击操作  发送数据给JS
//        btn_sendJS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //在Java中调用Javascript API,原生传递值给js 可传json 例如 new Object[]{new Gson().toJson(data)}
//                //第一个参数是约定注册的名称，与js接受处一致
//                //第二个参数是要传递的值
//                //第三个参数是接受js返回的回调，可用于js接受成功后，再去通知原生。
//                String msg="欢迎使用",name="Android";
//                dWebView.callHandler("nativeToJs",new Object[]{name,msg},new OnReturnValue<String>(){
//                    @Override
//                    public void onValue(String retValue) {
//                        Log.d("jsbridge","call succeed,return value is "+retValue);
//                    }
//
//                });
//            }
//        });



    }

    @Override
    public void testSyn(Object params) {

    }

    @Override
    public void testAsyn(Object msg, CompletionHandler<String> handler) {

    }

    @Override
    public void jsToNative(Object msg, CompletionHandler<String> handler) {

    }

    @Override
    public void getWenxin(Object msg, CompletionHandler<String> handler) {

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
