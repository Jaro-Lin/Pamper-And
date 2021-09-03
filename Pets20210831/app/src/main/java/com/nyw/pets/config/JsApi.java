package com.nyw.pets.config;

import android.webkit.JavascriptInterface;

import com.nyw.pets.myinterface.JsCallback;

import wendu.dsbridge.CompletionHandler;

public class JsApi {
    private JsCallback jsCallback;

    /**
     * js回调
     * @param callback
     */
    public JsApi(JsCallback callback) {
        this.jsCallback = callback;
    }


    /**
     * for synchronous invocation
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String testSyn(Object msg)  {
        jsCallback.testSyn(msg);
        return msg + "［syn call］";

    }


    /**
     * for asynchronous invocation
     * @param msg
     * @param handler
     */
    @JavascriptInterface
    public void testAsyn(Object msg, CompletionHandler<String> handler) {
        handler.complete(msg+"回调给js");
        jsCallback.testAsyn(msg,handler);
    }

    /**
     * 测试，jsToNative与js那里要一致，通过JavascriptInterface注解，方法名与注册名一致
     * @param msg  第一个参数是传递的值；
     * @param handler  第二个参数可回调信息给js
     */
    @JavascriptInterface
    public void jsToNative(Object msg, CompletionHandler<String> handler) {
        handler.complete(msg+"回调给js");
        jsCallback.jsToNative(msg,handler);
    }
    //微信登录
    @JavascriptInterface
    public void getJsWenxin(Object msg, CompletionHandler<String> handler) {
        jsCallback.getWenxin( msg,handler);
        handler.complete(msg+"回调给js");
    }
}
